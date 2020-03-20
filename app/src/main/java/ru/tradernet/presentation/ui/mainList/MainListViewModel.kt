package ru.tradernet.presentation.ui.mainList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import ru.tradernet.domain.interactors.TickerInteractor
import ru.tradernet.domain.model.TikerInfoModel
import ru.tradernet.presentation.Event
import timber.log.Timber

class MainListViewModel(
    interactor: TickerInteractor
) : ViewModel() {

    val showProblemMessage = MutableLiveData<Event<Unit>>()
    val tikers = MutableLiveData<List<TikerInfoModel>>()

    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        showProblemMessage.value = Event(Unit)
        Timber.w(exception)
    }

    init {
        viewModelScope.launch(errorHandler) {
            //            tikers.value = interactor.fetchTikersInfo()
        }
    }
}
