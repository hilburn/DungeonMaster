package com.hilburn.dungeonmaster.world.gen.structure;

import java.util.ArrayList;

import com.hilburn.dungeonmaster.helpers.RandomHelper;

import net.minecraft.world.gen.structure.StructureBoundingBox;

public class BBConnection extends StructureBoundingBox {
	private int direction;
	private int index;
	
	public BBConnection(){
	}
	
	public BBConnection(StructureBoundingBox BB, int index, int dir){
		super(BB);
		this.index=index;
		direction=dir;
	}
	
	public BBConnection(int minX, int minY, int minZ, int maxX, int maxY, int maxZ, int index, int dir){
		super(minX,minY,minZ,maxX,maxY,maxZ);
		this.index=index;
		direction=dir;
	}
	
	public static BBConnection getConnection(StructureBoundingBox BB, int index, int dir){
		switch (dir){
		case 0:
			return new BBConnection(BB.minX,BB.minY,BB.maxZ+1,BB.maxX,BB.minY,BB.maxZ+1,index, dir);
		case 1:
			return new BBConnection(BB.minX-1,BB.minY,BB.minZ,BB.minX-1,BB.minY,BB.maxZ,index, dir);
		case 2:
			return new BBConnection(BB.minX,BB.minY,BB.minZ-1,BB.maxX,BB.minY,BB.minZ-1,index, dir);
		case 3:
			return new BBConnection(BB.maxX+1,BB.minY,BB.minZ,BB.maxX+1,BB.minY,BB.maxZ,index, dir);
		}
		return null;
	}
	
	public static ArrayList<BBConnection> splitConnection(BBConnection BBOriginal, BBConnection BBSplit, int border){
		return BBOriginal.splitConnection(BBSplit, border);
	}
	
	/**Splits a BBConnection. Args: BBSplit: the value to split on, border: the border around BBSplit to also remove**/
	public ArrayList<BBConnection> splitConnection(StructureBoundingBox split,int border) {
		ArrayList<BBConnection> results=new ArrayList<BBConnection>();
		BBConnection splitBB=new BBConnection(split,index,direction);
		splitBB.maxX+=border;
		splitBB.minX-=border;
		splitBB.maxZ+=border;
		splitBB.minZ-=border;
		switch (direction%2){
		case 0:
			if (minX<splitBB.minX) results.add(new BBConnection(minX,minY,minZ,splitBB.minX,minY,minZ,index,direction));
			if (splitBB.maxX<maxX) results.add(new BBConnection(splitBB.maxX,minY,minZ,maxX,minY,minZ,index,direction));
		case 1:
			if (minZ<splitBB.minZ) results.add(new BBConnection(minX,minY,minZ,minX,minY,splitBB.minZ,index,direction));
			if (splitBB.maxZ<maxZ) results.add(new BBConnection(minX,minY,splitBB.maxZ,minX,minY,splitBB.maxZ,index,direction));
		}
		return results;
	}
	
	public int getSize(){
		return Math.max(getXSize(), getZSize());
	}
	
	public BBConnection randomize(){
		switch (direction%2){
		case 0:
			return new BBConnection(RandomHelper.getRandomInRange(minX+1, maxX-1),minY,minZ,maxX,minY,minZ,index,direction);
		default:
			return new BBConnection(minX,minY,RandomHelper.getRandomInRange(minZ+1, maxZ-1),minX,minY,maxZ,index,direction);
		}
	}
	public BBConnection randomize(int border){
		switch (direction%2){
		case 0:
			return new BBConnection(RandomHelper.getRandomInRange(minX, maxX-border),minY,minZ,maxX,minY,minZ,index,direction);
		default:
			return new BBConnection(minX,minY,RandomHelper.getRandomInRange(minZ, maxZ-border),minX,minY,maxZ,index,direction);
		}
	}

	public int getDirection(){return direction;}
	public int getIndex(){return index;}
}
