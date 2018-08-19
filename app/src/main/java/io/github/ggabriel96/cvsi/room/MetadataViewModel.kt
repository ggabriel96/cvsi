package io.github.ggabriel96.cvsi.room

import android.app.Application
import android.arch.lifecycle.AndroidViewModel

class MetadataViewModel(application: Application) : AndroidViewModel(application) {
    private val metadataRepository = MetadataRepository(application)

    fun insert(vararg metadatas: Metadata) {
        this.metadataRepository.insert(*metadatas)
    }
}
