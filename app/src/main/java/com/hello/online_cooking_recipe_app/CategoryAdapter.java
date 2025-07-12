package com.hello.online_cooking_recipe_app;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.hello.online_cooking_recipe_app.databinding.CategoryRvBinding;
import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private ArrayList<Recipe> dataList;
    private Context context;

    public CategoryAdapter(ArrayList<Recipe> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final CategoryRvBinding binding;

        public ViewHolder(CategoryRvBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CategoryRvBinding binding = CategoryRvBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipe recipe = dataList.get(position);

        Glide.with(context)
                .load(recipe.getImg())
                .into(holder.binding.img);

        holder.binding.tittle.setText(recipe.getTittle());

        String[] temp = recipe.getIng().split("\n");
        if (temp.length > 0) {
            holder.binding.time.setText(temp[0]);
        } else {
            holder.binding.time.setText("N/A");
        }

        holder.binding.next.setOnClickListener(v -> {
            Intent intent = new Intent(context, RecipeActivity.class);
            intent.putExtra("img", recipe.getImg());
            intent.putExtra("tittle", recipe.getTittle());
            intent.putExtra("des", recipe.getDes());
            intent.putExtra("ing", recipe.getIng());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
    }
}
