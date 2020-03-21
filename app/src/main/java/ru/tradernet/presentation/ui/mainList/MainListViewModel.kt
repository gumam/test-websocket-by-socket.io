package ru.tradernet.presentation.ui.mainList

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import ru.tradernet.domain.interactors.TickerInteractor
import ru.tradernet.domain.model.TickerInfoModel
import ru.tradernet.presentation.Event
import timber.log.Timber

class MainListViewModel(
    interactor: TickerInteractor
) : ViewModel() {

    val showProblemMessage = MutableLiveData<Event<Unit>>()
    val tikers = MediatorLiveData<List<TickerInfoModel>>()

    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        showProblemMessage.value = Event(Unit)
        Timber.w(exception)
    }

    init {
        viewModelScope.launch(errorHandler) {
            val tickersCodes = interactor.fetchTickersCodes()
            interactor.createSubscription(tickersCodes)
            tikers.addSource(interactor.subscribeToTickersInfo()) { tikersInfos ->
                tikers.value = tikersInfos
            }
        }
    }
}
