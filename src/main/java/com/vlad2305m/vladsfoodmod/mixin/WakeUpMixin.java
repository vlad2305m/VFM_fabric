package com.vlad2305m.vladsfoodmod.mixin;

import com.mojang.authlib.GameProfile;
import com.vlad2305m.vladsfoodmod.ModConfig;
import com.vlad2305m.vladsfoodmod.NutrientStore;
import com.vlad2305m.vladsfoodmod.interfaces.ShowNutrientInfo;
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

import java.util.*;

import static java.lang.Math.*;

@Mixin(ServerPlayerEntity.class)
public abstract class WakeUpMixin extends PlayerEntity implements ShowNutrientInfo {

    public WakeUpMixin(World world, BlockPos pos, float yaw, GameProfile profile) {
        super(world, pos, yaw, profile);
    }

    @Shadow public void sendMessage(Text message, boolean actionBar){}

    @Inject(method = "wakeUp(ZZ)V", at = @At("HEAD"))
    private void wakeUp(boolean bl, boolean updateSleepingPlayers, CallbackInfo ci){

        if (AutoConfig.getConfigHolder(ModConfig.class).getConfig().features.subtract_on_wakeup)
            ((VfmFoodStore)this.getHungerManager()).getNutrientStore().subtractDaily(1);

        showNutrientInfo();
    }

    public int showNutrientInfo(){
        if (!this.world.isClient && !AutoConfig.getConfigHolder(ModConfig.class).getConfig().features.disable_nutrient_system){

            Map<NutrientStore.nutrients, Double> map =
                    ((VfmFoodStore)hungerManager).getNutrientStore().getNutrientPercentage();

            Map.Entry<NutrientStore.nutrients, Double> def = new AbstractMap.SimpleImmutableEntry<>(null, 100.0);

            StringBuilder levels = new StringBuilder();
            for (Map.Entry<NutrientStore.nutrients, Double> i : map.entrySet()) {
                int n = (int) round(i.getValue() * 5);
                boolean neg = false;
                if (n < 80) { def = def.getValue() > i.getValue() ? i : def; }
                if (n < 0) { n = -n * 4; neg = true; }
                n = min(n, 100);
                levels
                        .append(i.getKey())
                        .append(":§8")
                        .append(i.getKey().toString().toCharArray()[0] == 'v' ? "`" : "")
                        .append(StringUtils.repeat("_", 11 - i.getKey().toString().length()))
                        .append("§f§l[")
                        .append(neg ? "§c" : "§a")
                        .append(StringUtils.repeat("|", n))
                        .append("§7")
                        .append(StringUtils.repeat("|", 100 - n))
                        .append("§f§l]§r\n");
            }



            sendMessage(Text.of(
                    "Your essential nutrient levels:\n" +
                            levels.toString() +
                            "Capacity: green — 20 days, red — 5 days" +
                            (def.getValue() < 16 ?
                            "\n\nYou are low on " +
                            def.getKey().toString() +
                            "\nTop 5 sources: " +
                            find_5_best(def.getKey()): "")
                    ), false);
        }
        return 1;
    }

    private String find_5_best(NutrientStore.nutrients n) {
        List<Map.Entry<String, Double>> best = new LinkedList<>();

        Map<String, NutrientStore> list = AutoConfig.getConfigHolder(ModConfig.class).getConfig().foodData.nutrientStoreMap;

        int i = 0;
        for (Map.Entry<String, NutrientStore> m : list.entrySet()) {
            if (i <= 5){ best.add(new AbstractMap.SimpleImmutableEntry<>(m.getKey(), m.getValue().getNutrientPercentage().get(n))); i++; continue;}
            best.sort(Map.Entry.comparingByValue());
            best.set(0, new AbstractMap.SimpleImmutableEntry<>(m.getKey(), m.getValue().getNutrientPercentage().get(n)));
        }
        best.sort(Map.Entry.comparingByValue());

        return to_best(best.get(5)) + ", " + to_best(best.get(4)) + ", " + to_best(best.get(3)) + ", " + to_best(best.get(2)) + ", " + to_best(best.get(1)) + ".";


    }

    private String to_best(Map.Entry<String, Double> e){
        return e.getKey() + " (§a" + StringUtils.repeat("|", (int)(e.getValue()*5)) + StringUtils.repeat(".", (int) round((e.getValue()*5 - (int)(e.getValue()*5))*4)) + "§r)";
    }

}
