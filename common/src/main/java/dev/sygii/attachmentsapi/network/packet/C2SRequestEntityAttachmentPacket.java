package dev.sygii.attachmentsapi.network.packet;

import dev.sygii.attachmentsapi.AttachmentsAPI;
import dev.sygii.attachmentsapi.registry.AttachmentManager;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.UUID;

public class C2SRequestEntityAttachmentPacket<V> {
    public static final ResourceLocation PACKET_ID = new ResourceLocation(AttachmentsAPI.MOD_ID, "request_attachment");
    protected final UUID uuid;
    protected final String id;
    private V value;

    public C2SRequestEntityAttachmentPacket(FriendlyByteBuf buf) {
        this(buf.readUUID(), buf.readUtf());
    }

    public C2SRequestEntityAttachmentPacket(UUID uuid, String id) {
        this.uuid = uuid;
        this.id = id;
    }

    public static <V> FriendlyByteBuf encode(UUID uuid, String id) {
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        buf.writeUUID(uuid);
        buf.writeUtf(id);
        return buf;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public String getId() {
        return this.id;
    }
}
