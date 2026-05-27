package com.devfelipeshimizu.app_recipe.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.devfelipeshimizu.app_recipe.R
import com.devfelipeshimizu.app_recipe.viewmodel.RecipeViewModel

class RecipeDetailActivity : AppCompatActivity() {

    private val viewModel: RecipeViewModel by viewModels()
    private var recipeId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)

        setupToolbar("Receita")

        recipeId = intent.getStringExtra("RECIPE_ID") ?: run {
            finish()
            return
        }

        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val tvTitle = findViewById<TextView>(R.id.tvTitle)
        val tvCategory = findViewById<TextView>(R.id.tvCategory)
        val tvPrepTime = findViewById<TextView>(R.id.tvPrepTime)
        val tvServings = findViewById<TextView>(R.id.tvServings)
        val tvDescription = findViewById<TextView>(R.id.tvDescription)
        val tvIngredients = findViewById<TextView>(R.id.tvIngredients)
        val tvSteps = findViewById<TextView>(R.id.tvSteps)
        val btnEdit = findViewById<Button>(R.id.btnEdit)
        val btnDelete = findViewById<Button>(R.id.btnDelete)

        viewModel.loading.observe(this) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.recipe.observe(this) { recipe ->
            recipe ?: return@observe
            tvTitle.text = recipe.title
            tvCategory.text = recipe.category
            tvPrepTime.text = "⏱ ${recipe.prepTime} min"
            tvServings.text = "👥 ${recipe.servings} porções"
            tvDescription.text = recipe.description

            // Formata ingredientes
            tvIngredients.text = recipe.ingredients.joinToString("\n") {
                "• ${it.name} — ${it.quantity}"
            }

            // Formata passos numerados
            tvSteps.text = recipe.steps.mapIndexed { index, step ->
                "${index + 1}. $step"
            }.joinToString("\n\n")
        }

        viewModel.error.observe(this) { error ->
            Toast.makeText(this, error, Toast.LENGTH_LONG).show()
        }

        viewModel.success.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            finish()
        }

        btnEdit.setOnClickListener {
            val intent = Intent(this, RecipeFormActivity::class.java)
            intent.putExtra("RECIPE_ID", recipeId)
            startActivity(intent)
        }

        btnDelete.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Deletar Receita")
                .setMessage("Tem certeza que deseja deletar esta receita?")
                .setPositiveButton("Deletar") { _, _ ->
                    viewModel.deleteRecipe(recipeId!!)
                }
                .setNegativeButton("Cancelar", null)
                .show()
        }

        viewModel.getRecipeById(recipeId!!)
    }

    override fun onResume() {
        super.onResume()
        recipeId?.let { viewModel.getRecipeById(it) }
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