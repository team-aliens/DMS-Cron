package team.aliens.dmscron.domain.meal

import java.time.LocalDate

data class MealInfoResponse(
    val mealDate: LocalDate,
    val breakfast: String,
    val lunch: String,
    val dinner: String
)