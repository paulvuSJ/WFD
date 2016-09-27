package com.cono.cs175.whatisfordinner;

import android.net.Uri;

import java.util.ArrayList;

public class Recipe {
    private String nameRecipe;
    private Uri imageUri;
    private String directions;
    private ArrayList<Integer> listOfIngredientID;

    public Recipe(String nameRecipe, Uri imageUri, String directions) {
        this.nameRecipe = nameRecipe;
        this.imageUri = imageUri;
        this.directions = directions;
        this.listOfIngredientID = new ArrayList<>();
    }

    public String getNameRecipe() {
        return nameRecipe;
    }

    public void setNameRecipe(String nameRecipe) {
        this.nameRecipe = nameRecipe;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    public ArrayList<Integer> getListOfIngredientID() {
        return listOfIngredientID;
    }

    public void setListOfIngredientID(int listOfIngredientID) {
        //this.listOfIngredientID = listOfIngredientID;
        this.listOfIngredientID.add(listOfIngredientID);
    }


    public String toString() {
        return getNameRecipe();
    }

}
