package com.hilburn.dungeonmaster.world.gen.structure;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

public class DungeonComponent extends StructureComponent{
	private static int MAX_ATTEMPTS = 7;
	protected int direction;
	protected int pattern;
	protected int blockSet;
	protected int feature;
	private int connectAttempts;
	
	public DungeonComponent(int type){
		componentType=type;
		coordBaseMode=-1;
		connectAttempts=0;
	}
	
	public DungeonComponent(int type, StructureBoundingBox BB, int dir){
		this(type);
		boundingBox=BB;
		direction=dir;
	}
	
	@Override
	protected void func_143012_a(NBTTagCompound p_143012_1_) {
		//Write extra data to NBT
		
	}

	@Override
	protected void func_143011_b(NBTTagCompound p_143011_1_) {
		//Read extra data from NBT
		
	}

	@Override
	public boolean addComponentParts(World p_74875_1_, Random p_74875_2_,StructureBoundingBox p_74875_3_) {
		return false;
	}
	
	
	public ArrayList<BBConnection> getConnections(int index){
		ArrayList<BBConnection> result=new ArrayList<BBConnection>();
		for (int i=0;i<4;i++){
			result.add(BBConnection.getConnection(boundingBox, index, i));
		}
		return result;
	}
	
	public ArrayList<BBConnection> getConnections(){
		return getConnections(0);
	}
	
	public ArrayList<BBConnection> getSplitConnections(BBConnection splitBB, int index, int border){
		ArrayList<BBConnection> result=new ArrayList<BBConnection>();
		for (int i=0;i<4;i++){
			if (i!=(splitBB.getDirection()+2)%4)
				result.add(BBConnection.getConnection(boundingBox, index, i));
			else
				result.addAll(BBConnection.splitConnection(BBConnection.getConnection(boundingBox, index, i), splitBB, border));
		}
		return result;
	}
	
	public boolean checkFit(ArrayList<DungeonComponent> components){
		for (DungeonComponent component:components){
			if (boundingBox.intersectsWith(component.boundingBox)) return false;
		}
		return true;
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
	
	public void addToChunk(World world){
		Block block;
		for (int x=boundingBox.minX; x<=boundingBox.maxX;x++){
			for (int y=boundingBox.minY; y<=boundingBox.maxY;y++){
				for (int z=boundingBox.minZ; z<=boundingBox.maxZ;z++){
					if (x==boundingBox.minX||x==boundingBox.maxX||y==boundingBox.minY||y==boundingBox.maxY||z==boundingBox.minZ||z==boundingBox.maxZ)block=Blocks.stone;
					else block=Blocks.air;
					setChunkBlock(world,x,y,z,block);
				}
			}
		}
	}
	
	public void setChunkBlock(World world, int x, int y, int z, Block block)
	{
		Chunk chunk = world.getChunkFromBlockCoords(x, z);
		Map<Vec3,Block> blocks= new Hashtable<Vec3,Block>();
		if (DungeonGenerate.place.containsKey(chunk)) blocks = DungeonGenerate.place.get(chunk);
		blocks.put(Vec3.createVectorHelper(x, y, z),block);
		DungeonGenerate.place.put(chunk,blocks);
	}
	
	public int dimInDirection(int dir){
		switch (dir%2){
		case 0:
			return boundingBox.getXSize();
		default:
			return boundingBox.getZSize();	
		}
	}
	
	public boolean canConnect()
	{
		return connectAttempts++<MAX_ATTEMPTS;
	}
	
	public int getUpDown(){return 0;}
	
	public int getDirection(){return direction;}

	public void print() {
		System.out.println(boundingBox.minX+" "+boundingBox.minY+" "+boundingBox.minZ+","+boundingBox.maxX+" "+	boundingBox.maxY+" "+boundingBox.maxZ);	
	}
}
