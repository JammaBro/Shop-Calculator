package com.shopcalculator;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class ShopCalculator
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(ShopCalculatorPlugin.class);
		RuneLite.main(args);
	}
}