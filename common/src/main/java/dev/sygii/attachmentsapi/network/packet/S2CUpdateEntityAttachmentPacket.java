package dev.sygii.attachmentsapi.network.packet;

import dev.sygii.attachmentsapi.AttachmentsAPI;
import dev.sygii.attachmentsapi.registry.AttachmentManager;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class S2CUpdateEntityAttachmentPacket<V> {
    public static final ResourceLocation PACKET_ID = new ResourceLocation(AttachmentsAPI.MOD_ID, "update_attachment_from_server");
    protected final int entityid;
    protected final String id;
    private V value;

    public S2CUpdateEntityAttachmentPacket(FriendlyByteBuf buf) {
        this(buf.readInt(), buf.readUtf());
    }

    public S2CUpdateEntityAttachmentPacket(int entityid, String id) {
        this.entityid = entityid;
        this.id = id;
    }

    public S2CUpdateEntityAttachmentPacket(int entityid, String id, V value) {
        this.entityid = entityid;
        this.id = id;
        this.value = value;
    }

    public static <V> FriendlyByteBuf encode(int entityId, String id, V value) {
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        buf.writeInt(entityId);
        buf.writeUtf(id);

        AttachmentManager.WRITERS.get(id).run(buf, value);
        return buf;
    }

    public int getEntityId() {
        return this.entityid;
    }

    public String getId() {
        return this.id;
    }
}
