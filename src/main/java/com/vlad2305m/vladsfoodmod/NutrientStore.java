package com.vlad2305m.vladsfoodmod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NutrientStore {
    public float carbohydrates;
    public float protein;
    public float fat;
    public float water;
    public float vitaminA;
    public float vitaminD;
    public float vitaminE;
    public float vitaminK;
    public float vitaminB1;
    public float vitaminB2;
    public float vitaminB3;
    public float vitaminB5;
    public float vitaminB6;
    public float vitaminB9;
    public float vitaminB12;
    public float vitaminC;

    public enum vitamins{ A, D, E, K, B1, B2, B3, B5, B6, B9, B12, C }

    public NutrientStore(float CH, float protein, float fat, float water,
            float A, float D, float E, float K, float B1, float B2, float B3,
            float B5, float B6, float B9, float B12, float C) {
        this.carbohydrates = CH;
        this.protein = protein;
        this.fat = fat;
        this.water = water;
        this.vitaminA = A;
        this.vitaminD = D;
        this.vitaminE = E;
        this.vitaminK = K;
        this.vitaminB1 = B1;
        this.vitaminB2 = B2;
        this.vitaminB3 = B3;
        this.vitaminB5 = B5;
        this.vitaminB6 = B6;
        this.vitaminB9 = B9;
        this.vitaminB12 = B12;
        this.vitaminC = C;
    }

    public NutrientStore(float CH, float protein, float fat, float water) {
        this.carbohydrates = CH;
        this.protein = protein;
        this.fat = fat;
        this.water = water;
        this.vitaminA = 0;
        this.vitaminD = 0;
        this.vitaminE = 0;
        this.vitaminK = 0;
        this.vitaminB1 = 0;
        this.vitaminB2 = 0;
        this.vitaminB3 = 0;
        this.vitaminB5 = 0;
        this.vitaminB6 = 0;
        this.vitaminB9 = 0;
        this.vitaminB12 = 0;
        this.vitaminC = 0;
    }

    public NutrientStore(
            float A, float D, float E, float K, float B1, float B2, float B3,
            float B5, float B6, float B9, float B12, float C) {
        this.carbohydrates = 0;
        this.protein = 0;
        this.fat = 0;
        this.water = 0;
        this.vitaminA = A;
        this.vitaminD = D;
        this.vitaminE = E;
        this.vitaminK = K;
        this.vitaminB1 = B1;
        this.vitaminB2 = B2;
        this.vitaminB3 = B3;
        this.vitaminB5 = B5;
        this.vitaminB6 = B6;
        this.vitaminB9 = B9;
        this.vitaminB12 = B12;
        this.vitaminC = C;
    }

    public NutrientStore() {
        this.carbohydrates = 0;
        this.protein = 0;
        this.fat = 0;
        this.water = 0;
        this.vitaminA = 0;
        this.vitaminD = 0;
        this.vitaminE = 0;
        this.vitaminK = 0;
        this.vitaminB1 = 0;
        this.vitaminB2 = 0;
        this.vitaminB3 = 0;
        this.vitaminB5 = 0;
        this.vitaminB6 = 0;
        this.vitaminB9 = 0;
        this.vitaminB12 = 0;
        this.vitaminC = 0;
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
        this.vitaminB1 += nutrientStore.vitaminB1;
        this.vitaminB2 += nutrientStore.vitaminB2;
        this.vitaminB3 += nutrientStore.vitaminB3;
        this.vitaminB5 += nutrientStore.vitaminB5;
        this.vitaminB6 += nutrientStore.vitaminB6;
        this.vitaminB9 += nutrientStore.vitaminB9;
        this.vitaminB12 += nutrientStore.vitaminB12;
        this.vitaminC += nutrientStore.vitaminC;
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
        this.vitaminB1 -= 1.5e-6 * f;
        this.vitaminB2 -= 1.3e-3 * f;
        this.vitaminB3 -= 16e-3 * f;
        this.vitaminB5 -= 5e-3 * f;
        this.vitaminB6 -= 1.3e-6 * f;
        this.vitaminB9 -= 400e-6 * f;
        this.vitaminB12 -= 2.4e-6 * f;
        this.vitaminC -= 90e-3 * f;
        return this;
    }

    public Map<vitamins, Double> getVitaminPercentage(){
        Map<vitamins, Double> map = new HashMap<>();
        map.put(vitamins.A, this.vitaminA / 900e-6);
        map.put(vitamins.D, this.vitaminD / 250e-6);
        map.put(vitamins.E, this.vitaminE / 15e-3);
        map.put(vitamins.K, this.vitaminK / 120e-6);
        map.put(vitamins.B1, this.vitaminB1 / 1.5e-6);
        map.put(vitamins.B2, this.vitaminB2 / 1.3e-3);
        map.put(vitamins.B3, this.vitaminB3 / 16e-3);
        map.put(vitamins.B5, this.vitaminB5 / 5e-3);
        map.put(vitamins.B6, this.vitaminB6 / 1.3e-6);
        map.put(vitamins.B9, this.vitaminB9 / 400e-6);
        map.put(vitamins.B12, this.vitaminB12 / 2.4e-6);
        map.put(vitamins.C, this.vitaminC / 90e-3);
        return map;
    }

    public ArrayList<vitamins> deficient(double n){
        Map<vitamins, Double> map = this.getVitaminPercentage();
        ArrayList<vitamins> list = new ArrayList<>();
        for (Map.Entry<vitamins, Double> i : map.entrySet()){
            if (i.getValue() <= n) list.add(i.getKey());
        }
        return list;
    }

    public boolean isSuffering(double n){
        return !this.deficient(n).isEmpty();
    }
}
