package az.mobile.bankgroup.task.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<State : Any, UiIntent : BaseUiIntent, Action : BaseAction>(
    initialState: State,
    private val reducer: BaseReducer<State, Action>,
) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<State> = _state.asStateFlow()

    protected fun dispatch(action: Action) {
        _state.value = reducer.reduce(_state.value, action)
    }

    abstract fun processIntent(intent: UiIntent)

    protected fun launchInViewModelScope(block: suspend () -> Unit) {
        viewModelScope.launch { block() }
    }
}
