package com.hilburn.dungeonmaster.world.gen.structure;

import java.util.ArrayList;

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
	private ArrayList<BBConnection> splitConnection(BBConnection BBSplit,int border) {
		ArrayList<BBConnection> results=new ArrayList<BBConnection>();
		BBSplit.maxX+=border;
		BBSplit.minX-=border;
		BBSplit.maxY+=border;
		BBSplit.minY-=border;
		switch (direction%2){
		case 0:
			if (minX<BBSplit.minX) results.add(new BBConnection(minX,minY,minZ,BBSplit.minX,minY,minZ,index,direction));
			if (BBSplit.maxX<maxX) results.add(new BBConnection(BBSplit.maxX,minY,minZ,maxX,minY,minZ,index,direction));
		case 1:
			if (minZ<BBSplit.minZ) results.add(new BBConnection(minX,minY,minZ,minX,minY,BBSplit.minZ,index,direction));
			if (BBSplit.maxZ<maxZ) results.add(new BBConnection(minX,minY,BBSplit.maxZ,minX,minY,BBSplit.maxZ,index,direction));
		}
		return results;
	}

	public int getDirection(){return direction;}
	public int getIndex(){return index;}
}
