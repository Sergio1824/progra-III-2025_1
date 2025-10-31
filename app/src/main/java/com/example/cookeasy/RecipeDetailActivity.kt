package com.example.cookeasy

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class RecipeDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)

        val title = intent.getStringExtra("title")
        val imageResId = intent.getIntExtra("imageResId", 0)
        val ingredients = intent.getStringExtra("ingredients")
        val steps = intent.getStringExtra("steps")

        val imageView: ImageView = findViewById(R.id.detailImage)
        val titleView: TextView = findViewById(R.id.detailTitle)
        val ingredientsView: TextView = findViewById(R.id.detailIngredients)
        val stepsView: TextView = findViewById(R.id.detailSteps)

        imageView.setImageResource(imageResId)
        titleView.text = title
        ingredientsView.text = "Ingredientes:\n$ingredients"
        stepsView.text = "Preparación:\n$steps"
    }
}
