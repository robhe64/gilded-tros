package com.gildedtros.strategy.quality;

import com.gildedtros.Item;
import com.gildedtros.strategy.UpdateItemStrategy;

public class LegendaryItemQualityUpdateStrategy implements UpdateItemStrategy {
    @Override
    public void updateQuality(Item item) {
        // No-op. Legendary items don't change in quality.
    }
}
