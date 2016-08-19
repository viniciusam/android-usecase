package com.viniciusam.usecase.executor;

import com.viniciusam.usecase.usecase.UseCase;

/**
 * Created by Vinicius on 15/08/2016.
 */
abstract public class AbstractExecutor {

    public <E> void executeUseCase(UseCase<E> useCase) {
        executeUseCase(useCase, null, null);
    }

    public <E> void executeUseCase(UseCase<E> useCase, OnSuccessCallback<E> onSuccessCallback) {
        executeUseCase(useCase, onSuccessCallback, null);
    }

    abstract public <E> void executeUseCase(
            UseCase<E> useCase,
            OnSuccessCallback<E> onSuccessCallback,
            OnErrorCallback onErrorCallback);

    abstract public void stopExecutor();

    public interface OnSuccessCallback<T> {
        void onSuccess(T t);
    }

    public interface OnErrorCallback {
        void onError(Exception e);
    }

}
