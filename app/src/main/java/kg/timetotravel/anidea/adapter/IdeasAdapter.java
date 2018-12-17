package kg.timetotravel.anidea.adapter;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

    private static ClickListener clickListener;
    Dialog myDialog;

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
        myDialog = new Dialog(parent.getContext());
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
    class TweetViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
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

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }
        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
            showPopup(v, tweetList.get(getAdapterPosition()));

        }

        @Override
        public boolean onLongClick(View v) {
            clickListener.onItemLongClick(getAdapterPosition(), v);
            return false;
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

    public void showPopup(View v, final Comments comment) {

        TextView txtclose;
        TextView ideaBody;
        TextView ideaDate;
        ImageView ideaPic;
        Button deleteBtn;
        Button okBtn;

        myDialog.setContentView(R.layout.custompopup);
        ideaPic = (ImageView) myDialog.findViewById(R.id.ideaPicPop);
        if (comment.getImageUrl() != null){
            String tweetPhotoUrl = comment.getImageUrl();
            Picasso.with(v.getContext()).load(tweetPhotoUrl).into(ideaPic);

            ideaPic.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            ideaPic.setAdjustViewBounds(true);

            ideaPic.setVisibility(tweetPhotoUrl != null ? View.VISIBLE : View.GONE);
        }

        deleteBtn = (Button) myDialog.findViewById(R.id.deleteIdeaBtn);
        okBtn = (Button) myDialog.findViewById(R.id.okBtn);

        ideaBody = (TextView) myDialog.findViewById(R.id.ideaBodyPop);
        ideaBody.setText(comment.getBody());

        ideaDate = (TextView) myDialog.findViewById(R.id.ideaDatePop);
        ideaDate.setText(comment.getName());

        txtclose =(TextView) myDialog.findViewById(R.id.txtclose);
        txtclose.setText("X");
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment.delete();
                getTweetList().remove(comment);
                notifyDataSetChanged();
                myDialog.dismiss();
            }
        });
        okBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });

        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        IdeasAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }
}
