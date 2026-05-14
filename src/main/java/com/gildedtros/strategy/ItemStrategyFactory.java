package com.gildedtros.strategy;

import com.gildedtros.Item;
import com.gildedtros.strategy.bounds.CommonQualityBound;
import com.gildedtros.strategy.bounds.LegendaryQualityBound;
import com.gildedtros.strategy.bounds.QualityBoundDecorator;
import com.gildedtros.strategy.quality.BackstagePassQualityUpdateStrategy;
import com.gildedtros.strategy.quality.CommonItemQualityUpdateStrategy;
import com.gildedtros.strategy.quality.GoodWineQualityUpdateStrategy;
import com.gildedtros.strategy.quality.LegendaryItemQualityUpdateStrategy;
import com.gildedtros.strategy.sell.CommonUpdateSellInStrategy;
import com.gildedtros.strategy.sell.LegendaryUpdateSellInStrategy;
import com.gildedtros.strategy.sell.UpdateSellInDecorator;

import java.util.Map;
import java.util.Set;

public class ItemStrategyFactory {
    private ItemStrategyFactory() {
    }

    private static final Map<Set<String>, UpdateItemStrategy> strategyMap = Map.of(
            Set.of("B-DAWG Keychain"), legendary(),
            Set.of("Backstage passes for Re:Factor", "Backstage passes for HAXX"),
            common(new BackstagePassQualityUpdateStrategy()),
            Set.of("Good Wine"), common(new GoodWineQualityUpdateStrategy()),
            Set.of("Duplicate Code",
                    "Long Methods",
                    "Ugly Variable Names"), common(new CommonItemQualityUpdateStrategy(2))
    );

    public static UpdateItemStrategy forItem(final Item item) {
        for (final Map.Entry<Set<String>, UpdateItemStrategy> entry : strategyMap.entrySet()) {
            if (entry.getKey().contains(item.name)) {
                return entry.getValue();
            }
        }
        return common(new CommonItemQualityUpdateStrategy());
    }

    private static UpdateItemStrategy common(UpdateItemStrategy qualityStrategy) {
        return new UpdateSellInDecorator(
                new CommonUpdateSellInStrategy(),
                new QualityBoundDecorator(
                        new CommonQualityBound(), qualityStrategy
                )
        );
    }

    private static UpdateItemStrategy legendary() {
        return new UpdateSellInDecorator(
                new LegendaryUpdateSellInStrategy(),
                new QualityBoundDecorator(
                        new LegendaryQualityBound(), new LegendaryItemQualityUpdateStrategy()
                )
        );
    }
}
