package com.fhdufhdu.kiosk.domain.store

import jakarta.validation.Valid
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("store")
class StoreController(
    val authenticationManager: AuthenticationManager,
    val storeService: StoreService
) {
    @PostMapping("sign-up")
    fun signUp(@RequestBody @Valid signUpDto: StoreRequest.SignUp) {
        storeService.create(signUpDto)
    }

    @PostMapping("sign-in")
    fun signIn(@RequestBody @Valid signInDto: StoreRequest.SingIn) {
        val idAndPassword = storeService.signIn(signInDto)
        val token = UsernamePasswordAuthenticationToken(idAndPassword.first, idAndPassword.second)
        val authentication = authenticationManager.authenticate(token)
        SecurityContextHolder.getContext().authentication = authentication
    }

    @PostMapping("sign-in/kiosk")
    fun signInKiosk(@RequestBody @Valid signInDto: StoreRequest.SingIn) {

    }
}
