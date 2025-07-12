package com.hello.online_cooking_recipe_app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;
import com.hello.online_cooking_recipe_app.databinding.ActivitySearchBinding;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchActivity extends AppCompatActivity {
    private ActivitySearchBinding binding;
    private SearchAdapter rvAdapter;
    private ArrayList<Recipe> datalist;
    private List<Recipe> recipes;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.search.requestFocus();

        AppDataBase db = Room.databaseBuilder(this, AppDataBase.class, "db_name")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .createFromAsset("recipe.db")
                .build();

        recipes = db.getDao().getAll();

        binding.goBackHome.setOnClickListener(v -> finish());
        setUpRecyclerView();

        binding.search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null && !s.toString().isEmpty()) {
                    filterData(s.toString());
                } else {
                    rvAdapter.filterList(new ArrayList<>(recipes));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        binding.rvSearch.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
            return false;
        });
    }

    private void filterData(String filterText) {
        List<Recipe> filteredList = new ArrayList<>();
        for (Recipe recipe : recipes) {
            if (recipe != null && recipe.getTittle() != null &&
                    recipe.getTittle().toLowerCase(Locale.ROOT).contains(filterText.toLowerCase(Locale.ROOT))) {
                filteredList.add(recipe);
            }
        }
        rvAdapter.filterList((ArrayList<Recipe>) filteredList);
    }

    private void setUpRecyclerView() {
        datalist = new ArrayList<>();
        binding.rvSearch.setLayoutManager(new LinearLayoutManager(this));

        for (Recipe recipe : recipes) {
            if (recipe != null && recipe.getCategory() != null && recipe.getCategory().contains("Popular")) {
                datalist.add(recipe);
            }
        }

        rvAdapter = new SearchAdapter(datalist, this);
        binding.rvSearch.setAdapter(rvAdapter);
    }
}
