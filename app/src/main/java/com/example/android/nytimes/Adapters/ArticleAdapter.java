package com.example.android.nytimes.Adapters;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.nytimes.Models.Article;
import com.example.android.nytimes.R;

import java.util.ArrayList;

import static com.example.android.nytimes.R.layout.article;

/**
 * Created by Gauri Gadkari on 6/16/17.
 */

public class ArticleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Article> articles;
    Context context;
    private final int articleWithImage = 0, articleWithoutImage = 1;
    int request_code = 100;

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
            case articleWithImage:
                View view1 = inflater.inflate(article, parent, false);
                viewHolder = new ArticleViewHolder(view1);

                break;
            case articleWithoutImage:
                View view2 = inflater.inflate(R.layout.article_without_image, parent, false);
                viewHolder = new ArticleNoImageViewHolder(view2);


        }
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Article currentArticle = articles.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_share_white_48dp);
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, currentArticle.getWebUrl());
                PendingIntent pendingIntent = PendingIntent.getActivity(context,
                        request_code,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                builder.setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary));
                builder.setActionButton(bitmap, "Share Link", pendingIntent, true);
                //builder.addDefaultShareMenuItem();
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(context, Uri.parse(currentArticle.getWebUrl()));
            }
        });

        switch (holder.getItemViewType()) {
            case articleWithImage:
                ArticleViewHolder holderImage = (ArticleViewHolder) holder;

                Glide.clear(holderImage.thumbNail);
                holderImage.thumbNail.setImageResource(0);
                String imageUrl = currentArticle.getMultimedia().get(0).getUrl();

                holderImage.thumbNail.setImageURI(Uri.parse("http://www.nytimes.com/images/2017/02/12/arts/12KIDMAN/12KIDMAN-thumbWide-v2.jpg"));
                Glide.with(context).load(imageUrl).placeholder(R.drawable.placeholder).error(R.drawable.placeholder).into(holderImage.thumbNail);
                holderImage.headline.setText(currentArticle.getHeadline().getMain());
                break;
            case articleWithoutImage:
                ArticleNoImageViewHolder holderNoImage = (ArticleNoImageViewHolder) holder;
                holderNoImage.headline.setText(currentArticle.getHeadline().getMain());
                holderNoImage.snippet.setText(currentArticle.getSnippet());
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
        protected TextView headline;
        protected ImageView thumbNail;

        public ArticleViewHolder(View itemView) {
            super(itemView);
            headline = (TextView) itemView.findViewById(R.id.headline);
            thumbNail = (ImageView) itemView.findViewById(R.id.thumbnail);

        }
    }

    public static class ArticleNoImageViewHolder extends RecyclerView.ViewHolder {
        protected TextView headline;
        protected TextView snippet;

        public ArticleNoImageViewHolder(View itemView) {
            super(itemView);
            headline = (TextView) itemView.findViewById(R.id.headline);
            snippet = (TextView) itemView.findViewById(R.id.snippet);

        }
    }
}
