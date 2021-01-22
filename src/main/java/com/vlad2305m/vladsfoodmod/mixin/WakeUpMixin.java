package com.vlad2305m.vladsfoodmod.mixin;

import com.mojang.authlib.GameProfile;
import com.vlad2305m.vladsfoodmod.ModConfig;
import com.vlad2305m.vladsfoodmod.NutrientStore;
import com.vlad2305m.vladsfoodmod.interfaces.VfmFoodStore;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.commons.lang3.StringUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Map;

import static java.lang.Math.*;

@Mixin(ServerPlayerEntity.class)
public abstract class WakeUpMixin extends PlayerEntity {

    public WakeUpMixin(World world, BlockPos pos, float yaw, GameProfile profile) {
        super(world, pos, yaw, profile);
    }

    @Shadow public void sendMessage(Text message, boolean actionBar){}

    @Inject(method = "wakeUp(ZZ)V", at = @At("HEAD"))
    private void wakeUp(boolean bl, boolean updateSleepingPlayers, CallbackInfo ci){
        showNutrientInfo();
    }

    public void showNutrientInfo(){
        if (!this.world.isClient){

            if (AutoConfig.getConfigHolder(ModConfig.class).getConfig().moduleA.subtract_on_wakeup)
                ((VfmFoodStore)this.getHungerManager()).getNutrientStore().subtractDaily(1);

            List<Map.Entry<NutrientStore.nutrients, Double>> map =
                    ((VfmFoodStore)hungerManager).getNutrientStore().getNutrientPercentage();

            StringBuilder levels = new StringBuilder();
            for (Map.Entry<NutrientStore.nutrients, Double> i : map) {
                int n = (int)floor(i.getValue() * 5);
                boolean neg = false;
                if (n < 0) { n = -n * 4; neg = true; }
                n = min(n, 100);
                levels
                        .append(i.getKey())
                        .append(":§8")
                        .append(StringUtils.repeat("_", 11 - i.getKey().toString().length()))
                        .append("§f§l[")
                        .append(neg ? "§c" : "§a")
                        .append(StringUtils.repeat("|", n))
                        .append("§7")
                        .append(StringUtils.repeat("|", 100 - n))
                        .append("§f]§r\n");
            }

            sendMessage(Text.of(
                    "Your essential nutrient levels:\n" +
                            levels.toString() +
                            "Capacity: green — 20 days, red — 5 days"
                    ), false);
        }
    }

}
