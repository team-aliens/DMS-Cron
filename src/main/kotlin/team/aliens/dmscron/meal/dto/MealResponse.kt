package team.aliens.dmscron.meal.dto

data class MealResponse(
    val meals: List<MealElement>
) {
    data class MealElement(
        val calInfo: String,
        val menu: String,
        val mealCode: String,
        val schoolName: String,
        val mealDate: String
    )
}

