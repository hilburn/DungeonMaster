package dungeonmaster.api;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockBB
{
    public int minX;
    public int minY;
    public int minZ;
    public int maxX;
    public int maxY;
    public int maxZ;

    public static BlockBB getBoundingBox(int minX, int minY, int minZ, int maxX, int maxY, int maxZ)
    {
        return new BlockBB(minX, minY, minZ, maxX, maxY, maxZ);
    }

    protected BlockBB(int minX, int minY, int minZ, int maxX, int maxY, int maxZ)
    {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
    }

    protected BlockBB(NBTTagCompound tagCompound)
    {
        int[] val = tagCompound.getIntArray("val");
        minX = val[0];
        minY = val[1];
        minZ = val[2];
        maxX = val[3];
        maxY = val[4];
        maxZ = val[5];
    }

    public BlockBB setBounds(int minX, int minY, int minZ, int maxX, int maxY, int maxZ)
    {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
        return this;
    }

    public BlockBB addCoord(int x, int y, int z)
    {
        int d3 = this.minX;
        int d4 = this.minY;
        int d5 = this.minZ;
        int d6 = this.maxX;
        int d7 = this.maxY;
        int d8 = this.maxZ;

        if (x < 0.0D)
        {
            d3 += x;
        }

        if (x > 0.0D)
        {
            d6 += x;
        }

        if (y < 0.0D)
        {
            d4 += y;
        }

        if (y > 0.0D)
        {
            d7 += y;
        }

        if (z < 0.0D)
        {
            d5 += z;
        }

        if (z > 0.0D)
        {
            d8 += z;
        }

        /**
         * Returns a bounding box with the specified bounds. Args: minX, minY, minZ, maxX, maxY, maxZ
         */
        return getBoundingBox(d3, d4, d5, d6, d7, d8);
    }

    /**
     * Returns a bounding box expanded by the specified vector (if negative numbers are given it will shrink). Args: x,
     * y, z
     */
    public BlockBB expand(int p_72314_1_, int p_72314_3_, int p_72314_5_)
    {
        int d3 = this.minX - p_72314_1_;
        int d4 = this.minY - p_72314_3_;
        int d5 = this.minZ - p_72314_5_;
        int d6 = this.maxX + p_72314_1_;
        int d7 = this.maxY + p_72314_3_;
        int d8 = this.maxZ + p_72314_5_;
        /**
         * Returns a bounding box with the specified bounds. Args: minX, minY, minZ, maxX, maxY, maxZ
         */
        return getBoundingBox(d3, d4, d5, d6, d7, d8);
    }

    public BlockBB expandToFit(BlockBB other)
    {
        int d0 = Math.min(this.minX, other.minX);
        int d1 = Math.min(this.minY, other.minY);
        int d2 = Math.min(this.minZ, other.minZ);
        int d3 = Math.max(this.maxX, other.maxX);
        int d4 = Math.max(this.maxY, other.maxY);
        int d5 = Math.max(this.maxZ, other.maxZ);
        /**
         * Returns a bounding box with the specified bounds. Args: minX, minY, minZ, maxX, maxY, maxZ
         */
        return getBoundingBox(d0, d1, d2, d3, d4, d5);
    }

    /**
     * Returns a bounding box offseted by the specified vector (if negative numbers are given it will shrink). Args: x,
     * y, z
     */
    public BlockBB getOffsetBoundingBox(int x, int y, int z)
    {
        /**
         * Returns a bounding box with the specified bounds. Args: minX, minY, minZ, maxX, maxY, maxZ
         */
        return getBoundingBox(this.minX + x, this.minY + y, this.minZ + z, this.maxX + x, this.maxY + y, this.maxZ + z);
    }

    /**
     * if instance and the argument bounding boxes overlap in the Y and Z dimensions, calculate the offset between them
     * in the X dimension.  return var2 if the bounding boxes do not overlap or if var2 is closer to 0 then the
     * calculated offset.  Otherwise return the calculated offset.
     */
    public int calculateXOffset(BlockBB other, int border)
    {
        if (other.maxY > this.minY && other.minY < this.maxY)
        {
            if (other.maxZ > this.minZ && other.minZ < this.maxZ)
            {
                int d1;

                if (border > 0.0D && other.maxX <= this.minX)
                {
                    d1 = this.minX - other.maxX;

                    if (d1 < border)
                    {
                        border = d1;
                    }
                }

                if (border < 0.0D && other.minX >= this.maxX)
                {
                    d1 = this.maxX - other.minX;

                    if (d1 > border)
                    {
                        border = d1;
                    }
                }

                return border;
            }
            else
            {
                return border;
            }
        }
        else
        {
            return border;
        }
    }

    /**
     * if instance and the argument bounding boxes overlap in the X and Z dimensions, calculate the offset between them
     * in the Y dimension.  return var2 if the bounding boxes do not overlap or if var2 is closer to 0 then the
     * calculated offset.  Otherwise return the calculated offset.
     */
    public int calculateYOffset(BlockBB other, int border)
    {
        if (other.maxX > this.minX && other.minX < this.maxX)
        {
            if (other.maxZ > this.minZ && other.minZ < this.maxZ)
            {
                int d1;

                if (border > 0.0D && other.maxY <= this.minY)
                {
                    d1 = this.minY - other.maxY;

                    if (d1 < border)
                    {
                        border = d1;
                    }
                }

                if (border < 0.0D && other.minY >= this.maxY)
                {
                    d1 = this.maxY - other.minY;

                    if (d1 > border)
                    {
                        border = d1;
                    }
                }

                return border;
            }
            else
            {
                return border;
            }
        }
        else
        {
            return border;
        }
    }

    /**
     * if instance and the argument bounding boxes overlap in the Y and X dimensions, calculate the offset between them
     * in the Z dimension.  return var2 if the bounding boxes do not overlap or if var2 is closer to 0 then the
     * calculated offset.  Otherwise return the calculated offset.
     */
    public int calculateZOffset(BlockBB other, int border)
    {
        if (other.maxX > this.minX && other.minX < this.maxX)
        {
            if (other.maxY > this.minY && other.minY < this.maxY)
            {
                int d1;

                if (border > 0.0D && other.maxZ <= this.minZ)
                {
                    d1 = this.minZ - other.maxZ;

                    if (d1 < border)
                    {
                        border = d1;
                    }
                }

                if (border < 0.0D && other.minZ >= this.maxZ)
                {
                    d1 = this.maxZ - other.minZ;

                    if (d1 > border)
                    {
                        border = d1;
                    }
                }

                return border;
            }
            else
            {
                return border;
            }
        }
        else
        {
            return border;
        }
    }

    /**
     * Returns whether the given bounding box intersects with this one. Args: BlockBB
     */
    public boolean intersectsWith(BlockBB other)
    {
        return !(other.maxX < this.minX || other.minX > this.maxX || other.maxY < this.minY || other.minY > this.maxY || other.maxZ < this.minZ || other.minZ > this.maxZ);
    }

    /**
     * Offsets the current bounding box by the specified coordinates. Args: x, y, z
     */
    public BlockBB offset(int x, int y, int z)
    {
        this.minX += x;
        this.minY += y;
        this.minZ += z;
        this.maxX += x;
        this.maxY += y;
        this.maxZ += z;
        return this;
    }

    /**
     * Returns if the supplied Vec3D is completely inside the bounding box
     */
    public boolean isVecInside(Vec3 vector)
    {
        return vector.xCoord > this.minX && vector.xCoord < this.maxX && (vector.yCoord > this.minY && vector.yCoord < this.maxY && vector.zCoord > this.minZ && vector.zCoord < this.maxZ);
    }

    /**
     * Returns the average length of the edges of the bounding box.
     */
    public double getAverageEdgeLength()
    {
        int d0 = this.maxX - this.minX;
        int d1 = this.maxY - this.minY;
        int d2 = this.maxZ - this.minZ;
        return (d0 + d1 + d2) / 3.0D;
    }

    /**
     * Returns a bounding box that is inset by the specified amounts
     */
    public BlockBB contract(int x, int y, int z)
    {
        int d3 = this.minX + x;
        int d4 = this.minY + y;
        int d5 = this.minZ + z;
        int d6 = this.maxX - x;
        int d7 = this.maxY - y;
        int d8 = this.maxZ - z;
        /**
         * Returns a bounding box with the specified bounds. Args: minX, minY, minZ, maxX, maxY, maxZ
         */
        return getBoundingBox(d3, d4, d5, d6, d7, d8);
    }

    /**
     * Returns a copy of the bounding box.
     */
    public BlockBB copy()
    {
        /**
         * Returns a bounding box with the specified bounds. Args: minX, minY, minZ, maxX, maxY, maxZ
         */
        return getBoundingBox(this.minX, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ);
    }

    /**
     * Checks if the specified vector is within the YZ dimensions of the bounding box. Args: Vec3D
     */
    private boolean isVecInYZ(Vec3 vector)
    {
        return vector != null && vector.yCoord >= this.minY && vector.yCoord <= this.maxY && vector.zCoord >= this.minZ && vector.zCoord <= this.maxZ;
    }

    /**
     * Checks if the specified vector is within the XZ dimensions of the bounding box. Args: Vec3D
     */
    private boolean isVecInXZ(Vec3 vector)
    {
        return vector != null && vector.xCoord >= this.minX && vector.xCoord <= this.maxX && vector.zCoord >= this.minZ && vector.zCoord <= this.maxZ;
    }

    /**
     * Checks if the specified vector is within the XY dimensions of the bounding box. Args: Vec3D
     */
    private boolean isVecInXY(Vec3 vector)
    {
        return vector != null && vector.xCoord >= this.minX && vector.xCoord <= this.maxX && vector.yCoord >= this.minY && vector.yCoord <= this.maxY;
    }

    /**
     * Sets the bounding box to the same bounds as the bounding box passed in. Args: BlockBB
     */
    public void setBB(BlockBB other)
    {
        this.minX = other.minX;
        this.minY = other.minY;
        this.minZ = other.minZ;
        this.maxX = other.maxX;
        this.maxY = other.maxY;
        this.maxZ = other.maxZ;
    }

    public BlockBB getFace(ForgeDirection direction)
    {
        switch (direction)
        {
            case DOWN:
                return new BlockBB(minX, minY, minZ, maxX, minY, maxZ);
            case UP:
                return new BlockBB(minX, maxY, minZ, maxX, maxY, maxZ);
            case NORTH:
                return new BlockBB(minX, minY, minZ, maxX, maxY, minZ);
            case SOUTH:
                return new BlockBB(minX, minY, maxZ, maxX, maxY, maxZ);
            case EAST:
                return new BlockBB(maxX, minY, minZ, maxX, maxY, maxZ);
            case WEST:
                return new BlockBB(minX, minY, minZ, minX, maxY, maxZ);
        }
        return copy();
    }

    public NBTTagCompound writeToNBT()
    {
        NBTTagCompound result = new NBTTagCompound();
        result.setIntArray("val", new int[]{minX, minY, minZ, maxX, maxY, maxZ});
        return result;
    }

    public BlockBB readFromNBT(NBTTagCompound tagCompound)
    {
        return new BlockBB(tagCompound);
    }

    public static BlockBB loadFromNBT(NBTTagCompound tagCompound)
    {
        return new BlockBB(tagCompound);
    }

    public String toString()
    {
        return "box[" + this.minX + ", " + this.minY + ", " + this.minZ + " -> " + this.maxX + ", " + this.maxY + ", " + this.maxZ + "]";
    }
}
