package dungeonmaster.structure;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import dungeonmaster.helpers.BBHelper;
import dungeonmaster.helpers.RandomHelper;

public class DungeonStair extends DungeonComponent {

	private static final int minWidth = 2;
	private static final int maxWidth = 4;
	private int heightChange;
	
	public DungeonStair(BBConnection connection, int dY){
		super(2);
		int width = RandomHelper.randomWeightedMiddle(minWidth, maxWidth+1,true);
		int height = width+1+RandomHelper.safeRandom(1);
		connection=connection.randomize(width);
		heightChange=dY;
		if (dY>0)
			boundingBox=BBHelper.getCorrectBox(connection.getDirection(), connection.minX, connection.minY, connection.minZ, width, height +dY, 10, 0);
		else
			boundingBox=BBHelper.getCorrectBox(connection.getDirection(), connection.minX, connection.minY+dY, connection.minZ, width, height-dY, 10, 0);
		direction=connection.getDirection();
	}
	
	public ArrayList<BBConnection> getSplitConnections(BBConnection splitBB, int index, int border){
		ArrayList<BBConnection> result=new ArrayList<BBConnection>();
		if (heightChange>0)
			result.add(BBConnection.getConnection(BBHelper.getFlatBB(boundingBox, boundingBox.minY+heightChange), index, direction));
		else
			result.add(BBConnection.getConnection(BBHelper.getFlatBB(boundingBox, boundingBox.minY), index, direction));
		return result;
	}
	
	@Override
	/**Returns 1 if an Up stair, 2 if a Down Stair**/
	public int getUpDown(){
		if (heightChange>0)
			return 1;
		else
			return 2;
	}
	
	private int getYMult(){
		if (getUpDown()==1) return 1;
		return -1;
	}
	
	
	@Override
	public void hollowWithBlocks(World world, Block block){
		int height=boundingBox.getYSize()-getYMult()*heightChange;
		if(block==null)block=Blocks.stone;
		for (int x=boundingBox.minX; x<=boundingBox.maxX;x++){
			for (int y=boundingBox.minY; y<=boundingBox.maxY;y++){
				for (int z=boundingBox.minZ; z<=boundingBox.maxZ;z++){
					if (x==boundingBox.minX||x==boundingBox.maxX||y==boundingBox.minY||y==boundingBox.maxY||z==boundingBox.minZ||z==boundingBox.maxZ)world.setBlock(x,y,z,block);
					else world.setBlock(x,y,z,Blocks.air);
				}
			}
		}
	}

}
