package com.example.androidtp2.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import com.example.androidtp2.R
import com.example.androidtp2.api.Api
import com.example.androidtp2.data.DoughData
import com.example.androidtp2.data.OrderData
import com.example.androidtp2.data.RecipeData


class OrderActivity : AppCompatActivity() {

    private val recipes = ArrayList<RecipeData>()
    private val doughs = ArrayList<DoughData>()

    private lateinit var recipesAdapter: ArrayAdapter<RecipeData>
    private lateinit var doughsAdapter: ArrayAdapter<DoughData>
    private lateinit var token: String

    private fun loadRecipes() {
        Api().get<List<RecipeData>>("https://mypizza.lesmoulinsdudev.com/recipes", ::loadRecipesSuccess)
    }

    private fun loadRecipesSuccess(responseCode: Int, loadedRecipes: List<RecipeData>?) {
            if (responseCode == 200 && loadedRecipes != null) {
                recipes.clear()
                recipes.addAll(loadedRecipes)
                updateRecipesList()
            }
    }

    private fun updateRecipesList() {
        runOnUiThread {
            recipesAdapter.notifyDataSetChanged()
        }
    }

    private fun loadDoughs() {
        Api().get<List<DoughData>>("https://mypizza.lesmoulinsdudev.com/doughs", ::loadDoughsSuccess)
    }

    private fun loadDoughsSuccess(responseCode: Int, loadedDoughs: List<DoughData>?) {
            if (responseCode == 200 && loadedDoughs != null) {
                doughs.clear()
                doughs.addAll(loadedDoughs)
                updateDoughsList()
            }
    }

    private fun updateDoughsList() {
        runOnUiThread {
            doughsAdapter.notifyDataSetChanged()
        }
    }

    private fun initializeSpinners() {
        val spinRecipe = findViewById<Spinner>(R.id.spinRecipe)
        spinRecipe.adapter = recipesAdapter
        val spinDough = findViewById<Spinner>(R.id.spinDough)
        spinDough.adapter = doughsAdapter
    }

    private fun sendOrder() {
        val selectedRecipe = findViewById<Spinner>(R.id.spinRecipe).selectedItem as RecipeData
        val selectedDough = findViewById<Spinner>(R.id.spinDough).selectedItem as DoughData
        val data = OrderData(selectedRecipe.id, selectedDough.id)

        Api().post("https://mypizza.lesmoulinsdudev.com/order", data, ::orderSuccess, token)
    }

    private fun orderSuccess(responseCode: Int) {
            if (responseCode == 200) {
                finish()
            }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
        recipesAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, recipes)
        doughsAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, doughs)
        token = intent.getStringExtra("TOKEN") ?: ""
        loadRecipes()
        loadDoughs()
        initializeSpinners()
        findViewById<Button>(R.id.btnOrder).setOnClickListener {
            sendOrder()
        }
    }

    public fun goToOrders(view: View) {
        finish();
    }
}