package com.devfelipeshimizu.app_recipe.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devfelipeshimizu.app_recipe.R
import com.devfelipeshimizu.app_recipe.model.Recipe

class RecipeAdapter(private val recipes: List<Recipe>) :
    RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    inner class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)
        val tvCategory: TextView = itemView.findViewById(R.id.tvCategory)
        val tvPrepTime: TextView = itemView.findViewById(R.id.tvPrepTime)
        val tvServings: TextView = itemView.findViewById(R.id.tvServings)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recipe, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.tvTitle.text = recipe.title
        holder.tvDescription.text = recipe.description
        holder.tvCategory.text = recipe.category
        holder.tvPrepTime.text = "⏱ ${recipe.prepTime} min"
        holder.tvServings.text = "👥 ${recipe.servings} porções"

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, RecipeDetailActivity::class.java)
            intent.putExtra("RECIPE_ID", recipe.id)
            intent.putExtra("RECIPE_TITLE", recipe.title)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount() = recipes.size
}