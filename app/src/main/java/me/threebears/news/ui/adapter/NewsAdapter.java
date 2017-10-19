package me.threebears.news.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import me.threebears.news.R;
import me.threebears.news.listener.OnItemClickListener;
import me.threebears.news.listener.OnReloadClickListener;
import me.threebears.news.model.entity.NewsEntity;

/**
 * Created by threebears on 2017/10/11.
 * 新闻adapter
 */

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;

    private Context mContext;
    private List<NewsEntity> mList = new ArrayList<>();

    private OnItemClickListener<NewsEntity> mOnItemClickListener;
    private FooterViewHolder mFooterViewHolder;

    public NewsAdapter(Context context) {
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.rv_item_footer, parent, false);
            return mFooterViewHolder = new FooterViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.rv_item_news, parent, false);
            return new NewsViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NewsViewHolder && mList.size() != 0) {
            ((NewsViewHolder) holder).loadViewHolder(mList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        int itemCount = mList.size();
        return itemCount == 0 ? 0 : itemCount + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount() && mList.size() > 0) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }

    public void newDataItem(List<NewsEntity> newDataList) {
        if (newDataList != null && newDataList.size() != 0) {
            mList.clear();
            addMoreItem(newDataList);
        }
    }

    public void addMoreItem(List<NewsEntity> newDataList) {
        if (newDataList != null && newDataList.size() != 0) {
            mList.addAll(newDataList);
            notifyDataSetChanged();
        }
    }

    private class NewsViewHolder extends RecyclerView.ViewHolder {

        private ImageView photo;
        private TextView title, description, datetime;

        public NewsViewHolder(View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.news_item_photo);
            title = itemView.findViewById(R.id.news_item_title);
            description = itemView.findViewById(R.id.news_item_description);
            datetime = itemView.findViewById(R.id.news_item_datetime);
        }

        void loadViewHolder(final NewsEntity newsEntity) {
            String picUrl = newsEntity.getPicUrl();
            if (TextUtils.isEmpty(picUrl)) {
                photo.setVisibility(View.GONE);
            } else {
                Glide.with(mContext)
                        .load(newsEntity.getPicUrl())
                        .asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.glide_pic_default)
                        .error(R.drawable.glide_pic_failed)
                        .into(photo);
                photo.setVisibility(View.VISIBLE);
            }
            title.setText(newsEntity.getTitle());
            description.setText("来源：" + newsEntity.getDescription());
            datetime.setText(newsEntity.getCtime());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, newsEntity);
                    }
                }
            });
        }
    }

    /**
     * 加载更多 ViewHolder
     */
    private class FooterViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;
        TextView prompt;

        public FooterViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.pb_loading);
            prompt = (TextView) itemView.findViewById(R.id.tv_prompt);
        }
    }

    public void setOnItemClickListener(OnItemClickListener<NewsEntity> itemClickListener) {
        this.mOnItemClickListener = itemClickListener;
    }

    /**
     * 请求失败后重试监听
     */
    private OnReloadClickListener mOnReloadClickListener;

    public void setOnReloadClickListener(OnReloadClickListener onReloadClickListener) {
        mOnReloadClickListener = onReloadClickListener;
    }

    public void setLoading() {
        mFooterViewHolder.prompt.setText("正在加载更多");
        mFooterViewHolder.prompt.setVisibility(View.VISIBLE);
        mFooterViewHolder.progressBar.setVisibility(View.VISIBLE);
    }

    public void setNotMore() {
        mFooterViewHolder.prompt.setText("没有更多了");
        mFooterViewHolder.progressBar.setVisibility(View.GONE);
    }

    public void setNetError() {
        mFooterViewHolder.prompt.setText("加载失败，点击重试");
        mFooterViewHolder.prompt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnReloadClickListener != null) {
                    mOnReloadClickListener.onClick();
                }
            }
        });
        mFooterViewHolder.progressBar.setVisibility(View.GONE);
    }

}
