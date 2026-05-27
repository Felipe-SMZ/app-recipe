package com.devfelipeshimizu.app_recipe.ui

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.devfelipeshimizu.app_recipe.R
import com.devfelipeshimizu.app_recipe.model.Ingredient
import com.devfelipeshimizu.app_recipe.model.Recipe
import com.devfelipeshimizu.app_recipe.viewmodel.RecipeViewModel
import com.google.android.material.textfield.TextInputEditText

class RecipeFormActivity : AppCompatActivity() {

    private val viewModel: RecipeViewModel by viewModels()
    private var currentRecipeId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_form)

        val tvTitle = findViewById<TextView>(R.id.tvTitle)
        val etId = findViewById<TextInputEditText>(R.id.etId)
        val etTitle = findViewById<TextInputEditText>(R.id.etTitle)
        val etDescription = findViewById<TextInputEditText>(R.id.etDescription)
        val etCategory = findViewById<TextInputEditText>(R.id.etCategory)
        val etPrepTime = findViewById<TextInputEditText>(R.id.etPrepTime)
        val etServings = findViewById<TextInputEditText>(R.id.etServings)
        val etIngredients = findViewById<TextInputEditText>(R.id.etIngredients)
        val etSteps = findViewById<TextInputEditText>(R.id.etSteps)
        val btnSave = findViewById<Button>(R.id.btnSave)

        val recipeId = intent.getStringExtra("RECIPE_ID")

        if (recipeId != null) {
            currentRecipeId = recipeId
            supportActionBar?.title = "Editar Receita"
            tvTitle.text = "✏️ Editar Receita"
            btnSave.text = "Atualizar Receita"
            etId.setText(recipeId)

            // Busca e preenche os dados da receita
            viewModel.getRecipeById(recipeId)
            viewModel.recipe.observe(this) { recipe ->
                recipe ?: return@observe
                etTitle.setText(recipe.title)
                etDescription.setText(recipe.description)
                etCategory.setText(recipe.category)
                etPrepTime.setText(recipe.prepTime.toString())
                etServings.setText(recipe.servings.toString())
                etIngredients.setText(
                    recipe.ingredients.joinToString("\n") { "${it.name}|${it.quantity}" }
                )
                etSteps.setText(recipe.steps.joinToString("\n"))
            }
        } else {
            setupToolbar("Nova Receita")
        }

        viewModel.success.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            finish()
        }

        viewModel.error.observe(this) { error ->
            Toast.makeText(this, error, Toast.LENGTH_LONG).show()
        }

        btnSave.setOnClickListener {
            val title = etTitle.text.toString().trim()
            val description = etDescription.text.toString().trim()
            val category = etCategory.text.toString().trim()
            val prepTime = etPrepTime.text.toString().trim()
            val servings = etServings.text.toString().trim()
            val ingredientsRaw = etIngredients.text.toString().trim()
            val stepsRaw = etSteps.text.toString().trim()

            if (title.isEmpty() || description.isEmpty() || category.isEmpty()
                || prepTime.isEmpty() || servings.isEmpty()
            ) {
                Toast.makeText(this, "Preencha todos os campos obrigatórios", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            val ingredients = ingredientsRaw.lines()
                .map { it.trim() }
                .filter { it.isNotBlank() && it.contains("|") }
                .map {
                    val parts = it.split("|")
                    Ingredient(
                        name = parts.getOrElse(0) { "" }.trim(),
                        quantity = parts.getOrElse(1) { "" }.trim()
                    )
                }
                .filter { it.name.isNotEmpty() && it.quantity.isNotEmpty() }

            val steps = stepsRaw.lines()
                .map { it.trim() }
                .filter { it.isNotBlank() }

            val recipe = Recipe(
                title = title,
                description = description,
                category = category,
                prepTime = prepTime.toInt(),
                servings = servings.toInt(),
                ingredients = ingredients,
                steps = steps
            )

            if (currentRecipeId != null) {
                viewModel.updateRecipe(currentRecipeId!!, recipe)
            } else {
                viewModel.createRecipe(recipe)
            }
        }
    }

    private fun setupToolbar(title: String, showBack: Boolean = true) {
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = title
        if (showBack) supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}