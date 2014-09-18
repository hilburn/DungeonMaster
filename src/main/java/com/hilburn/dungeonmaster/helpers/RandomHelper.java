package com.hilburn.dungeonmaster.helpers;

import com.hilburn.dungeonmaster.DungeonMaster;

public class RandomHelper {
	
	/**Returns a random multiplier (+-1)**/
	public static int getRandomPlusMinus() {
		switch(DungeonMaster.rand.nextInt(2)){
		case 0:
			return -1;
		default:
			return 1;
		}
	}

	/** Returns true with a percentage probability. Args: chance (int %) */
	public static boolean doWithChance(int num) {
		if (num >= 100)
			return true;
		if (num <= 0)
			return false;

		return DungeonMaster.rand.nextInt(100) < num;
	}
	
	/**Returns a random int between 2 values. Args int min, int max**/
	public static int getRandomInRange(int min, int max){
		return min+safeRandom(max-min); 
	}
	
	/**Returns a random integer either positive or negative**/
	public static int safeRandom(int val){
		if (val>=0) return DungeonMaster.rand.nextInt(val);
		else return -DungeonMaster.rand.nextInt(-val);
	}
	
	public static int randomWeightedMiddle(int min, int max, boolean square){
		int[] weight=new int[max-min];
		int total=0;
		for (int i=0;i<weight.length;i++){
			weight[i]=(int)(square? Math.pow(Math.min(i, weight.length-i), 2):Math.min(i, weight.length-i));
			total+=weight[i];
		}
		int random = safeRandom(total);
		for (int i=0;i<weight.length;i++){
			if (random<weight[i])return min+i;
			random-=weight[i];
		}
		return 1;
	}
}
