package com.shopcalculator;

import com.google.inject.Provides;

import javax.inject.Inject;
import javax.swing.*;
import java.util.Arrays;

import com.shopcalculator.utils.Icons;
import com.shopcalculator.ui.ShopCalculatorPanel;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.MenuOpened;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;

@Slf4j
@PluginDescriptor(
	name = "Shop Calculator"
)
public class ShopCalculatorPlugin extends Plugin
{
	@Inject
	private Client client;
	@Inject
	private ClientToolbar clientToolbar;
	@Inject
	private ShopCalculatorConfig config;

	private ShopCalculatorPanel panel;
	private NavigationButton navButton;

	@Override
	protected void startUp() throws Exception
	{
		panel = new ShopCalculatorPanel();

		navButton = NavigationButton.builder()
					.tooltip("Shop Calculator")
					.icon(Icons.NAV_BUTTON)
					.priority(10)
					.panel(panel)
					.build();

		clientToolbar.addNavigation(navButton);

		log.info("Shop Calculator started!");
	}

	@Override
	protected void shutDown() throws Exception
	{
		clientToolbar.removeNavigation(navButton);
		log.info("Shop Calculator stopped!");
	}

	@Provides
	ShopCalculatorConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(ShopCalculatorConfig.class);
	}
}
