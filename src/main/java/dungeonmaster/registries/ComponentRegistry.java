package dungeonmaster.registries;

import dungeonmaster.api.BlockBB;
import dungeonmaster.api.IBlockSet;
import dungeonmaster.api.IPattern;
import dungeonmaster.api.BaseDungeonComponent;
import dungeonmaster.helpers.RandomHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ComponentRegistry
{
    private static Map<Integer, Entry> registry = new LinkedHashMap<Integer, Entry>();
    private static int totalWeight;

    public static void registerComponent(Class<? extends BaseDungeonComponent> component, int id, int weight)
    {
        if (!registry.containsKey(id))
        {
            registry.put(id, new Entry(component,weight));
            totalWeight += weight;
        }
    }

    public static BaseDungeonComponent getDungeonComponent(int type, List<BlockBB> bounding, ForgeDirection facing, IBlockSet blockSet, List<IPattern> patterns)
    {
        return getInstance(registry.get(type).clazz).setComponentBounds(bounding).setFacing(facing).setBlockSet(blockSet).setPatterns(patterns);
    }

    public static BaseDungeonComponent getDungeonComponent(NBTTagCompound tagCompound)
    {
        BaseDungeonComponent dungeonComponent =  getInstance(registry.get((int)tagCompound.getByte("type")).clazz);
        dungeonComponent.loadFromNBT(tagCompound);
        return dungeonComponent;
    }

    public static BaseDungeonComponent getRandomComponent()
    {
        int rand = RandomHelper.getRandomInRange(0, totalWeight);
        for (Entry entry : registry.values())
        {
            rand -= entry.weight;
            if (rand<=0) return getInstance(entry.clazz);
        }
        return null;
    }

    private static BaseDungeonComponent getInstance(Class<? extends BaseDungeonComponent> clazz)
    {
        try
        {
            return clazz.newInstance();
        } catch (Exception ignored)
        {
        }
        return null;
    }

    private static class Entry
    {
        public Class<? extends BaseDungeonComponent> clazz;
        public int weight;

        private Entry(Class<? extends BaseDungeonComponent> clazz, int weight)
        {
            this.clazz = clazz;
            this.weight = weight;
        }
    }
}
