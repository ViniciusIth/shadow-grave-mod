package viniciusith.shadowgrave.registry;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import viniciusith.shadowgrave.ShadowGraveMod;
import viniciusith.shadowgrave.entity.ShadowEntity;

public class EntityRegistry {
    public static final Logger LOGGER = LoggerFactory.getLogger("shadowgrave");
    public static final EntityType<ShadowEntity> SHADOW_ENTITY = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(ShadowGraveMod.MOD_ID, "shadow"),
            FabricEntityTypeBuilder
                    .create(SpawnGroup.CREATURE, ShadowEntity::new)
                    .dimensions(EntityDimensions.fixed(1f, 2f))
                    .build());


    public static void registerAllEntities() {
        LOGGER.info("Registering Entities");

        FabricDefaultAttributeRegistry.register(SHADOW_ENTITY, ShadowEntity.setAttributes());
    }
}
