package io.github.ggabriel96.cvsi.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [PictureMetadata::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pictureMetadataDao(): PictureMetadataDao
}
