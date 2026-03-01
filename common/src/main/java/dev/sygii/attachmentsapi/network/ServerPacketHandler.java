package dev.sygii.attachmentsapi.network;

import dev.architectury.networking.NetworkManager;
import dev.sygii.attachmentsapi.attachment.synced.SyncedAttachment;
import dev.sygii.attachmentsapi.network.packet.C2SRequestEntityAttachmentPacket;
import dev.sygii.attachmentsapi.network.packet.C2SUpdateEntityAttachmentPacket;
import dev.sygii.attachmentsapi.network.packet.S2CUpdateEntityAttachmentPacket;
import dev.sygii.attachmentsapi.registry.AttachmentManager;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

import java.util.Objects;
import java.util.UUID;

public class ServerPacketHandler {

    public static void init() {
        NetworkManager.registerReceiver(NetworkManager.Side.C2S, C2SUpdateEntityAttachmentPacket.PACKET_ID, (buf, context) -> {
            UUID entityId = buf.readUUID();
            String id = buf.readUtf();
            SyncedAttachment.ContextRunner runner = AttachmentManager.READERS.get(id).run(SyncedAttachment.Context.SERVER, buf);


            //Object asd = AttachmentManager.READERS.get(id).readServer(buf);
            context.queue(() -> {
                var server = context.getPlayer().getServer();
                if (server == null) return;

                for (ServerLevel serverWorld : server.getAllLevels()) {
                    Entity entity = serverWorld.getEntity(entityId);
                    if (entity != null && Objects.equals(entity.getClass(), AttachmentManager.SYNCED_ACCESSORS.get(id).getTarget())) {
                        Object returnedObj = runner.run(SyncedAttachment.Context.SERVER, AttachmentManager.SYNCED_ACCESSORS.get(id).get(entity));

                        AttachmentManager.SYNCED_ACCESSORS.get(id).set(entity, returnedObj);
                    }
                }
            });
        });

        NetworkManager.registerReceiver(NetworkManager.Side.C2S, C2SRequestEntityAttachmentPacket.PACKET_ID, (buf, context) -> {
            UUID entityUuid = buf.readUUID();
            String id = buf.readUtf();

            context.queue(() -> {
                var server = context.getPlayer().getServer();
                if (server == null) return;

                Object asd;
                for (ServerLevel serverWorld : server.getAllLevels()) {
                    Entity entity = serverWorld.getEntity(entityUuid);
                    Class<?> clazz = AttachmentManager.SYNCED_ACCESSORS.get(id).getTarget();
                    if (entity != null && clazz.isInstance(entity)) {
                        asd = AttachmentManager.SYNCED_ACCESSORS.get(id).get(entity);
                        var responseBuf = S2CUpdateEntityAttachmentPacket.encode(entity.getId(), id, asd);

                        NetworkManager.sendToPlayer((ServerPlayer) context.getPlayer(), S2CUpdateEntityAttachmentPacket.PACKET_ID, responseBuf);
                    }
                }
            });
        });

        /*ServerPlayNetworking.registerGlobalReceiver(C2SUpdatePlayerAttachmentPacket.PACKET_ID, (server, player, handler, buf, responseSender) -> {
            String id = buf.readString();
            Object asd = AttachmentManager.READERS.get(id).run(buf);
            server.execute(() -> {
                AttachmentManager.SYNCED_ACCESSORS.get(id).set(player, asd);
            });
        });*/
    }

}
