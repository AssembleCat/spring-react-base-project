package org.spring.base.common.model.entity

import org.spring.base.common.types.master.BookType
import java.io.Serializable
import javax.persistence.*


@Entity(name = "BOOK")
class BookMaster(
    @EmbeddedId
    val bookKey: BookKey,
    @Enumerated(EnumType.STRING)
    @Column(name = "BOOK_TYPE", length = 5, nullable = false)
    val bookType: BookType,
    @Column(name = "NOTE", length = 100)
    val note: String
) {
    @Embeddable
    data class BookKey(
        @Column(name = "TITLE", length = 100, nullable = false)
        val title: String,
        @Column(name = "ISBM", length = 100, nullable = false)
        val isbm: String,
        @Column(name = "AUTHOR", length = 100, nullable = false)
        val author: String
    ) : Serializable
}
