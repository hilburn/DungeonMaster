package dungeonmaster.api;

import net.minecraft.world.World;

public interface IBlockSet
{
    int getSetSize();

    void setBlock(World world, int x, int y, int z, IDungeonComponent component, IPattern pattern);
}
