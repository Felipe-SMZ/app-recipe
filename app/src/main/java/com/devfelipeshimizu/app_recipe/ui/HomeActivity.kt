package com.devfelipeshimizu.app_recipe.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.devfelipeshimizu.app_recipe.R

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        supportActionBar?.hide()

        findViewById<CardView>(R.id.cardList).setOnClickListener {
            startActivity(Intent(this, RecipeListActivity::class.java))
        }

        findViewById<CardView>(R.id.cardCreate).setOnClickListener {
            startActivity(Intent(this, RecipeFormActivity::class.java))
        }

        findViewById<CardView>(R.id.cardSearch).setOnClickListener {
            startActivity(Intent(this, RecipeSearchActivity::class.java))
        }

//        findViewById<CardView>(R.id.cardUpdate).setOnClickListener {
//            startActivity(Intent(this, RecipeFormActivity::class.java))
//        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}