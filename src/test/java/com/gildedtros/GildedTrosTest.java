package com.gildedtros;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedTrosTest {

    @Test
    void foo() {
        Item[] items = new Item[] { new Item("foo", 0, 0) };
        GildedTros app = new GildedTros(items);
        app.updateQuality();
        assertEquals("fixme", app.items[0].name);
    }

}
