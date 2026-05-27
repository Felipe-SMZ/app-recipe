package com.devfelipeshimizu.app_recipe.network

import com.devfelipeshimizu.app_recipe.model.Recipe
import retrofit2.Response
import retrofit2.http.*

interface RecipeApiService {

    @GET("recipes")
    suspend fun getAllRecipes(): Response<List<Recipe>>

    @GET("recipes/{id}")
    suspend fun getRecipeById(@Path("id") id: String): Response<Recipe>

    @POST("recipes")
    suspend fun createRecipe(@Body recipe: Recipe): Response<Recipe>

    @PUT("recipes/{id}")
    suspend fun updateRecipe(
        @Path("id") id: String,
        @Body recipe: Recipe
    ): Response<Recipe>

    @DELETE("recipes/{id}")
    suspend fun deleteRecipe(@Path("id") id: String): Response<Unit>
}