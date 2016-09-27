package com.cono.cs175.whatisfordinner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class RecipesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.recipeList);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().hide();

        ArrayList<Recipe> recipes = WFDSingleton.getInstance().getAllRecipes();

        ArrayList<String> recipesNames = new ArrayList<>();

        for(Recipe r : recipes) {
            recipesNames.add(r.getNameRecipe());
        }




        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, recipesNames);

        listView.setAdapter(adapter);

        //  WORKING ON CLICK
        listView.setOnItemClickListener(mRecipeDisplay);

        // TODO: SHORT PRESS TO ADD THE RECIPE INTO MEALS MENU IN HERE



        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View view,int index, long arg3) {
                Toast.makeText(view.getContext(), "item: LONGPRESS" + index,Toast.LENGTH_LONG).show();
                Intent intents = new Intent();

                Bundle bundle = new Bundle();
                bundle.putInt("keyOfLongPress", index);

                Intent intent = new Intent(RecipesActivity.this, AddRecipeActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, 101);


                return true;
            }
        });

    }

    private AdapterView.OnItemClickListener mRecipeDisplay = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Toast.makeText(view.getContext(), "Click detected!", Toast.LENGTH_LONG).show();

        }
    };

    private ListView listView;

    private ArrayAdapter<String> adapter;



}
