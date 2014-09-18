package com.hilburn.dungeonmaster.world.gen.structure;

import com.hilburn.dungeonmaster.helpers.BBHelper;
import com.hilburn.dungeonmaster.helpers.RandomHelper;

import net.minecraft.world.gen.structure.StructureBoundingBox;

public class DungeonRoom extends DungeonComponent {
	
	private static final int minWidth = 6;
	private static final int maxWidth = 14;
	private static final int minHeight = 4;
	private static final int maxHeight = 6;

	public DungeonRoom(StructureBoundingBox BB, int dir) {
		super(3, BB, dir);
	}
	
	public DungeonRoom(int minX, int minY, int minZ, int width, int height, int length, int dir){
		super(3,BBHelper.getCorrectBox(dir, minX, minY, minZ, width, height, length, width/2),dir);
	}
	
	public DungeonRoom(int minX, int minY, int minZ, int width, int height, int length, int xOffset, int zOffset, int dir){
		super(3,BBHelper.getCorrectBox(dir, minX, minY, minZ, width, height, length, xOffset, zOffset),dir);
	}
	
	public DungeonRoom(BBConnection connection){
		super(3);
		//System.out.println(connection.minX+" "+ connection.minY+" "+ connection.minZ);
		int width = RandomHelper.getRandomInRange(minWidth, maxWidth);
		boundingBox=BBHelper.getCorrectBox(connection.getDirection(), connection.minX, connection.minY, connection.minZ, width, RandomHelper.getRandomInRange(minHeight, maxHeight), RandomHelper.getRandomInRange(minWidth, maxWidth), width/2);
		direction=connection.getDirection();
	}
	
}
