package com.vlad2305m.vladsfoodmod.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.GlassBottleItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(GlassBottleItem.class)
public class BottleFillingMixin {

    @ModifyArg(method = "use", at = @At(value = "INVOKE",
            target = "net/minecraft/item/GlassBottleItem.fill (Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/item/ItemStack;)Lnet/minecraft/item/ItemStack;"),
            index = 2)
    private ItemStack RSetPotion(ItemStack itemStack, PlayerEntity player, ItemStack bottledWater){
        if (!player.world.isClient) {
            int i = player.world.random.nextInt(158);
            bottledWater.getOrCreateSubTag("display").putString("Name", "{\"text\":\""+new String[]{
                    "Acqua Panna", "Aqua Carpatica", "Agua Cinco Estrellas", "AdeS", "Agua Mineral Salus", "Agua Vida",
                    "Ambo Mineral Water", "Amma Kudineer", "Antipodes Water Company", "Apenta", "Apollinaris", "Aqua Pura",
                    "Aquafina", "Aquaqueen", "Aquas", "Arrowhead Water", "Ayvaz Water", "Arwa", "Badamli", "Badoit",
                    "Ballygowan", "Bear-lithia", "Belu", "Berdawni", "Bílinská kyselka", "Bisleri", "Blue Spring Living Water",
                    "Borjomi", "Boxed water", "Buxton", "Callaway Blue", "Ciego Montero", "Ciel", "Cool Blue", "Hiram Codd",
                    "Contrex", "Cool Ridge", "Crystal Clear", "Crystal Geyser Natural Alpine Spring Water", "Crystal Geyser",
                    "Culligan", "Damavand Mineral Water", "Dana", "Dasani", "Deep River Rock", "Deep Spring",
                    "Deer Park Spring Water", "Dejà Blue", "Donat Mg", "Ein Gedi Mineral Water", "Energy Brands", "Ethos Water",
                    "Ervina Waters", "Evian", "Jermuk", "Highland Spring", "Staatl. Fachingen", "Farris", "Fiji Water",
                    "Fruit2O", "Fuentealta", "Galvanina", "Ganten", "Gerolsteiner Brunnen", "Glaceau", "Great Value",
                    "Gourmet Foods", "HappyWater", "Harrogate Spa Water", "HerbalH2O Reusable Glass Bottled Enhanced Water.",
                    "Highland Spring", "Himalaya", "Ice Mountain", "Iceland Pure Spring Water", "Icelandic Glacial", "Isklar",
                    "Istisu", "Jamnica", "Jana", "JALPY", "Jantzen", "JUST", "Kellogg's Special K2O Protein Water",
                    "Knjaz Miloš a.d.", "Le Minerale", "Liquid Death", "Lithia", "Londonderry Lithia", "Mai Dubai",
                    "Malvern water", "Malvern Water", "Mattoni", "Mey Eden", "Mohai Agnes mineral water", "Montellier",
                    "Mount Franklin Water", "Mountain Valley Spring Water", "Nabeglavi", "Nada", "Naya Waters",
                    "Nestlé Pure Life", "Nestlé Waters", "Nestlé Waters North America", "Neverfail", "NEWater",
                    "Nongfu Spring", "Open Water", "Ozarka", "Panama Blue", "Panna", "PATHWATER", "Pennine Spring",
                    "Penta Water", "Perrier", "Persa", "Pluto Water", "Poland Spring", "Powwow Water", "Princes Gate Spring Water",
                    "Propel Fitness Water", "Pump", "Radenska", "Ramlösa", "Sabil Mineral Water", "Sairme", "Saka", "Samaria",
                    "San Mateo", "San Pellegrino", "Sanavi", "Sanfaustino", "Säwan", "Sannine", "Selters", "Sierra Springs",
                    "Sirab", "Sohat", "Souroti", "Spa", "Sparkletts", "Eau St. Justin", "Tannourine", "Tau",
                    "Tipperary Natural Mineral Water", "Topo Chico", "Trump Ice", "Tŷ Nant", "Valpre", "Verna Natural Mineral Water",
                    "VEEN", "Vittel", "Volvic", "Voss", "Water4", "Watsons Water", "Whistler Water", "Zaječická hořká", "Zephyrhills"
            }[i]+"\",\"italic\":false}");
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
        }
        return bottledWater;
    }
}
