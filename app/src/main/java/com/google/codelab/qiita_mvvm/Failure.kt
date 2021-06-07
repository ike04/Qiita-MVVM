package com.google.codelab.qiita_mvvm

import retrofit2.HttpException

data class Failure(
    val message: FailureType,
    val retry: () -> Unit
)

enum class FailureType(val message: Int) {
    NetworkError(R.string.offline_error),
    RequestError(R.string.no_articles),
    UnexpectedError(R.string.unexpected_error)
}

fun getMessage(e: Throwable): FailureType {
    if (e is HttpException) {
        return FailureType.UnexpectedError
    } else if (e is NullPointerException) {
        return FailureType.RequestError
    }
    return FailureType.NetworkError
}
