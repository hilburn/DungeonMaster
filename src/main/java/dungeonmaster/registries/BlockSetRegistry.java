package dungeonmaster.registries;

import dungeonmaster.api.IBlockSet;
import dungeonmaster.helpers.RandomHelper;

import java.util.ArrayList;
import java.util.List;

public class BlockSetRegistry
{
    private static List<IBlockSet> registry = new ArrayList<IBlockSet>();

    public static int getBlockSetKey(IBlockSet pattern)
    {
        return registry.indexOf(pattern);
    }

    public static IBlockSet getBlockSet(int index)
    {
        try
        {
            return registry.get(index);
        }catch (Exception e)
        {
            return registry.get(RandomHelper.safeRandom(registry.size()));
        }
    }
}
