package kg.timetotravel.anidea;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import kg.timetotravel.anidea.adapter.IdeasAdapter;

public class TopIdeas extends AppCompatActivity {

    private IdeasAdapter tweetAdapter;

    @BindView(R.id.tweets_recycler_view)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_ideas);
        ButterKnife.bind(this);

        initRecyclerView();
    }

    private void initRecyclerView() {
        RecyclerView tweetsRecyclerView = findViewById(R.id.tweets_recycler_view);
        tweetsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tweetAdapter = new IdeasAdapter();
        tweetAdapter.setItems(Comments.findWithQuery(Comments.class, "SELECT * FROM COMMENTS ORDER BY RATING DESC"));

        tweetsRecyclerView.setAdapter(tweetAdapter);
    }
}
