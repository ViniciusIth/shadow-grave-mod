package viniciusith.shadowgrave.entity.shadow;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import viniciusith.shadowgrave.ShadowGraveMod;
import viniciusith.shadowgrave.entity.ShadowEntity;

import java.util.UUID;

public class ShadowEntityRenderer extends GeoEntityRenderer<ShadowEntity> {
    public ShadowEntityRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new ShadowEntityModel());
    }

    @Override
    public Identifier getTexture(ShadowEntity animatable) {
        Identifier texture = new Identifier(ShadowGraveMod.MOD_ID, "textures/entity/shadow_overlay.png");

        PlayerListEntry playerListEntry = MinecraftClient.getInstance()
                .getNetworkHandler()
                .getPlayerListEntry(animatable.getOwner().orElse(UUID.randomUUID()));

        if (playerListEntry != null) {
            texture = playerListEntry.getSkinTexture();
        }

        return texture;
    }
}
