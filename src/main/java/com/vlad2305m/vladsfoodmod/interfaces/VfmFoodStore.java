package com.vlad2305m.vladsfoodmod.interfaces;

import com.vlad2305m.vladsfoodmod.NutrientStore;

public interface VfmFoodStore {
    void vfm_flush();
    NutrientStore getNutrientStore();
}
