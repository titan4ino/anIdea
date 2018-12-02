package kg.timetotravel.anidea;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;

import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kg.timetotravel.anidea.adapter.IdeasAdapter;


public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";

    private IdeasAdapter tweetAdapter;

    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    @BindView(R.id.tweets_recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.buttonTest)
    Button buttonTest;

    Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        random = new Random();

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mRecyclerView.addOnScrollListener(new HideShowScrollListener() {

            @Override
            public void onHide() {
                navigation.animate().setInterpolator(new AccelerateDecelerateInterpolator()).translationY(150);
                // do your hiding animation here
            }

            @Override
            public void onShow() {
                navigation.animate().setInterpolator(new AccelerateDecelerateInterpolator()).translationY(0);
                // do your showing animation here
            }
        });
        initRecyclerView();
    }

    private void initRecyclerView() {
        RecyclerView tweetsRecyclerView = findViewById(R.id.tweets_recycler_view);
        tweetsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tweetAdapter = new IdeasAdapter();

        tweetAdapter.setItems(Comments.findWithQuery(Comments.class, "SELECT * FROM COMMENTS ORDER BY ID DESC"));

        tweetsRecyclerView.setAdapter(tweetAdapter);
    }

    @OnClick(R.id.buttonTest)
    public void OnClick(View view) {

        Comments comment = new Comments("Test : " + random.nextInt(),
                "Thu Dec 12 07:31:08",
                "https://www.w3schools.com/w3css/img_fjords.jpg");

        tweetAdapter.getTweetList().add(comment);
        tweetAdapter.notifyDataSetChanged();

        comment.save();
        Log.i(TAG, "OnClick: ON test Click");
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    return true;
                case R.id.navigation_dashboard:
                    Intent intent = new Intent(MainActivity.this, NewIdeaActivity.class);
                    startActivityForResult(intent, 1);
                    //return true;
                case R.id.navigation_notifications:
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        long id = data.getExtras().getLong("id");

        Comments comments = Comments.findById(Comments.class, id);

        tweetAdapter.getTweetList().add(0, comments);
        tweetAdapter.notifyDataSetChanged();
    }
}
