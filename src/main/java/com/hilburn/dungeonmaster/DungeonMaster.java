package com.hilburn.dungeonmaster;

import java.util.Random;

import com.hilburn.dungeonmaster.blocks.ModBlocks;
import com.hilburn.dungeonmaster.helpers.RandomHelper;
import com.hilburn.dungeonmaster.proxies.CommonProxy;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ModInformation.MODID, name = ModInformation.NAME, version = ModInformation.VERSION)
public class DungeonMaster {
	
	public static final Random rand = new Random();
	
	@Instance(ModInformation.MODID)
	public static DungeonMaster instance;
	
	@SidedProxy(clientSide="com.hilburn.dungeonmaster.proxies.ClientProxy", serverSide="com.hilburn.dungeonmaster.proxies.ServerProxy")
	public static CommonProxy proxy; 
	
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event){

		ModBlocks.init();

		proxy.initSounds();
		proxy.initRenderers();	
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event){

	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event){
		
	}
}
