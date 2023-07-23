package com.shopcalc;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("shopcalc")
public interface ShopCalcConfig extends Config {
	// How to do config items
	@ConfigItem(
			keyName = "greeting",
			name = "Welcome Greeting",
			description = "The message to show to the user when they login"
	)

	// How to add default strings
	default String greeting() {
		return "Hello";
	}
}
