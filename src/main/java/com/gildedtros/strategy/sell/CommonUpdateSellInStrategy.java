package com.gildedtros.strategy.sell;

import com.gildedtros.Item;

public class CommonUpdateSellInStrategy implements UpdateSellInStrategy {
    @Override
    public void updateSellIn(Item item) {
        item.sellIn -= 1;
    }
}
