package com.example.parcial2tap

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


data class Fruits (
    val name: String,
    val id: Long,
    val nutritions: Nutritions,
    var imageUrl: String
)

data class Nutritions (
    val calories: Long,
    val fat: Double,
    val sugar: Double,
    val carbohydrates: Double,
    val protein: Double
)
