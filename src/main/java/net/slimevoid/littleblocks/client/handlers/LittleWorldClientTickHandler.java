package net.slimevoid.littleblocks.client.handlers;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.slimevoid.littleblocks.core.lib.ConfigurationLib;


public class LittleWorldClientTickHandler {
    @SubscribeEvent
    public void onServerTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            if (ConfigurationLib.littleWorldClient != null) {
                ConfigurationLib.littleWorldClient.tick();
            }
        }
    }
}
