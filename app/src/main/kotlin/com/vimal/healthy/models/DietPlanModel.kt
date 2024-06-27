package com.vimal.healthy.models

data class DietPlanModel(
    val name: String,
    val kcalValue: String,
    val preparationTime: String,
    val difficulty: String,
    val steps: String,
    val colorPrimary: Int,
    val colorSecondary: Int,
    val stepsColor: String,
//    val textEasyColor: Color,
//    val textMediumColor: Color,
    val image: String,

    )