package com.fhdufhdu.kiosk.entity

import jakarta.persistence.*
import java.sql.Timestamp

@Entity
@Table(name = "store")
class Store(id: String, password: String, salt: String, name: String) {
    @Id
    val id: String = id

    @Column
    val password: String = password

    @Column
    val salt: String = salt

    @Column
    var name: String = name
        protected set

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    val createdAt: Timestamp = Timestamp(System.currentTimeMillis())

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    var updatedAt: Timestamp = Timestamp(System.currentTimeMillis())
        protected set

    fun changeName(name: String) {
        this.name = name
    }
}