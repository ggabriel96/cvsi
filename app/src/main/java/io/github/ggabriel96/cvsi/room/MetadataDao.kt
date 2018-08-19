package io.github.ggabriel96.cvsi.room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface MetadataDao {
    @Insert
    fun insert(vararg metadatas: Metadata)

    @Delete
    fun delete(metadata: Metadata)

    @Query("SELECT * FROM metadata WHERE img_filename = :imgFilename")
    fun find(imgFilename: String): Metadata

    @Query("SELECT * FROM metadata")
    fun list(): List<Metadata>
}
