package com.gildedtros.strategy.quality;

import com.gildedtros.Item;
import com.gildedtros.strategy.UpdateItemStrategy;

import static com.gildedtros.strategy.quality.QualityConstants.BASE_UNIT;

public class CommonItemQualityUpdateStrategy implements UpdateItemStrategy {
    private int multiplier = 1;

    public CommonItemQualityUpdateStrategy() {
    }

    public CommonItemQualityUpdateStrategy(int multiplier) {
        this.multiplier = multiplier;
    }

    @Override
    public void updateQuality(Item item) {
        if (item.sellIn < 1) {
            item.quality -= BASE_UNIT * 2 * multiplier;
        } else {
            item.quality -= BASE_UNIT * multiplier;
        }
    }
}
