package com.shopcalculator;

import javax.inject.Inject;
import com.shopcalculator.utils.Icons;
import com.shopcalculator.ui.ShopCalculatorPanel;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;

@Slf4j
@PluginDescriptor(
	name = "Shop Calculator",
	description = "Calculator for bulk buying costs from NPC shops",
	tags = {"shop", "calculator", "item"}
)

public class ShopCalculatorPlugin extends Plugin {
	@Inject
	private ClientToolbar clientToolbar;
	private NavigationButton navButton;

	@Override
	protected void startUp() {
		// Create and add the custom panel to the side panel
		ShopCalculatorPanel panel = new ShopCalculatorPanel();

		navButton = NavigationButton.builder()
				.tooltip("Shop Calculator")
				.icon(Icons.NAV_BUTTON)
				.priority(10)
				.panel(panel)
				.build();

		clientToolbar.addNavigation(navButton);
	}

	@Override
	protected void shutDown() {
		// Remove the custom panel from the side panel when the plugin is disabled
		clientToolbar.removeNavigation(navButton);
	}
}
