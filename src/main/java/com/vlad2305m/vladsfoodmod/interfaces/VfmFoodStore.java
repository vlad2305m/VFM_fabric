package com.vlad2305m.vladsfoodmod.interfaces;

import com.vlad2305m.vladsfoodmod.NutrientStore;

import java.util.List;
import java.util.Map;

public interface VfmFoodStore {
    void vfm_flush();
    NutrientStore getNutrientStore();
}
