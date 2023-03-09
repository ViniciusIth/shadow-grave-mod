package viniciusith.shadowgrave.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Optional;
import java.util.UUID;

public class ShadowEntity extends HostileEntity implements GeoEntity {
    protected static final TrackedData<Optional<UUID>> OWNER_UUID;
    protected static final String OWNER_UUID_NAME = "owner_uuid";

    static {
        OWNER_UUID = DataTracker.registerData(ShadowEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
    }

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public ShadowEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);

        this.setNoGravity(true);
        this.noClip = true;
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 0.5f)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 8f)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 2f)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.4f);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();

        this.dataTracker.startTracking(OWNER_UUID, Optional.empty());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);

        this.setOwner(nbt.getUuid(OWNER_UUID_NAME));
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);

        nbt.putUuid(OWNER_UUID_NAME, this.getOwner().orElse(UUID.randomUUID()));
    }

    public Optional<UUID> getOwner() {
        return this.dataTracker.get(OWNER_UUID);
    }

    public void setOwner(UUID ownerUuid) {
        this.dataTracker.set(OWNER_UUID, Optional.of(ownerUuid));
    }

    // GeckoLib methods
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

}
