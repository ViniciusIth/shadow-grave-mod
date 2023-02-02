package viniciusith.shadowgrave.registry;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import viniciusith.shadowgrave.ShadowGraveMod;


public class SoundRegistry {

    public static final Logger LOGGER = LoggerFactory.getLogger("shadowgrave");

    public static final Identifier SHADOW_SOUND_DETECT = new Identifier(ShadowGraveMod.MOD_ID, "shadowgrave.shadow.detect");
    public static final Identifier SHADOW_SOUND_AMBIENT = new Identifier(ShadowGraveMod.MOD_ID, "shadowgrave.shadow.ambient");

    public static SoundEvent SHADOW_SOUND_DETECT_EVENT = SoundEvent.of(SHADOW_SOUND_DETECT);
    public static SoundEvent SHADOW_SOUND_AMBIENT_EVENT = SoundEvent.of(SHADOW_SOUND_AMBIENT);

    public static void registerAllSounds() {
        LOGGER.info("Registering Sounds");
        Registry.register(Registries.SOUND_EVENT, SoundRegistry.SHADOW_SOUND_DETECT, SoundRegistry.SHADOW_SOUND_DETECT_EVENT);
        Registry.register(Registries.SOUND_EVENT, SoundRegistry.SHADOW_SOUND_AMBIENT, SoundRegistry.SHADOW_SOUND_AMBIENT_EVENT);
    }
}
