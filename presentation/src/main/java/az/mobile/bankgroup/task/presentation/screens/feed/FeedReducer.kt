package az.mobile.bankgroup.task.presentation.screens.feed

import az.mobile.bankgroup.task.presentation.base.BaseReducer

class FeedReducer : BaseReducer<FeedState, FeedAction>() {
    override fun reduce(currentState: FeedState, action: FeedAction): FeedState = when (action) {
        is FeedAction.HeadlineSet -> currentState.copy(headline = action.value)
    }
}
