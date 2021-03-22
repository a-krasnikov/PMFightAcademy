@file:Suppress("WildcardImport")
package krasnikov.project.pmfightacademy.info.ui

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import krasnikov.project.pmfightacademy.app.ui.base.BaseViewModel
import krasnikov.project.pmfightacademy.info.data.InfoContent
import krasnikov.project.pmfightacademy.info.data.InfoRepository
import krasnikov.project.pmfightacademy.utils.State
import javax.inject.Inject

@HiltViewModel
class InfoViewModel @Inject constructor(private val infoRepository: InfoRepository) :
    BaseViewModel() {

    private val _infoScreenContent = MutableStateFlow<State<InfoContent, String /*error*/>>(
        State.Loading
    )
    val infoScreenContent = _infoScreenContent.asStateFlow()

    fun getInfo() {
        viewModelScope.launch(exceptionHandler) {
            _infoScreenContent.value = State.Content(infoRepository.getInfo())
        }
    }

    override fun handleError(throwable: Throwable) {
        TODO("Not yet implemented")
    }
}
