package team.aliens.dmscron

import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import team.aliens.dmscron.thirdparty.api.FeignClientProperty
import team.aliens.dmscron.thirdparty.api.client.NeisMealApi
import team.aliens.dmscron.thirdparty.api.client.dto.NeisMealResponse
import team.aliens.dmscron.thirdparty.api.client.dto.ProcessNeisMeal

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

    @Value("\${open-feign.key}")
    private val KEY: String,

    private val neisMealApi: NeisMealApi
) {

    @GetMapping("/test")
    fun get(): ProcessNeisMeal {
        val meal = neisMealApi.getNeisMeal(
            key = KEY,
            type = FeignClientProperty.TYPE,
            pageIndex = FeignClientProperty.PAGE_INDEX,
            pageSize = FeignClientProperty.PAGE_SIZE,
            sdSchoolCode = FeignClientProperty.SD_SCHOOL_CODE,
            schoolCode = FeignClientProperty.SCHOOL_CODE,
            startedYmd = FeignClientProperty.STARTED_YMD,
            endedYmd = FeignClientProperty.ENDED_YMD
        )

        val a = Gson().fromJson(meal, NeisMealResponse::class.java)

        val c = a.mealServiceDietInfo[0].head[0].list_total_count - 1

        val mealP: MutableList<ProcessNeisMeal.ProcessNeisMealDetails> = mutableListOf()

        for (i: Int in 0 until c) {
            val calInfo = a.mealServiceDietInfo[1].row[i].CAL_INFO
            val menu = a.mealServiceDietInfo[1].row[i].DDISH_NM
                .replace("<br/>", "||")
                .replace("/", "&")
                .replace("[0-9.()]".toRegex(), "")
                .replace("\\p{Z}".toRegex(), "")
            val mealDate = a.mealServiceDietInfo[1].row[i].MLSV_YMD
            val mealCode = a.mealServiceDietInfo[1].row[i].MMEAL_SC_CODE
            val schoolName = a.mealServiceDietInfo[1].row[i].SCHUL_NM

            mealP.add(i, ProcessNeisMeal.ProcessNeisMealDetails(calInfo, menu, mealDate, mealCode, schoolName))
        }

        return ProcessNeisMeal(mealP)
    }

}
