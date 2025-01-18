package com.example.androidtp2


import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.androidtp2.data.OrdersData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.firstOrNull

private val Context.OrdersStore by preferencesDataStore(name = "orders");

class OrdersStorage(private var context: Context) {

    private val orderKey = stringPreferencesKey("orders")

    suspend fun write(ordersJson: String) {
        context.OrdersStore.edit { preferences ->
            preferences[orderKey] = ordersJson
        }
    }

    suspend fun read(): List<OrdersData> {
        val data = context.OrdersStore.data.firstOrNull()?.get(orderKey)
        if (data == null) {
            return emptyList()
        }

        // Désérialiser le JSON en liste d'objets OrdersData
        val type = object : TypeToken<List<OrdersData>>() {}.type
        return Gson().fromJson(data, type)
    }
}