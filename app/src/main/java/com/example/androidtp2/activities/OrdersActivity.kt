package com.example.androidtp2.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.Toast
import com.example.androidtp2.OrdersStorage
import com.example.androidtp2.R
import com.example.androidtp2.adapter.OrdersAdapter
import com.example.androidtp2.api.Api
import com.example.androidtp2.data.OrdersData
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


class OrdersActivity : AppCompatActivity() {


    private val orders = ArrayList<OrdersData>()
    private lateinit var ordersAdapter: OrdersAdapter
    private val mainScope = MainScope()
    private lateinit var token: String


    private fun addOrder() {
        val token = intent.getStringExtra("TOKEN")
        Api().get<List<OrdersData>>("https://mypizza.lesmoulinsdudev.com/orders", ::loadOrdersSuccess, token)
    }

    private fun loadOrdersSuccess(responseCode: Int, loadedOrders: List<OrdersData>?) {
            if (responseCode == 200 && loadedOrders != null) {
                orders.clear()
                orders.addAll(loadedOrders)
                ordersAdapter.notifyDataSetChanged()
            }
    }

    private fun saveOrdersList(ordersJson: String){
        val ordresStorage = OrdersStorage(this)
        mainScope.launch{
            ordresStorage.write(ordersJson)
        }
    }

    private fun initializeOrdersList() {
        val ordersListView = findViewById<ListView>(R.id.lstOrders)
        ordersListView.adapter = ordersAdapter
    }

    private fun updateOrdersList() {
        val ordersStorage = OrdersStorage(this)
        mainScope.launch {
            orders.clear()
            orders.addAll(ordersStorage.read())
            ordersAdapter.notifyDataSetChanged()
        }
    }

    override fun onResume() {
        super.onResume()
        addOrder()
        ordersAdapter= OrdersAdapter(this, orders)
        initializeOrdersList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orders)
        token = intent.getStringExtra("TOKEN") ?: ""
        ordersAdapter = OrdersAdapter(this, orders)
        initializeOrdersList()
        addOrder()
    }

    public fun newOrder(view: View) {
        val intent = Intent(this, OrderActivity::class.java)
        intent.putExtra("TOKEN", token)
        startActivity(intent)
    }
}