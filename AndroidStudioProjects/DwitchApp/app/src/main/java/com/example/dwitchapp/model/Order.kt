package com.example.dwitchapp.model

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.util.Date

data class Order (
    val id: Long? = null,
    val documentID: String? = null,
    val placedAt: Date? = null,
    val receivedAt: Date? = null,
    val cookMessage: String? = null,
    val price: Long? = null,
    val progress: Long? = null,
    val createdAt: Date? = null,
    val updatedAt: Date? = null,
    val publishedAt: Date? = null,
    val ingredients: List<Ingredient>? = null,
    val usersPermissionsUser: UsersPermissionsUser? = null,
    val store: Store? = null
)


val orders = listOf(
    Order(
        id = 1,
        documentID = "DOC12345",
        placedAt = Date(2024, 10, 11, 11, 45),
        receivedAt = Date(2024, 10, 11, 12, 0),
        cookMessage = "Ajoutez un peu plus d'oignons caramélisés.",
        price = 12L, // en centimes
        progress = 50L,
        createdAt = Date(2024, 10, 10, 9, 30),
        updatedAt = Date(2024, 10, 11, 10, 0),
        publishedAt = Date(2024, 10, 11, 11, 0),
        ingredients = listOf(
            Ingredient(name = "Oignons caramélisés", ingredientKind = Ingredientkind.TOPPING),
            Ingredient(name = "Pain Ciabatta", ingredientKind = Ingredientkind.BREAD)
        ),
        usersPermissionsUser = UsersPermissionsUser(
            id = 101,
            username = "john_doe",
            email = "john.doe@example.com"
        ),
        store = Store(
            id = 501,
            documentID = "STORE12345",
            name = "Dwitch Bonlieu",
            isOpen = true,
            city = "Annecy",
            zipCode = "74000",
            createdAt = Date(2024, 10, 10, 9, 30),
            updatedAt = Date(2024, 10, 11, 10, 0),
            publishedAt = Date(2024, 10, 11, 11, 0)
        )
    ),
    Order(
        id = 2,
        documentID = "DOC67890",
        placedAt = Date(2024, 11, 19, 11, 45),
        receivedAt = Date(2024, 11, 19, 12, 0),
        cookMessage = "Pas de sauce, merci.",
        price = 14L, // 14.00 €
        progress = 70L,
        createdAt = Date(2024, 11, 18, 9, 30),
        updatedAt = Date(2024, 11, 19, 10, 0),
        publishedAt = Date(2024, 11, 19, 11, 0),
        ingredients = listOf(
            Ingredient(name = "Cornichons", ingredientKind = Ingredientkind.TOPPING),
            Ingredient(name = "Jambon Cru", ingredientKind = Ingredientkind.MAIN),
            Ingredient(name = "Pain Baguette", ingredientKind = Ingredientkind.BREAD)
        ),
        usersPermissionsUser = UsersPermissionsUser(
            id = 102,
            username = "jane_smith",
            email = "jane.smith@example.com"
        ),
        store = Store(
            id = 502,
            documentID = "STORE67890",
            name = "Dwitch Bonlieu",
            isOpen = true,
            city = "Annecy",
            zipCode = "74000",
            createdAt = Date(2024, 11, 18, 9, 30),
            updatedAt = Date(2024, 11, 19, 10, 0),
            publishedAt = Date(2024, 11, 19, 11, 0)
        )
    ),
    Order(
        id = 3,
        documentID = "DOC13579",
        placedAt = Date(2024, 11, 20, 11, 45),
        receivedAt = Date(2024, 11, 20, 12, 0),
        cookMessage = "Ajoutez de la mayonnaise.",
        price = 15L, // 15.00 €
        progress = 10L,
        createdAt = Date(2024, 11, 19, 9, 30),
        updatedAt = Date(2024, 11, 20, 10, 0),
        publishedAt = Date(2024, 11, 20, 11, 0),
        ingredients = listOf(
            Ingredient(name = "Jambon Cru", ingredientKind = Ingredientkind.MAIN),
            Ingredient(name = "Pain Ciabatta", ingredientKind = Ingredientkind.BREAD),
            Ingredient(name = "Mayonnaise", ingredientKind = Ingredientkind.SAUCE)
        ),
        usersPermissionsUser = UsersPermissionsUser(
            id = 103,
            username = "alex_keller",
            email = "alex.keller@example.com"
        ),
        store = Store(
            id = 503,
            documentID = "STORE13579",
            name = "Dwitch Bonlieu",
            isOpen = true,
            city = "Annecy",
            zipCode = "74000",
            createdAt = Date(2024, 11, 19, 9, 30),
            updatedAt = Date(2024, 11, 20, 10, 0),
            publishedAt = Date(2024, 11, 20, 11, 0)
        )
    )
)




