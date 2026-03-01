package dev.sygii.attachmentsapi.network;

import dev.architectury.networking.NetworkManager;
import dev.sygii.attachmentsapi.attachment.synced.SyncedAttachment;
import dev.sygii.attachmentsapi.network.packet.S2CUpdateEntityAttachmentPacket;
import dev.sygii.attachmentsapi.registry.AttachmentManager;
import net.minecraft.world.entity.Entity;

public class ClientPacketHandler {

    public static void init() {
        NetworkManager.registerReceiver(NetworkManager.Side.S2C, S2CUpdateEntityAttachmentPacket.PACKET_ID, (buf, context) -> {
            int entityId = buf.readInt();
            String id = buf.readUtf();

            SyncedAttachment.ContextRunner runner = AttachmentManager.READERS.get(id).run(SyncedAttachment.Context.CLIENT, buf);

            context.queue(() -> {
                Entity entity = context.getPlayer().level().getEntity(entityId);
                if (entity != null) {
                    Object returnedObj = runner.run(SyncedAttachment.Context.CLIENT, AttachmentManager.SYNCED_ACCESSORS.get(id).get(entity));
                    AttachmentManager.SYNCED_ACCESSORS.get(id).set(entity, returnedObj);
                }
            });
        });
    }

}
