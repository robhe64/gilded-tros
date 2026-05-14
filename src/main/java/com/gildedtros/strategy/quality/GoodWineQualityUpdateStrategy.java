package com.gildedtros.strategy.quality;

import com.gildedtros.Item;
import com.gildedtros.strategy.UpdateItemStrategy;

import static com.gildedtros.strategy.quality.QualityConstants.BASE_UNIT;

public class GoodWineQualityUpdateStrategy implements UpdateItemStrategy {
    @Override
    public void updateQuality(Item item) {
        if (item.sellIn < 1) {
            item.quality += BASE_UNIT * 2;
        } else {
            item.quality += BASE_UNIT;
        }
    }
}
