package dev.sygii.attachmentsapi.mixin.entity;

import dev.sygii.attachmentsapi.AttachmentsAPI;
import dev.sygii.attachmentsapi.attachment.synced.SyncedAttachment;
import dev.sygii.attachmentsapi.registry.AttachmentManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Debug(export = true)
@Mixin(Entity.class)
public class EntityMixin {

    Entity entity = ((Entity)(Object)this);

    @Inject(method = "saveWithoutId", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;addAdditionalSaveData(Lnet/minecraft/nbt/CompoundTag;)V"))
    protected void writeAttachmentNbt(CompoundTag nbt, CallbackInfoReturnable<CompoundTag> cir) {
        //NbtList nbtList = new NbtList();
        CompoundTag attachments = new CompoundTag();
        /*for (Variant var :  ((EntityAccess)entity).getVariantOverlays()) {
            nbtList.add(NbtString.of(var.id().toString()));
        }*/
        for (Map.Entry<String, SyncedAttachment.NBTWriter> entry : AttachmentManager.NBTWRITERS.entrySet()) {
            //NbtList attachmentList = new NbtList();
            CompoundTag comp = new CompoundTag();

            Class<?> clazz = AttachmentManager.SYNCED_ACCESSORS.get(entry.getKey()).getTarget();
            if (clazz.isInstance(entity)) {
                entry.getValue().run(comp, AttachmentManager.SYNCED_ACCESSORS.get(entry.getKey()).get(entity));

                attachments.put(entry.getKey(), comp);
                //attachments.add(comp);
                nbt.put(AttachmentsAPI.ATTACHMENT_NBT_KEY, attachments);
                //System.out.println(nbt.toString());
            }
        }
        //nbt.put(AttachmentsAPI.ATTACHMENT_NBT_KEY, nbtList);
        //System.out.println(nbt.toString());
    }

    @Inject(method = "load", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;readAdditionalSaveData(Lnet/minecraft/nbt/CompoundTag;)V"))
    protected void onReadCustomDataFromNbt(CompoundTag nbt, CallbackInfo ci) {
        if (nbt.contains(AttachmentsAPI.ATTACHMENT_NBT_KEY)) {
            CompoundTag attachments = nbt.getCompound(AttachmentsAPI.ATTACHMENT_NBT_KEY);

            for (Map.Entry<String, SyncedAttachment.NBTReader> entry : AttachmentManager.NBTREADERS.entrySet()) {
                Class<?> clazz = AttachmentManager.SYNCED_ACCESSORS.get(entry.getKey()).getTarget();
                if (clazz.isInstance(entity)) {
                    CompoundTag comp = attachments.getCompound(entry.getKey());
                    //System.out.println(comp);
                    /*System.out.println(attachments);
                    System.out.println(list.getString(0));*/
                    Object current = AttachmentManager.SYNCED_ACCESSORS.get(entry.getKey()).get(entity);
                    AttachmentManager.SYNCED_ACCESSORS.get(entry.getKey()).set(entity, entry.getValue().run(comp, current));
                }
            }
        }
    }

}
