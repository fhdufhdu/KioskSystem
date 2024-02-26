package com.fhdufhdu.kiosk.domain.store

import jakarta.validation.constraints.NotBlank
import org.jetbrains.annotations.NotNull

class StoreRequest {

    class SignUp(
        @NotNull
        @NotBlank
        val id: String,

        @NotNull
        @NotBlank
        val password: String,

        @NotNull
        @NotBlank
        val name: String
    )

    class SingIn(
        @NotNull
        @NotBlank
        val id: String,

        @NotNull
        @NotBlank
        val password: String
    )
}