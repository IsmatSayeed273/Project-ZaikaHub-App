package com.hello.online_cooking_recipe_app;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "recipe")
public class Recipe {

    @NonNull
    private String img;

    @NonNull
    private String tittle;

    @NonNull
    private String des;

    @NonNull
    private String ing;

    @NonNull
    private String category;

    @PrimaryKey(autoGenerate = true)
    public int uid;

    public Recipe(@NonNull String img, @NonNull String tittle, @NonNull String des, @NonNull String ing, @NonNull String category) {
        this.img = img;
        this.tittle = tittle;
        this.des = des;
        this.ing = ing;
        this.category = category;
    }

    public String getImg() { return img; }
    public void setImg(String img) { this.img = img; }

    public String getTittle() { return tittle; }
    public void setTittle(String tittle) { this.tittle = tittle; }

    public String getDes() { return des; }
    public void setDes(String des) { this.des = des; }

    public String getIng() { return ing; }
    public void setIng(String ing) { this.ing = ing; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public int getUid() { return uid; }
    public void setUid(int uid) { this.uid = uid; }
}
