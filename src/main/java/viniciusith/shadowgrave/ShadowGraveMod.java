package viniciusith.shadowgrave;

import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import viniciusith.shadowgrave.entity.ShadowEntity;
import viniciusith.shadowgrave.registry.EntityRegistry;
import viniciusith.shadowgrave.registry.SoundRegistry;

public class ShadowGraveMod implements ModInitializer {
    public static final String MOD_ID = "shadowgrave";

    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("shadowgrave");

    public static void spawnShadow(World world, Vec3d pos, PlayerEntity player) {
        if (world.isClient) return;

        DefaultedList<ItemStack> combinedInventory = DefaultedList.of();

        combinedInventory.addAll(player.getInventory().main);
        combinedInventory.addAll(player.getInventory().armor);
        combinedInventory.addAll(player.getInventory().offHand);

        ShadowEntity shadow = new ShadowEntity(EntityRegistry.SHADOW_ENTITY, world);
        shadow.setPersistent();
        shadow.setPos(pos.x, pos.y, pos.z);

//        shadow.setSpawnPos(pos);
//        shadow.setItems(combinedInventory);
//        shadow.setXp(player.totalExperience);
        shadow.setOwner(player.getUuid());

        world.spawnEntity(shadow);


        player.totalExperience = 0;
        player.experienceProgress = 0;
        player.experienceLevel = 0;
        player.getInventory().clear();
    }

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        LOGGER.info("Hello Fabric world!");

        SoundRegistry.registerAllSounds();
        EntityRegistry.registerAllEntities();
    }
}