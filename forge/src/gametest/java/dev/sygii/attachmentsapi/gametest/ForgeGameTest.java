package dev.sygii.attachmentsapi.gametest;

import dev.sygii.attachmentsapi.AttachmentsAPI;
import net.minecraft.gametest.framework.*;
import net.minecraftforge.event.RegisterGameTestsEvent;
import net.minecraftforge.fml.common.Mod;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ConfigurationBuilder;

@Mod.EventBusSubscriber(modid = AttachmentsAPI.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ForgeGameTest {
    public void registerTests(RegisterGameTestsEvent event) {
        new Reflections(
                new ConfigurationBuilder()
                        .forPackages(ForgeGameTest.class.getPackageName())
                        .addScanners(Scanners.SubTypes)
        ).getSubTypesOf(Object.class).forEach(event::register);
    }
}
