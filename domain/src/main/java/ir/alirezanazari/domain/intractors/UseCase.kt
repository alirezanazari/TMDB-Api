package ir.alirezanazari.domain.intractors

import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver


abstract class UseCase<T, P>(private val io: Scheduler, private val ui: Scheduler) {

    private var disposable: CompositeDisposable = CompositeDisposable()

    abstract fun build(param: P): Single<T>

    fun execute(observer: DisposableSingleObserver<T>, param: P) {

        val observable: Single<T> = build(param)
            .subscribeOn(io)
            .observeOn(ui)

        disposable.add(observable.subscribeWith(observer))
    }

    fun clearDisposable() {
        if (!disposable.isDisposed) disposable.dispose()
    }

    fun getDisposable(): CompositeDisposable {
        return disposable
    }
}