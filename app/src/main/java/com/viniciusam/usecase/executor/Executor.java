package com.viniciusam.usecase.executor;

import com.viniciusam.usecase.usecase.UseCase;

/**
 * Created by Vinicius on 15/08/2016.
 */
public interface Executor {

    <E> void executeUseCase(
            UseCase<E> useCase,
            OnSuccessCallback<E> onSuccessCallback,
            OnErrorCallback onErrorCallback);

    void stopExecutor();

    interface OnSuccessCallback<T> {
        void onSuccess(T t);
    }

    interface OnErrorCallback {
        void onError(Exception e);
    }

}
