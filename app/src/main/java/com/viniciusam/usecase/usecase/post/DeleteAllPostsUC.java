package com.viniciusam.usecase.usecase.post;

import com.viniciusam.usecase.model.Post;
import com.viniciusam.usecase.usecase.UseCase;

import io.realm.Realm;

/**
 * Created by Vinicius on 18/08/2016.
 */
public class DeleteAllPostsUC implements UseCase<Void> {

    @Override
    public Void run() throws Exception {
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.beginTransaction();
            realm.delete(Post.class);
            realm.commitTransaction();
            return null;
        } finally {
            realm.close();
        }
    }

}
