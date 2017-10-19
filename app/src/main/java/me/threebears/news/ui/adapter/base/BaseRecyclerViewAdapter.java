/*
 * Copyright (c) 2016 咖枯 <kaku201313@163.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package me.threebears.news.ui.adapter.base;

import android.content.Context;
import android.support.annotation.AnimRes;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.threebears.news.R;
import me.threebears.news.listener.OnItemClickListener;
import me.threebears.news.listener.OnReloadClickListener;
import me.threebears.news.ui.adapter.BaseViewHolder;

/**
 * @author 咖枯
 * @version 1.0 2016/8/6
 */
public abstract class BaseRecyclerViewAdapter<T> extends
        RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_ITEM = 0;
    public static final int TYPE_FOOTER = 1;
    protected int mLastPosition = -1;

    private int mItemLayoutRes = -1;
    protected Context mContext;
    protected List<T> mList = new ArrayList<>();
    protected OnItemClickListener<T> mOnItemClickListener;
    protected OnReloadClickListener mOnReloadClickListener;

    private FooterViewHolder mFooterViewHolder;

    public BaseRecyclerViewAdapter(Context context, @LayoutRes int itemLayoutRes) {
        this.mContext = context;
        this.mItemLayoutRes = itemLayoutRes;
    }

    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setOnReloadClickListener(OnReloadClickListener onReloadClickListener) {
        this.mOnReloadClickListener = onReloadClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            View view = getView(parent, R.layout.rv_item_footer);
            return mFooterViewHolder = new FooterViewHolder(view);
        } else {
            View view = getView(parent, mItemLayoutRes);
            final BaseViewHolder baseViewHolder = new BaseViewHolder(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        int position = baseViewHolder.getLayoutPosition();
                        mOnItemClickListener.onItemClick(view, mList.get(position));
                    }
                }
            });
            return baseViewHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BaseViewHolder && mList.size() != 0 && mList.size() != position) {
            BaseViewHolder viewHolder = (BaseViewHolder) holder;
            onBindViewHolder(viewHolder, mList.get(position), position);
        } else {
            ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
            if (layoutParams != null && layoutParams instanceof
                    StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams params =
                        (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
                params.setFullSpan(true);
            }
        }
    }

    /**
     * item
     *
     * @param holder   ViewHolder
     * @param data     实体数据
     * @param position 索引
     */
    protected abstract void onBindViewHolder(BaseViewHolder holder, T data, int position);

    protected View getView(ViewGroup parent, int layoutId) {
        return LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
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

    protected void setItemAppearAnimation(RecyclerView.ViewHolder holder, int position,
                                          @AnimRes int type) {
        if (position > mLastPosition) {
            Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), type);
            holder.itemView.startAnimation(animation);
            mLastPosition = position;
        }
    }

    public void setData(List<T> data) {
        mList.clear();
        mList.addAll(data);
        notifyDataSetChanged();
    }

    public void addMoreData(List<T> data) {
        // int startPosition = mList.size();
        mList.addAll(data);
        // 此处用这个会造成数组越界，待修复
        // notifyItemRangeInserted(startPosition, mList.size());
        notifyDataSetChanged();
    }

    public void deleteData(int position) {
        mList.remove(position);
        notifyItemRemoved(position);
    }

    private class FooterViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;
        TextView prompt;

        public FooterViewHolder(View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.pb_loading);
            prompt = itemView.findViewById(R.id.tv_prompt);
        }
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
