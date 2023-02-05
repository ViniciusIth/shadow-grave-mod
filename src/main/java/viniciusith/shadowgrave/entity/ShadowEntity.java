package viniciusith.shadowgrave.entity;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.text.Text;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
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

    public GameProfile getOwner() {
        return this.shadowOwner;
    }

    public void setOwner(@NotNull GameProfile owner) {
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

    private void retrieveItems(Entity attacker) {
        if (world.isClient)
            return;

        ItemScatterer.spawn(this.world, this.getBlockPos(), this.items);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);

        nbt.putInt("ItemCount", this.getItems().size());

        nbt.put("Items", Inventories.writeNbt(new NbtCompound(), this.getItems(), true));

        nbt.putInt("XP", this.getXp());

        if (shadowOwner != null)
            nbt.put("ShadowOwner", NbtHelper.writeGameProfile(new NbtCompound(), this.getOwner()));

    }


    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);

        this.setItems(DefaultedList.ofSize(nbt.getInt("ItemCount"), ItemStack.EMPTY));

        Inventories.readNbt(nbt.getCompound("Items"), this.items);

        this.setXp(nbt.getInt("XP"));

        if (nbt.contains("ShadowOwner"))
            this.setOwner(NbtHelper.toGameProfile(nbt.getCompound("ShadowOwner")));
    }

    @Override
    public int getXpToDrop() {
        return this.getXp();
    }

    @Override
    public void move(MovementType movementType, Vec3d movement) {
        super.move(movementType, movement);
        this.checkBlockCollision();
    }

    @Override
    public void tick() {
        // Random tip, you can make an if statement to only run when ticks are divisible
        // by some number, like 20.
        // This will make so that the function runs only on those tick distances.

//        if (!this.world.isClient()) {
//            MinecraftServer server = this.getServer();
//            assert server != null;
//
//            PlayerManager playerManager = server.getPlayerManager();

        // this.active = playerManager.getPlayer(this.shadowOwner.getId()) != null;
//        }

        super.tick();
    }

    @Override
    protected void initGoals() {
        super.initGoals();

        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, false));
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