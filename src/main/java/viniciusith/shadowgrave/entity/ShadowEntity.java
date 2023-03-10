package viniciusith.shadowgrave.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;
import viniciusith.shadowgrave.core.DeathHelper;

import java.util.Optional;
import java.util.UUID;

public class ShadowEntity extends HostileEntity implements GeoEntity {
    protected static final TrackedData<Optional<UUID>> OWNER_UUID;
    protected static final String OWNER_UUID_NAME = "owner_uuid";

    static {
        OWNER_UUID = DataTracker.registerData(ShadowEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
    }

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private DefaultedList<ItemStack> storedInventory;
    private int storedXp;

    public ShadowEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);

        this.setNoGravity(true);
        this.noClip = true;

        this.storedXp = 0;
        this.storedInventory = DefaultedList.ofSize(41, ItemStack.EMPTY);
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

        Inventories.readNbt(nbt.getCompound("Items"), this.storedInventory);
        this.storedXp = nbt.getInt("StoredXp");
        this.setOwner(nbt.getUuid(OWNER_UUID_NAME));
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);

        nbt.put("Items", Inventories.writeNbt(new NbtCompound(), this.storedInventory, true));
        nbt.putInt("ItemCount", this.storedInventory.size());
        nbt.putInt("StoredXp", storedXp);
        nbt.putUuid(OWNER_UUID_NAME, this.getOwner().orElse(UUID.randomUUID()));
    }

    @Override
    public int getXpToDrop() {
        return this.storedXp;
    }

    @Override
    public void onDeath(DamageSource damageSource) {
        this.dead = true;

        PlayerEntity entity = (PlayerEntity) damageSource.getAttacker();

        DeathHelper.retrieveItems(entity, this, this.world);
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        boolean attackerIsPlayer = source.getAttacker() != null && source.getAttacker().isPlayer();

        if (source.isOutOfWorld() || (attackerIsPlayer)) {
            return super.damage(source, amount);
        }

        return false;
    }

    public Optional<UUID> getOwner() {
        return this.dataTracker.get(OWNER_UUID);
    }

    public void setOwner(UUID ownerUuid) {
        this.dataTracker.set(OWNER_UUID, Optional.of(ownerUuid));
    }

    public int getXp() {
        return storedXp;
    }

    public void setXp(int xp) {
        this.storedXp = xp;
    }

    public DefaultedList<ItemStack> getStoredInventory() {
        return storedInventory;
    }

    public void setStoredInventory(DefaultedList<ItemStack> inventory) {
        this.storedInventory = inventory;
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
