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

There seems to be a "default" strategy as well, as not every name is covered by the requirements. The unimplemented feature
maps to multiple names as well. Maybe try to exactly match strings (allow multiple strings per strategy) and apply the default
if nothing matches?

The `GildedTros` code is predictably incomprehensible, so I'll probably need full branch coverage before doing any
changes or additions to it.

The existing code might not map perfectly to the requirements that are already implemented, so in that case I'll assume
that the written requirements are correct and the code is incorrect.

There seems to be some validation logic on quality (can never be negative, never rise above 50 unless legendary), so a
Quality value object might be useful, but the Item class isn't supposed to change. So any attempt to introduce such a
value object will probably be half baked. Need to figure out where I put validation logic then, if I can't change the
domain class.

Maybe the validation logic can be in the strategies. Common & Legendary abstract strategy will enforce hard caps, 
common strategy ancestor enforces quality >= 0, concrete implementations do further calculations? Maybe, but an 
inheritance tree like that might be too rigid. Doesn't sound terribly extensible. To investigate further later.

There's an implicit assumption that "normal" quality change is a step of 1 (increase or decrease), and this can double.
This should probably be configured somewhere centrally instead of just doing + 1 everywhere? 

Trying to clean up the if statements a bit first before doing a full refactoring might be useful? But if the tests work
well enough, that might not be necessary.

## More dependencies?

If nullability becomes a concern (which I kind of doubt), jspecify and nullaway might be useful to add.
AssertJ for tests would be nice as well. 

## Possible edge cases
> "Good Wine" actually increases in Quality the older it gets"

Does this apply after the `sellIn` date? Probably not but should test.

What happens to the legendary items if the quality isn't set to 80 in the constructor? Does it just stay the same or is
it increased to 80 somehow? 

## Refactoring plan
Strategy pattern seems fine here. I don't want a rigid inheritance tree just because of the upper and lower bounds on the
quality, so this might be better in a separate strategy. Some sort of decorator can wrap the 2 together so the bounds execute after
the update, so the update strategies don't even need to bother with bounds. 

Strategies will need to be determined based on the name of the item. A simple factory method that returns the right strategy
per name should probably be enough. 

The strategy for "smelly items" is pretty much the same as normal items but with a multiplier. I could add a multiplier to
the constructor of the default update strategy instead of making a new strategy altogether for it. Remains to be seen which
is simpler.

One thing that annoys me is where the base "unit" of quality change will live. I don't really have a good place to put this
so I'll probably have to put it somewhere in a constants class as a static constant. 

Note after a semi-failed first attempt: I failed to take into account that `sellIn` conditionally decrements. I don't really
have a clean place to put that decrement right now. Another strategy feels kind of overkill for this, but I don't have a better
solution. Perhaps two decorators? One for quality (update + clamp) and one for the entire item (sellIn + clampedUpdate).
Or maybe combine it all into one decorator and have it just be "updateItem" instead of update quality.