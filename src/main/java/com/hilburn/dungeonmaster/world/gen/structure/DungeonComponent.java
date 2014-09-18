package com.hilburn.dungeonmaster.world.gen.structure;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

public class DungeonComponent extends StructureComponent{
	protected int direction;
	
	public DungeonComponent(int type){
		componentType=type;
		coordBaseMode=-1;
	}
	
	public DungeonComponent(int type, StructureBoundingBox BB, int dir){
		componentType=type;
		coordBaseMode=-1;
		boundingBox=BB;
		direction=dir;
	}
	
	@Override
	protected void func_143012_a(NBTTagCompound p_143012_1_) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void func_143011_b(NBTTagCompound p_143011_1_) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean addComponentParts(World p_74875_1_, Random p_74875_2_,StructureBoundingBox p_74875_3_) {
		return false;
	}
	
	
	public ArrayList<BBConnection> getConnections(){
		ArrayList<BBConnection> result=new ArrayList<BBConnection>();
		for (int i=0;i<4;i++){
			result.add(BBConnection.getConnection(boundingBox, 0, i));
		}
		return result;
	}
	
	public void fillWithBlocks(World world, Block block){
		if (block==null)block=Blocks.stone;
		for (int x=boundingBox.minX; x<=boundingBox.maxX;x++){
			for (int y=boundingBox.minY; y<=boundingBox.maxY;y++){
				for (int z=boundingBox.minZ; z<=boundingBox.maxZ;z++){
					world.setBlock(x,y,z,block);
				}
			}
		}
	}
	
	public void hollowWithBlocks(World world, Block block){
		if (block==null)block=Blocks.stone;
		for (int x=boundingBox.minX; x<=boundingBox.maxX;x++){
			for (int y=boundingBox.minY; y<=boundingBox.maxY;y++){
				for (int z=boundingBox.minZ; z<=boundingBox.maxZ;z++){
					if (x==boundingBox.minX||x==boundingBox.maxX||y==boundingBox.minY||y==boundingBox.maxY||z==boundingBox.minZ||z==boundingBox.maxZ)world.setBlock(x,y,z,block);
					else world.setBlock(x,y,z,Blocks.air);
				}
			}
		}
	}
	
	public int getDirection(){return direction;}
}
