package com.fhdufhdu.kiosk.entity

import jakarta.persistence.*
import java.sql.Timestamp

@Entity
@Table(name = "recommend_product")
class RecommendProduct() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "main_product_id")
    val mainProduct: Product? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recommend_product_id")
    val recommendProduct: Product? = null

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    val createdAt: Timestamp = Timestamp(System.currentTimeMillis())

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    var updatedAt: Timestamp = Timestamp(System.currentTimeMillis())
        protected set
}