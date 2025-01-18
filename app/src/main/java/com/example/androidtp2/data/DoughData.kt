package com.example.androidtp2.data

data class DoughData(
    val id: Int,
    val name: String
) {
    override fun toString(): String {
        return name
    }
}
