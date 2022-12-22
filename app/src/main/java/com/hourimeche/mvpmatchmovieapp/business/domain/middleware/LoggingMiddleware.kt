package com.hourimeche.mvpmatchmovieapp.business.domain.middleware

import android.util.Log
import com.hourimeche.mvpmatchmovieapp.business.domain.redux.Action
import com.hourimeche.mvpmatchmovieapp.business.domain.redux.Middleware
import com.hourimeche.mvpmatchmovieapp.business.domain.redux.State
import com.hourimeche.mvpmatchmovieapp.business.domain.redux.Store

/**
 * This [Middleware] is responsible for logging every [Action] that is processed to the Logcat, so
 * that we can use this for debugging.
 */
class LoggingMiddleware<S : State, A : Action> : Middleware<S, A> {
    override suspend fun process(action: A, currentState: S, store: Store<S, A>) {
        Log.v(
            "LoggingMiddleware",
            "Processing action: $action; Current state: $currentState"
        )
    }
}