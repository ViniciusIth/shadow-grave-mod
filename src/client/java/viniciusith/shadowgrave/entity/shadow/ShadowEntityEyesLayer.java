package viniciusith.shadowgrave.entity.shadow;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;
import viniciusith.shadowgrave.ShadowGraveMod;
import viniciusith.shadowgrave.entity.ShadowEntity;

public class ShadowEntityEyesLayer extends AutoGlowingGeoLayer<ShadowEntity> {

    private static final Identifier TEXTURE = new Identifier(ShadowGraveMod.MOD_ID, "textures/entity/shadow_eyes.png");

    public ShadowEntityEyesLayer(GeoRenderer<ShadowEntity> entityRendererIn) {
        super(entityRendererIn);
    }

    // Apply the glasses texture layer to the existing geo model, and render it over the top of the existing model
    @Override
    public void render(MatrixStack poseStack, ShadowEntity animatable, BakedGeoModel bakedModel, RenderLayer renderType, VertexConsumerProvider bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        RenderLayer armorRenderType = RenderLayer.getEyes(TEXTURE);

        getRenderer().reRender(
                getDefaultBakedModel(animatable), poseStack, bufferSource, animatable, armorRenderType, bufferSource.getBuffer(armorRenderType),
                partialTick,
                packedLight,
                OverlayTexture.DEFAULT_UV,
                1, 1, 1, 1);
    }
}
