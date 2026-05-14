package com.gildedtros.strategy.sell;

import com.gildedtros.Item;
import com.gildedtros.strategy.UpdateItemStrategy;

public class UpdateSellInDecorator implements UpdateItemStrategy {
    private final UpdateSellInStrategy updateSellInStrategy;
    private final UpdateItemStrategy updateItemStrategy;

    public UpdateSellInDecorator(UpdateSellInStrategy updateSellInStrategy, UpdateItemStrategy updateItemStrategy) {
        this.updateSellInStrategy = updateSellInStrategy;
        this.updateItemStrategy = updateItemStrategy;
    }

    @Override
    public void updateQuality(Item item) {
        updateItemStrategy.updateQuality(item);
        updateSellInStrategy.updateSellIn(item);
    }
}
