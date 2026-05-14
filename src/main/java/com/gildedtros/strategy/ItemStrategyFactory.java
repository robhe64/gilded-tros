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

import java.util.List;

public class ItemStrategyFactory {
    private ItemStrategyFactory() {
    }

    private static final List<String> LEGENDARY_ITEMS = List.of(
            "B-DAWG Keychain"
    );

    private static final List<String> BACKSTAGE_PASSES = List.of(
            "Backstage passes for Re:Factor",
            "Backstage passes for HAXX"
    );

    private static final List<String> SMELLY_ITEMS = List.of(
            "Duplicate Code",
            "Long Methods",
            "Ugly Variable Names"
    );

    public static UpdateItemStrategy forItem(final Item item) {
        if (LEGENDARY_ITEMS.contains(item.name)) {
            return legendary();
        } else if (BACKSTAGE_PASSES.contains(item.name)) {
            return common(new BackstagePassQualityUpdateStrategy());
        } else if (item.name.equals("Good Wine")) {
            return common(new GoodWineQualityUpdateStrategy());
        } else {
            return common(new CommonItemQualityUpdateStrategy());
        }
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
