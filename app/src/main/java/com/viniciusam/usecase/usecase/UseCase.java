package com.viniciusam.usecase.usecase;

import com.viniciusam.usecase.executor.Executor;

/**
 * Created by Vinicius on 15/08/2016.
 */
abstract public class UseCase<E> {

    abstract public E run() throws Exception;

    public void runOnExecutor(Executor executor, Executor.OnSuccessCallback<E> onSuccessCallback,
                              Executor.OnErrorCallback onErrorCallback) {
        executor.executeUseCase(this, onSuccessCallback, onErrorCallback);
    }

    public void runOnExecutor(Executor executor, Executor.OnSuccessCallback<E> onSuccessCallback) {
        executor.executeUseCase(this, onSuccessCallback, null);
    }

    public void runOnExecutor(Executor executor, Executor.OnErrorCallback onErrorCallback) {
        executor.executeUseCase(this, null, onErrorCallback);
    }

    public void runOnExecutor(Executor executor) {
        executor.executeUseCase(this, null, null);
    }

}
