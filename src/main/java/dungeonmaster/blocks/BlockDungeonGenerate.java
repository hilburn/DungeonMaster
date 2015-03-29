package dungeonmaster.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import dungeonmaster.structure.DungeonGenerate;

public class BlockDungeonGenerate extends Block{

	protected BlockDungeonGenerate() {
		super(Material.iron);
		setBlockName(BlockInfo.DUNGEONGEN_NAME);
		setCreativeTab(CreativeTabs.tabBlock);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x,int y, int z, EntityPlayer player,int side, float hitX, float hitY,float hitZ) {
		if (!world.isRemote){
	//		int meta=world.getBlockMetadata(x, y, z);
	//		DungeonComponent test=new DungeonComponent(1, BBHelper.getCorrectBox(meta%4, x, y+6*(meta+1), z, 2, 4, 10, 1,1),meta%4);
	//		world.setBlockMetadataWithNotify(x, y, z, meta+1, 3);
	//		test.hollowWithBlocks(world, null);
	//		ArrayList<BBConnection> temp = new ArrayList<BBConnection>();
	//		temp.addAll(test.getConnections());
	//		for (BBConnection temps:temp){
	//			new DungeonComponent(1,temps,temps.getDirection()).fillWithBlocks(world, Blocks.glowstone);
	//		}
			DungeonGenerate.generateDungeon(world,x,y+6,z);
		}
		return true;
	}

}
