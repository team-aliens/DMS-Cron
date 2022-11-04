package team.aliens.dmscron.thirdparty.api.client.dto

data class ProcessNeisMeal(
    val meals: List<ProcessNeisMealDetails>
) {
    data class ProcessNeisMealDetails(
        val calInfo: String,
        val menu: String,
        val mealCode: String,
        val schoolName: String,
        val mealDate: String
    )
}

