package dungeonmaster.blocks;

import net.minecraft.block.Block;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModBlocks {

	public static Block dungeon;
	public static void init() {
		GameRegistry.registerBlock(dungeon=new BlockDungeonGenerate(), BlockInfo.DUNGEONGEN_NAME);		
	}

}
