package com.hilburn.dungeonmaster.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import com.hilburn.dungeonmaster.helpers.BBHelper;
import com.hilburn.dungeonmaster.world.gen.structure.DungeonComponent;

public class BlockDungeonGenerate extends Block{

	protected BlockDungeonGenerate() {
		super(Material.iron);
		setBlockName(BlockInfo.DUNGEONGEN_NAME);
		setCreativeTab(CreativeTabs.tabBlock);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x,int y, int z, EntityPlayer player,int side, float hitX, float hitY,float hitZ) {
		int meta=world.getBlockMetadata(x, y, z);
		DungeonComponent test=new DungeonComponent(1, BBHelper.getCorrectBox(meta%4, x, y+6*(meta+1), z, 2, 5, 10, 1,1),meta%4);
		world.setBlockMetadataWithNotify(x, y, z, meta+1, 3);
		test.hollowWithBlocks(world, null);
		return true;
	}

}
