package com.fhdufhdu.kiosk.entity

import jakarta.persistence.*
import java.sql.Timestamp
import java.util.*

@Entity
@Table(name = "product")
class Product(name: String, description: String, image: String) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null //val(final)을 쓰더라도 allOpen 때문에 final이 아니게된다. 하지만 동시에 setter는 제한할 수 있다.

    @ManyToOne(fetch = FetchType.LAZY)
    val store: Store? = null

    @Column
    var name: String = name
        protected set

    @Column
    var description: String = description
        protected set

    @Column
    var image: String = image
        protected set

    @OneToMany(mappedBy = "mainProduct")
    val _recommendProducts: MutableList<RecommendProduct> = LinkedList()

    // 변경 가능한 MutableList를 변경 불가능하게.
    // OneToMany는 읽기 전용으로 쓰는 것이 안전하다고 생각함.
    val recommendProduct: List<RecommendProduct>
        get() = _recommendProducts

    @Column(name = "created_at")
    val createdAt: Timestamp = Timestamp(System.currentTimeMillis())

    @Column(name = "updated_at")
    var updatedAt: Timestamp = Timestamp(System.currentTimeMillis())
        protected set
}