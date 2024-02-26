package com.fhdufhdu.kiosk.auth

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fhdufhdu.kiosk.common.KioskPasswordEncoder
import com.fhdufhdu.kiosk.domain.store.StoreRequest
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.web.server.ResponseStatusException

class LoginFilter(
    path: String,
    private val authenticationManager: AuthenticationManager,
    private val passwordEncoder: KioskPasswordEncoder
) :
    AbstractAuthenticationProcessingFilter(
        AntPathRequestMatcher(path, "POST"), authenticationManager
    ) {
    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication? {
        try {
            val om = jacksonObjectMapper()
            val signInDto = om.readValue(request.inputStream, StoreRequest.SingIn::class.java)

            val salt = passwordEncoder.makeSalt(signInDto.id)
            val token = UsernamePasswordAuthenticationToken(signInDto.id, signInDto.password + salt)

            val authentication = authenticationManager.authenticate(token)

            println((authentication.principal as StoreUserDetails).username)

            return authentication
        } catch (e: Exception) {
            e.printStackTrace()
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED)
        }

        return null
    }

}