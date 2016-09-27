package com.cono.cs175.whatisfordinner;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.content.*;

/**
 * A simple {@link Fragment} subclass.
 */
public class LandscapeFragment extends Fragment implements View.OnClickListener {


    public LandscapeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_landscape, container, false);

        // Inflate the layout for this fragment
        //  return inflater.inflate(R.layout.fragment_landscape, container, false);
        ImageButton btnIBListen;
        Button btnNewDishListen, tbtRecipesListen, btnMealsListen, tbnGroceriesListen;

        btnIBListen = (ImageButton) view.findViewById(R.id.btnMainLandscape);
        btnNewDishListen = (Button) view.findViewById(R.id.btnNewDishLandscape);
        tbtRecipesListen = (Button) view.findViewById(R.id.btnRecipesLandscape);
        btnMealsListen = (Button) view.findViewById(R.id.btnMealsLandscape);
        tbnGroceriesListen = (Button) view.findViewById(R.id.btnGroceriesLandscape);

        checkImageButton(btnIBListen);
        checkButton(btnMealsListen);
        checkButton(tbnGroceriesListen);
        checkButton(btnNewDishListen);
        checkButton(tbtRecipesListen);

        return view;
    }

    public void checkButton(Button button) {
        if(button != null) {
            button.setOnClickListener(this);
        }
    }
    public void checkImageButton(ImageButton imageButton) {
        if(imageButton != null) {
            imageButton.setOnClickListener(this);
        }
    }

    public void onClick(View v) {
        Log.d("Test", "Test");
        switch (v.getId()) {
            case R.id.btnMainLandscape:
                //  TODO: ADDING CODES HERE
                //Toast.makeText(getActivity(), getString(R.string.author_and_version), Toast.LENGTH_SHORT).show();
                //Toast.makeText(getActivity(), getString(R.string.author_name), Toast.LENGTH_SHORT).show();
                MainWFDActivity aboutMe = new MainWFDActivity();
                aboutMe.aboutAuthor(getView());

                break;
            case R.id.btnMealsLandscape:
                Intent intentMeals = new Intent(getActivity(), MealsActivity.class);
                startActivity(intentMeals);
                break;
            case R.id.btnRecipesLandscape:
                Intent intentRecipes = new Intent(getActivity(), RecipesActivity.class);
                startActivity(intentRecipes);
                break;
            case R.id.btnGroceriesLandscape:
                Intent intentGroceries = new Intent(getActivity(), GroceriesActivity.class);
                startActivity(intentGroceries);
                break;
            case R.id.btnNewDishLandscape:
                Intent intentNewDish = new Intent(getActivity(), AddRecipeActivity.class);
                startActivity(intentNewDish);
                break;
            default:
                Toast.makeText(getActivity(), "btnNewDishLandscape", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}