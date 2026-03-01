package dev.sygii.attachmentsapi.attachment.synced;

import dev.architectury.networking.NetworkManager;
import dev.sygii.attachmentsapi.attachment.AttachmentIdentifier;
import dev.sygii.attachmentsapi.network.packet.C2SRequestEntityAttachmentPacket;
import dev.sygii.attachmentsapi.network.packet.C2SUpdateEntityAttachmentPacket;
import dev.sygii.attachmentsapi.network.packet.S2CUpdateEntityAttachmentPacket;
import dev.sygii.attachmentsapi.registry.AttachmentManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

public class SyncedAttachment<V> {
    private final AttachmentIdentifier id;

    public SyncedAttachment(AttachmentIdentifier id) {
        this.id = id;
    }

    public V get(Object instance) {
        return (V) AttachmentManager.SYNCED_ACCESSORS.get(id.toString()).get(instance);
    }

    public void setAndSyncFromClient(Object instance, V value) {
        set(instance, value);
        syncFromClient(instance, value);
    }

    public void set(Object instance, V value) {
        AttachmentManager.SYNCED_ACCESSORS.get(id.toString()).set(instance, value);
    }

    public void syncFromClient(Object instance, V value) {
        if (instance instanceof Entity entity) {
            FriendlyByteBuf buf = C2SUpdateEntityAttachmentPacket.encode(entity.getUUID(), getId().toString(), value);
            NetworkManager.sendToServer(C2SUpdateEntityAttachmentPacket.PACKET_ID, buf);
        }
        /*if (instance instanceof PlayerEntity player) {
            net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking.send(new C2SUpdatePlayerAttachmentPacket<V>(getId().toString(), value));
        }*/
    }

    public void requestFromServer(Object instance) {
        if (instance instanceof Entity entity) {
            FriendlyByteBuf buf = C2SRequestEntityAttachmentPacket.encode(entity.getUUID(), getId().toString());
            NetworkManager.sendToServer(C2SRequestEntityAttachmentPacket.PACKET_ID, buf);
        }
    }

    public void syncFromServer(ServerPlayer player, Object instance, V value) {
        if (instance instanceof Entity entity) {
            FriendlyByteBuf buf = S2CUpdateEntityAttachmentPacket.encode(entity.getId(), getId().toString(), value);
            NetworkManager.sendToPlayer(player, S2CUpdateEntityAttachmentPacket.PACKET_ID, buf);
        }
    }

    public SyncedAttachment<V> registerNBTSerializers(NBTWriter<V> writer, NBTReader<V> reader) {
        AttachmentManager.NBTWRITERS.put(getId().toString(), writer);
        AttachmentManager.NBTREADERS.put(getId().toString(), reader);
        return this;
    }

    public interface Writer<T> {
        void run(FriendlyByteBuf buf, T value);
    }

    public interface Reader<T> {
        ContextRunner run(Context context, FriendlyByteBuf buf);
    }

    public interface ContextRunner<T> {
        T run(Context context, T instance);
    }

    public enum Context {
        CLIENT,
        SERVER;
    }

    public interface NBTWriter<T> {
        void run(CompoundTag nbt, T value);
    }

    public interface NBTReader<T> {
        T run(CompoundTag nbt, Object instance);
    }

    public AttachmentIdentifier getId() {
        return this.id;
    }
}
