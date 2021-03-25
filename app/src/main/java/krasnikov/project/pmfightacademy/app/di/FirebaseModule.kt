package krasnikov.project.pmfightacademy.app.di

import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import krasnikov.project.pmfightacademy.R
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FirebaseModule {

    @Singleton
    @Provides
    fun provideFirebaseRemoteConfig(firebaseRemoteConfigSettings: FirebaseRemoteConfigSettings): FirebaseRemoteConfig {
        val remoteConfig = Firebase.remoteConfig
        remoteConfig.setConfigSettingsAsync(firebaseRemoteConfigSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
        return remoteConfig
    }

    @Provides
    fun provideFirebaseRemoteConfigSettings(): FirebaseRemoteConfigSettings {
        return remoteConfigSettings {
            minimumFetchIntervalInSeconds = FIREBASE_FETCH_INTERVAL
        }
    }

    private companion object {
        const val FIREBASE_FETCH_INTERVAL = 3600L
    }
}
