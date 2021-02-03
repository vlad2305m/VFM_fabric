package com.vlad2305m.vladsfoodmod.mixin;

import com.vlad2305m.vladsfoodmod.ModConfig;
import com.vlad2305m.vladsfoodmod.NutrientStore;
import com.vlad2305m.vladsfoodmod.interfaces.VfmFoodStore;
import com.vlad2305m.vladsfoodmod.misc.Pair;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PotionItem;
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
    private final Queue<Pair<Integer, Float>> vfm_intestine = new LinkedList<>();
    private float vfm_blood = 40;
    private int vfm_mouth_modifier = (int) (20.0F* vfm_random.nextFloat());
    private int vfm_mouth_timer = 40;
    private int vfm_last_meal = 0;
    private int vfm_stomach_timer = 0;
    private boolean vfm_send_packet = false;
    private final NutrientStore vfm_essential_nutrients = new NutrientStore().subtractDaily(-10F);
    private int vfm_nutrient_timer = 0;
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
        if(!AutoConfig.getConfigHolder(ModConfig.class).getConfig().features.disable_food_system){
        if(AutoConfig.getConfigHolder(ModConfig.class).getConfig().features.delay_system){
            this.vfm_mouth += foodLevelIn; //TODO check if turning this off works
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
        NutrientStore stackValue = AutoConfig.getConfigHolder(ModConfig.class).getConfig().foodData.nutrientStoreMap.get(item.getTranslationKey());
        if (stackValue == null && item instanceof PotionItem) stackValue = AutoConfig.getConfigHolder(ModConfig.class).getConfig().foodData.nutrientStoreMap.get(Items.POTION.getTranslationKey());
        if (stackValue != null) this.vfm_essential_nutrients.add(stackValue);
    }

    @Inject(method = "update", at = @At("HEAD"), cancellable = true)
    public void update(PlayerEntity player, CallbackInfo info) {
        this.prevFoodLevel = this.foodLevel;

        ModConfig.Features features = AutoConfig.getConfigHolder(ModConfig.class).getConfig().features;

        if (!features.disable_nutrient_system){
            if (!AutoConfig.getConfigHolder(ModConfig.class).getConfig().foodData.disable_muliplier) {
                double m = features.generic_nutrient_data_multiplier;
                AutoConfig.getConfigHolder(ModConfig.class).getConfig().foodData.nutrientStoreMap.forEach((String s, NutrientStore n)->n.multiply(m));
                AutoConfig.getConfigHolder(ModConfig.class).getConfig().foodData.disable_muliplier = true;
            } // apply multiplier once
            if (features.subtract_each_24h) {
                this.vfm_nutrient_timer++;
                if(this.vfm_nutrient_timer  > 24000) {
                    this.vfm_essential_nutrients.subtractDaily(1);
                    this.vfm_nutrient_timer -= 24000;
                }
            }
            if (this.exhaustion > 4.0F) {
                if (features.nutrient_damage_penalty && vfm_essential_nutrients.isSuffering(0)) player.dealDamage(player, player);
                if (features.nutrient_death_penalty && vfm_essential_nutrients.isSuffering(-5)) player.kill();//damage(new DamageSource(vfm_essential_nutrients.deficient(-5).toString()),3.4028235E38F);
            }
        }

        //TODO move organs to another class; add debugging /commands
        if (!features.disable_food_system) {

            if (this.exhaustion > 4.0F) {
                this.exhaustion -= 4.0F;
                if (this.vfm_blood > 0.0F) {
                    this.vfm_blood = Math.max(this.vfm_blood - 1.0F, 0.0F);
                }
                this.vfm_send_packet = true;
            }

            if (features.delay_system) {
                this.vfm_stomach_timer++;
                if (this.vfm_last_meal > 0) this.vfm_last_meal++;
                this.vfm_intestine.forEach((Pair<Integer, Float> e) -> e.first++);

                if (this.vfm_mouth > 0.0F) {
                    if (this.vfm_mouth_timer + this.vfm_mouth_modifier > 0) {
                        this.vfm_mouth_timer--;
                    } else {
                        if (this.vfm_last_meal == 0) this.vfm_last_meal = 1;
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
                    if (this.vfm_stomach_timer >= 100) {
                        this.vfm_stomach_timer = 0;
                        if (this.vfm_last_meal > 4000) {
                            this.vfm_intestine.offer(new Pair<>(0, Math.min(this.vfm_stomach, 1.235F)));
                            this.vfm_stomach -= Math.min(this.vfm_stomach, 1.235F);
                        } else if (this.vfm_last_meal > 800) {
                            float f = Math.min(this.vfm_stomach, ((float) (this.vfm_last_meal / 100 - 7)) * 0.03633F + 0.088F);
                            this.vfm_intestine.offer(new Pair<>(0, f));
                            this.vfm_stomach -= f;
                        }
                    }
                } else this.vfm_last_meal = 0;

                if (!this.vfm_intestine.isEmpty()) {
                    if (this.vfm_stomach_timer == 99) {
                        if (this.vfm_intestine.element().first > 3000) {
                            this.vfm_blood += this.vfm_intestine.remove().second;
                        }
                        this.vfm_intestine.forEach((Pair<Integer, Float> e) -> {
                            float f = e.second / (35.0F - ((float) e.first) / 100.0F);
                            e.second -= f;
                            vfm_blood += f;
                        });
                    }
                }

                if (this.vfm_blood > 100.0F) {
                    this.vfm_blood = 100.0F;
                    player.addStatusEffect(new StatusEffectInstance(
                            StatusEffects.POISON, 400, 2, true, false, true));
                }

                if (this.vfm_stomach > 40.0F) {
                    this.vfm_stomach = 1.0F;
                    player.addStatusEffect(new StatusEffectInstance(
                            StatusEffects.NAUSEA, 200, 20, true, false, true));
                }

                this.foodLevel = Math.min((int) this.vfm_stomach, 20);
                this.foodSaturationLevel = Math.max(Math.min((int) this.vfm_blood - 20, 20), 0);


            }
            else if (this.vfm_blood > 100.0F) {
                this.vfm_blood = 40.0F;
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
                    if (player.getHealth() > 10.0F || player.world.getDifficulty() == Difficulty.HARD ||
                            player.getHealth() > 1.0F && player.world.getDifficulty() == Difficulty.NORMAL) {
                        player.damage(DamageSource.STARVE, 1.0F);
                    }

                    this.foodStarvationTimer = 0;
                }
            }
            else this.foodStarvationTimer = 0;


            if (this.foodLevel < 7 && this.vfm_blood > 10.0F) {
                this.foodLevel = 10;
                this.foodSaturationLevel = 0;
            } // TODO eating speed hooks
            else if (this.vfm_blood <= 10.0F && player.isSprinting()) {
                player.addStatusEffect(new StatusEffectInstance(
                        StatusEffects.SLOWNESS, 20, this.vfm_blood > 5 ? 1 : 2, true, false, false));
            } // sprinting support / neglecting

            if (player.isSneaking() || !features.delay_system) {
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
            this.vfm_mouth_timer = tag.getInt("vfm_mouth_timer");
            this.vfm_stomach_timer = tag.getInt("vfm_stomach_timer");
            this.vfm_last_meal = tag.getInt("vfm_last_meal");
            int[] food_times = tag.getIntArray("vfm_intestine_times");
            long[] intestine_contents = tag.getLongArray("vfm_intestine_contents");
            int n = food_times.length;
            for (int i = 0; i < n; i++) {
                this.vfm_intestine.offer(new Pair<>(food_times[i], ((float) intestine_contents[i]) / 1000000.0F));
            }
            this.vfm_nutrient_timer = tag.getInt("vfm_nutrient_timer");
            this.vfm_essential_nutrients.fromTag(tag, "_blood");
        }
    }

    @Inject(method = "toTag", at = @At("TAIL"))
    public void toTag(CompoundTag tag, CallbackInfo info) {
        tag.putFloat("vfm_mouth", this.vfm_mouth);
        tag.putFloat("vfm_stomach", this.vfm_stomach);
        tag.putFloat("vfm_blood", this.vfm_blood);
        tag.putInt("vfm_mouth_timer", this.vfm_mouth_timer);
        tag.putInt("vfm_stomach_timer", this.vfm_stomach_timer);
        tag.putInt("vfm_last_meal", this.vfm_last_meal);
        List<Integer> food_times = new ArrayList<>();
        List<Long> intestine_contents = new ArrayList<>();
        this.vfm_intestine.forEach((Pair<Integer, Float> e) -> {
            food_times.add(e.first);
            intestine_contents.add((long)(e.second*1000000.0F));
        });
        tag.putIntArray("vfm_intestine_times", food_times);
        tag.putLongArray("vfm_intestine_contents", intestine_contents);
        tag.putInt("vfm_nutrient_timer", this.vfm_nutrient_timer);
        this.vfm_essential_nutrients.toTag(tag, "_blood");
    }

    @Inject(method = "isNotFull", at = @At("TAIL"), cancellable = true)
    public void isNotFull(CallbackInfoReturnable<Boolean> infoReturnable){
        if (!AutoConfig.getConfigHolder(ModConfig.class).getConfig().features.disable_food_system) infoReturnable.setReturnValue(this.vfm_mouth <= 20.0F && this.vfm_stomach < 20.0F);
    }

    public void vfm_flush() {
        float f = Math.min(this.vfm_mouth, 6.0F);
        this.vfm_mouth -= f;
        this.vfm_stomach += f;
    }

    public NutrientStore getNutrientStore(){ return vfm_essential_nutrients; }

}
