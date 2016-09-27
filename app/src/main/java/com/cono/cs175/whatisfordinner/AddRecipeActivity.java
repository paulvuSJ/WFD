package com.cono.cs175.whatisfordinner;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class AddRecipeActivity extends AppCompatActivity {

    private static final int NUM_OF_INGREDIENTS_MIN = 1;
    private static final int NUM_OF_INGREDIENTS_MAX = 10;
    private static final int rcCC = 33;
    private static int RESULT_LOAD_IMG = 1;
    private EditText recipeEditText;
    private Button imageButtonChoose;
    private ImageView imageViewRecipe;
    private LinearLayout linearLayout;
    private Button buttonAddIngredient;
    private ArrayList<WFDSingleton.IngredientsList> myIngredients;
    private ArrayList<Spinner> myArrayListSpinners;
    private ArrayAdapter<WFDSingleton.IngredientsList> spinnerArrayAdapter;
    private Intent in;
    private Recipe myRecipe;
    private boolean isEdited = false;
    private EditText rDirections;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setContentView(R.layout.activity_add_recipe);

        imageViewRecipe = (ImageView) this.findViewById(R.id.imageViewRecipe);
        imageButtonChoose = (Button) this.findViewById(R.id.imageButtonChoose);
        rDirections = (EditText) findViewById(R.id.editTextDirections);
        //imageButtonChoose.setOnClickListener((View.OnClickListener) this);

        //  Use to obtain the value of Recipe name field
        recipeEditText = (EditText) findViewById(R.id.editTextRecipeName);
        myIngredients = WFDSingleton.getInstance().getAllIngredients();

        //  To hide the action bar
        //  if(getSupportActionBar() != null) getSupportActionBar().hide();

        linearLayout = (LinearLayout) findViewById(R.id.myLinearLayoutSpin);
        myArrayListSpinners = new ArrayList<>();
        //  ArrayAdapter<WFDSingleton.IngredientsList>
        spinnerArrayAdapter = new ArrayAdapter<WFDSingleton.IngredientsList>(this, android.R.layout.simple_spinner_dropdown_item, myIngredients);

        for (int i = 0; i < NUM_OF_INGREDIENTS_MAX; i++) {
            Spinner spinner = new Spinner(this);
            spinner.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));

            spinner.setAdapter(spinnerArrayAdapter);
            linearLayout.addView(spinner);
            myArrayListSpinners.add(spinner);
        }

        //	WE NEED TO GET THE RIGHT KEY PASSED ALONG WHEN USER HAS LONG PRESSED ON AN ITEM OF RECIPE MENU (IT IS THE MENU NAME)
        Bundle bundle = getIntent().getExtras();
        int index = -1;
        if (bundle != null)
            index = bundle.getInt("keyOfLongPress");

        if (index == -1) {
            //  If value not change, then no long press, so create new object Recipe
            myRecipe = new Recipe("", null, null);
        } else {
            //  Else, get the old object Recipe to edit it
            myRecipe = WFDSingleton.getInstance().getAllRecipes().get(index);
            isEdited = true;

            recipeEditText.setText(myRecipe.getNameRecipe());
            rDirections.setText(myRecipe.getDirections());

            if (myRecipe.getImageUri() != null) {
                try {
                    final Uri imageUri = myRecipe.getImageUri();

                    final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                    imageViewRecipe.setImageBitmap(selectedImage);
                } catch (FileNotFoundException e) {
                    imageViewRecipe.setImageResource(R.drawable.chooseimage);
                }
            }


            for (int i = 0; i < myRecipe.getListOfIngredientID().size(); i++) {
                int j = myRecipe.getListOfIngredientID().get(i);
                    myArrayListSpinners.get(i).setSelection(j + 1);
            }
            //spinnerArrayAdapter.notifyDataSetChanged();

        }


    }


    public void AddNewIngredient(View view) {

        final LayoutInflater factory = LayoutInflater.from(view.getContext());
        final View textEntryView = factory.inflate(R.layout.custom_view, null);

        final EditText ingredientNameEditText = (EditText) textEntryView.findViewById(R.id.editTextIngredientName);
        final EditText caloriesNameEditText = (EditText) textEntryView.findViewById(R.id.editTextCalories);
        final EditText unitNameEditText = (EditText) textEntryView.findViewById(R.id.editTextUnit);

        final AlertDialog.Builder alertAddIngredient = new AlertDialog.Builder(view.getContext());
        alertAddIngredient.setTitle("Add New Ingredient").setView(textEntryView);
        alertAddIngredient.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String ingredientName = ingredientNameEditText.getText().toString();
                String unitName = unitNameEditText.getText().toString();
                String calories = caloriesNameEditText.getText().toString();
                if (calories.isEmpty()) calories = "0";
                int intCalories = Integer.parseInt(calories);

                if (!ingredientName.isEmpty()) {
                    myIngredients = WFDSingleton.getInstance().addMoreIngredient(ingredientName, intCalories, unitName);
                    spinnerArrayAdapter.addAll(myIngredients);
                    spinnerArrayAdapter.notifyDataSetChanged();
                    Toast.makeText(getBaseContext(), "New Ingredient Added", Toast.LENGTH_SHORT).show();
                    //  Log.d("Test+++++++++++++++++", "Test+++++++++++++++++++++");
                }

            }
        });
        alertAddIngredient.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // TODO Add code here for cancel - Show up a notification for users to see.
                Toast.makeText(getBaseContext(), "Cancelled - No Ingredient Added", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
        alertAddIngredient.show();
    }


    void picPhoto(View view) {
        String str[] = new String[]{"Use Camera", "Photo from Gallery"};
        new AlertDialog.Builder(view.getContext()).setItems(str,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        performImgPicAction(which);
                    }
                }).show();
    }

    void performImgPicAction(int which) {
        if (which == 1) {
            in = new Intent(
                    Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(Intent.createChooser(in, "Select profile picture"), which);

        } else {
            in = new Intent();
            //in.setAction(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            in.setAction(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            if (hasImageCaptureBug()) {
                in.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File("/sdcard/tmp")));
            } else {
                in.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            }
            startActivityForResult(in, RESULT_LOAD_IMG);
        }

//        startActivityForResult(Intent.createChooser(in, "Select profile picture"), which);
    }

    public boolean hasImageCaptureBug() {

        // list of known devices that have the bug
        ArrayList<String> devices = new ArrayList<String>();
        devices.add("android-devphone1/dream_devphone/dream");
        devices.add("generic/sdk/generic");
        devices.add("vodafone/vfpioneer/sapphire");
        devices.add("tmobile/kila/dream");
        devices.add("verizon/voles/sholes");
        devices.add("google_ion/google_ion/sapphire");

        return devices.contains(android.os.Build.BRAND + "/" + android.os.Build.PRODUCT + "/" + android.os.Build.DEVICE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK && null != data) {
                // Get the Image from data

                final Uri selectedImage = data.getData();
                //	Adding this url into the Recipe Object
                //myRecipe.imageUri = selectedImage.getPath();

                final InputStream inputStreamImage = getContentResolver().openInputStream(selectedImage);
                final Bitmap bitmapSelectedImage = BitmapFactory.decodeStream(inputStreamImage);

                ImageView imgView = (ImageView) findViewById(R.id.imageViewRecipe);
                imgView.setImageBitmap(bitmapSelectedImage);

                //  Added image to the current Recipe Object
                myRecipe.setImageUri(selectedImage);

            } else {
                Toast.makeText(this, "You haven't picked Image\nOr the camera won't support this option\nPlease choose image in gallery", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }

    }


    public void AddReceipt(View view) {
        //  TODO: Need to add the selected ingredients and saved them into the recipes
        //  As req. we can added maximum 10 ingredients for 1 recipes
        //String recipe = ingredientsList.getSelectedItem().toString();

        final LayoutInflater factory = LayoutInflater.from(view.getContext());
        //recipeList
        //final View textEntryView = factory.inflate(R.layout.activity_add_recipe, null);
        final View textEntryView = factory.inflate(R.layout.content_recipes, null);

        //  Get Recipe Name
        EditText rName = (EditText) findViewById(R.id.editTextRecipeName);
        final String recipeName = rName.getText().toString();

        //	Get Recipe Directions

        String recipeDirections = rDirections.getText().toString();

        //	Get Selected Ingredients and add it to the Recipe Object
        for (Spinner s : myArrayListSpinners) {
            //	Get the index of selected Spinner that users picked
            int index = s.getSelectedItemPosition(); //	 getSelectedItemPosition()
            //  Log.d("Test+++++++++++++++++", "Test+++++++++++++++++++++");
            Log.d("value of position is:--------------------------------------------------- " + index, "");
            if (index > 0) {
                //	TODO ADD IT TO THE ArrayList of Recipe
                //	Using index - 1 to keep the first index of Ingredients which is used for "Select Ingredient"
                //ArrayList<Integer> tempArray = new ArrayList<>(index - 1);

                myRecipe.setListOfIngredientID(index - 1);
            }

        }

        myRecipe.setNameRecipe(recipeName);
        myRecipe.setDirections(recipeDirections);

        if (recipeName.isEmpty()) {
            Toast.makeText(this, "Please enter Recipe Name.", Toast.LENGTH_LONG).show();
            return;
        }

        if (WFDSingleton.getInstance().checkRecipeDuplication(recipeName) && !isEdited) {
            //	We found duplication - Ignore case sensitive
            Toast.makeText(this, "Not allow duplication recipe.", Toast.LENGTH_LONG).show();
            return;
        } else {
            if (!isEdited) {
                WFDSingleton.getInstance().insertRecipe(myRecipe);
            }
        }
        finish();
    }
}
