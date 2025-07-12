package com.hello.online_cooking_recipe_app;

import androidx.room.Dao;
import androidx.room.Query;
import java.util.List;

@Dao
public interface RecipeDao {
    @Query("SELECT * FROM recipe")
    List<Recipe> getAll();
}
