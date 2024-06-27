package com.vimal.healthy.models

data class VerticalRvModel(
    val dishTitle: String,
    val dishCategory: String,
    val dishImage: Int,
    val dishCalories: String,
    val protein: String,
    val fat: String,
    val carbo: String,
    val images: IntArray
)