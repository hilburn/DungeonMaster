package dungeonmaster;

import java.util.Random;

import dungeonmaster.blocks.ModBlocks;
import dungeonmaster.proxies.CommonProxy;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import dungeonmaster.reference.Reference;

@Mod(modid = Reference.ID, name = Reference.NAME, version = Reference.VERSION_FULL)
public class DungeonMaster {
	
	public static final Random rand = new Random();
	
	@Instance(Reference.ID)
	public static DungeonMaster instance;
	
	@SidedProxy(clientSide="dungeonmaster.proxies.ClientProxy", serverSide="dungeonmaster.proxies.ServerProxy")
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
