package team.aliens.dmscron.thirdparty.api.client.dto

/**
 *
 * Neis API 에서 호출한 HTML 형식의 JSON 을 Gson 을 사용하여 오브젝트로 변환 시켜줄때 사용하는 객체
 *
 * @author leejeongyoon
 * @date 2022/11/07
 * @version 1.0.0
 **/
data class NeisMealResponse(
    val mealServiceDietInfo: List<MealServiceDietInfo>
)

data class MealServiceDietInfo(
    val head: List<Head>,
    val row: List<Row>
)

data class Head(
    val RESULT: RESULT,
    val list_total_count: Int
)

data class RESULT(
    val CODE: String,
    val MESSAGE: String
)

data class Row(
    val CAL_INFO: String,
    val DDISH_NM: String,
    val MLSV_YMD: String,
    val MMEAL_SC_CODE: String,
    val MMEAL_SC_NM: String,
    val SCHUL_NM: String
)