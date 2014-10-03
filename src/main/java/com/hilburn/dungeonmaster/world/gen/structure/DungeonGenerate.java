package com.hilburn.dungeonmaster.world.gen.structure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.ForgeChunkManager.Type;

import com.hilburn.dungeonmaster.DungeonMaster;
import com.hilburn.dungeonmaster.helpers.RandomHelper;


public class DungeonGenerate{
	
	private static int numRooms=8000;
	private static int maxRoomsPerLevel=1200;
	private static int spacing=6;
	private static int roomChance=80;
	private static int doorChance=80;
	private static int stairChance=10;
	private static int[] numRoomsPerLevel = new int[26];
	private static int[] pairOfStairs = new int[26];
	private static ArrayList<DungeonComponent> components;
	private static ArrayList<BBConnection> connections;
	public static Map<Chunk,Map<Vec3,Block>> place = new Hashtable<Chunk,Map<Vec3,Block>>();
	
	public static void generateDungeon(World world, int x, int y, int z){
		components=new ArrayList<DungeonComponent>();
		connections=new ArrayList<BBConnection>();
		//for (int i=0;i<numRoomsPerLevel.length;i++)numRoomsPerLevel[i]=0;
		long start = System.currentTimeMillis();
		Arrays.fill(numRoomsPerLevel,0);
		Arrays.fill(pairOfStairs,0);
		int dungeonRooms=1;
		if (numRooms/maxRoomsPerLevel>25)dungeonRooms=maxRoomsPerLevel*25;
		DungeonComponent startRoom = new DungeonRoom(x,y,z,15,6,15,8,8,1);
		numRoomsPerLevel[y/10]=1;
		components.add(startRoom);
		connections.addAll(startRoom.getConnections());
		while (dungeonRooms<numRooms){
			int thisConnectionIndex=RandomHelper.safeRandom(connections.size());
			BBConnection thisConnection = connections.get(thisConnectionIndex);
			int thisLevel=thisConnection.minY/10;
			boolean levelFull = numRoomsPerLevel[thisLevel]>=maxRoomsPerLevel;
			if (levelFull&&pairOfStairs[thisLevel]>2)continue;
			DungeonComponent thisComponent = components.get(thisConnection.getIndex());
			
			DungeonComponent newComponent;
			if (thisComponent.getComponentType()!=3&&RandomHelper.doWithChance(roomChance)&&!levelFull){
				newComponent = new DungeonRoom(thisConnection.randomize());
			}else{
				if (RandomHelper.doWithChance(levelFull? stairChance+50:stairChance)){
					newComponent = new DungeonStair(thisConnection,RandomHelper.getRandomPlusMinus()*10);
				}else
					newComponent = new DungeonCorridor(thisConnection);
			}
			if (newComponent.checkFit(components)){
				//addComponent(newComponent, thisConnectionIndex, thisConnection);
				connections.addAll(newComponent.getSplitConnections(thisConnection, components.size(), spacing));
				components.add(newComponent);
				connections.remove(thisConnectionIndex);
				connections.addAll(thisConnection.splitConnection(newComponent.getBoundingBox(), spacing));
				if (newComponent.getComponentType()==2){
					if (newComponent.getUpDown()==1&&(pairOfStairs[thisLevel]==0||pairOfStairs[thisLevel]==2)){
						pairOfStairs[thisLevel]++;
						pairOfStairs[thisLevel+1]+=2;
					}else if(newComponent.getUpDown()==2&&pairOfStairs[thisLevel]<2){
						pairOfStairs[thisLevel]+=2;
						pairOfStairs[thisLevel-1]++;
					}
				}else if(newComponent.getComponentType()==3){
					dungeonRooms++;
					numRoomsPerLevel[thisLevel]++;
				}
			}			
		}
		
		for (DungeonComponent component:components){
		
			//component.hollowWithBlocks(world, null);
			component.addToChunk(world);
		}
		long end = System.currentTimeMillis();
		System.out.println("Dungeon Calculated - Size of "+components.size()+" - in "+ (end-start) + "ms");
		int i=1;
		for (Chunk chunk:place.keySet())
		{
			Map<Vec3,Block> blocks = place.get(chunk);
			System.out.println("Placing Chunk "+(i++)+" of "+place.size());
			for (Vec3 loc:blocks.keySet())
			{
				Block block = blocks.get(loc);
				world.setBlock((int)loc.xCoord, (int)loc.yCoord, (int)loc.zCoord, block);
			}
		}
		
		System.out.println("Dungeon Generated - in "+ (System.currentTimeMillis()-end) + "ms");
	}
}