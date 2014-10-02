package com.hilburn.dungeonmaster.world.gen.structure;

import net.minecraft.world.gen.structure.StructureBoundingBox;

import com.hilburn.dungeonmaster.helpers.BBHelper;
import com.hilburn.dungeonmaster.helpers.RandomHelper;

public class DungeonCorridor extends DungeonComponent {

	private static final int minWidth = 2;
	private static final int maxWidth = 4;
	private static final int minLength = 8;
	private static final int maxLength = 20;

	public DungeonCorridor(StructureBoundingBox BB, int dir) {
		super(1, BB, dir);
	}
	
	public DungeonCorridor(int minX, int minY, int minZ, int width, int height, int length, int dir){
		super(1,BBHelper.getCorrectBox(dir, minX, minY, minZ, width, height, length, width/2),dir);
	}
	
	public DungeonCorridor(int minX, int minY, int minZ, int width, int height, int length, int xOffset, int zOffset, int dir){
		super(1,BBHelper.getCorrectBox(dir, minX, minY, minZ, width, height, length, xOffset, zOffset),dir);
	}
	
	public DungeonCorridor(BBConnection connection){
		super(1);
		int width = RandomHelper.randomWeightedMiddle(minWidth, maxWidth+1,true);
		int height = width+1+RandomHelper.safeRandom(1);
		connection=connection.randomize(width+1);
		boundingBox=BBHelper.getCorrectBox(connection.getDirection(), connection.minX, connection.minY, connection.minZ, width, height , RandomHelper.getRandomInRange(minLength, maxLength), 0);
		direction=connection.getDirection();
	}

}
