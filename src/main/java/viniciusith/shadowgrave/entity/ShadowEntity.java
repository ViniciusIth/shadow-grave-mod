package viniciusith.shadowgrave.entity;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class ShadowEntity extends HostileEntity implements GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private GameProfile shadowOwner;
    private DefaultedList<ItemStack> items = DefaultedList.ofSize(41, ItemStack.EMPTY);
    private Vec3d spawnPos;
    private int xp;

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

    public PlayerEntity getOwner() {
        return this.owner;
    }

    public GameProfile getOwner() {
        return this.shadowOwner;
    }

    public void setOwner(GameProfile owner) {
        this.shadowOwner = owner;
        this.setCustomName(Text.of("Shadow of " + owner.getName()));
    }

    public DefaultedList<ItemStack> getItems() {
        return this.items;
    }

    public void setItems(DefaultedList<ItemStack> items) {
        this.items = items;
    }

    public Vec3d getSpawnPos() {
        return this.spawnPos;
    }

    public void setSpawnPos(Vec3d pos) {
        this.spawnPos = pos;
    }

    public int getXp() {
        return this.xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public void setName(String name) {
        this.setCustomName(Text.of("Shadow of " + name));
    }

    @Override
    protected void initGoals() {
        super.initGoals();

        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(3, new MeleeAttackGoal(this, 1d, false));
        this.goalSelector.add(4, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F, 1.0F));
        this.goalSelector.add(5, new WanderAroundGoal(this, 1d));

        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }

    @Override
    public void onDeath(DamageSource damageSource) {
        Entity attacker = damageSource.getAttacker();
        PlayerInventory ownerInventory = this.owner.getInventory();

        assert attacker != null;
        retrieveItems(attacker);

        super.onDeath(damageSource);
    }

    private void retrieveItems(Entity attacker) {
        World world = attacker.world;

        if (world.isClient) return;

        ItemScatterer.spawn(attacker.world, this.getBlockPos(), this.items);
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        boolean attackerIsPlayer = (source.getAttacker() != null && source.getAttacker().isPlayer());

        if (source.isOutOfWorld() || attackerIsPlayer) {
            return super.damage(source, amount);
        }

        return false;
    }

    @Override
    public void onDeath(DamageSource source) {
        retrieveItems(source.getAttacker());

        super.onDeath(source);
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