package com.devfelipeshimizu.app_recipe.repository

import com.devfelipeshimizu.app_recipe.model.Recipe
import com.devfelipeshimizu.app_recipe.network.RetrofitClient

class RecipeRepository {

    private val api = RetrofitClient.api

    suspend fun getAllRecipes() = api.getAllRecipes()

    suspend fun getRecipeById(id: String) = api.getRecipeById(id)

    suspend fun createRecipe(recipe: Recipe) = api.createRecipe(recipe)

    suspend fun updateRecipe(id: String, recipe: Recipe) = api.updateRecipe(id, recipe)

    suspend fun deleteRecipe(id: String) = api.deleteRecipe(id)
}