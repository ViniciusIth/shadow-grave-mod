package viniciusith.shadowgrave.entity.shadow;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;
import viniciusith.shadowgrave.ShadowGraveMod;
import viniciusith.shadowgrave.entity.ShadowEntity;

public class ShadowEntityModel extends GeoModel<ShadowEntity> {
    @Override
    public Identifier getModelResource(ShadowEntity animatable) {
        return new Identifier(ShadowGraveMod.MOD_ID, "geo/entity/shadow.geo.json");
    }

    @Override
    public Identifier getTextureResource(ShadowEntity animatable) {
        return new Identifier(ShadowGraveMod.MOD_ID, "textures/entity/shadow_mask.png");
    }

    @Override
    public RenderLayer getRenderType(ShadowEntity animatable, Identifier texture) {
        return RenderLayer.getEntityTranslucent(texture);
    }

    @Override
    public Identifier getAnimationResource(ShadowEntity animatable) {
        return null;
    }
}
