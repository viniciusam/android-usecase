package com.viniciusam.usecase.usecase.post;

import com.viniciusam.usecase.model.Post;
import com.viniciusam.usecase.usecase.base.RealmUseCase;

/**
 * Created by Vinicius on 18/08/2016.
 */
public class DeleteAllPostsUC extends RealmUseCase<Void> {

    @Override
    public Void run() throws Exception {
        realm().beginTransaction();
        realm().delete(Post.class);
        realm().commitTransaction();
        return null;
    }
}
