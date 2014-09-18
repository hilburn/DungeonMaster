package com.hilburn.dungeonmaster.world.gen.structure;

import java.util.ArrayList;

import net.minecraft.world.World;


public class DungeonGenerate{
	
	private static int numRooms=10;
	private static int maxRoomsPerLevel=5;
	private static int[] numRoomsPerLevel = new int[26];
	private ArrayList<DungeonComponent> components;
	private ArrayList<DungeonComponent> connections;
	
	public static void generateDungeon(World world, int x, int y, int z){
		for (int i=0;i<numRoomsPerLevel.length;i++)numRoomsPerLevel[i]=0;
		int dungeonRooms=numRooms;
		if (numRooms/maxRoomsPerLevel>25)dungeonRooms=maxRoomsPerLevel*25;
		//TODO: Place initial room
		while (dungeonRooms>0){
			//TODO: Select connection
			//TODO: Try to build component off it
			//TODO: If it fits
			//TODO: Add new connections
			//TODO: Remove old connection
			//TODO: Add new component
			//TODO: If it's a room, decr dungeonRooms, inc roomsPerLevel[y/10]
		}
		//TODO: Loop through all components, build them
	}
}