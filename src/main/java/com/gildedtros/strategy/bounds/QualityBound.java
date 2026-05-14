package com.gildedtros.strategy.bounds;

import com.gildedtros.Item;

public interface QualityBound {
    void clamp(Item item);
}
