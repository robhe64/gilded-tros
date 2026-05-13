package com.gildedtros;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("Gilded Tros Test")
class GildedTrosTest {
    private final GildedTros app = new GildedTros(new Item[1]);
    private static final int DEFAULT_QUALITY = 40;
    private static final int ONE_QUALITY = 1;
    private static final int ZERO_QUALITY = 0;
    private static final int LEGENDARY_QUALITY = 80;
    private static final int MAXIMUM_QUALITY = 50;

    @BeforeEach
    void cleanupApp() {
        app.items = new Item[1];
    }

    @Nested
    @DisplayName("Normal Items")
    class NormalItems {
        @ParameterizedTest(name = "{0}")
        @MethodSource("normalItemQualityCases")
        @DisplayName("Given normal item, when updating quality, then quality is updated correctly")
        void normalItemsUpdatesQuality(
                final String scenario,
                final int sellIn,
                final int startingQuality,
                final int expectedQuality
        ) {
            // Given
            final Item item = insertItem(new Item("normal item", sellIn, startingQuality));

            // When
            app.updateQuality();

            // Then
            assertThat(item.quality).isEqualTo(expectedQuality);
        }

        private static Stream<Arguments> normalItemQualityCases() {
            return Stream.of(
                    Arguments.of("before sell date, quality decreases by one", 10, DEFAULT_QUALITY, DEFAULT_QUALITY - 1),
                    Arguments.of("after sell date, quality decreases by two", 0, DEFAULT_QUALITY, DEFAULT_QUALITY - 2),
                    Arguments.of("quality does not go below zero", 10, ZERO_QUALITY, ZERO_QUALITY),
                    Arguments.of("after sell date, quality still does not go below zero", 0, ONE_QUALITY, ZERO_QUALITY)
            );
        }
    }

    @Nested
    @DisplayName("Good Wine")
    class GoodWine {
        @ParameterizedTest(name = "{0}")
        @MethodSource("goodWineQualityCases")
        @DisplayName("Given Good Wine, when updating quality, then quality is updated correctly")
        void goodWineUpdatesQuality(
                final String scenario,
                final int sellIn,
                final int startingQuality,
                final int expectedQuality
        ) {
            // Given
            final Item item = insertItem(new Item("Good Wine", sellIn, startingQuality));

            // When
            app.updateQuality();

            // Then
            assertThat(item.quality).isEqualTo(expectedQuality);
        }

        private static Stream<Arguments> goodWineQualityCases() {
            return Stream.of(
                    Arguments.of("before sell date, quality increases by one", 10, DEFAULT_QUALITY, DEFAULT_QUALITY + 1),
                    Arguments.of("after sell date, quality increases by two", 0, DEFAULT_QUALITY, DEFAULT_QUALITY + 2),
                    Arguments.of("quality does not go above 50", 10, MAXIMUM_QUALITY, MAXIMUM_QUALITY),
                    Arguments.of("after sell date, quality still does not go above 50", 0, MAXIMUM_QUALITY, MAXIMUM_QUALITY),
                    Arguments.of("before sell date, quality increases but caps at 50", 0, MAXIMUM_QUALITY - 1, MAXIMUM_QUALITY)

            );
        }
    }

    @Nested
    @DisplayName("Backstage passes")
    class BackstagePasses {
        @ParameterizedTest(name = "{0} - {1}")
        @MethodSource("backstagePassQualityCases")
        @DisplayName("Given backstage pass, when updating quality, then quality is updated correctly")
        void updatesQuality(
                final String itemName,
                final String scenario,
                final int sellIn,
                final int startingQuality,
                final int expectedQuality
        ) {
            // Given
            final Item item = insertItem(new Item(itemName, sellIn, startingQuality));

            // When
            app.updateQuality();

            // Then
            assertThat(item.quality).isEqualTo(expectedQuality);
        }

        // 2 Names * 4 Scenarios = 8 tests
        private static Stream<Arguments> backstagePassQualityCases() {
            return backstagePassNames()
                    .flatMap(itemName -> backstagePassScenarios()
                            .map(scenario -> Arguments.of(
                                    itemName,
                                    scenario.description(),
                                    scenario.sellIn(),
                                    scenario.startingQuality(),
                                    scenario.expectedQuality()
                            )));
        }

        private static Stream<String> backstagePassNames() {
            return Stream.of(
                    "Backstage passes for Re:Factor",
                    "Backstage passes for HAXX"
            );
        }

        private static Stream<Scenario> backstagePassScenarios() {
            return Stream.of(
                    new Scenario("more than 10 days left, quality increases by one", 11, DEFAULT_QUALITY, DEFAULT_QUALITY + 1),
                    new Scenario("10 days or less left, quality increases by two", 10, DEFAULT_QUALITY, DEFAULT_QUALITY + 2),
                    new Scenario("5 days or less left, quality increases by three", 5, DEFAULT_QUALITY, DEFAULT_QUALITY + 3),
                    new Scenario("quality is capped at 50", 5, MAXIMUM_QUALITY - 1, MAXIMUM_QUALITY),
                    new Scenario("after conference, quality drops to zero", 0, DEFAULT_QUALITY, ZERO_QUALITY)
            );
        }
    }

    @Nested
    @DisplayName("Legendary Items")
    class LegendaryItems {
        @ParameterizedTest(name = "{0}")
        @MethodSource("legendaryItemQualityCases")
        @DisplayName("Given a legendary item, when updating quality, then quality is updated correctly")
        void legendaryItemsUpdatesQuality(
                final String scenario,
                final int sellIn,
                final int startingQuality,
                final int expectedQuality
        ) {
            // Given
            final Item item = insertItem(new Item("B-DAWG Keychain", sellIn, startingQuality));

            // When
            app.updateQuality();

            // Then
            assertThat(item.quality).isEqualTo(expectedQuality);
        }

        private static Stream<Arguments> legendaryItemQualityCases() {
            return Stream.of(
                    Arguments.of("before sell date, quality does not change", 10, LEGENDARY_QUALITY, LEGENDARY_QUALITY),
                    Arguments.of("after sell date, quality still does not change", -1, LEGENDARY_QUALITY, LEGENDARY_QUALITY),

                    // This seems a bit like coincidental behavior? Perhaps legendary items need their required constraint of always 80?
                    Arguments.of("before sell date, quality does not change even if below 80", 10, DEFAULT_QUALITY, DEFAULT_QUALITY),
                    Arguments.of("after sell date, quality does not change even if below 80", 10, DEFAULT_QUALITY, DEFAULT_QUALITY)
            );
        }
    }

    // The items not implemented yet
    @Nested
    @DisplayName("Smelly items")
    class SmellyItems {
        @ParameterizedTest(name = "{0} - {1}")
        @MethodSource("smellyItemQualityCases")
        @DisplayName("Given a smelly item, when updating quality, then quality is updated correctly")
        void updatesQuality(
                final String itemName,
                final String scenario,
                final int sellIn,
                final int startingQuality,
                final int expectedQuality
        ) {
            // Given
            final Item item = insertItem(new Item(itemName, sellIn, startingQuality));

            // When
            app.updateQuality();

            // Then
            assertThat(item.quality).isEqualTo(expectedQuality);
        }

        // 2 Names * 4 Scenarios = 8 tests
        private static Stream<Arguments> smellyItemQualityCases() {
            return smellyItemNames()
                    .flatMap(itemName -> smellyItemScenarios()
                            .map(scenario -> Arguments.of(
                                    itemName,
                                    scenario.description(),
                                    scenario.sellIn(),
                                    scenario.startingQuality(),
                                    scenario.expectedQuality()
                            )));
        }

        private static Stream<String> smellyItemNames() {
            return Stream.of(
                    "Duplicate Code",
                    "Long Methods",
                    "Ugly Variable Names"
            );
        }

        private static Stream<Scenario> smellyItemScenarios() {
            return Stream.of(
                    new Scenario("before sell date, quality decreases by two", 11, DEFAULT_QUALITY, DEFAULT_QUALITY - 2),
                    new Scenario("after sell date, quality decreases by four", 0, DEFAULT_QUALITY, DEFAULT_QUALITY - 4),
                    new Scenario("before sell date, quality does not drop below zero", 10, ONE_QUALITY, ZERO_QUALITY),
                    new Scenario("after sell date, quality does not drop below zero", 0, ONE_QUALITY, ZERO_QUALITY)

            );
        }
    }

    private record Scenario(
            String description,
            int sellIn,
            int startingQuality,
            int expectedQuality
    ) {
    }

    // Not very necessary, but it makes the tests a bit more fluent in my opinion.
    private Item insertItem(final Item item) {
        app.items[0] = item;
        return item;
    }
}
