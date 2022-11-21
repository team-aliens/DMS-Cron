package team.aliens.dmscron.school.service

import com.google.gson.Gson
import org.springframework.stereotype.Service
import team.aliens.dmscron.school.service.dto.ProcessedNeisSchoolInfoResponse
import team.aliens.dmscron.thirdparty.api.client.properties.NeisRequestProperty
import team.aliens.dmscron.thirdparty.api.client.NeisSchoolInfo
import team.aliens.dmscron.thirdparty.api.client.dto.NeisSchoolInfoResponse

/**
 *
 * 학교 이름과 주소로 학교기본정보 Neis API 를 호출해서 가공하는 Service
 *
 * @author leejeongyoon
 * @date 2022/11/21
 * @version 1.0.0
 **/
@Service
class NeisSchoolInfoService(
    private val neisSchoolInfo: NeisSchoolInfo
) {

    fun execute(schoolName: String, schoolAddress: String): ProcessedNeisSchoolInfoResponse {

        /**
         * HTML 형식의 JSON 을 Gson 을 사용하여 오브젝트로 변환
         **/
        val neisSchoolInfoHtml = neisSchoolInfo.getNeisSchoolInfo(
            type = NeisRequestProperty.TYPE,
            pageIndex = NeisRequestProperty.PAGE_INDEX,
            pageSize = NeisRequestProperty.PAGE_SIZE,
            schoolName = schoolName,
            schoolAddress = schoolAddress
        )
        val neisSchoolInfoResponse = Gson().fromJson(neisSchoolInfoHtml, NeisSchoolInfoResponse::class.java)

        return ProcessedNeisSchoolInfoResponse(
            sdSchoolCode = neisSchoolInfoResponse.schoolInfo[1].row[0].SD_SCHUL_CODE,
            regionCode = neisSchoolInfoResponse.schoolInfo[1].row[0].ATPT_OFCDC_SC_CODE
        )
    }
}