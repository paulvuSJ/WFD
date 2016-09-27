package com.cono.cs175.whatisfordinner;

import android.util.Log;

import java.util.ArrayList;

public class WFDSingleton {
    private ArrayList<Recipe> myRecipes;
    private ArrayList<IngredientsList> myIngredients;

    private static WFDSingleton instance;

    private WFDSingleton(){
        myRecipes = new ArrayList<>();
        myIngredients = new ArrayList<>();
        createIngredients();
    }

    public static WFDSingleton getInstance(){
        if(instance == null){
            instance = new WFDSingleton();
        }
        return instance;

    }

    //public void insertRecipe(String recipeName, ArrayList<Recipe> recipe){
    public void insertRecipe(Recipe recipe){
        myRecipes.add(recipe);
    }

    //	CHECK RECIPE DUPLICATION BY NAME
    public boolean checkRecipeDuplication(String InputNameRecipe) {
        for(Recipe recipe : myRecipes) {
            //	It is great to avoid case sensitive at this point ... not sure what Android has to deal with it
            //	Do I need toString() when it is already string ???
            //if(InputNameRecipe.toString().toLowerCase() == recipe.getNameRecipe().toString().toLowerCase()) {
            if(InputNameRecipe.toString().toLowerCase().equals(recipe.getNameRecipe().toString().toLowerCase())) {
                Log.d("Test", "found equal ---------------------");
                return true;
            }
        }
        return false;
    }





    public ArrayList<Recipe> getAllRecipes(){
        return myRecipes;
    }

    public ArrayList<IngredientsList> getAllIngredients() {
        return myIngredients;
    }

    public void createIngredients() {
        myIngredients.add(new IngredientsList("Select Ingredient"));
        myIngredients.add(new IngredientsList("Item 1", 1, "lb"));
        myIngredients.add(new IngredientsList("Item 2", 2, "lbs"));
        myIngredients.add(new IngredientsList("Item 3", 1, "pc"));
        myIngredients.add(new IngredientsList("Item 4", 3, "ounces"));
        myIngredients.add(new IngredientsList("Item 5", 6, "tablespoon"));
        myIngredients.add(new IngredientsList("Item 6", 8, "teaspoon"));
        myIngredients.add(new IngredientsList("Item 7", 10, "grams"));
        myIngredients.add(new IngredientsList("Item 8", 11, "cups"));
        myIngredients.add(new IngredientsList("Item 9", 15, "oz"));
        myIngredients.add(new IngredientsList("Item 10", 5, "ml"));
    }

    public ArrayList<IngredientsList> addMoreIngredient(String name, int calorie, String unit) {
        ArrayList<IngredientsList> temp = new ArrayList<>();
        if(unit == null) {
            temp.add(new IngredientsList(name, calorie));
        } else {
            temp.add(new IngredientsList(name, calorie, unit));
        }
        return temp;
    }

    public class IngredientsList {
        private String name;    //	Name of the ingredient
        private int calories;   //	Calculate the calories for extra credits
        private String unit;    //  Units of Ingredients such as lbs/kg/pc ect

        public IngredientsList() {
            this.name = null;
            this.calories = 0;    //	By default, we do not care about calories
            this.unit = null;
        }

        public IngredientsList(String ingredientName) {
            this.name = ingredientName;
            this.calories = 0;    //	By default, we do not care about calories
        }

        public IngredientsList(String ingredientName, int ingredientCalories) {
            this.name = ingredientName;
            this.calories = ingredientCalories;
        }

        public IngredientsList(String ingredientName, int ingredientCalories, String unitName) {
            this.name = ingredientName;
            this.calories = ingredientCalories;
            this.unit = unitName;
        }

        public int getCalories() {
            return calories;
        }

        public void setCalories(int calories) {
            this.calories = calories;
        }

        public String getName() {
            return name;
        }

        public String getUnit() {
            return unit;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String toString() {
            String t = ((getCalories() > 1) ? " calories" : " calorie");
            if(getCalories() <= 0) {
                return getName() + (getUnit() == null ? "" : " (  - " + getUnit() + " )") ;
            } else {
                return getName() + " ( " + getCalories() +  t + (getUnit() == null ? "" : " - " + getUnit()) + " Units )";
            }

        }

    }





}
