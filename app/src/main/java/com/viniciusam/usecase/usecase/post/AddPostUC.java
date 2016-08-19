package com.viniciusam.usecase.usecase.post;

import com.viniciusam.usecase.model.Post;
import com.viniciusam.usecase.usecase.base.RealmUseCase;

/**
 * Created by Vinicius on 15/08/2016.
 */
public class AddPostUC extends RealmUseCase<Post> {

    @Override
    public Post run() throws InterruptedException {
        Number lastId = realm().where(Post.class)
                .max("id");

        int id = (lastId == null) ? 1 : lastId.intValue() + 1;
        Post post = new Post(id, "Post #" + id + " Title");

        realm().beginTransaction();
        realm().copyToRealm(post);
        realm().commitTransaction();

        return post;
    }

}
