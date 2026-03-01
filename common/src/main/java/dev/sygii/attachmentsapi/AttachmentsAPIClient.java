package dev.sygii.attachmentsapi;

import dev.sygii.attachmentsapi.network.ClientPacketHandler;
import net.fabricmc.api.ClientModInitializer;

public class AttachmentsAPIClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		/*ClsientEntityEvents.ENTITY_LOAD.register((entity, world) -> {
			MyAttachments.testingSynced.requestFromServer(entity);
		});
		UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
			if (entity instanceof LivingEntity living) {
				System.out.println(MyAttachments.testingSynced.get(living));
				return ActionResult.PASS;
			}
			return ActionResult.FAIL;
		});*/
		ClientPacketHandler.init();
	}
}
