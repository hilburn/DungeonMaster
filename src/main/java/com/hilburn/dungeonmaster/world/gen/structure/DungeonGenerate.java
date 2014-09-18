package com.hilburn.dungeonmaster.world.gen.structure;

import java.util.ArrayList;

import com.hilburn.dungeonmaster.helpers.RandomHelper;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;


public class DungeonGenerate{
	
	private static int numRooms=10;
	private static int maxRoomsPerLevel=5;
	private static int spacing=5;
	private static int roomChance=60;
	private static int doorChance=80;
	private static int stairChance=10;
	private static int[] numRoomsPerLevel = new int[26];
	private static ArrayList<DungeonComponent> components;
	private static ArrayList<BBConnection> connections;
	
	public static void generateDungeon(World world, int x, int y, int z){
		components=new ArrayList<DungeonComponent>();
		connections=new ArrayList<BBConnection>();
		for (int i=0;i<numRoomsPerLevel.length;i++)numRoomsPerLevel[i]=0;
		int dungeonRooms=1;
		if (numRooms/maxRoomsPerLevel>25)dungeonRooms=maxRoomsPerLevel*25;
		DungeonComponent startRoom = new DungeonRoom(x,y,z,15,6,15,8,8,1);
		components.add(startRoom);
		connections.addAll(startRoom.getConnections());
		while (dungeonRooms<numRooms){
			int thisConnectionIndex=RandomHelper.safeRandom(connections.size());
			BBConnection thisConnection = connections.get(thisConnectionIndex);
			DungeonComponent thisComponent = components.get(thisConnection.getIndex());
			DungeonComponent newComponent = new DungeonRoom(thisConnection.randomize());
			int thisLevel=newComponent.getBoundingBox().minY/10;
			if (newComponent.checkFit(components)){
				//addComponent(newComponent, thisConnectionIndex, thisConnection);
				connections.addAll(newComponent.getSplitConnections(thisConnection, components.size(), spacing));
				components.add(newComponent);
				connections.remove(thisConnectionIndex);
				connections.addAll(thisConnection.splitConnection(newComponent.getBoundingBox(), spacing));
				if (newComponent.getComponentType()==3){
					dungeonRooms++;
					numRoomsPerLevel[thisLevel]++;
				}
			}
			
		}
		for (DungeonComponent component:components){
			component.hollowWithBlocks(world, null);
		}
		for (BBConnection temps:connections){
			new DungeonComponent(1,temps,temps.getDirection()).fillWithBlocks(world, Blocks.glowstone);
		}
	}
	
	private static void addComponent(DungeonComponent newComponent,int connectionIndex, BBConnection connection){
		
	}
}