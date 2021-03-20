package krasnikov.project.pmfightacademy.utils

import androidx.navigation.NavController

fun interface NavigationEvent {
    fun navigate(controller: NavController)
}