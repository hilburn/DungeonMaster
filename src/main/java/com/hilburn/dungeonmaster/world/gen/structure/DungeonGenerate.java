package com.hilburn.dungeonmaster.world.gen.structure;

import java.util.ArrayList;

import net.minecraft.world.World;

import com.hilburn.dungeonmaster.helpers.RandomHelper;


public class DungeonGenerate{
	
	private static int numRooms=100;
	private static int maxRoomsPerLevel=15;
	private static int spacing=5;
	private static int roomChance=80;
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
			int thisLevel=thisConnection.minY/10;
			DungeonComponent newComponent;
			if (thisComponent.getComponentType()!=3&&RandomHelper.doWithChance(roomChance)&&numRoomsPerLevel[thisLevel]<maxRoomsPerLevel){
				newComponent = new DungeonRoom(thisConnection.randomize());
			}else{
				if (RandomHelper.doWithChance(stairChance)){
					newComponent = new DungeonStair(thisConnection,RandomHelper.getRandomPlusMinus()*10);
					
				}else
					newComponent = new DungeonCorridor(thisConnection);
			}
			if (newComponent.checkFit(components)){
				//addComponent(newComponent, thisConnectionIndex, thisConnection);
				connections.addAll(newComponent.getSplitConnections(thisConnection, components.size(), spacing));
				components.add(newComponent);
				if (newComponent.getComponentType()==2)newComponent.print();
				connections.remove(thisConnectionIndex);
				connections.addAll(thisConnection.splitConnection(newComponent.getBoundingBox(), spacing));
				if (newComponent.getComponentType()==3){
					dungeonRooms++;
					numRoomsPerLevel[thisLevel]++;
				}
			}
			
		}
		for (DungeonComponent component:components){
			if (component.getComponentType()==2)component.print();
			component.hollowWithBlocks(world, null);
		}
	}
	
	private static void addComponent(DungeonComponent newComponent,int connectionIndex, BBConnection connection){
		
	}
}