package com.vlad2305m.vladsfoodmod.mixin;

import com.mojang.authlib.GameProfile;
import com.vlad2305m.vladsfoodmod.NutrientStore;
import com.vlad2305m.vladsfoodmod.interfaces.VfmFoodStore;
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

import java.util.Collections;
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
            List<Map.Entry<NutrientStore.vitamins, Double>> map = ((VfmFoodStore)hungerManager).getVitaminPercentage();

            StringBuilder levels = new StringBuilder();
            for (Map.Entry<NutrientStore.vitamins, Double> i : map) {
                int n = (int)floor(i.getValue() * 5);
                boolean neg = false;
                if (n < 0) { n = -n * 4; neg = true; }
                n = min(n, 100);
                levels
                        .append(i.getKey())
                        .append(":§8")
                        .append(StringUtils.repeat("_", 4 - i.getKey().toString().length()))
                        .append("§f§l[§a")
                        .append(StringUtils.repeat("|", n))
                        .append(neg ? "§c" :"§7")
                        .append(StringUtils.repeat("|", 100 - n))
                        .append("§f]§r\n");
            }

            sendMessage(Text.of(
                    "Your essential nutrient levels:\n" +
                            levels.toString() +
                            "END"
                    ), false);

        }
    }

}
