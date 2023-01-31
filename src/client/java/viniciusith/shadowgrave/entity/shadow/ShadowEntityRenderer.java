package viniciusith.shadowgrave.entity.shadow;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import viniciusith.shadowgrave.ShadowGraveMod;
import viniciusith.shadowgrave.entity.ShadowEntity;

public class ShadowEntityRenderer extends GeoEntityRenderer<ShadowEntity> {
    public ShadowEntityRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new ShadowEntityModel());
    }

    @Override
    public Identifier getTexture(ShadowEntity animatable) {
        return new Identifier(ShadowGraveMod.MOD_ID, "textures/entity/shadow_overlay.png");
    }

    @Override
    public RenderLayer getRenderType(ShadowEntity animatable, Identifier texture, VertexConsumerProvider bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }
}
