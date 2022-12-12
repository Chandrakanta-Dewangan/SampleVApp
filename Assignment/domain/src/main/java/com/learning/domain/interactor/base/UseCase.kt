package com.learning.domain.interactor.base


abstract class UseCase<T, in P> {
    abstract suspend fun execute(searchString: String, params: P? = null): T
}