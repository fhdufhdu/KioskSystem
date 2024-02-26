package com.fhdufhdu.kiosk.common

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.security.MessageDigest

@Component
class KioskPasswordEncoder : PasswordEncoder {
    private fun encrypt(source: String, algorithm: String): String {
        val md = MessageDigest.getInstance(algorithm)
        val digest = md.digest(source.toByteArray())
        val result = StringBuilder()
        digest.forEach {
            result.append(((it.toInt() and 0xff) + 0x100).toString(16).substring(1))
        }
        return result.toString();
    }

    override fun encode(rawPassword: CharSequence?): String {
        return encrypt(rawPassword.toString(), "SHA-512")
    }

    override fun matches(rawPassword: CharSequence?, encodedPassword: String?): Boolean {
        rawPassword ?: return false
        encodedPassword ?: return false
        println(encode(rawPassword))
        println(encodedPassword)
        return encode(rawPassword) == encodedPassword
    }

    fun makeSalt(source: String): String {
        var salt = source
        for (i in 0..2) {
            salt = encode(salt)
        }
        return salt
    }
}