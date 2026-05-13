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

    @BeforeEach
    void cleanupApp() {
        app.items = new Item[1];
    }

    @Nested
    @DisplayName("Default Items")
    class DefaultItems {
        @ParameterizedTest(name = "{0}")
        @MethodSource("defaultItemQualityCases")
        @DisplayName("Given default item, when updating quality, then quality is updated correctly")
        void updatesQuality(
                final String scenario,
                final int sellIn,
                final int startingQuality,
                final int expectedQuality
        ) {
            // Given
            final Item item = insertItem(new Item("foo", sellIn, startingQuality));

            // When
            app.updateQuality();

            // Then
            assertThat(item.quality).isEqualTo(expectedQuality);
        }

        private static Stream<Arguments> defaultItemQualityCases() {
            return Stream.of(
                    Arguments.of("before sell date, quality decreases by one", 10, DEFAULT_QUALITY, DEFAULT_QUALITY - 1),
                    Arguments.of("after sell date, quality decreases by two", 0, DEFAULT_QUALITY, DEFAULT_QUALITY - 2),
                    Arguments.of("quality does not go below zero", 10, ZERO_QUALITY, ZERO_QUALITY),
                    Arguments.of("after sell date, quality still does not go below zero", 0, ONE_QUALITY, ZERO_QUALITY)
            );
        }
    }

    // Not very necessary, but it makes the tests a bit more fluent in my opinion.
    private Item insertItem(final Item item) {
        app.items[0] = item;
        return item;
    }
}
