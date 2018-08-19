package io.github.ggabriel96.cvsi.room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface PictureMetadataDao {
    @Insert
    fun insert(vararg pictureMetadatas: PictureMetadata)

    @Delete
    fun delete(pictureMetadata: PictureMetadata)

    @Query("SELECT * FROM picture_metadata WHERE img_filename = :imgFilename")
    fun find(imgFilename: String): PictureMetadata

    @Query("SELECT * FROM picture_metadata")
    fun list(): List<PictureMetadata>
}
