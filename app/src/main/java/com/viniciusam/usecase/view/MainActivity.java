package com.viniciusam.usecase.view;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.viniciusam.usecase.R;
import com.viniciusam.usecase.adapter.PostAdapter;
import com.viniciusam.usecase.executor.AbstractExecutor;
import com.viniciusam.usecase.executor.LooperExecutor;
import com.viniciusam.usecase.model.Post;
import com.viniciusam.usecase.usecase.post.AddPostUC;
import com.viniciusam.usecase.usecase.post.DeleteAllPostsUC;
import com.viniciusam.usecase.usecase.post.DeletePostUC;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements PostAdapter.Callbacks {

    private AbstractExecutor mExecutor;
    private Realm mRealm;
    private RealmResults mPostResults;

    private RecyclerView mRecyclerView;
    private PostAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mExecutor = new LooperExecutor();
        mRealm = Realm.getDefaultInstance();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new PostAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        mPostResults = mRealm.where(Post.class)
                .findAllSortedAsync("id");
        mPostResults.addChangeListener(new RealmChangeListener<RealmResults>() {
            @Override
            public void onChange(RealmResults results) {
                mAdapter.updateList(results);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mExecutor.executeUseCase(
                        new AddPostUC(),
                        new AbstractExecutor.OnSuccessCallback<Post>() {
                            @Override
                            public void onSuccess(Post post) {
                                showMessage("Post " + post.getId() + " inserted!");
                            }
                        },
                        new AbstractExecutor.OnErrorCallback() {
                            @Override
                            public void onError(Exception e) {
                                Log.e("Error", e.getMessage());
                            }
                        }
                );
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mExecutor.stopExecutor();
        mPostResults.removeChangeListeners();
        mRealm.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete_all) {
            mExecutor.executeUseCase(
                    new DeleteAllPostsUC(),
                    new AbstractExecutor.OnSuccessCallback<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            showMessage("All posts deleted!");
                        }
                    });

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showMessage(String text) {
//        Snackbar.make(mRecyclerView, text, Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show();
        Toast.makeText(this, text, Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onDeleteClicked(int pos) {
        mExecutor.executeUseCase(
                new DeletePostUC(this, mAdapter.getItem(pos).getId()),
                new AbstractExecutor.OnSuccessCallback<Post>() {
                    @Override
                    public void onSuccess(Post post) {
                        showMessage("Post " + post.getId() + " deleted!");
                    }
                },
                new AbstractExecutor.OnErrorCallback() {
                    @Override
                    public void onError(Exception e) {
                        Log.e("Error", e.getMessage());
                    }
                });
    }
}
