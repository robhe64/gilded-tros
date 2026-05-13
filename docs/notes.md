# Working notes

This file contains my working notes during the exploration of the codebase, its requirements and current behavior.

## Plain requirements

### Presumably already implemented

These features are presumably already implemented. Will need tests to verify behavior.

- All items have a SellIn value which denotes the number of days we have to sell the item
- All items have a Quality value which denotes how valuable the item is
- At the end of each day our system lowers both values for every item
- Once the sell by date has passed, Quality degrades twice as fast
- The Quality of an item is never negative
- "Good Wine" actually increases in Quality the older it gets
- The Quality of an item is never more than 50
- "B-DAWG Keychain", being a legendary item, never has to be sold or decreases in Quality
- "Backstage passes" for very interesting conferences increases in Quality as its SellIn value approaches;
  Quality increases by 2 when there are 10 days or less and by 3 when there are 5 days or less but
  Quality drops to 0 after the conference
- An item can never have its Quality increase above 50, however legendary items always have Quality 80.

### Not implemented yet

- Smelly items ("Duplicate Code", "Long Methods", "Ugly Variable Names") degrade in Quality twice as fast as normal
  items

### Stable API

> However, do not alter the Item class or Items property...

`Item` class cannot be changed. Items property seems to refer to the `items` property in `GildedTros`, so presumably
that class still needs to exist in some form as well.

> you can make the UpdateQuality method and Items property static if you like...

So the only thing that cannot change is the `Item` class? `GildedTros` can have some changes.

## General remarks

Most of the requirements (and complexity) of this program seem to be related to the quality property. The quality is
influenced mostly by the `sellIn` property and the name of the item. The `sellIn` property seems like just a factor
in the calculation, and it's the name that decides the calculation strategy. Some sort of strategy pattern mapped to the
name of the item perhaps?

The `GildedTros` code is predictably incomprehensible, so I'll probably need full branch coverage before doing any
changes
or additions to it.

The existing code might not map perfectly to the requirements that are already implemented, so in that case I'll assume
that the written requirements are correct and the code is incorrect.

There seems to be some validation logic on quality (can never be negative, never rise above 50 unless legendary), so a
Quality value object might be useful, but the Item class isn't supposed to change. So any attempt to introduce such a
value object will probably be half baked. Need to figure out where I put validation logic then, if I can't change the
domain class.

> "Good Wine" actually increases in Quality the older it gets"

Does this apply after the `sellIn` date? Probably not but should test.

## More dependencies?

If nullability becomes a concern (which I kind of doubt), jspecify and nullaway might be useful to add.
AssertJ for tests would be nice as well. 