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

    @Query("DELETE FROM metadata")
    fun clear()

    @Query("SELECT * FROM metadata WHERE img_filename = :imgFilename")
    fun find(imgFilename: String): Metadata

    @Query("SELECT * FROM metadata ORDER BY img_filename DESC")
    fun list(): List<Metadata>
}
