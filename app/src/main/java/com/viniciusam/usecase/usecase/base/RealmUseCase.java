package com.viniciusam.usecase.usecase.base;

import io.realm.Realm;

/**
 * Created by Vinicius on 18/08/2016.
 */
abstract public class RealmUseCase<E> extends UseCase<E> {

    private Realm mRealm;

    @Override
    public void beforeExecute() {
        mRealm = Realm.getDefaultInstance();
    }

    @Override
    public void afterExecute() {
        mRealm.close();
    }

    public Realm realm() {
        return mRealm;
    }

}
