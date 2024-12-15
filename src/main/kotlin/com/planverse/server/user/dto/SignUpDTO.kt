package com.planverse.server.user.dto

import com.planverse.server.user.entity.UserInfoEntity
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class SignUpDTO(
    @field:NotBlank
    val name: String,

    @field:NotBlank
    @field:Size(min = 3, max = 50)
    val nickname: String,

    @field:Email
    @field:NotBlank
    val email: String,

    @field:NotBlank
    @field:Pattern(regexp = "^[A-Z]+(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,20}$", message = "영문 숫자 특수기호 조합 8자리 이상 20자리 이하여야 합니다.")
    val pwd: String,
) {
    fun toEntity(encodedPassword: String): UserInfoEntity {
        return UserInfoEntity(name = name, nickname = nickname, email = email, pwd = encodedPassword, authentication = false)
    }
}