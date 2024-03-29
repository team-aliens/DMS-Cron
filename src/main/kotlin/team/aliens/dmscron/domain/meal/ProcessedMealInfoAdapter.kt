package team.aliens.dmscron.domain.meal

import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Value
import team.aliens.dmscron.thirdparty.api.client.properties.NeisRequestProperty
import team.aliens.dmscron.thirdparty.api.client.NeisFeignClient
import team.aliens.dmscron.thirdparty.api.client.dto.NeisMealResponse
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import org.springframework.stereotype.Component
import team.aliens.dmscron.domain.meal.exception.MealInfoExceptions
import team.aliens.dmscron.global.exception.GlobalExceptions

/**
 *
 * 급식식단정보 Neis API 를 호출해서 가공하는 Service
 *
 * @author leejeongyoon
 * @date 2022/11/07
 * @version 1.0.0
 **/
@Component
class ProcessedMealInfoAdapter(

    @Value("\${open-feign.neis-key}")
    private val neisKey: String,

    private val neisClient: NeisFeignClient
) {

    fun execute(sdSchoolCode: String, regionCode: String): MealInfoResponse {
        val nextMonth = LocalDate.now().plusMonths(1)

        /**
         * HTML 형식의 JSON 을 Gson 을 사용하여 오브젝트로 변환
         **/
        val mealHtml = neisClient.getMealInfo(
            key = neisKey,
            type = NeisRequestProperty.TYPE,
            pageIndex = NeisRequestProperty.PAGE_INDEX,
            pageSize = NeisRequestProperty.PAGE_SIZE,
            sdSchoolCode = sdSchoolCode,
            regionCode = regionCode,
            startedYmd = localDateToString(nextMonth.withDayOfMonth(1).toString()),
            endedYmd = localDateToString(nextMonth.withDayOfMonth(nextMonth.lengthOfMonth()).toString())
        )

        runCatching {
            val mealJson = Gson().fromJson(mealHtml, NeisMealResponse::class.java) ?: throw MealInfoExceptions.NotFound()

            val mealTotalCount = mealJson.mealServiceDietInfo[0].head[0].list_total_count

            val mealCodes = mutableListOf<String>()
            val mealInfoElements = mutableListOf<MealInfoResponse.MealInfoElement>()

            val breakfastMap = mutableMapOf<LocalDate, String>()
            val lunchMap = mutableMapOf<LocalDate, String>()
            val dinnerMap = mutableMapOf<LocalDate, String>()

            for (i: Int in 0 until mealTotalCount) {
                val mealCode = getMealCode(mealJson, i)
                val calInfo = getCalInfo(mealJson, i)
                val menu = getMenuReplace(mealJson, i)
                val mealDate = getMealDate(mealJson, i)

                val transferMealDate = transferMealDate(mealDate)
                val mealLocalDate = stringToLocalDate(transferMealDate)

                mealCodes.add(
                    index = i,
                    element = mealCode
                )

                val menuAndClaInfo = "$menu||$calInfo"

                when (mealCodes[i]) {
                    "1" -> breakfastMap[mealLocalDate] = menuAndClaInfo
                    "2" -> lunchMap[mealLocalDate] = menuAndClaInfo
                    "3" -> dinnerMap[mealLocalDate] = menuAndClaInfo
                }

                mealInfoElements.add(
                    index = i,
                    element = MealInfoResponse.MealInfoElement(
                        mealDate = mealLocalDate,
                        breakfast = breakfastMap[mealLocalDate].orEmpty(),
                        lunch = lunchMap[mealLocalDate].orEmpty(),
                        dinner = dinnerMap[mealLocalDate].orEmpty()
                    )
                )
            }

            return MealInfoResponse(mealInfoElements)
        }.onFailure {
            throw MealInfoExceptions.NotFound()
        }.getOrElse {
            throw GlobalExceptions.InternalServerError()
        }
    }

    /**
     * yyyy-MM-dd 을 yyyMMdd 로 변경
     */
    private fun localDateToString(localDate: String): String {
        val sb = StringBuffer()
        sb.append(localDate)
        sb.deleteCharAt(4)
        sb.deleteCharAt(6)

        return sb.toString()
    }

    private fun getRow(response: NeisMealResponse, i: Int) = response.mealServiceDietInfo[1].row[i]

    private fun getMealCode(response: NeisMealResponse, i: Int) = getRow(response, i).MMEAL_SC_CODE

    private fun getCalInfo(response: NeisMealResponse, i: Int) = getRow(response, i).CAL_INFO

    private fun getMenuReplace(response: NeisMealResponse, i: Int) = getRow(response, i).DDISH_NM
            .replace("<br/>", "||")  // <br/>를 ||로 변환
            .replace("/", "&")  // /를 &로 변환
            .replace("[0-9.()]".toRegex(), "") // "0 ~ 9", ".", "(", ")" 를 전부 제거하는 정규식
            .replace("\\p{Z}".toRegex(), "") // 공백을 전부 제거하는 정규식

    private fun getMealDate(response: NeisMealResponse, i: Int) = getRow(response, i).MLSV_YMD

    /**
     * yyyyMMdd 을 yyyy-MM-dd 로 변환
     */
    private fun transferMealDate(mealDate: String): String {
        val sb = StringBuffer()
        sb.append(mealDate)
        sb.insert(4, "-")
        sb.insert(7, "-")

        return sb.toString()
    }

    /**
     * String 타입을 LocalDate 타입 으로 변환
     */
    private fun stringToLocalDate(transferMealDate: String): LocalDate {
        val passer = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        return LocalDate.parse(transferMealDate, passer)
    }
}