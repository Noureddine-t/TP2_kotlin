package com.example.androidtp2.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.androidtp2.R
import com.example.androidtp2.data.OrdersData

class OrdersAdapter(private val context: Context, private val dataSource: ArrayList<OrdersData>) : BaseAdapter() {
    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getItemId(position: Int): Long = position.toLong()
    override fun getItem(position: Int): Any = dataSource[position]
    override fun getCount(): Int = dataSource.size

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val rowView = inflater.inflate(R.layout.orders_list_item, parent, false)
        val orders = dataSource[position]

        val recipeView = rowView.findViewById<TextView>(R.id.lblRecipe)
        val doughView = rowView.findViewById<TextView>(R.id.lblDough)
        val orderDateView = rowView.findViewById<TextView>(R.id.lblOrderDate)

        recipeView.text = orders.recipe
        doughView.text = orders.dough
        orderDateView.text = orders.orderDate

        return rowView
    }
}