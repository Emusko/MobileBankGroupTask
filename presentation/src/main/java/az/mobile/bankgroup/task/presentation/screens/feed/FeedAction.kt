package az.mobile.bankgroup.task.presentation.screens.feed

import az.mobile.bankgroup.task.presentation.base.BaseAction

sealed interface FeedAction : BaseAction {
    data class HeadlineSet(val value: String) : FeedAction
}
