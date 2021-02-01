package com.vlad2305m.vladsfoodmod.mixin;

import com.vlad2305m.vladsfoodmod.ModConfig;
import com.vlad2305m.vladsfoodmod.data.WaterBrands;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.GlassBottleItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.Objects;

@Mixin(GlassBottleItem.class)
public class BottleFillingMixin {

    @ModifyArg(method = "use", at = @At(value = "INVOKE",
            target = "net/minecraft/item/GlassBottleItem.fill (Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/item/ItemStack;)Lnet/minecraft/item/ItemStack;"),
            index = 2)
    private ItemStack RSetPotion(ItemStack itemStack, PlayerEntity player, ItemStack bottledWater){
        if (!player.world.isClient && AutoConfig.getConfigHolder(ModConfig.class).getConfig().features.water_branding) {
            int i = player.world.random.nextInt(158);
            bottledWater.getOrCreateSubTag("display")
                    .putString("Name", "{\"text\":\"" + WaterBrands.brands[i] + "\",\"italic\":false}");
            int colour;
            switch (i){
                case (27) :  colour = 4487367; break;
                case (59) :  colour = new int[]{16492341, 12068955, 15819071, 15655598}[player.world.random.nextInt(4)]; break;
                case (82) :  colour = 16730050; break;
                case (85) :  colour = 0; break;
                default:  colour = -1;
            }
            if (colour != -1) {
                bottledWater.getOrCreateTag().putInt("CustomPotionColor", colour);
            }
            bottledWater.getOrCreateTag().putString("vfm_biome", Objects.requireNonNull(player.world.getRegistryManager().get(Registry.BIOME_KEY).getId(player.world.getBiome(player.getBlockPos()))).toString());
        }
        return bottledWater;
    }
}
