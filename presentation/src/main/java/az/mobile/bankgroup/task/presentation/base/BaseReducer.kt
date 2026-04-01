package az.mobile.bankgroup.task.presentation.base

abstract class BaseReducer<State : Any, Action : BaseAction> {
    abstract fun reduce(currentState: State, action: Action): State
}
