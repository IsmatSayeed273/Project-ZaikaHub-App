package com.hello.online_cooking_recipe_app;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import com.hello.online_cooking_recipe_app.databinding.ActivityHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private PopularAdapter rvAdapter;
    private ActivityHomeBinding binding;
    private ArrayList<Recipe> datalist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setUpRecyclerView();

        binding.editTextText.setOnClickListener(v ->
                startActivity(new Intent(HomeActivity.this, SearchActivity.class))
        );

        setCategoryClickListener(binding.salad, "Salad");
        setCategoryClickListener(binding.mainDish, "Dish");
        setCategoryClickListener(binding.drinks, "Drinks");
        setCategoryClickListener(binding.desserts, "Desserts");

        binding.menu.setOnClickListener(v -> openBottomMenu());
    }

    private void setCategoryClickListener(android.view.View view, String category) {
        view.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, CategoryActivity.class);
            intent.putExtra("CATEGORY", category);  // Fixed intent key
            startActivity(intent);
        });
    }

    private void openBottomMenu() {
        Dialog dialog = new Dialog(HomeActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_sheet);
        dialog.show();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setGravity(Gravity.BOTTOM);
        }
    }

    private void setUpRecyclerView() {
        datalist = new ArrayList<>();
        binding.rvpopular.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        AppDataBase db = Room.databaseBuilder(this, AppDataBase.class, "db_name")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .createFromAsset("recipe.db")
                .build();

        RecipeDao daoObject = db.getDao();
        List<Recipe> recipes = daoObject.getAll();

        if (recipes != null) {
            for (Recipe recipe : recipes) {
                if (recipe.getCategory() != null && recipe.getCategory().contains("Popular")) {
                    datalist.add(recipe);
                }
            }
        }

        rvAdapter = new PopularAdapter(datalist, this);
        binding.rvpopular.setAdapter(rvAdapter);
    }
}
