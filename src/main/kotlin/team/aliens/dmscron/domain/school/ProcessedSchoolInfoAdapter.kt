package team.aliens.dmscron.domain.school

import com.google.gson.Gson
import org.springframework.stereotype.Component
import team.aliens.dmscron.thirdparty.api.client.NeisFeignClient
import team.aliens.dmscron.thirdparty.api.client.properties.NeisRequestProperty
import team.aliens.dmscron.thirdparty.api.client.dto.NeisSchoolInfoResponse

/**
 *
 * 학교 이름과 주소로 학교기본정보 Neis API 를 호출해서 가공하는 Service
 *
 * @author leejeongyoon
 * @date 2022/11/21
 * @version 1.0.0
 **/
@Component
class ProcessedSchoolInfoAdapter(
    private val neisClient: NeisFeignClient
) {

    fun execute(schoolName: String, schoolAddress: String): SchoolInfoResponse {

        /**
         * HTML 형식의 JSON 을 Gson 을 사용하여 오브젝트로 변환
         **/
        val neisSchoolInfoHtml = neisClient.getSchoolInfo(
            type = NeisRequestProperty.TYPE,
            pageIndex = NeisRequestProperty.PAGE_INDEX,
            pageSize = NeisRequestProperty.PAGE_SIZE,
            schoolName = schoolName,
            schoolAddress = schoolAddress
        )
        val neisSchoolInfoResponse = Gson().fromJson(neisSchoolInfoHtml, NeisSchoolInfoResponse::class.java)

        return SchoolInfoResponse(
            sdSchoolCode = neisSchoolInfoResponse.schoolInfo[1].row[0].SD_SCHUL_CODE,
            regionCode = neisSchoolInfoResponse.schoolInfo[1].row[0].ATPT_OFCDC_SC_CODE
        )
    }
}