package team.aliens.dmscron.thirdparty.api

import feign.codec.ErrorDecoder
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import team.aliens.dmscron.thirdparty.api.error.FeignClientErrorDecoder

@EnableFeignClients(basePackages = ["team.aliens.dmscron"])
@Configuration
class FeignClientConfiguration {

    @Bean
    @ConditionalOnMissingBean(value = [ErrorDecoder::class])
    fun commonFeignErrorDecoder() = FeignClientErrorDecoder()
}