package team.aliens.dmscron.thirdparty.api.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import team.aliens.dmscron.thirdparty.api.client.properties.NeisParamProperty

@FeignClient(name = "neis-school-info", url = "https://open.neis.go.kr/hub")
interface NeisSchoolInfo {

    @GetMapping("/schoolInfo")
    fun getNeisSchoolInfo(
        @RequestParam(name = NeisParamProperty.TYPE) type: String,
        @RequestParam(name = NeisParamProperty.PAGE_INDEX) pageIndex: Int,
        @RequestParam(name = NeisParamProperty.PAGE_SIZE) pageSize: Int,
        @RequestParam(name = NeisParamProperty.SCHOOL_NAME) schoolName: String,
        @RequestParam(name = NeisParamProperty.SCHOOL_ADDRESS) schoolAddress: String
    ): String
}