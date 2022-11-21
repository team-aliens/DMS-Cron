package team.aliens.dmscron.thirdparty.api.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "neis-school-info", url = "https://open.neis.go.kr/hub")
interface NeisSchoolInfo {

    @GetMapping("/schoolInfo")
    fun getNeisSchoolInfo(
        @RequestParam(name = "Type") type: String,
        @RequestParam(name = "pIndex") pageIndex: Int,
        @RequestParam(name = "pSize") pageSize: Int,
        @RequestParam(name = "SCHUL_NM") schoolName: String,
        @RequestParam(name = "LCTN_SC_NM") schoolAddress: String
    ): String
}