package org.spring.base.core.domain.repository.master.book

import org.spring.base.common.model.entity.BookMaster
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BookMasterRepository: JpaRepository<BookMaster, BookMaster.BookKey> {
}
