package team.aliens.dmscron.domain.meal

import java.time.LocalDate

data class MealInfoResponse(
    val mealInfoElements: List<MealInfoElement>
) {

    data class MealInfoElement(
        val mealDate: LocalDate,
        val breakfast: String,
        val lunch: String,
        val dinner: String
    )
}