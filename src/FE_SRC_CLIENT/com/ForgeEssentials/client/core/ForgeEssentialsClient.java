package com.ForgeEssentials.client.core;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Main client side mod file.
 */

@SideOnly(Side.CLIENT)
@Mod(modid = "FEClientMod", name = "Forge Essentials|ClientMod", version = "0.1.0")
public class ForgeEssentialsClient{

	public boolean presentOnServer;
	
	public void preLoad(FMLPreInitializationEvent e) {
		System.out.println("Initializing Forge Essentials client side mod.");
		System.out.println("Detecting Forge Essentials server mod.");
		if (Loader.isModLoaded("ForgeEssentials")){
			System.out.println("Server side mod found.");
			presentOnServer = true;
		}
		
	}

	public void load(FMLInitializationEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void postLoad(FMLPostInitializationEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void serverStarting(FMLServerStartingEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void serverStarted(FMLServerStartedEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void serverStopping(FMLServerStoppingEvent e) {
		// TODO Auto-generated method stub
		
	}

}
