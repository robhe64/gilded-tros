package com.gildedtros.strategy.quality;

import com.gildedtros.Item;
import com.gildedtros.strategy.UpdateItemStrategy;

import static com.gildedtros.strategy.quality.QualityConstants.BASE_UNIT;

public class BackstagePassQualityUpdateStrategy implements UpdateItemStrategy {
    @Override
    public void updateQuality(Item item) {
        if (item.sellIn <= 0) {
            item.quality = 0;
        } else if (item.sellIn < 6) {
            item.quality += BASE_UNIT * 3;
        } else if (item.sellIn < 11) {
            item.quality += BASE_UNIT * 2;
        } else {
            item.quality += BASE_UNIT;
        }
    }
}
