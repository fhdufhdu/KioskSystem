package com.fhdufhdu.kiosk.auth

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fhdufhdu.kiosk.common.KioskPasswordEncoder
import com.fhdufhdu.kiosk.domain.store.StoreRequest
import com.fhdufhdu.kiosk.repository.StoreRepository
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.context.HttpSessionSecurityContextRepository
import org.springframework.web.filter.OncePerRequestFilter

class LoginFilter(
    private val passwordEncoder: KioskPasswordEncoder,
    private val storeRepository: StoreRepository
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        if (request.requestURI != "/store/sign-in") {
            filterChain.doFilter(request, response)
        } else {
            val newRequest = LoginRequestWrapper(request)
            try {
                val om = jacksonObjectMapper()
                val signInDto = om.readValue(newRequest.inputStream, StoreRequest.SingIn::class.java)

                val store = storeRepository.findByIdOrNull(signInDto.id) ?: return failLogin(response)
                val auth = StoreUserDetails(store)
                auth.isAuthenticated = passwordEncoder.matches(signInDto.password + store.salt, store.password)
                if (!auth.isAuthenticated) return failLogin(response)

                // 현재 리퀘스트 바운드에서 적용되는 부분
                SecurityContextHolder.getContext().authentication = auth
                // 리퀘스트 바운드 영역 데이터를 글로벌한 영역으로 저장함. 향후 다른 리퀘스트에서도 세션이 유지되도록
                newRequest.session.setAttribute(
                    HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                    SecurityContextHolder.getContext()
                )
                // 해당 세션 비활성화 유지 시간 설정
                newRequest.session.maxInactiveInterval = 10
            } catch (err: Exception) {
                err.printStackTrace()
                return failLogin(response)
            }
        }
    }

    private fun failLogin(response: HttpServletResponse) {
        response.sendError(403)
    }
}