package com.hilburn.dungeonmaster.world.gen.structure;

import java.util.Random;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

public class DungeonComponent extends StructureComponent{
	public DungeonComponent(int type){
		componentType=type;
	}
	@Override
	protected void func_143012_a(NBTTagCompound p_143012_1_) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void func_143011_b(NBTTagCompound p_143011_1_) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean addComponentParts(World p_74875_1_, Random p_74875_2_,StructureBoundingBox p_74875_3_) {
		return false;
	}

}
