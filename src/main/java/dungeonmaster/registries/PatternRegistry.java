package dungeonmaster.registries;

import dungeonmaster.api.IPattern;
import dungeonmaster.helpers.RandomHelper;

import java.util.ArrayList;
import java.util.List;

public class PatternRegistry
{
    private static List<IPattern> registry = new ArrayList<IPattern>();

    public static int getPatternKey(IPattern pattern)
    {
        return registry.indexOf(pattern);
    }

    public static IPattern getPattern(int index)
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
