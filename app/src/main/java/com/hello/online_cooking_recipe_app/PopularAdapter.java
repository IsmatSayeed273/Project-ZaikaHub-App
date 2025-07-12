package com.hello.online_cooking_recipe_app;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hello.online_cooking_recipe_app.databinding.PopularRvItemBinding;

import java.util.ArrayList;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ViewHolder> {

    private ArrayList<Recipe> dataList;
    private Context context;

    public PopularAdapter(ArrayList<Recipe> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final PopularRvItemBinding binding;

        public ViewHolder(PopularRvItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PopularRvItemBinding binding = PopularRvItemBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipe recipe = dataList.get(position);

        Glide.with(context).load(recipe.getImg()).into(holder.binding.popularImg);

        holder.binding.popularTxt.setText(recipe.getTittle());

        String[] time = recipe.getIng().split("\n");
        if (time.length > 0) {
            holder.binding.popularTime.setText(time[0]);
        }

        holder.itemView.setOnClickListener(v -> {
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
