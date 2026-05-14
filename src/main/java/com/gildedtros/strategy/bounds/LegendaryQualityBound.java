package com.gildedtros.strategy.bounds;

import com.gildedtros.Item;

public class LegendaryQualityBound implements QualityBound {
    @Override
    public void clamp(Item item) {
        item.quality = Math.clamp(item.quality, 0, 80);
    }
}
