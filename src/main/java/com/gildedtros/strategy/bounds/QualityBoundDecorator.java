package com.gildedtros.strategy.bounds;

import com.gildedtros.Item;
import com.gildedtros.strategy.UpdateItemStrategy;

public class QualityBoundDecorator implements UpdateItemStrategy {
    private final QualityBound qualityBound;
    private final UpdateItemStrategy updateItemStrategy;

    public QualityBoundDecorator(QualityBound qualityBound, UpdateItemStrategy updateItemStrategy) {
        this.qualityBound = qualityBound;
        this.updateItemStrategy = updateItemStrategy;
    }

    @Override
    public void updateQuality(Item item) {
        updateItemStrategy.updateQuality(item);
        qualityBound.clamp(item);
    }
}
