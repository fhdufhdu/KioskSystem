package com.fhdufhdu.kiosk.auth

import com.fhdufhdu.kiosk.entity.Store
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class StoreUserDetails(private val store: Store) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return ArrayList()
    }

    override fun getPassword(): String {
        return store.password
    }

    override fun getUsername(): String {
        return store.id
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}