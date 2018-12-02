package kg.timetotravel.anidea.adapter;

import android.support.v7.widget.RecyclerView;
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

    private List<Comments> tweetList = new ArrayList<>();

    public void setItems(Collection<Comments> tweets) {
        tweetList.addAll(tweets);
        notifyDataSetChanged();
    }

    public void clearItems() {
        tweetList.clear();
        notifyDataSetChanged();
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
        /*private TextView nickTextView;
        private ImageView userImageView;
        private TextView contentTextView;
        private TextView retweetsTextView;
        private TextView likesTextView;*/

        // Мы также создали конструктор, который принимает на вход View-компонент строкИ
        // и ищет все дочерние компоненты
        public TweetViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.comBody);
            creationDateTextView = itemView.findViewById(R.id.comName);
            tweetImageView = itemView.findViewById(R.id.ideaImageView);
            //userImageView = itemView.findViewById(R.id.profile_image_view);
            /*nickTextView = itemView.findViewById(R.id.author_nick_text_view);
            contentTextView = itemView.findViewById(R.id.tweet_content_text_view);
            retweetsTextView = itemView.findViewById(R.id.retweets_text_view);
            likesTextView = itemView.findViewById(R.id.likes_text_view);*/
        }
        public void bind(Comments tweet) {
            nameTextView.setText(tweet.getBody());
            creationDateTextView.setText(tweet.getName());

            //Picasso.with(itemView.getContext()).load(tweet.getUser().getImageUrl()).into(userImageView);

            String tweetPhotoUrl = tweet.getImageUrl();
            Picasso.with(itemView.getContext()).load(tweetPhotoUrl).into(tweetImageView);
            tweetImageView.setVisibility(tweetPhotoUrl != null ? View.VISIBLE : View.GONE);
        }

/*        private String getFormattedDate(String rawDate) {
            SimpleDateFormat utcFormat = new SimpleDateFormat(TWITTER_RESPONSE_FORMAT, Locale.ROOT);
            SimpleDateFormat displayedFormat = new SimpleDateFormat(MONTH_DAY_FORMAT, Locale.getDefault());
            try {
                Date date = utcFormat.parse(rawDate);
                return displayedFormat.format(date);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }*/
    }
}
