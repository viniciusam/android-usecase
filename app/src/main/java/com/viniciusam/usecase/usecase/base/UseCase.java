package com.viniciusam.usecase.usecase.base;

import com.viniciusam.usecase.executor.Executor;

/**
 * Created by Vinicius on 15/08/2016.
 */
abstract public class UseCase<E> {

    protected OnSuccessCallback mOnSuccessCallback;
    protected OnErrorCallback mOnErrorCallback;

    public UseCase() {
    }

    abstract public E run() throws Exception;

    public void execute(Executor executor) {
        executor.execute(this);
    }

    public void beforeExecute() {}

    public void afterExecute() {}

    public UseCase onSuccess(OnSuccessCallback onSuccessCallback) {
        mOnSuccessCallback = onSuccessCallback;
        return this;
    }

    public UseCase onError(OnErrorCallback onErrorCallback) {
        mOnErrorCallback = onErrorCallback;
        return this;
    }

    public void callOnSuccess() {
        if (mOnSuccessCallback != null) {
            mOnSuccessCallback.onSuccess();
        }
    }

    public void callOnError(Exception e) {
        if (mOnErrorCallback != null) {
            mOnErrorCallback.onError(e);
        }
    }

    public interface OnSuccessCallback {
        void onSuccess();
    }

    public interface OnErrorCallback {
        void onError(Exception e);
    }

}
