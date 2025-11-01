package net.slimevoid.littleblocks.client.handlers;

import net.slimevoid.littleblocks.core.lib.ConfigurationLib;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

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
