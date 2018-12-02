package kg.timetotravel.anidea;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import kg.timetotravel.anidea.adapter.IdeasAdapter;


public class MainActivity extends AppCompatActivity {

    ArrayList<Comments> comments = new ArrayList<Comments>();
    private IdeasAdapter tweetAdapter;
    BottomNavigationView navigation;
    RecyclerView mRecyclerView;

    private List<Idea> ideas;
    private ArrayAdapter<Idea> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final CardView topbar = (CardView) findViewById(R.id.topBar);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mRecyclerView = (RecyclerView) findViewById(R.id.tweets_recycler_view);
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
        loadTweets();


        ListView listView = (ListView) findViewById(R.id.listView1);
        ideas = new ArrayList<>();
        ideas = JSONHelper.importFromJSON(this);
        if(ideas!=null){
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ideas);
            listView.setAdapter(adapter);
            Toast.makeText(this, "Данные восстановлены", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "Не удалось открыть данные", Toast.LENGTH_LONG).show();
        }
    }
    private void loadTweets() {
        Collection<Comments> tweets = getTweets();
        tweetAdapter.setItems(tweets);
    }
    private Collection<Comments> getTweets() {
        return Arrays.asList(
                new Comments("Sed ut perspiciatis unde omnis iste natus error sit voluptatem", "Thu Dec 12 07:31:08", "https://www.w3schools.com/w3css/img_fjords.jpg"),
                new Comments("accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore", "Thu Dec 12 07:31:08", "https://www.w3schools.com/w3images/lights.jpg"),
                new Comments("veritatis et quasi architecto beatae vitae dicta sunt", "Thu Dec 12 07:31:08", null),
                new Comments("explicabo. Nemo enim ipsam voluptatem quia voluptas ", "Thu Dec 12 07:31:08", "https://www.w3schools.com/w3css/img_fjords.jpg"),
                new Comments("dolor sit amet, consectetur, adipisci velit", "Thu Dec 12 07:31:08", null),
                new Comments("numquam eius modi tempora incidunt ut labore", "Thu Dec 12 07:31:08", "https://www.w3schools.com/css/img_mountains.jpg"),
                new Comments("nisi ut aliquid ex ea commodi consequatur", "Thu Dec 12 07:31:08", "https://www.w3schools.com/w3css/img_fjords.jpg")
        );
    }

    private void initRecyclerView() {
        RecyclerView tweetsRecyclerView = findViewById(R.id.tweets_recycler_view);
        tweetsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tweetAdapter = new IdeasAdapter();
        tweetsRecyclerView.setAdapter(tweetAdapter);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_dashboard:
                    Intent intent = new Intent(getApplicationContext(), NewIdeaActivity.class);
                    startActivity(intent);
                    //return true;
                case R.id.navigation_notifications:
                    return true;
            }
            return false;
        }
    };
}
