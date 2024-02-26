package com.fhdufhdu.kiosk.entity

import jakarta.persistence.*
import java.sql.Timestamp

@Entity
@Table(name = "product_option")
class ProductOption(name: String, category: String, description: String, image: String) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    val product: Product? = null

    @Column
    var name: String = name
        protected set

    @Column
    var category: String = category
        protected set

    @Column
    var description: String = description
        protected set

    @Column
    var image: String = image
        protected set

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    val createdAt: Timestamp = Timestamp(System.currentTimeMillis())

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    var updatedAt: Timestamp = Timestamp(System.currentTimeMillis())
        protected set
}