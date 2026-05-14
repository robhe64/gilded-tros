package com.gildedtros.strategy.quality;

import com.gildedtros.Item;
import com.gildedtros.strategy.UpdateItemStrategy;

public class CommonItemQualityUpdateStrategy implements UpdateItemStrategy {
    @Override
    public void updateQuality(Item item) {
        if (item.sellIn < 0) {
            item.quality -= QualityConstants.BASE_UNIT;
        } else {
            item.quality -= QualityConstants.BASE_UNIT * 2;
        }
    }
}
