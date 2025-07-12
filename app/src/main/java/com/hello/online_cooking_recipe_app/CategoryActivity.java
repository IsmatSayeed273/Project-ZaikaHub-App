package com.hello.online_cooking_recipe_app;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;
import com.hello.online_cooking_recipe_app.databinding.ActivityCategoryBinding;
import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    private CategoryAdapter rvAdapter;
    private ArrayList<Recipe> datalist;
    private ActivityCategoryBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String category = getIntent().getStringExtra("CATEGORY");

        if (category != null && !category.trim().isEmpty()) {
            binding.category.setText(category);  // ✅ Dynamically updating category title
        } else {
            binding.category.setText("Unknown Category");
            Toast.makeText(this, "Category not received!", Toast.LENGTH_SHORT).show();
        }

        setUpRecyclerView(category);

        binding.goBackHome.setOnClickListener(v -> finish());
    }

    private void setUpRecyclerView(String category) {
        datalist = new ArrayList<>();
        binding.rvCategory.setLayoutManager(new LinearLayoutManager(this));

        AppDataBase db = Room.databaseBuilder(getApplicationContext(), AppDataBase.class, "db_name")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .createFromAsset("recipe.db")
                .build();

        RecipeDao daoObject = db.getDao();

        List<Recipe> recipes = daoObject.getAll();

        if (recipes == null || recipes.isEmpty()) {
            Log.e("CategoryActivity", "No recipes found in the database");
            Toast.makeText(this, "No recipes available", Toast.LENGTH_SHORT).show();
            return;
        }

        Log.d("CategoryActivity", "Total recipes found: " + recipes.size());

        if (category == null || category.trim().isEmpty()) {
            Log.e("CategoryActivity", "No category received from intent");
            Toast.makeText(this, "Category not found", Toast.LENGTH_SHORT).show();
            return;
        }

        category = category.trim(); // Remove extra spaces
        Log.d("CategoryActivity", "Category received: " + category);

        for (Recipe recipe : recipes) {
            if (recipe != null && recipe.getCategory() != null) {
                String recipeCategory = recipe.getCategory().trim();
                Log.d("CategoryActivity", "Checking recipe: " + recipe.getTittle() + " (Category: " + recipeCategory + ")");

                if (recipeCategory.equalsIgnoreCase(category)) {
                    datalist.add(recipe);
                }
            }
        }

        Log.d("CategoryActivity", "Filtered recipes count: " + datalist.size());

        if (datalist.isEmpty()) {
            Log.e("CategoryActivity", "No recipes matched the selected category");
            Toast.makeText(this, "No recipes found for this category", Toast.LENGTH_SHORT).show();
        }

        rvAdapter = new CategoryAdapter(datalist, this);
        binding.rvCategory.setAdapter(rvAdapter);
    }
}
