package com.vlad2305m.vladsfoodmod.mixin;

import com.vlad2305m.vladsfoodmod.ModConfig;
import com.vlad2305m.vladsfoodmod.NutrientStore;
import com.vlad2305m.vladsfoodmod.interfaces.VfmFoodStore;
import me.sargunvohra.mcmods.autoconfig1.AutoConfig;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.packet.s2c.play.HealthUpdateS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.*;


@Mixin(HungerManager.class)
public abstract class FoodStoreMixin implements VfmFoodStore {
    private final Random vfm_random = new Random();
    private ItemStack vfm_mouth_item = null;
    private float vfm_mouth = 0;
    private float vfm_stomach = 20;
    private Queue<Map.Entry<Long, Float>> vfm_intestine = new LinkedList<>();
    private float vfm_blood = 40;
    private int vfm_mouth_modifier = (int) (20.0F* vfm_random.nextFloat());
    private int vfm_mouth_timer = 40;
    private long vfm_last_meal = -10000;
    private long vfm_stomach_timer = 0;
    private boolean vfm_post_read = true;
    private long vfm_gametime = 0;
    private boolean vfm_send_packet = false;
    private final NutrientStore vfm_essential_nutrients = new NutrientStore().subtractDaily(-10F);
    private long vfm_nutrient_timer = 0;
    @Shadow private int foodLevel;
    @Shadow private float foodSaturationLevel;
    @Shadow private int foodStarvationTimer;
    @Shadow private float exhaustion;
    @Shadow private int prevFoodLevel;

    @Shadow public void addExhaustion(float exhaustion) {  }
    @Shadow public int getFoodLevel() { return 0; }
    @Shadow public float getSaturationLevel() { return 0; }



    @Inject(method = "add", at = @At("HEAD"), cancellable = true)
    public void add(int foodLevelIn, float foodSaturationModifier, CallbackInfo info) {
        if(!AutoConfig.getConfigHolder(ModConfig.class).getConfig().moduleA.disable_food_system){
        if(AutoConfig.getConfigHolder(ModConfig.class).getConfig().moduleA.delay_system){
            this.vfm_mouth += foodLevelIn;
            this.vfm_mouth += foodSaturationModifier * foodLevelIn * 2.0F;
            this.foodLevel = Math.min((int) this.vfm_stomach, 20);
            this.foodSaturationLevel = Math.max(Math.min((int) this.vfm_blood - 20, 20), 0);
        } else {
            this.vfm_blood += foodLevelIn + foodSaturationModifier * foodLevelIn * 2.0F;
        }
            this.vfm_send_packet = true;
            info.cancel();
        }
    }

    @Inject(method = "eat", at = @At("HEAD"))
    public void eat(Item item, ItemStack stack, CallbackInfo info) {
        this.vfm_mouth_item = stack;
        this.vfm_essential_nutrients.add(AutoConfig.getConfigHolder(ModConfig.class).getConfig().moduleB
                .nutrientStoreMap.get(stack.getTranslationKey()));
    }

    @Inject(method = "update", at = @At("HEAD"), cancellable = true)
    public void update(PlayerEntity player, CallbackInfo info) {
        Difficulty difficulty = player.world.getDifficulty();
        this.vfm_gametime = player.world.getTime();
        if (AutoConfig.getConfigHolder(ModConfig.class).getConfig().moduleA.subtract_each_24h
                && this.vfm_gametime - this.vfm_nutrient_timer  > 24000) {
            this.vfm_essential_nutrients.subtractDaily(1);
            this.vfm_nutrient_timer += 24000;
        }
        if (!AutoConfig.getConfigHolder(ModConfig.class).getConfig().moduleA.disable_food_system) {
            boolean vfm_delay_enabled = AutoConfig.getConfigHolder(ModConfig.class).getConfig().moduleA.delay_system;
            if (this.vfm_post_read) {
                this.vfm_stomach_timer = this.vfm_gametime - this.vfm_stomach_timer;
                this.vfm_nutrient_timer = this.vfm_gametime - this.vfm_nutrient_timer;
                this.vfm_last_meal = this.vfm_gametime - this.vfm_last_meal;
                Queue<Map.Entry<Long, Float>> vfm_intestine_new = new LinkedList<>();
                while (this.vfm_intestine.peek() != null) {
                    vfm_intestine_new.add(new AbstractMap.SimpleImmutableEntry<>(
                            this.vfm_gametime - this.vfm_intestine.element().getKey(),
                            this.vfm_intestine.remove().getValue()));
                }
                this.vfm_intestine = vfm_intestine_new;
                this.vfm_post_read = false;
            }
            long time_passed = vfm_gametime - this.vfm_last_meal;
            long stomach_timer = vfm_gametime - this.vfm_stomach_timer;
            this.prevFoodLevel = this.foodLevel;
            if (this.exhaustion > 4.0F) {
                this.exhaustion -= 4.0F;
                if (this.vfm_blood > 0.0F) {
                    this.vfm_blood = Math.max(this.vfm_blood - 1.0F, 0.0F);
                }
                this.vfm_send_packet = true;
                if (vfm_essential_nutrients.isSuffering(0)) player.dealDamage(player, player);
                if (vfm_essential_nutrients.isSuffering(-5)) player.kill();
            }

            if (this.vfm_mouth > 0.0F) {
                if (this.vfm_mouth_timer + this.vfm_mouth_modifier > 0 && vfm_delay_enabled) {
                    this.vfm_mouth_timer--;
                } else {
                    if (time_passed > 4000) {
                        this.vfm_last_meal = vfm_gametime;
                        time_passed = 0;
                    }
                    float f = Math.min(1.0F + 1.0F * this.vfm_mouth_modifier / 20.0F, this.vfm_mouth);
                    this.vfm_mouth -= f;
                    this.vfm_stomach += f;
                    this.vfm_mouth_modifier = (int) (20.0F * vfm_random.nextFloat());
                    this.vfm_mouth_timer = 40;
                    if (this.vfm_mouth_item != null) {
                        player.world.playSound(null, player.getX(), player.getY(), player.getZ(),
                                this.vfm_mouth_item.getEatSound(), SoundCategory.PLAYERS, 0.5F,
                                player.world.random.nextFloat() * 0.1F + 0.9F);
                    }
                }
            }

            if (this.vfm_stomach > 0.0F) {
                if (stomach_timer >= 100 || !vfm_delay_enabled) {
                    this.vfm_stomach_timer = vfm_gametime;
                    if (time_passed > 4000 || !vfm_delay_enabled) {
                        this.vfm_intestine.offer(new AbstractMap.SimpleImmutableEntry<>(vfm_gametime, Math.min(this.vfm_stomach, 1.235F)));
                        this.vfm_stomach -= Math.min(this.vfm_stomach, 1.235F);
                    } else if (time_passed > 800) {
                        float f = Math.min(this.vfm_stomach, ((float) (int) (time_passed / 100 - 7)) * 0.03633F + 0.088F);
                        this.vfm_intestine.offer(new AbstractMap.SimpleImmutableEntry<>(vfm_gametime, f));
                        this.vfm_stomach -= f;
                    }
                }
            }

            if (this.vfm_intestine.peek() != null) {
                if (this.vfm_gametime - this.vfm_intestine.element().getKey() > 3000 || !vfm_delay_enabled) {
                    this.vfm_blood += this.vfm_intestine.remove().getValue();
                }
                if (stomach_timer == 99) {
                    Queue<Map.Entry<Long, Float>> new_intestine = new LinkedList<>();
                    while (this.vfm_intestine.peek() != null) {
                        float f = this.vfm_intestine.element().getValue() / (35.0F - ((float) (this.vfm_gametime - this.vfm_intestine.element().getKey())) / 100.0F);
                        new_intestine.add(new AbstractMap.SimpleImmutableEntry<>(this.vfm_intestine.element().getKey(), this.vfm_intestine.remove().getValue() - f));
                        vfm_blood += f;
                    }
                    this.vfm_intestine = new_intestine;
                }
            }

            boolean flag = player.world.getGameRules().getBoolean(GameRules.NATURAL_REGENERATION);
            if (flag && this.vfm_blood > 50.0F && player.canFoodHeal()) {
                ++this.foodStarvationTimer;
                if (this.foodStarvationTimer >= 10) {
                    float f = Math.min(this.vfm_blood - 18.0F, 6.0F);
                    player.heal(f / 6.0F);
                    this.addExhaustion(f);
                    this.foodStarvationTimer = 0;
                }
            }
            else if (flag && this.vfm_blood >= 20.0F && player.canFoodHeal()) {
                ++this.foodStarvationTimer;
                if (this.foodStarvationTimer >= 80) {
                    player.heal(1.0F);
                    this.addExhaustion(6.0F);
                    this.foodStarvationTimer = 0;
                }
            }
            else if (this.vfm_blood <= 0) {
                ++this.foodStarvationTimer;
                if (this.foodStarvationTimer >= 80) {
                    if (player.getHealth() > 10.0F || difficulty == Difficulty.HARD ||
                            player.getHealth() > 1.0F && difficulty == Difficulty.NORMAL) {
                        player.damage(DamageSource.STARVE, 1.0F);
                    }

                    this.foodStarvationTimer = 0;
                }
            }
            else {
                this.foodStarvationTimer = 0;
            }

            if (this.vfm_blood > 100.0F) {
                this.vfm_blood = 100.0F;
                player.addStatusEffect(new StatusEffectInstance(
                        StatusEffects.POISON, 400, 2, true, false, true));
            }

            if (!vfm_delay_enabled && this.vfm_blood > 40.0F) {
                this.vfm_blood = 40.0F;
            }

            if (this.vfm_stomach > 40.0F) {
                this.vfm_stomach = 1.0F;
                player.addStatusEffect(new StatusEffectInstance(
                        StatusEffects.NAUSEA, 200, 20, true, false, true));
            }

            this.foodLevel = Math.min((int) this.vfm_stomach, 20);
            this.foodSaturationLevel = Math.max(Math.min((int) this.vfm_blood - 20, 20), 0);

            if (this.vfm_stomach < 7 && this.vfm_blood > 10.0F) {
                this.foodLevel = 10;
                this.foodSaturationLevel = 0;
            } // TODO eating speed hooks & potions
            else if (this.vfm_blood <= 10.0F && player.isSprinting()) {
                player.addStatusEffect(new StatusEffectInstance(
                        StatusEffects.SLOWNESS, 20, this.vfm_blood > 5 ? 1 : 2, true, false, false));
            } // sprinting support / neglecting

            if (player.isSneaking() || !vfm_delay_enabled) {
                this.foodLevel = (int) (this.vfm_blood / 5.0f);
            } // blood % showing

            if (this.vfm_send_packet && !player.world.isClient) {
                ((ServerPlayerEntity) player).networkHandler.sendPacket(
                        new HealthUpdateS2CPacket(player.getHealth(), this.getFoodLevel(), this.getSaturationLevel()));
                this.vfm_send_packet = false;
            } // force sync

            info.cancel();
        }
    }

    @Inject(method = "fromTag", at = @At("TAIL"))
    public void fromTag(CompoundTag tag, CallbackInfo info) {
        if (tag.contains("vfm_mouth_timer", 99)) {
            this.vfm_mouth = tag.getFloat("vfm_mouth");
            this.vfm_stomach = tag.getFloat("vfm_stomach");
            this.vfm_blood = tag.getFloat("vfm_blood");
            this.vfm_post_read = true;
            this.vfm_mouth_timer = tag.getInt("vfm_mouth_timer");
            this.vfm_stomach_timer = tag.getLong("vfm_stomach_timer");
            this.vfm_last_meal = tag.getLong("vfm_last_meal");
            long[] food_times = tag.getLongArray("vfm_intestine_times");
            long[] intestine_contents = tag.getLongArray("vfm_intestine_contents");
            int n = food_times.length;
            for (int i = 0; i < n; i++) {
                this.vfm_intestine.offer(new AbstractMap.SimpleImmutableEntry<>
                        (food_times[i], ((float) intestine_contents[i]) / 1000000.0F));
            }
            this.vfm_nutrient_timer = tag.getLong("vfm_nutrient_timer");
            this.vfm_essential_nutrients.fromTag(tag, "_blood");
        }
    }

    @Inject(method = "toTag", at = @At("TAIL"))
    public void toTag(CompoundTag tag, CallbackInfo info) {
        tag.putFloat("vfm_mouth", this.vfm_mouth);
        tag.putFloat("vfm_stomach", this.vfm_stomach);
        tag.putFloat("vfm_blood", this.vfm_blood);
        tag.putInt("vfm_mouth_timer", this.vfm_mouth_timer);
        tag.putLong("vfm_stomach_timer", this.vfm_gametime - this.vfm_stomach_timer);
        tag.putLong("vfm_last_meal", this.vfm_gametime - this.vfm_last_meal);
        Queue<Map.Entry<Long, Float>> vfm_intestine_new = new LinkedList<>();
        List<Long> food_times = new ArrayList<>();
        List<Long> intestine_contents = new ArrayList<>();
        while (this.vfm_intestine.peek() != null) {
            food_times.add(this.vfm_gametime - this.vfm_intestine.element().getKey());
            intestine_contents.add((long)(this.vfm_intestine.element().getValue()*1000000.0F));
            vfm_intestine_new.add(this.vfm_intestine.remove());
        }
        this.vfm_intestine = vfm_intestine_new;
        tag.putLongArray("vfm_intestine_times", food_times);
        tag.putLongArray("vfm_intestine_contents", intestine_contents);
        tag.putLong("vfm_nutrient_timer", this.vfm_gametime - this.vfm_nutrient_timer);
        this.vfm_essential_nutrients.toTag(tag, "_blood");
    }

    @Inject(method = "isNotFull", at = @At("TAIL"), cancellable = true)
    public void isNotFull(CallbackInfoReturnable<Boolean> infoReturnable){
        if (!AutoConfig.getConfigHolder(ModConfig.class).getConfig().moduleA.disable_food_system) infoReturnable.setReturnValue(this.vfm_mouth <= 20.0F && this.vfm_stomach < 20.0F);
    }

    public void vfm_flush() {
        float f = Math.min(this.vfm_mouth, 6.0F);
        this.vfm_mouth -= f;
        this.vfm_stomach += f;
    }

    public NutrientStore getNutrientStore(){ return vfm_essential_nutrients; }

}
