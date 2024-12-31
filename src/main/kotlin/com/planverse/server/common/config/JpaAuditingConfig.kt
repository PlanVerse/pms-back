package com.planverse.server.common.config

import com.planverse.server.common.constant.Constant
import com.planverse.server.common.constant.StatusCode
import com.planverse.server.common.exception.BaseException
import com.planverse.server.user.dto.UserInfoDTO
import com.planverse.server.user.entity.UserInfoEntity
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import java.util.*

@Configuration
class JpaAuditingConfig(
    val auditorAwareConfig: AuditorAwareConfig
) {
    @Bean
    fun auditorProvider(): AuditorAware<Long> {
        return auditorAwareConfig
    }
}

@Configuration
class AuditorAwareConfig : AuditorAware<Long> {
    override fun getCurrentAuditor(): Optional<Long> {
        val authentication: Authentication? = SecurityContextHolder.getContext().authentication

        if (authentication === null || !authentication.isAuthenticated) {
            throw BaseException(StatusCode.FAIL)
        } else if (authentication.principal.equals("anonymousUser")) {
            return Optional.of(Constant.SYSTEM_USER)
        } else {
            val userInfo: UserInfoDTO = UserInfoDTO.toDto(authentication.principal as UserInfoEntity)
            return Optional.of(userInfo.id!!)
        }
    }
}
