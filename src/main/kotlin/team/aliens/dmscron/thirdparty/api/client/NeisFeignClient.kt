package team.aliens.dmscron.thirdparty.api.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import team.aliens.dmscron.thirdparty.api.client.properties.NeisParamProperty

@FeignClient(name = "neis-feign-client", url = "https://open.neis.go.kr/hub")
interface NeisFeignClient {

    @GetMapping("/schoolInfo")
    fun getSchoolInfo(
        @RequestParam(name = NeisParamProperty.TYPE) type: String,
        @RequestParam(name = NeisParamProperty.PAGE_INDEX) pageIndex: Int,
        @RequestParam(name = NeisParamProperty.PAGE_SIZE) pageSize: Int,
        @RequestParam(name = NeisParamProperty.SCHOOL_NAME) schoolName: String,
        @RequestParam(name = NeisParamProperty.SCHOOL_ADDRESS) schoolAddress: String
    ): String

    @GetMapping("/mealServiceDietInfo")
    fun getMealInfo(
        @RequestParam(name = NeisParamProperty.KEY) key: String,
        @RequestParam(name = NeisParamProperty.TYPE) type: String,
        @RequestParam(name = NeisParamProperty.PAGE_INDEX) pageIndex: Int,
        @RequestParam(name = NeisParamProperty.PAGE_SIZE) pageSize: Int,
        @RequestParam(name = NeisParamProperty.SD_SCHOOL_CODE) sdSchoolCode: String,
        @RequestParam(name = NeisParamProperty.REGION_CODE) regionCode: String,
        @RequestParam(name = NeisParamProperty.STARTED_YMD) startedYmd: String,
        @RequestParam(name = NeisParamProperty.ENDED_YMD) endedYmd: String
    ): String
}