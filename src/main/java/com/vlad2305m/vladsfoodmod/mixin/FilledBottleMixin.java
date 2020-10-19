package com.vlad2305m.vladsfoodmod.mixin;

import com.vlad2305m.vladsfoodmod.interfaces.VfmFoodStore;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PotionItem.class)
public class FilledBottleMixin {

    @Inject(method = "finishUsing", at = @At(value = "HEAD"))
    public void finishUsing(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable infoReturnable) {
        if(user instanceof PlayerEntity) {
            ((VfmFoodStore)((PlayerEntity) user).getHungerManager()).vfm_flush();
        }
    }
}
