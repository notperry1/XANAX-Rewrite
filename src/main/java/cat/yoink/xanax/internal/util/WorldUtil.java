package cat.yoink.xanax.internal.util;

import cat.yoink.xanax.internal.traits.Minecraft;
import net.minecraft.util.math.BlockPos;

/**
 * @author yoink
 */
public final class WorldUtil implements Minecraft
{
    @SuppressWarnings("deprecation")
    public static boolean isBreakable(BlockPos pos)
    {
        return mc.world.getBlockState(pos).getBlock().getBlockHardness(mc.world.getBlockState(pos), mc.world, pos) != -1;
    }
}
