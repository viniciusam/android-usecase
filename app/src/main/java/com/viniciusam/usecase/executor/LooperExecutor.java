package com.viniciusam.usecase.executor;

import android.os.Handler;
import android.os.Looper;

import com.viniciusam.usecase.usecase.UseCase;

/**
 * Created by vinicius.moreira on 18/08/2016.
 */
public class LooperExecutor extends AbstractExecutor {

    private Handler mMainHandler;
    private Handler mWorkHandler;

    public LooperExecutor() {
        mMainHandler = new Handler(Looper.getMainLooper());
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                mWorkHandler = new Handler(Looper.myLooper());
                Looper.loop();
            }
        }.start();
    }

    @Override
    public <E> void executeUseCase(final UseCase<E> useCase,
                                   final OnSuccessCallback<E> onSuccessCallback,
                                   final OnErrorCallback onErrorCallback) {
        mWorkHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    final E e = useCase.run();
                    if (onSuccessCallback != null) {
                        mMainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                onSuccessCallback.onSuccess(e);
                            }
                        });
                    }
                } catch (final Exception e) {
                    if (onErrorCallback != null) {
                        mMainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                onErrorCallback.onError(e);
                            }
                        });
                    }
                }
            }
        });
    }

    @Override
    public void stopExecutor() {
        if (mWorkHandler != null) {
            mWorkHandler.getLooper().quit();
        }
    }
}
