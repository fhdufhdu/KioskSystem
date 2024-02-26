package com.fhdufhdu.kiosk.domain.store

import com.fhdufhdu.kiosk.common.KioskPasswordEncoder
import com.fhdufhdu.kiosk.entity.Store
import com.fhdufhdu.kiosk.repository.StoreRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class StoreService(
    val storeRepository: StoreRepository,
    val passwordEncoder: KioskPasswordEncoder
) {
    fun create(signUpDto: StoreRequest.SignUp) {
        val existsStore = storeRepository.existsById(signUpDto.id)
        if (existsStore) {
            throw ResponseStatusException(HttpStatus.CONFLICT, "이미 존재하는 아이디입니다.")
        }
        val salt = passwordEncoder.makeSalt(signUpDto.id)
        val password = passwordEncoder.encode(signUpDto.password + salt)
        val newStore = Store(signUpDto.id, password, salt, signUpDto.name)
        storeRepository.save(newStore)
    }

    fun signIn(signInDto: StoreRequest.SingIn): Pair<String, String> {
        val store = storeRepository.findByIdOrNull(signInDto.id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)

        val equalPassword = passwordEncoder.matches(signInDto.password + store.salt, store.password)

        if (!equalPassword) throw ResponseStatusException(HttpStatus.NOT_FOUND)

        return Pair(store.id, store.password)
    }
}