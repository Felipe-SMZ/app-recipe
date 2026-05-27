package com.devfelipeshimizu.app_recipe.model

data class Recipe( val id: String? = null,
                   val title: String,
                   val description: String,
                   val category: String,
                   val prepTime: Int,
                   val servings: Int,
                   val ingredients: List<Ingredient>,
                   val steps: List<String>,
                   val createdAt: String? = null,
                   val updatedAt: String? = null)
data class Ingredient(
    val name: String,
    val quantity: String
)