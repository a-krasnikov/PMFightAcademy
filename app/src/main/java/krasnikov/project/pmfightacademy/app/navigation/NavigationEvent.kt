package krasnikov.project.pmfightacademy.app.navigation

import androidx.fragment.app.FragmentManager

fun interface NavigationEvent {
    fun navigate(fragmentManager: FragmentManager)
}