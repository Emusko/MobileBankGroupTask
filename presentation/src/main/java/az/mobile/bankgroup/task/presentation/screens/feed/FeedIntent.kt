package az.mobile.bankgroup.task.presentation.screens.feed

import az.mobile.bankgroup.task.presentation.base.BaseUiIntent

sealed interface FeedIntent : BaseUiIntent {
    data object ToggleFeedClicked : FeedIntent
}
