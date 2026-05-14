package com.gildedtros.strategy.sell;

import com.gildedtros.Item;

public class LegendaryUpdateSellInStrategy implements UpdateSellInStrategy {
    @Override
    public void updateSellIn(Item item) {
        // No-op. sellIn of legendary items doesn't change because they aren't sold.
    }
}
