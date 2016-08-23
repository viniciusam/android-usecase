package com.viniciusam.usecase.executor;

import android.os.Handler;
import android.os.Looper;

import com.viniciusam.usecase.usecase.UseCase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Vinicius on 15/08/2016.
 */
public class ThreadedExecutor implements Executor {

    private volatile Handler mMainHandler;
    private volatile ExecutorService mExecutorService;

    public ThreadedExecutor() {
        mMainHandler = new Handler(Looper.getMainLooper());
        mExecutorService = Executors.newCachedThreadPool();
    }

    @Override
    public <E> void executeUseCase(final UseCase<E> useCase,
                                   final OnSuccessCallback<E> onSuccessCallback,
                                   final OnErrorCallback onErrorCallback) {
        mExecutorService.execute(new Runnable() {
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
        mExecutorService.shutdown();
    }

}
