package com.vlad2305m.vladsfoodmod;

public class NutrientStore {
    float vitaminA;
    float vitaminD;
    float vitaminE;
    float vitaminK;
    float vitaminB1;
    float vitaminB2;
    float vitaminB3;
    float vitaminB5;
    float vitaminB6;
    float vitaminB7;
    float vitaminB9;
    float vitaminB12;
    float vitaminC;

    public NutrientStore(
            float A, float D, float E, float K, float B1, float B2, float B3,
            float B5, float B6, float B7, float B9, float B12, float C
    ) {
        this.vitaminA = A;
        this.vitaminD = D;
        this.vitaminE = E;
        this.vitaminK = K;
        this.vitaminB1 = B1;
        this.vitaminB2 = B2;
        this.vitaminB3 = B3;
        this.vitaminB5 = B5;
        this.vitaminB6 = B6;
        this.vitaminB7 = B7;
        this.vitaminB9 = B9;
        this.vitaminB12 = B12;
        this.vitaminC = C;
    }

    public void add(NutrientStore nutrientStore) {
        this.vitaminA += nutrientStore.vitaminA;
        this.vitaminD += nutrientStore.vitaminD;
        this.vitaminE += nutrientStore.vitaminE;
        this.vitaminK += nutrientStore.vitaminK;
        this.vitaminB1 += nutrientStore.vitaminB1;
        this.vitaminB2 += nutrientStore.vitaminB2;
        this.vitaminB3 += nutrientStore.vitaminB3;
        this.vitaminB5 += nutrientStore.vitaminB5;
        this.vitaminB6 += nutrientStore.vitaminB6;
        this.vitaminB7 += nutrientStore.vitaminB7;
        this.vitaminB9 += nutrientStore.vitaminB9;
        this.vitaminB12 += nutrientStore.vitaminB12;
        this.vitaminC += nutrientStore.vitaminC;
    }


}
