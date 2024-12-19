package com.example.dwitchapp.model

import androidx.compose.ui.graphics.Color
import com.example.dwitchapp.model.Ingredientkind.*
import com.example.ui.theme.OpenColors
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.LocalDateTime
import java.util.Date


enum class Ingredientkind {
    @Json(name = "bread") BREAD,
    @Json(name = "main")MAIN,
    @Json(name = "topping")TOPPING,
    @Json(name = "sauce") SAUCE;


}
   @JsonClass(generateAdapter = true)
    data class Ingredient(
        val id: Long? = null,
        val documentId: String? = null,
        val name: String? = null,
        val description: String? = null,
        val isVegan: Boolean? = false,
        val isSpicy: Boolean? = false,
        val ingredientKind: Ingredientkind = TOPPING ,

    )




fun Ingredientkind.color(): Color {
    return when (this) {
        BREAD -> OpenColors.Purple40
        MAIN -> OpenColors.pink0
        TOPPING -> OpenColors.orange0
        SAUCE -> OpenColors.blue8
    }
}



fun Ingredientkind.emoji(): String {
    return when (this) {
        BREAD -> "🍞"
        MAIN -> "🍖"
        TOPPING -> "🧀"
        SAUCE -> "🍯"
    }
}