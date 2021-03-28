package krasnikov.project.pmfightacademy.info.data

import com.google.firebase.storage.StorageReference

data class InfoContent(val description: String, val images: List<StorageReference>)
