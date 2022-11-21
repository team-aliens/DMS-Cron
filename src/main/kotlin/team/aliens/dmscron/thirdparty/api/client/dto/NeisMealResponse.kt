package team.aliens.dmscron.thirdparty.api.client.dto

data class NeisMealResponse(
    val mealServiceDietInfo: List<MealServiceDietInfo>
)

data class MealServiceDietInfo(
    val head: List<NeisMealHead>,
    val row: List<NeisMealRow>
)

data class NeisMealHead(
    val RESULT: NeisMealRESULT,
    val list_total_count: Int
)

data class NeisMealRESULT(
    val CODE: String,
    val MESSAGE: String
)

data class NeisMealRow(
    val CAL_INFO: String,
    val DDISH_NM: String,
    val MLSV_YMD: String,
    val MMEAL_SC_CODE: String,
    val MMEAL_SC_NM: String,
    val SCHUL_NM: String
)