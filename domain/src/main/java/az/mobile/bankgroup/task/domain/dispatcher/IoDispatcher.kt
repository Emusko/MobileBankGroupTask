package az.mobile.bankgroup.task.domain.dispatcher

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention
import kotlin.annotation.Retention

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IoDispatcher
