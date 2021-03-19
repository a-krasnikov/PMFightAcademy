package krasnikov.project.pmfightacademy.info.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import krasnikov.project.pmfightacademy.info.data.InfoContent
import krasnikov.project.pmfightacademy.info.data.InfoRepository
import krasnikov.project.pmfightacademy.utils.State

class InfoViewModel(private val infoRepository: InfoRepository) : ViewModel() {

    private val _infoScreenContent = MutableLiveData<State<InfoContent, String>>()
    val infoScreenContent = _infoScreenContent as LiveData<State<InfoContent, String>>

    fun getInfo() {
        _infoScreenContent.value = State.Loading
        viewModelScope.launch {
            _infoScreenContent.value = State.Content(infoRepository.getInfo())
        }
    }
    //add exception handler
}
