package com.hilburn.dungeonmaster.helpers;

import net.minecraft.world.gen.structure.StructureBoundingBox;

public class BBHelper {
	
    public static StructureBoundingBox getCorrectBox(int direction, int x, int y, int z, int width, int height, int length, int xShift, int zShift) {
        int minX = 0;
        int maxX = 0;
        int minY = y;
        int maxY = y + height;
        int minZ = 0;
        int maxZ = 0;

        switch (direction) {
            case 0:
                minX = x - xShift;
                maxX = minX + width;
                minZ = z - zShift;
                maxZ = minZ + length;
                break;

            case 1:
            	maxX = x + zShift;
                minX = maxX - length;
                minZ = z - xShift;
                maxZ = minZ + width;
                break;

            case 2:
                minX = x - xShift;
                maxX = minX + width;
                maxZ = z + zShift;
                minZ = maxZ - length;
                break;

            case 3:
                minX = x - zShift;
                maxX = minX + length;
                minZ = z - xShift;
                maxZ = minZ + width;
                break;
        }

        return new StructureBoundingBox(minX, minY, minZ, maxX, maxY, maxZ);
    }
    
    public static StructureBoundingBox getCorrectBox(int direction, int x, int y, int z, int width, int height, int length, int xShift){
    	return getCorrectBox(direction, x, y , z,width,height,length,xShift,0);
    }
    
    public static StructureBoundingBox getFlatBB(StructureBoundingBox BB,int yValue){
    	BB.minY=yValue;
    	BB.maxY=yValue;
    	return BB;
    }
    
}
