package com.acoders.marvelfanbook.features.superheroes.framework.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.acoders.marvelfanbook.features.common.domain.models.Thumbnail
import com.acoders.marvelfanbook.features.superheroes.domain.models.Superhero

@Entity(tableName = "super_heroes")
data class SuperHeroEntity(
    @PrimaryKey
    val id: Long,
    val name: String = "",
    val description: String = "",
    val thumbnailPath: String = "",
    val thumbnailExtension: String = ""
) {

    fun toDomainModel(): Superhero = Superhero(id, name, description, Thumbnail(thumbnailPath, thumbnailExtension))
}