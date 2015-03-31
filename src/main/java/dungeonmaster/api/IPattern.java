package dungeonmaster.api;

import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.EnumSet;

public interface IPattern
{
    int getPatternVariation();

    EnumSet<ForgeDirection> getValidSides();

    int getBlock(World world, int x, int y, int z, IDungeonComponent component);
}
