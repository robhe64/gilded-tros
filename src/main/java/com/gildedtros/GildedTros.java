package com.gildedtros;

import com.gildedtros.strategy.ItemStrategyFactory;

class GildedTros {
    Item[] items;

    public GildedTros(Item[] items) {
        this.items = items;
    }

    // Temporary new method, will replace old one in time, just for arranging the new code
    public void updateQualityNew() {
        for (final Item item : items) {
            // Should handle decrement sellIn, quality updates and bounds correctly
            ItemStrategyFactory.forItem(item).updateQuality(item);
        }
    }

    // Invoked each "day", decreasing sellIn of most things by 1
    public void updateQuality() {
        for (int i = 0; i < items.length; i++) {
            if (!items[i].name.equals("Good Wine")
                    && !items[i].name.equals("Backstage passes for Re:Factor")
                    && !items[i].name.equals("Backstage passes for HAXX"))
            {
                // Seems like the non-negative validation rule, but 0 also doesn't pass here, probably to not decrement
                // it below 0?
                if (items[i].quality > 0) {
                    if (!items[i].name.equals("B-DAWG Keychain")) {
                        /* Possible item groups here:
                            - Duplicate Code/Long Methods/Ugly Variable Names
                            - Defaults
                         */
                        items[i].quality = items[i].quality - 1;
                    }
                }
            } else {
                if (items[i].quality < 50) {
                    /* Possible item groups here:
                        - Good Wine
                        - Backstage passes
                    */
                    items[i].quality = items[i].quality + 1;

                    if (items[i].name.equals("Backstage passes for Re:Factor") || items[i].name.equals("Backstage passes for HAXX") ) {
                        /* Possible item groups here:
                            - Backstage passes
                        */
                        if (items[i].sellIn < 11) {
                            if (items[i].quality < 50) { // Don't increase above 50
                                items[i].quality = items[i].quality + 1;
                            }
                        }

                        // Double the quality gain of backstage passes (+2 instead of +1) if sellIn < 6
                        if (items[i].sellIn < 6) {
                            if (items[i].quality < 50) {
                                items[i].quality = items[i].quality + 1;
                            }
                        }
                    }
                }
            }

            // The B-DAWG Keychain isn't sold, so decrease sellIn for everything else
            if (!items[i].name.equals("B-DAWG Keychain")) {
                items[i].sellIn = items[i].sellIn - 1;
            }

            // If sell date is passed, quality degrades twice as fast
            if (items[i].sellIn < 0) {
                if (!items[i].name.equals("Good Wine")) {
                    if (!items[i].name.equals("Backstage passes for Re:Factor") && !items[i].name.equals("Backstage passes for HAXX")) {
                        if (items[i].quality > 0) {
                            if (!items[i].name.equals("B-DAWG Keychain")) {
                                /* Possible item groups here:
                                    - Duplicate Code/Long Methods/Ugly Variable Names
                                    - Defaults
                                 */
                                items[i].quality = items[i].quality - 1; // Decrement again (-2 instead of -1)
                            }
                        }
                    } else {
                        /* Possible item groups here:
                            - Backstage passes
                         */
                        items[i].quality = items[i].quality - items[i].quality; // worthless if sellIn < 0 (this is just 0)
                    }
                } else {
                    if (items[i].quality < 50) { // Another hard cap check, very decentralized in this code
                        /* Possible item groups here:
                            - Good Wine
                         */
                        items[i].quality = items[i].quality + 1;
                    }
                }
            }
        }
    }
}