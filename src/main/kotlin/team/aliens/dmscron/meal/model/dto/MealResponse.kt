package team.aliens.dmscron.meal.model.dto

data class MealResponse(
    val meals: List<MealDetails>
) {
    data class MealDetails(
        val calInfo: String,
        val menu: String,
        val mealCode: String,
        val schoolName: String,
        val mealDate: String
    )
}

