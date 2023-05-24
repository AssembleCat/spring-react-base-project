package org.spring.base.core.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.lang.NonNull
import java.io.Serializable

abstract class BaseService<T, B : Serializable>(
    private val repository: JpaRepository<T, B>
) {
    fun delete(@NonNull t: T): Boolean {
        return try {
            repository.delete(t)
            true
        } catch (e: Exception) {
            false
        }
    }
    fun deleteAll(): Boolean {
        return try {
            repository.deleteAll()
            true
        } catch (e: Exception) {
            false
        }
    }

    fun save(@NonNull t: T): Boolean {
        return try {
            repository.save(t)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun saveAll(@NonNull t: List<T>): Boolean {
        return try {
            repository.saveAll(t)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun deleteAllAndSaveAll(@NonNull t: List<T>): Boolean {
        return try {
            repository.deleteAll()
            repository.saveAll(t)
            true
        } catch (e: Exception){
            false
        }
    }
}
