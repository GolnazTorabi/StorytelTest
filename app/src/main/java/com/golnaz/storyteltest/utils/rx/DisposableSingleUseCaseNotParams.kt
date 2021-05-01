package com.golnaz.storyteltest.utils.rx

import com.golnaz.storyteltest.utils.network.BaseResponse
import com.golnaz.storyteltest.utils.network.RetrofitException
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

abstract class DisposableSingleUseCaseNotParams<T> {
    internal abstract fun buildUseCaseSingle(): Single<T>

    fun execute(
        onSuccess: ((t: T) -> Unit),
        onError: ((t: BaseResponse) -> Unit),
        onFinish: () -> Unit = {}
    ) {
        buildUseCaseSingle()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterTerminate(onFinish)
            .subscribe(object : DisposableSingleObserver<T>() {
                override fun onSuccess(t: T) {
                    onSuccess(t)
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                    if (e is RetrofitException) {
                        val error: RetrofitException = e
                        val baseResponse = error.getErrorBodyAs(BaseResponse::class.java)
                        onError(baseResponse)
                    } else {
                        onError(
                            BaseResponse(
                                500,
                                false,
                                "خطایی رخ داده است، لطفا دوباره تلاش کنید"
                            )
                        )
                    }
                }
            })


    }
}