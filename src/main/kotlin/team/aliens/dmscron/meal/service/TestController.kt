package team.aliens.dmscron.meal.service

import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import team.aliens.dmscron.thirdparty.api.FeignClientProperty
import team.aliens.dmscron.thirdparty.api.client.NeisMeal
import team.aliens.dmscron.thirdparty.api.client.dto.NeisMealResponse
import team.aliens.dmscron.meal.model.dto.MealResponse
import team.aliens.dmscron.meal.model.dto.MealResponse.MealDetails

/**
 *
 * 급식 json 가공을 위한 테스트 API 비즈니스 로직과 무관
 *
 * @author leejeongyoon
 * @date 2022/11/04
 * @version 1.0.0
 **/
@RestController
class TestController(

    @Value("\${open-feign.neis-key}")
    private val KEY: String,

    private val neisMeal: NeisMeal
) {

    @GetMapping("/test")
    fun get(): MealResponse {
        val neisMealHtml = neisMeal.getNeisMeal(
            key = KEY,
            type = FeignClientProperty.TYPE,
            pageIndex = FeignClientProperty.PAGE_INDEX,
            pageSize = FeignClientProperty.PAGE_SIZE,
            sdSchoolCode = FeignClientProperty.SD_SCHOOL_CODE,
            schoolCode = FeignClientProperty.SCHOOL_CODE,
            startedYmd = FeignClientProperty.STARTED_YMD,
            endedYmd = FeignClientProperty.ENDED_YMD
        )
        val neisMealResponse = Gson().fromJson(neisMealHtml, NeisMealResponse::class.java)

        val neisMealTotalCount = neisMealResponse.mealServiceDietInfo[0].head[0].list_total_count - 1

        val meals: MutableList<MealDetails> = mutableListOf()

        for (i: Int in 0 until neisMealTotalCount) {
            val calInfo = neisMealResponse.mealServiceDietInfo[1].row[i].CAL_INFO
            val menu = neisMealResponse.mealServiceDietInfo[1].row[i].DDISH_NM
                .replace("<br/>", "||") // <br/>를 ||로 변환
                .replace("/", "&")  // /를 &로 변환
                .replace("[0-9.()]".toRegex(), "")  // "0 ~ 9", ".", "(", ")" 를 전부 제거하는 정규식
                .replace("\\p{Z}".toRegex(), "")    // 공백을 전부 제거하는 정규식
            val mealDate = neisMealResponse.mealServiceDietInfo[1].row[i].MLSV_YMD
            val mealCode = neisMealResponse.mealServiceDietInfo[1].row[i].MMEAL_SC_CODE
            val schoolName = neisMealResponse.mealServiceDietInfo[1].row[i].SCHUL_NM

            meals.add(
                index = i,
                element = MealDetails(
                    calInfo = calInfo,
                    menu = menu,
                    mealCode = mealCode,
                    schoolName = schoolName,
                    mealDate = mealDate
                )
            )
        }

        return MealResponse(meals)
    }

}
