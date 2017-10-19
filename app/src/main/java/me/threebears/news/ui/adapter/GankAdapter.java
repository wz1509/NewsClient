package me.threebears.news.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import me.threebears.news.R;
import me.threebears.news.model.entity.GankEntity;
import me.threebears.news.ui.adapter.base.BaseRecyclerViewAdapter;

/**
 * Created time 2017/10/18.
 *
 * @author threeBears
 */

public class GankAdapter extends BaseRecyclerViewAdapter<GankEntity> {

    private final static int TYPE_GANK_IMAGE = 100;
    private final static int TYPE_GANK_NO_IMAGE = 101;

    public GankAdapter(Context context) {
        super(context, R.layout.rv_item_news);
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount() && mList.size() > 0) {
            return TYPE_FOOTER;
        } else if (mList.size() != 0) {
            List<String> list = mList.get(position).getImages();
            if (list != null && list.size() > 0) {
                return TYPE_GANK_IMAGE;
            } else {
                return TYPE_GANK_NO_IMAGE;
            }
        }
        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_GANK_IMAGE:
                return BaseViewHolder.get(getView(parent, R.layout.rv_item_gank_image));
            case TYPE_GANK_NO_IMAGE:
                return BaseViewHolder.get(getView(parent, R.layout.rv_item_gank_no_image));
            default:
                return super.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    protected void onBindViewHolder(BaseViewHolder holder, final GankEntity data, int position) {
        List<String> list = mList.get(position).getImages();
        if (list != null && list.size() > 0) {
            ImageView imageView = holder.getView(R.id.gank_item_image);
            if (imageView != null) {
                Glide.with(mContext)
                        .load(list.get(0))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(imageView);
            }
        }
        holder.setTextView(R.id.gank_item_title, data.getDesc())
                .setTextView(R.id.gank_item_description, "来源：" + data.getWho())
                .setTextView(R.id.gank_item_datetime, data.getPublishedAt());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(view, data);
                }
            }
        });
    }

}
