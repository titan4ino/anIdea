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
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kg.timetotravel.anidea.adapter.IdeasAdapter;


public class MainActivity extends AppCompatActivity {

    private IdeasAdapter tweetAdapter;

    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    @BindView(R.id.tweets_recycler_view)
    RecyclerView mRecyclerView;


    @BindView(R.id.buttonNewIdea)
    Button buttonTest;

    Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        random = new Random();

        final View view = findViewById(R.id.navigation);
        final int[] heightNav = {0};
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        view.post(new Runnable() {
            @Override
            public void run() {
                heightNav[0] = view.getHeight();
            }
        });

        mRecyclerView.addOnScrollListener(new HideShowScrollListener() {
            @Override
            public void onHide() {
                navigation.animate().setInterpolator(new AccelerateDecelerateInterpolator()).translationY(heightNav[0]); //animate the hiding according to navBar's height (heightNav)
                // hiding anim
            }

            @Override
            public void onShow() {
                navigation.animate().setInterpolator(new AccelerateDecelerateInterpolator()).translationY(0);
                // showing anim
            }
        });

        initRecyclerView();

        //Set click listener for RecyclerView elements
        tweetAdapter.setOnItemClickListener(new IdeasAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.d("CLICK", "onItemClick position: " + position);

            }

            @Override
            public void onItemLongClick(int position, View v) {
                Log.d("CLICK", "onItemLongClick pos = " + position);
            }
        });
    }

    private void initRecyclerView() {
        RecyclerView tweetsRecyclerView = findViewById(R.id.tweets_recycler_view);
        tweetsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tweetAdapter = new IdeasAdapter();
        tweetAdapter.setItems(Comments.findWithQuery(Comments.class, "SELECT * FROM COMMENTS ORDER BY ID DESC"));
        tweetsRecyclerView.setAdapter(tweetAdapter);
    }

    @OnClick(R.id.buttonNewIdea)
    public void OnClick(View view) {
        Intent intent = new Intent(MainActivity.this, NewIdeaActivity.class);
        startActivityForResult(intent, 1);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home: //Closing the ap

                case R.id.navigation_dashboard:
                    //empty
                case R.id.navigation_notifications:
                    Intent intent = new Intent(MainActivity.this, TopIdeas.class);
                    startActivity(intent);
                    //return true;
            }
            return false;
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data != null){
            long id = data.getExtras().getLong("id");
            Comments comments = Comments.findById(Comments.class, id);
            tweetAdapter.getTweetList().add(0, comments);
            tweetAdapter.notifyDataSetChanged();
        }
    }


}
