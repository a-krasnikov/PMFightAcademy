package krasnikov.project.pmfightacademy.notifications

import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.ktx.messaging
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import krasnikov.project.pmfightacademy.app.data.pref.SharedPref
import krasnikov.project.pmfightacademy.app.di.IoDispatcher
import javax.inject.Inject

class FirebaseMessagingManager @Inject constructor(
    private val firebaseMessaging: FirebaseMessaging,
    private val sharedPref: SharedPref,
    @IoDispatcher
    private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun unsubscribeFromMessages() {
        withContext(ioDispatcher) {
            firebaseMessaging.unsubscribeFromTopic(TOPIC_NAME).addOnSuccessListener {
                sharedPref.notificationState = SharedPref.NOTIFICATIONS_DISABLED
            }.await()
        }
    }

    suspend fun subscribeToMessages() {
        withContext(ioDispatcher) {
            firebaseMessaging.subscribeToTopic(TOPIC_NAME).addOnSuccessListener {
                sharedPref.notificationState = SharedPref.NOTIFICATIONS_ENABLED
            }.await()
        }
    }

    private companion object {
        const val TOPIC_NAME = "training_updates"
    }
}
