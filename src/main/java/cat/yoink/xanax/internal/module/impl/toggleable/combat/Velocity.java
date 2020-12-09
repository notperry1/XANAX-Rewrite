package cat.yoink.xanax.internal.module.impl.toggleable.combat;

import cat.yoink.xanax.internal.event.impl.CollisionEvent;
import cat.yoink.xanax.internal.event.impl.PacketEvent;
import cat.yoink.xanax.internal.event.impl.WaterPushEvent;
import cat.yoink.xanax.internal.module.ModuleCategory;
import cat.yoink.xanax.internal.module.main.ModuleData;
import cat.yoink.xanax.internal.module.state.StateModule;
import cat.yoink.xanax.internal.setting.types.NumberSetting;
import cat.yoink.xanax.internal.setting.types.StateSetting;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;
import net.minecraftforge.client.event.PlayerSPPushOutOfBlocksEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author yoink
 */
@ModuleData(name = "Velocity", category = ModuleCategory.COMBAT)
public final class Velocity extends StateModule
{
    private final StateSetting velocity = addSetting(new StateSetting("Velocity", true));
    private final StateSetting explosions = addSetting(new StateSetting("Explosions", true));
    private final NumberSetting horizontal = addSetting(new NumberSetting("Horizontal", v -> velocity.getValue() || explosions.getValue(), 0, 0, 100, 1));
    private final NumberSetting vertical = addSetting(new NumberSetting("Vertical", v -> velocity.getValue() || explosions.getValue(), 0, 0, 100, 1));
    private final StateSetting fishable = addSetting(new StateSetting("Fishable", false));
    private final StateSetting noPush = addSetting(new StateSetting("NoPush", true));

    @SubscribeEvent
    public void onPlayerSPPushOutOfBlocks(PlayerSPPushOutOfBlocksEvent event)
    {
        if (noPush.getValue() && event.getEntity().equals(mc.player)) event.setCanceled(true);
    }

    @SubscribeEvent
    public void onPacket(PacketEvent event)
    {
        if (isSafe())
        {
            if (event.getPacket() instanceof SPacketEntityStatus && !fishable.getValue() && ((SPacketEntityStatus) event.getPacket()).getOpCode() == 31 && ((SPacketEntityStatus) event.getPacket()).getEntity(mc.world) instanceof EntityFishHook && ((EntityFishHook) ((SPacketEntityStatus) event.getPacket()).getEntity(mc.world)).caughtEntity.equals(mc.player))
            {
                event.setCanceled(true);
            }

            if (event.getPacket() instanceof SPacketEntityVelocity && velocity.getValue() && ((SPacketEntityVelocity) event.getPacket()).getEntityID() == mc.player.getEntityId())
            {
                SPacketEntityVelocity packet = (SPacketEntityVelocity) event.getPacket();

                packet.motionX = packet.motionX / 100 * horizontal.getValue().intValue();
                packet.motionY = packet.motionY / 100 * vertical.getValue().intValue();
                packet.motionZ = packet.motionZ / 100 * horizontal.getValue().intValue();
            }

            if (event.getPacket() instanceof SPacketExplosion && explosions.getValue())
            {
                SPacketExplosion packet = ((SPacketExplosion) event.getPacket());

                packet.motionX = packet.motionX / 100 * horizontal.getValue().intValue();
                packet.motionY = packet.motionY / 100 * vertical.getValue().intValue();
                packet.motionZ = packet.motionZ / 100 * horizontal.getValue().intValue();
            }
        }
    }

    @SubscribeEvent
    public void onWaterPush(WaterPushEvent event)
    {
        if (noPush.getValue()) event.setCanceled(true);
    }

    @SubscribeEvent
    public void onCollision(CollisionEvent event)
    {
        if (noPush.getValue()) event.setCanceled(true);
    }
}
