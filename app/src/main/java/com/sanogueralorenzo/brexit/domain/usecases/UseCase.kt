package com.sanogueralorenzo.brexit.domain.usecases

import io.reactivex.Observable

interface UseCase<T> {
    fun execute(): Observable<T>
}
