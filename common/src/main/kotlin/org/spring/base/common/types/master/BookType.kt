package org.spring.base.common.types.master

/**
 * BookType(장르) 종류
 *
 * EnumClass의 예시로 표현하였지만 따로 데이터관리(DB)하면 더 좋겠죠!
 */
enum class BookType(val code: String) {
    MYSTERY("00001"),
    ROMANCE("00002"),
    FANTASY("00003"),
    THRILLER("00004"),
    HORROR("00005"),
    BIOGRAPHY("00006"),
    SELFHELP("00007"),
    ADVENTURE("00008"),
    UNKNOWN("99999");

    companion object {
        /**
         * @param v 장르코드
         * @return 코드에 해당하는 정보가 없을경우 return UNKNOWN
         */
        fun findByCodeOrDefault(v: String): BookType = values().find { it.code == v } ?: UNKNOWN
    }
}
