package com.fhdufhdu.kiosk.repository

import com.fhdufhdu.kiosk.entity.Store
import org.springframework.data.jpa.repository.JpaRepository

interface StoreRepository : JpaRepository<Store, String> {
}