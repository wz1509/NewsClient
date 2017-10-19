package me.threebears.news.ui.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import me.threebears.news.R;
import me.threebears.news.model.entity.GankEntity;
import me.threebears.news.ui.adapter.base.BaseRecyclerViewAdapter;

/**
 * Created by threebears on 2017/10/13.
 *
 * @author threebears
 *         图片适配器
 */

public class GankGirlAdapter extends BaseRecyclerViewAdapter<GankEntity> {

    public GankGirlAdapter(Context context) {
        super(context, R.layout.rv_item_photo);
    }

    @Override
    protected void onBindViewHolder(BaseViewHolder holder, GankEntity data, int position) {
        Glide.with(mContext)
                .load(data.getUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.glide_pic_default)
                .error(R.drawable.glide_pic_failed)
                .into((ImageView) holder.getView(R.id.photo_item_image));
    }
}
