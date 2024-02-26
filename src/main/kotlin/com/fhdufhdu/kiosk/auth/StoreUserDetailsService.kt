package com.fhdufhdu.kiosk.auth

import com.fhdufhdu.kiosk.repository.StoreRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service


@Service
class StoreUserDetailsService(
    val storeRepository: StoreRepository,
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val store = storeRepository.findByIdOrNull(username) ?: throw UsernameNotFoundException("인증 실패!!!")
        val a = StoreUserDetails(store)
        return a
    }
}