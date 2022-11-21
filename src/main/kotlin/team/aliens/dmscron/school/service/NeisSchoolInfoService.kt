package team.aliens.dmscron.school.service

import com.google.gson.Gson
import org.springframework.stereotype.Service
import team.aliens.dmscron.school.service.dto.ProcessedNeisSchoolInfoResponse
import team.aliens.dmscron.thirdparty.api.FeignClientProperty
import team.aliens.dmscron.thirdparty.api.client.NeisSchoolInfo
import team.aliens.dmscron.thirdparty.api.client.dto.NeisSchoolInfoResponse

@Service
class NeisSchoolInfoService(
    private val neisSchoolInfo: NeisSchoolInfo
) {

    fun execute(schoolName: String, schoolAddress: String): ProcessedNeisSchoolInfoResponse {
        val neisSchoolInfoHtml = neisSchoolInfo.getNeisSchoolInfo(
            type = FeignClientProperty.TYPE,
            pageIndex = FeignClientProperty.PAGE_INDEX,
            pageSize = FeignClientProperty.PAGE_SIZE,
            schoolName = schoolName,
            schoolAddress = schoolAddress
        )
        val neisSchoolInfoResponse = Gson().fromJson(neisSchoolInfoHtml, NeisSchoolInfoResponse::class.java)

        return ProcessedNeisSchoolInfoResponse(
            sdSchoolCode = neisSchoolInfoResponse.schoolInfo[0].row[0].SD_SCHUL_CODE,
            regionCode = neisSchoolInfoResponse.schoolInfo[0].row[0].ATPT_OFCDC_SC_CODE
        )
    }
}