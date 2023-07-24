package com.shopcalculator;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("Shop Calculator")
public interface ShopCalculatorConfig extends Config {

	@ConfigItem(
			keyName = "example",
			name = "example",
			description = "example"
	)

	default boolean example() {
		return true;
	}
}
