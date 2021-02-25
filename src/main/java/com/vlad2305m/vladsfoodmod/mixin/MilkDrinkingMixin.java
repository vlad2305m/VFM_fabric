package com.vlad2305m.vladsfoodmod.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MilkBucketItem;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MilkBucketItem.class)
public class MilkDrinkingMixin {
    @Inject(method = "finishUsing", at = @At(value = "HEAD"))
    public void finishUsing(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> infoReturnable) {
        if (user instanceof PlayerEntity) {
            ((PlayerEntity) user).getHungerManager().eat(stack.getItem(), stack);
        }
    }
}
