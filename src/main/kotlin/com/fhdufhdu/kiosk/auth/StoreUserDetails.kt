package com.fhdufhdu.kiosk.auth

import com.fhdufhdu.kiosk.entity.Store
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority

class StoreUserDetails(val store: Store) : Authentication {
    private var isAuthenticated: Boolean = false
    override fun getName(): String {
        return store.name
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return ArrayList()
    }

    override fun getCredentials(): Any {
        return store.password
    }

    override fun getDetails(): Any {
        return store.name
    }

    override fun getPrincipal(): Any {
        return store.id
    }

    override fun isAuthenticated(): Boolean {
        return isAuthenticated
    }

    override fun setAuthenticated(isAuthenticated: Boolean) {
        this.isAuthenticated = isAuthenticated
    }
}