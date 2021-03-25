package krasnikov.project.pmfightacademy.info.data

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import krasnikov.project.pmfightacademy.app.di.IoDispatcher
import javax.inject.Inject

class InfoRepository @Inject constructor(
    private val firebaseRemoteConfig: FirebaseRemoteConfig,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun getInfo(): InfoContent {
        return withContext(ioDispatcher) {
            firebaseRemoteConfig.fetchAndActivate().addOnFailureListener {

            }.await()
            InfoContent(firebaseRemoteConfig.getString(REMOTE_CONFIG_INFO_TEXT_KEY))
        }
    }

    private companion object {
        const val REMOTE_CONFIG_INFO_TEXT_KEY = "description"
    }
}
