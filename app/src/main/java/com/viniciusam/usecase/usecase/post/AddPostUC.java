package com.viniciusam.usecase.usecase.post;

import com.viniciusam.usecase.model.Post;
import com.viniciusam.usecase.usecase.UseCase;

import io.realm.Realm;

/**
 * Created by Vinicius on 15/08/2016.
 */
public class AddPostUC extends UseCase<Post> {

    @Override
    public Post run() throws InterruptedException {
        Realm realm = Realm.getDefaultInstance();
        try {
            Number lastId = realm.where(Post.class)
                    .max("id");

            int id = (lastId == null) ? 1 : lastId.intValue() + 1;
            Post post = new Post(id, "Post #" + id + " Title");

            realm.beginTransaction();
            realm.copyToRealm(post);
            realm.commitTransaction();

            return post;
        } finally {
            realm.close();
        }
    }

}
