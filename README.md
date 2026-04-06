# Classic Enchanting

Minecraft mod that rebalances enchanting mechanics, inspired by older mechanics from before release 1.8.

Enchanting still costs levels; however, the level cost is now based on the **enchantment power** (up to 30) instead of its index in the enchanting table (1,2,3). The Lapis Lazuli cost also follows this.

Also, the enchantment clue/hint tooltip (e.g., "_Unbreaking III . . . ?_") is removed on the client-side. A true dice roll!

For some counter-balance, the grindstone is also tweaked to give back a lot more experience from grinding away enchantments.

## Why?

My goal is to somewhat follow Jeb's vision of [impermanent gear](https://bsky.app/profile/jebox.bsky.social/post/3me4d6q52sk2h). By **de-emphasizing** the maximization of enchantments, enchantments become more like **temporary special buffs** where you just take what you can get.

The vanilla enchanting table may be seen as too weak compared to villager trading, which very well may be; however, even the enchanting table on its own is quite powerful. Enchantments only cost up to 3 levels in vanilla; once you read level 30, you tend to hover around there and somewhat frequently get high level enchantments, given you do not die. If you factor in XP farms, the RNG of the table almost does not matter as you can just keep enchanting.

In contrast, the enchanting table used to cost an entire 50 levels when introduced. This is _too_ difficult, especially for what you may get. It was eventually changed to cost only 30 levels. This is a decent balance; enchantments are powerful, but rare enough to feel special.

This mod offers a **compromise** between the old and new systems. By default, the max power enchantment from the table (power level 30) will cost only _half_ of the enchantment power. Like in vanilla, you still must have at least 30 levels to purchase the enchantment.

To address villagers, this mod is best combined with some kind of villager trade nerf, especially for trades which contain enchantments (mostly the Librarian's books).

## Client and Server

While this mod does modify the actual enchantment cost on the server-side, it is **highly recommended** to have it installed on both client and server. Without it installed on the client, the true cost will be obscured. Further, the client will be able to see the vanilla enchantment clue (cheating!).

## Configuration

This mod can be configured in-game via [Mod Menu](https://modrinth.com/mod/modmenu) or by manually editing its JSON file `config/classic-enchanting.json`.

`enchantmentLevelCostMultiplier`
- Enchantment Level Cost Multiplier
- The cost of an enchantment will be its listed power (maximum of 30 in the enchanting table) multiplied by this value.
  - `1.0` => max table enchantment will cost all 30 XP levels.
  - `0.5` => max table enchantment will cost 15 XP levels.
- Also applies to Lapis Lazuli cost.
- Default: `0.5`. Supported value range: `0.5` – `1.0`.

`enableGrindstoneMultiplier`
- Enable Grindstone Experience Multiplier
- Grindstone gives more experience. Amount is automatically calculated based on the XP cost multiplier.
- The calculation is based on the configurable enchantment cost multiplier. For example, if using a cost multiplier of 1.0, then the max enchantment cost is 30. The original cost in vanilla would be only 3. The new cost is 10 times greater than the original cost (30 / 3 = 10). Thus, set the grindstone xp multiplier to be 10.0. The goal is to counter-balance the grindstone in a world with extra expensive enchanting.
- Default: `true`.
