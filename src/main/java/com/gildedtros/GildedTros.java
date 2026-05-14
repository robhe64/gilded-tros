package com.gildedtros;

import com.gildedtros.strategy.ItemStrategyFactory;

class GildedTros {
    Item[] items;

    public GildedTros(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (final Item item : items) {
            // Should handle decrement sellIn, quality updates and bounds correctly
            ItemStrategyFactory.forItem(item).updateQuality(item);
        }
    }
}