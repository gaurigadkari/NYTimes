package com.example.android.nytimes.adapters;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.nytimes.models.Article;
import com.example.android.nytimes.R;
import com.example.android.nytimes.utils.Utilities;

import java.util.ArrayList;

import static com.example.android.nytimes.R.layout.article;

public class ArticleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Article> articles;
    Context context;
    private final int ARTICLE_WITH_IMAGE = 0, ARTICLE_WITHOUT_IMAGE = 1;
    private final int REQUEST_CODE = 100;

    public ArticleAdapter(Context context, ArrayList<Article> articles) {
        this.context = context;
        this.articles = articles;
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(context);

        switch (viewType) {
            case ARTICLE_WITH_IMAGE:
                View articleWithImageview = inflater.inflate(article, parent, false);
                viewHolder = new ArticleViewHolder(articleWithImageview);

                break;
            case ARTICLE_WITHOUT_IMAGE:
                View articleWithoutImageview = inflater.inflate(R.layout.article_without_image, parent, false);
                viewHolder = new ArticleNoImageViewHolder(articleWithoutImageview);


        }
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final Article currentArticle = articles.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (Utilities.isNetworkAvailable(context) && Utilities.isOnline()) {
                    Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_share_white_48dp);
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_TEXT, currentArticle.getWebUrl());
                    PendingIntent pendingIntent = PendingIntent.getActivity(context,
                            REQUEST_CODE,
                            intent,
                            PendingIntent.FLAG_UPDATE_CURRENT);
                    CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                    builder.setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary));
                    builder.setActionButton(bitmap, "Share Link", pendingIntent, true);
                    //builder.addDefaultShareMenuItem();
                    CustomTabsIntent customTabsIntent = builder.build();
                    customTabsIntent.launchUrl(context, Uri.parse(currentArticle.getWebUrl()));
                } else {
                    Snackbar.make(holder.itemView, "Make sure your device is connected to the internet", Snackbar.LENGTH_LONG).show();
                }

            }
        });
        //DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        switch (holder.getItemViewType()) {

            case ARTICLE_WITH_IMAGE:
                ArticleViewHolder holderImage = (ArticleViewHolder) holder;
                Glide.clear(holderImage.thumbNail);
                holderImage.thumbNail.setImageResource(0);
                String imageUrl = currentArticle.getMultimedia().get(0).getUrl();
                Glide.with(context).load(imageUrl).placeholder(R.drawable.placeholder).error(R.drawable.placeholder).into(holderImage.thumbNail);
                holderImage.headline.setText(currentArticle.getHeadline().getMain());
                holderImage.snippet.setText(currentArticle.getSnippet());
                if (!currentArticle.getTag().equals("")) {
                    holderImage.tag.setText(currentArticle.getTag());
                } else {
                    holderImage.tag.setText(R.string.misc);
                }
                break;
            case ARTICLE_WITHOUT_IMAGE:
                ArticleNoImageViewHolder holderNoImage = (ArticleNoImageViewHolder) holder;

                holderNoImage.headline.setText(currentArticle.getHeadline().getMain());
                holderNoImage.snippet.setText(currentArticle.getSnippet());
                if (currentArticle.getTag() != null && !currentArticle.getTag().equals("")) {
                    holderNoImage.tag.setText(currentArticle.getTag());
                } else {
                    holderNoImage.tag.setText(R.string.misc);
                }
                break;

        }
    }

    @Override
    public int getItemViewType(int position) {
        if (articles.get(position).getMultimedia().size() > 0) {
            return 0;
        } else return 1;
    }


    public static class ArticleViewHolder extends RecyclerView.ViewHolder {
        protected TextView headline, snippet, tag;
        protected ImageView thumbNail;

        public ArticleViewHolder(View itemView) {
            super(itemView);
            headline = (TextView) itemView.findViewById(R.id.headline);
            thumbNail = (ImageView) itemView.findViewById(R.id.thumbnail);
            snippet = (TextView) itemView.findViewById(R.id.snippet);
            tag = (TextView) itemView.findViewById(R.id.tag);
        }
    }

    public static class ArticleNoImageViewHolder extends RecyclerView.ViewHolder {
        protected TextView headline, snippet, tag;

        public ArticleNoImageViewHolder(View itemView) {
            super(itemView);
            headline = (TextView) itemView.findViewById(R.id.headline);
            snippet = (TextView) itemView.findViewById(R.id.snippet);
            tag = (TextView) itemView.findViewById(R.id.tag);


        }
    }
}
