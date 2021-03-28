package krasnikov.project.pmfightacademy.info.data

import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import krasnikov.project.pmfightacademy.app.data.exception.InvalidDataSentException
import krasnikov.project.pmfightacademy.app.di.IoDispatcher
import java.lang.Exception
import javax.inject.Inject

class InfoRepository @Inject constructor(
    private val firebaseRemoteConfig: FirebaseRemoteConfig,
    private val firebaseImageStorage: StorageReference,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun getInfo(): InfoContent {
        return withContext(ioDispatcher) {
            firebaseRemoteConfig.fetchAndActivate().addOnFailureListener(createFailureListener())
                .await()
            val imageReferences =
                firebaseImageStorage.listAll().addOnFailureListener(createFailureListener())
                    .await().items
            InfoContent(firebaseRemoteConfig.getString(REMOTE_CONFIG_INFO_TEXT_KEY), imageReferences)
        }
    }

    private fun createFailureListener(): OnFailureListener {
        return OnFailureListener {
            throw InvalidDataSentException()
        }
    }

    private companion object {
        const val REMOTE_CONFIG_INFO_TEXT_KEY = "description"
    }
}
