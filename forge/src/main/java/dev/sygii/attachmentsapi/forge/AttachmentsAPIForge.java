package dev.sygii.attachmentsapi.forge;

import dev.sygii.attachmentsapi.AttachmentsAPI;
import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(AttachmentsAPI.MOD_ID)
public final class AttachmentsAPIForge {
    public AttachmentsAPIForge() {
        // Submit our event bus to let Architectury API register our content on the right time.
        EventBuses.registerModEventBus(AttachmentsAPI.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());

        // Run our common setup.
        AttachmentsAPI.init();
    }
}
