package com.hello.online_cooking_recipe_app;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.hello.online_cooking_recipe_app.databinding.ActivityRecipeBinding;

public class RecipeActivity extends AppCompatActivity {
    private ActivityRecipeBinding binding;
    private boolean imgCrop = true;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecipeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Glide.with(this).load(getIntent().getStringExtra("img")).into(binding.itemImage);
        binding.recipeTitle.setText(getIntent().getStringExtra("tittle"));
        binding.stepsData.setText(getIntent().getStringExtra("des"));

        String ingText = getIntent().getStringExtra("ing");
        if (ingText != null) {
            String[] ing = ingText.split("\n");
            if (ing.length > 0) {
                binding.time.setText(ing[0]);
                StringBuilder ingList = new StringBuilder();
                for (int i = 1; i < ing.length; i++) {
                    ingList.append("\n• ").append(ing[i]);
                }
                binding.ingData.setText(ingList.toString().trim());
            }
        }

        binding.steps.setBackground(null);
        binding.steps.setTextColor(getColor(R.color.black));

        binding.steps.setOnClickListener(v -> {
            binding.steps.setBackgroundResource(R.drawable.btn_ing);
            binding.steps.setTextColor(getColor(R.color.white));
            binding.ing.setTextColor(getColor(R.color.black));
            binding.ing.setBackground(null);
            binding.stepScroll.setVisibility(View.VISIBLE);
            binding.ingScroll.setVisibility(View.GONE);
        });

        binding.ing.setOnClickListener(v -> {
            binding.ing.setBackgroundResource(R.drawable.btn_ing);
            binding.ing.setTextColor(getColor(R.color.white));
            binding.steps.setTextColor(getColor(R.color.black));
            binding.steps.setBackground(null);
            binding.ingScroll.setVisibility(View.VISIBLE);
            binding.stepScroll.setVisibility(View.GONE);
        });

        binding.fullscreen.setOnClickListener(v -> {
            if (imgCrop) {
                binding.itemImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
                Glide.with(this).load(getIntent().getStringExtra("img")).into(binding.itemImage);
                binding.fullscreen.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);
                binding.shade.setVisibility(View.GONE);
                imgCrop = false;
            } else {
                binding.itemImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Glide.with(this).load(getIntent().getStringExtra("img")).into(binding.itemImage);
                binding.fullscreen.setColorFilter(null);
                binding.shade.setVisibility(View.GONE);
                imgCrop = true;
            }
        });

        binding.backBtn.setOnClickListener(v -> finish());
    }
}