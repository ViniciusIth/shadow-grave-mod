package viniciusith.shadowgrave.registry;

import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import viniciusith.shadowgrave.entity.shadow.ShadowEntityRenderer;

public class EntityRegistryClient {

    public static void registerRenderers() {
        EntityRendererRegistry.register(EntityRegistry.SHADOW_ENTITY, ShadowEntityRenderer::new);
    }

}
