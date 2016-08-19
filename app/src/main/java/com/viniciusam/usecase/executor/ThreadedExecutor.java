package com.viniciusam.usecase.executor;

import android.os.Handler;
import android.os.Looper;

import com.viniciusam.usecase.usecase.UseCase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Vinicius on 15/08/2016.
 */
public class ThreadedExecutor extends AbstractExecutor {

    private boolean mIsRunning;
    private Handler mMainHandler;
    private ExecutorService mExecutorService;

    public ThreadedExecutor() {
        mIsRunning = false;
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
                mIsRunning  = true;
                try {
                    final E e = useCase.run();

                    if (mIsRunning && onSuccessCallback != null) {
                        mMainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                onSuccessCallback.onSuccess(e);
                            }
                        });
                    }
                } catch (final Exception e) {
                    if (mIsRunning && onErrorCallback != null) {
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
        mIsRunning = false;
        mExecutorService.shutdown();
    }

}
