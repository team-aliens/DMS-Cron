package team.aliens.dmscron.meal.service

import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import team.aliens.dmscron.meal.service.dto.ProcessedMealResponse
import team.aliens.dmscron.thirdparty.api.FeignClientProperty
import team.aliens.dmscron.thirdparty.api.client.NeisMeal
import team.aliens.dmscron.thirdparty.api.client.dto.NeisMealResponse
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 *
 * Neis API 에서 호출한 HTML 형식의 JSON 을 Gson 을 사용하여 오브젝트로 변환하고 DB 형식에 맞게 데이터를 가공하는 Service
 *
 * @author leejeongyoon
 * @date 2022/11/07
 * @version 1.0.0
 **/
@Service
class ProcessingNeisMealsService(

    @Value("\${open-feign.neis-key}")
    private val neisKey: String,

    private val neisMeal: NeisMeal
) {

    fun execute(): List<ProcessedMealResponse> {
        val neisMealHtml = neisMeal.getNeisMeal(
            key = neisKey,
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

        val mealCodes: MutableList<String> = mutableListOf()

        val breakfastMap: MutableMap<LocalDate, String> = mutableMapOf()
        val lunchMap: MutableMap<LocalDate, String> = mutableMapOf()
        val dinnerMap: MutableMap<LocalDate, String> = mutableMapOf()

        val mealRespons: MutableList<ProcessedMealResponse> = mutableListOf()

        for (i: Int in 0 until neisMealTotalCount) {
            val mealCode = neisMealResponse.mealServiceDietInfo[1].row[i].MMEAL_SC_CODE
            val calInfo = neisMealResponse.mealServiceDietInfo[1].row[i].CAL_INFO
            val menu = neisMealResponse.mealServiceDietInfo[1].row[i].DDISH_NM
                .replace("<br/>", "||") // <br/>를 ||로 변환
                .replace("/", "&")  // /를 &로 변환
                .replace("\\p{Z}[0-9.()]".toRegex(), "")  // "0 ~ 9", ".", "(", ")" 를 전부 제거하는 정규식
                .replace("".toRegex(), "")  // 공백을 전부 제거하는 정규식
            val mealDate = neisMealResponse.mealServiceDietInfo[1].row[i].MLSV_YMD

            // ex) yyyyMMdd 을 yyyy-MM-dd 로 변환
            val transferMealDate = StringBuffer()
            transferMealDate.append(mealDate)
            transferMealDate.insert(4, "-")
            transferMealDate.insert(7, "-")

            // String 타입을 LocalDate 타입 으로 변환
            val passer = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val mealDateParse = LocalDate.parse(transferMealDate, passer)

            mealCodes.add(
                index = i,
                element = mealCode
            )

            when (mealCodes[i]) {
                "1" -> breakfastMap[mealDateParse] = "$menu||$calInfo"
                "2" -> lunchMap[mealDateParse] = "$menu||$calInfo"
                "3" -> dinnerMap[mealDateParse] = "$menu||$calInfo"
            }

            mealRespons.add(
                index = i,
                element = ProcessedMealResponse(
                    mealDate = mealDateParse,
                    breakfast = breakfastMap[mealDateParse].orEmpty(),
                    lunch = lunchMap[mealDateParse].orEmpty(),
                    dinner = dinnerMap[mealDateParse].orEmpty()
                )
            )
        }

        return mealRespons
    }
}