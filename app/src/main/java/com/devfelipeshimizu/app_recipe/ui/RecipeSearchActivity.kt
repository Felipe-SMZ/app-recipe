package com.devfelipeshimizu.app_recipe.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.devfelipeshimizu.app_recipe.R
import com.devfelipeshimizu.app_recipe.viewmodel.RecipeViewModel
import com.google.android.material.textfield.TextInputEditText

class RecipeSearchActivity : AppCompatActivity() {

    private val viewModel: RecipeViewModel by viewModels()
    private var foundRecipeId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_search)

        setupToolbar("Buscar Receita")

        val etRecipeId = findViewById<TextInputEditText>(R.id.etRecipeId)
        val btnSearch = findViewById<Button>(R.id.btnSearch)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val cardResult = findViewById<CardView>(R.id.cardResult)
        val tvRecipeTitle = findViewById<TextView>(R.id.tvRecipeTitle)
        val tvRecipeCategory = findViewById<TextView>(R.id.tvRecipeCategory)
        val tvRecipeDescription = findViewById<TextView>(R.id.tvRecipeDescription)
        val tvRecipeInfo = findViewById<TextView>(R.id.tvRecipeInfo)
        val btnViewDetail = findViewById<Button>(R.id.btnViewDetail)

        viewModel.loading.observe(this) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.recipe.observe(this) { recipe ->
            if (recipe != null) {
                foundRecipeId = recipe.id
                cardResult.visibility = View.VISIBLE
                tvRecipeTitle.text = recipe.title
                tvRecipeCategory.text = "📁 ${recipe.category}"
                tvRecipeDescription.text = recipe.description
                tvRecipeInfo.text = "⏱ ${recipe.prepTime} min  •  👥 ${recipe.servings} porções"
            } else {
                cardResult.visibility = View.GONE
            }
        }

        viewModel.error.observe(this) { error ->
            cardResult.visibility = View.GONE
            Toast.makeText(this, error, Toast.LENGTH_LONG).show()
        }

        btnSearch.setOnClickListener {
            val id = etRecipeId.text.toString().trim()
            if (id.isEmpty()) {
                Toast.makeText(this, "Digite um ID", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.getRecipeById(id)
        }

        btnViewDetail.setOnClickListener {
            foundRecipeId?.let { id ->
                val intent = Intent(this, RecipeDetailActivity::class.java)
                intent.putExtra("RECIPE_ID", id)
                startActivity(intent)
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