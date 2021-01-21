package com.vlad2305m.vladsfoodmod;

import net.minecraft.nbt.CompoundTag;

import java.util.*;

public class NutrientStore {
    public float carbohydrates;
    public float protein;
    public float fat;
    public float water;
    public float vitaminA;
    public float vitaminD;
    public float vitaminE;
    public float vitaminK;
    public float vitaminB6;
    public float vitaminB12;
    public float vitaminC;
    public float mineralK;
    public float mineralCa;
    public float mineralP;
    public float mineralMg;
    public float mineralCu;
    public float mineralFe;
    public float mineralSe;

    public enum nutrients { vitaminA, vitaminD, vitaminE, vitaminK, vitaminB6, vitaminB12,
        vitaminC, mineralK, mineralCa, mineralP, mineralMg, mineralCu, mineralFe, mineralSe }

    /*public NutrientStore(float CH, float protein, float fat, float water,
            float A, float D, float E, float K, float B6, float B12, float C,
            float KK, float Ca, float P, float Mg, float Cu, float Fe, float Se) {
        this.carbohydrates = CH;
        this.protein = protein;
        this.fat = fat;
        this.water = water;
        this.vitaminA = A;
        this.vitaminD = D;
        this.vitaminE = E;
        this.vitaminK = K;
        this.vitaminB6 = B6;
        this.vitaminB12 = B12;
        this.vitaminC = C;
        this.mineralK = KK;
        this.mineralCa = Ca;
        this.mineralP = P;
        this.mineralMg = Mg;
        this.mineralCu = Cu;
        this.mineralFe = Fe;
        this.mineralSe = Se;
    }*/

    public NutrientStore(float n, float CH, float protein, float fat, float water,
                         float A, float D, float E, float K, float B6, float B12, float C,
                         float KK, float Ca, float P, float Mg, float Cu, float Fe, float Se) {
        this.carbohydrates = CH * n;
        this.protein = protein * n;
        this.fat = fat * n;
        this.water = water * n;
        this.vitaminA = A * n;
        this.vitaminD = D * n;
        this.vitaminE = E * n;
        this.vitaminK = K * n;
        this.vitaminB6 = B6 * n;
        this.vitaminB12 = B12 * n;
        this.vitaminC = C * n;
        this.mineralK = KK * n;
        this.mineralCa = Ca * n;
        this.mineralP = P * n;
        this.mineralMg = Mg * n;
        this.mineralCu = Cu * n;
        this.mineralFe = Fe * n;
        this.mineralSe = Se * n;
    }

    /*public NutrientStore(float CH, float protein, float fat, float water) {
        this.carbohydrates = CH;
        this.protein = protein;
        this.fat = fat;
        this.water = water;
        this.vitaminA = 0;
        this.vitaminD = 0;
        this.vitaminE = 0;
        this.vitaminK = 0;
        this.vitaminB6 = 0;
        this.vitaminB12 = 0;
        this.vitaminC = 0;
        this.mineralK = 0;
        this.mineralCa = 0;
        this.mineralP = 0;
        this.mineralMg = 0;
        this.mineralCu = 0;
        this.mineralFe = 0;
        this.mineralSe = 0;
    }

    public NutrientStore(
            float A, float D, float E, float K, float B6, float B12, float C,
            float KK, float Ca, float P, float Mg, float Cu, float Fe, float Se) {
        this.carbohydrates = 0;
        this.protein = 0;
        this.fat = 0;
        this.water = 0;
        this.vitaminA = A;
        this.vitaminD = D;
        this.vitaminE = E;
        this.vitaminK = K;
        this.vitaminB6 = B6;
        this.vitaminB12 = B12;
        this.vitaminC = C;
        this.mineralK = KK;
        this.mineralCa = Ca;
        this.mineralP = P;
        this.mineralMg = Mg;
        this.mineralCu = Cu;
        this.mineralFe = Fe;
        this.mineralSe = Se;
    }*/

    public NutrientStore() {
        this.carbohydrates = 0;
        this.protein = 0;
        this.fat = 0;
        this.water = 0;
        this.vitaminA = 0;
        this.vitaminD = 0;
        this.vitaminE = 0;
        this.vitaminK = 0;
        this.vitaminB6 = 0;
        this.vitaminB12 = 0;
        this.vitaminC = 0;
        this.mineralK = 0;
        this.mineralCa = 0;
        this.mineralP = 0;
        this.mineralMg = 0;
        this.mineralCu = 0;
        this.mineralFe = 0;
        this.mineralSe = 0;
    }

    public void add(NutrientStore nutrientStore) {
        this.carbohydrates += nutrientStore.carbohydrates;
        this.protein += nutrientStore.protein;
        this.fat += nutrientStore.fat;
        this.water += nutrientStore.water;
        this.vitaminA += nutrientStore.vitaminA;
        this.vitaminD += nutrientStore.vitaminD;
        this.vitaminE += nutrientStore.vitaminE;
        this.vitaminK += nutrientStore.vitaminK;
        this.vitaminB6 += nutrientStore.vitaminB6;
        this.vitaminB12 += nutrientStore.vitaminB12;
        this.vitaminC += nutrientStore.vitaminC;
        this.mineralK += nutrientStore.mineralK;
        this.mineralCa += nutrientStore.mineralCa;
        this.mineralP += nutrientStore.mineralP;
        this.mineralMg += nutrientStore.mineralMg;
        this.mineralCu += nutrientStore.mineralCu;
        this.mineralFe += nutrientStore.mineralFe;
        this.mineralSe += nutrientStore.mineralSe;
    }

    public NutrientStore subtractDaily(float f) {
        //this.carbohydrates -=  * f;
        this.protein -= 56 * f;
        //this.fat -=  * f;
        this.water -= 2000 * f;
        this.vitaminA -= 900e-6 * f;
        this.vitaminD -= 250e-6 * f;
        this.vitaminE -= 15e-3 * f;
        this.vitaminK -= 120e-6 * f;
        this.vitaminB6 -= 1.3e-6 * f;
        this.vitaminB12 -= 2.4e-6 * f;
        this.vitaminC -= 90e-3 * f;
        this.mineralK -= 4.7 * f;
        this.mineralCa -= 1 * f;
        this.mineralP -= 0.7 * f;
        this.mineralMg -= 0.4 * f;
        this.mineralCu -= 0.9e-3 * f;
        this.mineralFe -= 8e-3 * f;
        this.mineralSe -= 55e-6 * f;
        return this;
    }

    public List<Map.Entry<nutrients, Double>> getNutrientPercentage(){
        ejectExcess();
        List<Map.Entry<nutrients, Double>> map = new ArrayList<>();
        map.add(new AbstractMap.SimpleImmutableEntry<>(nutrients.vitaminA, this.vitaminA / 900e-6));
        map.add(new AbstractMap.SimpleImmutableEntry<>(nutrients.vitaminD, this.vitaminD / 250e-6));
        map.add(new AbstractMap.SimpleImmutableEntry<>(nutrients.vitaminE, this.vitaminE / 15e-3));
        map.add(new AbstractMap.SimpleImmutableEntry<>(nutrients.vitaminK, this.vitaminK / 120e-6));
        map.add(new AbstractMap.SimpleImmutableEntry<>(nutrients.vitaminB6, this.vitaminB6 / 1.3e-6));
        map.add(new AbstractMap.SimpleImmutableEntry<>(nutrients.vitaminB12, this.vitaminB12 / 2.4e-6));
        map.add(new AbstractMap.SimpleImmutableEntry<>(nutrients.vitaminC, this.vitaminC / 90e-3));
        map.add(new AbstractMap.SimpleImmutableEntry<>(nutrients.mineralK, this.mineralK / 4.7));
        map.add(new AbstractMap.SimpleImmutableEntry<>(nutrients.mineralCa, (double)this.mineralCa));
        map.add(new AbstractMap.SimpleImmutableEntry<>(nutrients.mineralP, this.mineralP / 0.7));
        map.add(new AbstractMap.SimpleImmutableEntry<>(nutrients.mineralMg, this.mineralMg / 0.4));
        map.add(new AbstractMap.SimpleImmutableEntry<>(nutrients.mineralCu, this.mineralCu / 0.9e-3));
        map.add(new AbstractMap.SimpleImmutableEntry<>(nutrients.mineralFe, this.mineralFe / 8e-3));
        map.add(new AbstractMap.SimpleImmutableEntry<>(nutrients.mineralSe, this.mineralSe / 55e-6));
        return map;
    }

    public void ejectExcess(){
        //this.carbohydrates = (float)Math.min(this.carbohydrates,  * 20);
        this.protein = Math.min(this.protein, 56 * 20);
        //this.fat = (float)Math.min(this.fat,  * 20);
        this.water = Math.min(this.water, 2000 * 20);
        this.vitaminA = (float)Math.min(this.vitaminA, 900e-6 * 20);
        this.vitaminD = (float)Math.min(this.vitaminD, 250e-6 * 20);
        this.vitaminE = (float)Math.min(this.vitaminE, 15e-3 * 20);
        this.vitaminK = (float)Math.min(this.vitaminK, 120e-6 * 20);
        this.vitaminB6 = (float)Math.min(this.vitaminB6, 1.3e-6 * 20);
        this.vitaminB12 = (float)Math.min(this.vitaminB12, 2.4e-6 * 20);
        this.vitaminC = (float)Math.min(this.vitaminC, 90e-3 * 20);
        this.mineralK = (float)Math.min(this.mineralK, 4.7 * 20);
        this.mineralCa = Math.min(this.mineralCa, 20);
        this.mineralP = (float)Math.min(this.mineralP, 0.7 * 20);
        this.mineralMg = (float)Math.min(this.mineralMg, 0.4 * 20);
        this.mineralCu = (float)Math.min(this.mineralCu, 0.9e-3 * 20);
        this.mineralFe = (float)Math.min(this.mineralFe, 8e-3 * 20);
        this.mineralSe = (float)Math.min(this.mineralSe, 55e-6 * 20);
    }

    public ArrayList<nutrients> deficient(double n){
        List<Map.Entry<nutrients, Double>> map = this.getNutrientPercentage();
        ArrayList<nutrients> list = new ArrayList<>();
        for (Map.Entry<nutrients, Double> i : map){
            if (i.getValue() <= n) list.add(i.getKey());
        }
        return list;
    }

    public boolean isSuffering(double n){
        return !this.deficient(n).isEmpty();
    }

    public void toTag(CompoundTag tag, String suffix) {
        tag.putFloat("vfm_store_vitaminA"+suffix, this.vitaminA);
        tag.putFloat("vfm_store_vitaminD"+suffix, this.vitaminD);
        tag.putFloat("vfm_store_vitaminE"+suffix, this.vitaminE);
        tag.putFloat("vfm_store_vitaminK"+suffix, this.vitaminK);
        tag.putFloat("vfm_store_vitaminB6"+suffix, this.vitaminB6);
        tag.putFloat("vfm_store_vitaminB12"+suffix, this.vitaminB12);
        tag.putFloat("vfm_store_vitaminC"+suffix, this.vitaminC);
        tag.putFloat("vfm_store_mineralK"+suffix, this.mineralK);
        tag.putFloat("vfm_store_mineralCa"+suffix, this.mineralCa);
        tag.putFloat("vfm_store_mineralP"+suffix, this.mineralP);
        tag.putFloat("vfm_store_mineralMg"+suffix, this.mineralMg);
        tag.putFloat("vfm_store_mineralCu"+suffix, this.mineralCu);
        tag.putFloat("vfm_store_mineralFe"+suffix, this.mineralFe);
        tag.putFloat("vfm_store_mineralSe"+suffix, this.mineralSe);
    }

    public void fromTag(CompoundTag tag, String suffix) {
        this.vitaminA = tag.getFloat("vfm_store_vitaminA"+suffix);
        this.vitaminD = tag.getFloat("vfm_store_vitaminD"+suffix);
        this.vitaminE = tag.getFloat("vfm_store_vitaminE"+suffix);
        this.vitaminK = tag.getFloat("vfm_store_vitaminK"+suffix);
        this.vitaminB6 = tag.getFloat("vfm_store_vitaminB6"+suffix);
        this.vitaminB12 = tag.getFloat("vfm_store_vitaminB12"+suffix);
        this.vitaminC = tag.getFloat("vfm_store_vitaminC"+suffix);
        this.mineralK = tag.getFloat("vfm_store_mineralK"+suffix);
        this.mineralCa = tag.getFloat("vfm_store_mineralCa"+suffix);
        this.mineralP = tag.getFloat("vfm_store_mineralP"+suffix);
        this.mineralMg = tag.getFloat("vfm_store_mineralMg"+suffix);
        this.mineralCu = tag.getFloat("vfm_store_mineralCu"+suffix);
        this.mineralFe = tag.getFloat("vfm_store_mineralFe"+suffix);
        this.mineralSe = tag.getFloat("vfm_store_mineralSe"+suffix);
    }
}
