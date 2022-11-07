package team.aliens.dmscron.thirdparty.api.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "neis-meal", url = "https://open.neis.go.kr/hub")
interface NeisMeal {

    @GetMapping("/mealServiceDietInfo")
    fun getNeisMeal(
        @RequestParam(name = NeisMealParamProperty.KEY) key: String,
        @RequestParam(name = NeisMealParamProperty.TYPE) type: String,
        @RequestParam(name = NeisMealParamProperty.PAGE_INDEX) pageIndex: Int,
        @RequestParam(name = NeisMealParamProperty.PAGE_SIZE) pageSize: Int,
        @RequestParam(name = NeisMealParamProperty.SCHOOL_CODE) sdSchoolCode: String,
        @RequestParam(name = NeisMealParamProperty.SD_SCHOOL_CODE) schoolCode: String,
        @RequestParam(name = NeisMealParamProperty.STARTED_YMD) startedYmd: String,
        @RequestParam(name = NeisMealParamProperty.ENDED_YMD) endedYmd: String
    ): String
}