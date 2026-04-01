package az.mobile.bankgroup.task.presentation.screens.feed

import az.mobile.bankgroup.task.presentation.base.BaseViewModel

class FeedViewModel : BaseViewModel<FeedState, FeedIntent, FeedAction>(
    initialState = FeedState(),
    reducer = FeedReducer(),
) {
    init {
        processIntent(FeedIntent.Appeared)
    }

    override fun processIntent(intent: FeedIntent) {
        when (intent) {
            FeedIntent.Appeared -> dispatch(FeedAction.HeadlineSet("Feed"))
        }
    }
}
