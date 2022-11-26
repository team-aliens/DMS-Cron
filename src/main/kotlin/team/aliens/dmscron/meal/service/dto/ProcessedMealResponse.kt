package team.aliens.dmscron.meal.service.dto

import java.time.LocalDate

data class ProcessedMealResponse(
    val mealDate: LocalDate,
    val breakfast: String,
    val lunch: String,
    val dinner: String
)