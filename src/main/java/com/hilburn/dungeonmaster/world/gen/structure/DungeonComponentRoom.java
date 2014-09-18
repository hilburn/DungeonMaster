package com.hilburn.dungeonmaster.world.gen.structure;

import com.hilburn.dungeonmaster.helpers.BBHelper;

import net.minecraft.world.gen.structure.StructureBoundingBox;

public class DungeonComponentRoom extends DungeonComponent {
	
	public DungeonComponentRoom(StructureBoundingBox BB) {
		super(3);
		boundingBox=BB;
	}
	
	public DungeonComponentRoom(StructureBoundingBox BB, int dir) {
		super(3, BB, dir);
	}
	
	public DungeonComponentRoom(int minX, int minY, int minZ, int width, int height, int length, int dir){
		super(3,BBHelper.getCorrectBox(dir, minX, minY, minZ, width, height, length, width/2),dir);
	}
	
	
}
