package com.viniciusam.usecase.usecase.post;

import android.content.Context;

import com.viniciusam.usecase.R;
import com.viniciusam.usecase.model.Post;
import com.viniciusam.usecase.usecase.base.RealmUseCase;

/**
 * Created by Vinicius on 18/08/2016.
 */
public class DeletePostUC extends RealmUseCase<Post> {

    private Context mContext;
    private int mIdToDelete;

    public DeletePostUC(Context context, int idToDelete) {
        mContext = context;
        mIdToDelete = idToDelete;
    }

    @Override
    public Post run() throws Exception {
        Post post = realm().where(Post.class)
                .equalTo("id", mIdToDelete)
                .findFirst();

        if (post == null) {
            throw new Exception(
                    String.format(mContext.getString(R.string.post_not_found), mIdToDelete));
        }

        Post oldPost = realm().copyFromRealm(post);

        realm().beginTransaction();
        post.deleteFromRealm();
        realm().commitTransaction();

        return oldPost;
    }
}
