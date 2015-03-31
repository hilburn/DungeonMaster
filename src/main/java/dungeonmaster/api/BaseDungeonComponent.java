package dungeonmaster.api;

import dungeonmaster.registries.BlockSetRegistry;
import dungeonmaster.registries.PatternRegistry;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseDungeonComponent
{
    private final int type;
    protected List<BlockBB> componentBounds = new ArrayList<BlockBB>();
    protected ForgeDirection facing;
    protected IBlockSet blockSet;
    protected List<IPattern> patterns = new ArrayList<IPattern>();

    protected BaseDungeonComponent(int type)
    {
        this.type = type;
    }

    public List<BlockBB> getComponentBounds()
    {
        return componentBounds;
    }

    public List<BlockBB> getConnectionPoints(ForgeDirection direction)
    {
        List<BlockBB> result = new ArrayList<BlockBB>();
        if (direction == ForgeDirection.UP || direction == ForgeDirection.DOWN) return result;
        for (BlockBB part : componentBounds)
        {
            BlockBB connection = part.getFace(direction);
            connection.offset(direction.offsetX, direction.offsetY, direction.offsetZ);
            connection.maxY = connection.minY;
            result.add(connection);
        }
        return result;
    }

    public int getType()
    {
        return type;
    }

    public boolean canConnect(BaseDungeonComponent component)
    {
        return true;
    }

    public NBTTagCompound writeToNBT()
    {
        NBTTagCompound tagCompound = new NBTTagCompound();
        tagCompound.setByte("type", (byte)type);
        tagCompound.setByte("facing", (byte)facing.ordinal());
        NBTTagList list = new NBTTagList();
        for (BlockBB boundingbox : getComponentBounds())
        {
            list.appendTag(boundingbox.writeToNBT());
        }
        tagCompound.setTag("BB", list);
        int[] patterns = new int[this.patterns.size()];
        for (int i = 0; i < patterns.length; i++) patterns[i] = PatternRegistry.getPatternKey(this.patterns.get(i));
        tagCompound.setIntArray("Patterns", patterns);
        tagCompound.setInteger("Block", BlockSetRegistry.getBlockSetKey(blockSet));
        return null;
    }

    public void loadFromNBT(NBTTagCompound tagCompound)
    {
        this.facing = ForgeDirection.getOrientation(tagCompound.getByte("facing"));
        NBTTagList tagList = tagCompound.getTagList("BB", 10);
        for (int i = 0; i < tagList.tagCount(); i++)
        {
            componentBounds.add(BlockBB.loadFromNBT(tagList.getCompoundTagAt(i)));
        }
        for (int i : tagCompound.getIntArray("Patterns")) this.patterns.add(PatternRegistry.getPattern(i));
        this.blockSet = BlockSetRegistry.getBlockSet(tagCompound.getInteger("Block"));
    }

    public BaseDungeonComponent setComponentBounds(List<BlockBB> componentBounds)
    {
        this.componentBounds = componentBounds;
        return this;
    }

    public BaseDungeonComponent setFacing(ForgeDirection facing)
    {
        this.facing = facing;
        return this;
    }

    public BaseDungeonComponent setBlockSet(IBlockSet blockSet)
    {
        this.blockSet = blockSet;
        return this;
    }

    public BaseDungeonComponent setPatterns(List<IPattern> patterns)
    {
        this.patterns = patterns;
        return this;
    }
}
