package krasnikov.project.pmfightacademy.info.data

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class InfoRepository(
    private val firebaseRemoteConfig: FirebaseRemoteConfig,
    private val dispatcher: CoroutineDispatcher
) {

    suspend fun getInfo(): InfoContent {
        return withContext(dispatcher) {
            firebaseRemoteConfig.fetch().addOnFailureListener {
                //handle error
                //throw custom error
            }.await()
            InfoContent(firebaseRemoteConfig.getString(REMOTE_CONFIG_INFO_TEXT_KEY))
        }
    }

    private companion object {
        const val REMOTE_CONFIG_INFO_TEXT_KEY = "description"
    }
}
