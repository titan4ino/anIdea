package kg.timetotravel.anidea.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import kg.timetotravel.anidea.Comments;
import kg.timetotravel.anidea.R;

// Унаследовали наш адаптер от RecyclerView.Adapter
// Здесь же указали наш собственный ViewHolder, который предоставит нам доступ к View-компонентам
public class IdeasAdapter extends RecyclerView.Adapter<IdeasAdapter.TweetViewHolder> {

    private final String TAG = "IdeasAdapter";

    private List<Comments> tweetList = new ArrayList<>();

    public void setItems(Collection<Comments> tweets) {
        tweetList.clear();
        tweetList.addAll(tweets);
        notifyDataSetChanged();
    }

    public List<Comments> getTweetList(){
        return tweetList;
    }

    @Override
    public TweetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        return new TweetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TweetViewHolder holder, int position) {
        holder.bind(tweetList.get(position));
    }

    @Override
    public int getItemCount() {
        return tweetList.size();
    }

    // Предоставляет прямую ссылку на каждый View-компонент
    // Используется для кэширования View-компонентов и последующего быстрого доступа к ним
    class TweetViewHolder extends RecyclerView.ViewHolder {
        // Ваш ViewHolder должен содержать переменные для всех
        // View-компонентов, которым вы хотите задавать какие-либо свойства
        // в процессе работы пользователя со списком

        private TextView nameTextView;
        private TextView creationDateTextView;
        private ImageView tweetImageView;
        private TextView ratingView;

        // Мы также создали конструктор, который принимает на вход View-компонент строкИ
        // и ищет все дочерние компоненты
        public TweetViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.comBody);
            creationDateTextView = itemView.findViewById(R.id.comName);
            tweetImageView = itemView.findViewById(R.id.ideaImageView);
            ratingView = itemView.findViewById(R.id.ratingView);
        }
        public void bind(Comments tweet) {
            nameTextView.setText(tweet.getBody());
            creationDateTextView.setText(tweet.getName());
            ratingView.setText(String.valueOf(tweet.getRating()) + "/10");

            String tweetPhotoUrl = tweet.getImageUrl();
            Picasso.with(itemView.getContext()).load(tweetPhotoUrl).into(tweetImageView);
            tweetImageView.setVisibility(tweetPhotoUrl != null ? View.VISIBLE : View.GONE);
        }
    }
}
