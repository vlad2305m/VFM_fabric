package com.vlad2305m.vladsfoodmod.mixin;

import com.mojang.authlib.GameProfile;
import com.vlad2305m.vladsfoodmod.interfaces.VfmFoodStore;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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
            sendMessage(Text.of(((VfmFoodStore)hungerManager).getVitaminPercentage().toString()), false);
        }
    }

}
