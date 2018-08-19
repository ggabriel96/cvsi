package io.github.ggabriel96.cvsi.room

import android.app.Application
import android.os.AsyncTask

class MetadataRepository(application: Application) {
    private val appDatabase: AppDatabase = AppDatabase.getInstance(application)
    private val pictureMetadataDao = appDatabase.pictureMetadataDao()

    fun insert(vararg metadatas: Metadata) {
        AsyncInserter(this.pictureMetadataDao).execute(*metadatas)
    }

    class AsyncInserter(private val metadataDao: MetadataDao) :
            AsyncTask<Metadata, Void, Void>() {
        override fun doInBackground(vararg params: Metadata): Void? {
            this.metadataDao.insert(*params)
            return null
        }

    }
}
