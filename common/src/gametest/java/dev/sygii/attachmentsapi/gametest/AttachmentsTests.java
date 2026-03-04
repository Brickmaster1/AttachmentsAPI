package dev.sygii.attachmentsapi.gametest;

import dev.sygii.attachmentsapi.AttachmentsAPI;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;

public class AttachmentsTests {
    public static final String TEMPLATE_EMPTY = AttachmentsAPI.MOD_ID  + ":empty";

    @GameTest(template = TEMPLATE_EMPTY)
    public void testAttachment(GameTestHelper context) {
        context.succeedIf(() -> {

        });
    }
}
