package com.fhdufhdu.kiosk.domain.store

import jakarta.validation.Valid
import org.springframework.security.authentication.AuthenticationManager
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
        println(1)
    }

    @PostMapping("sign-in/kiosk")
    fun signInKiosk(@RequestBody @Valid signInDto: StoreRequest.SingIn) {

    }
}
