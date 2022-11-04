package team.aliens.dmscron.thirdparty.api.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "neis-meal", url = "https://open.neis.go.kr/hub")
interface NeisMealApi {

    @GetMapping("/mealServiceDietInfo")
    fun getNeisMeal(
        @RequestParam(name = "KEY") key: String,
        @RequestParam(name = "Type") type: String,
        @RequestParam(name = "pIndex") pageIndex: Int,
        @RequestParam(name = "pSize") pageSize: Int,
        @RequestParam(name = "ATPT_OFCDC_SC_CODE") sdSchoolCode: String,
        @RequestParam(name = "SD_SCHUL_CODE") schoolCode: String,
        @RequestParam(name = "MLSV_FROM_YMD") startedYmd: String,
        @RequestParam(name = "MLSV_TO_YMD") endedYmd: String
    ): String
}