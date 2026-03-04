package dev.sygii.attachmentsapi.gametest;

import dev.sygii.attachmentsapi.AttachmentsAPI;
import dev.sygii.attachmentsapi.attachment.AttachmentIdentifier;
import dev.sygii.attachmentsapi.attachment.synced.SyncedAttachment;
import dev.sygii.attachmentsapi.gametest.block.TestSoftSideBlock;
import dev.sygii.attachmentsapi.registry.AttachmentDeclarer;
import dev.sygii.attachmentsapi.registry.AttachmentInitializer;
import dev.sygii.attachmentsapi.registry.AttachmentRegistrar;
import dev.sygii.attachmentsapi.registry.SyncedAttachmentRegistrar;
import net.minecraft.world.level.block.Block;

public class TestAttachments implements AttachmentInitializer {
    public static AttachmentIdentifier testSoftSideBlockId = AttachmentIdentifier.of(AttachmentsAPI.MOD_ID + "-tests", "test");
    public static SyncedAttachment<TestSoftSideBlock> test;

    @Override
    public void declareAttachments(AttachmentDeclarer declarer) {
        declarer.declareAttachment(
                testSoftSideBlockId,
                Block.class.getName(),
                Block.class, null
        );
    }

    @Override
    public void registerAttachments(AttachmentRegistrar registrar) {

    }

    @Override
    public void registerSyncedAttachments(SyncedAttachmentRegistrar syncedRegistrar) {

    }
}
