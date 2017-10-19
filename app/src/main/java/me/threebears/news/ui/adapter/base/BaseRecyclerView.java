//package me.threebears.news.ui.adapter;
//
//import android.content.Context;
//import android.support.annotation.LayoutRes;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import me.threebears.news.R;
//import me.threebears.news.listener.OnItemClickListener;
//import me.threebears.news.listener.OnReloadClickListener;
//
///**
// * Created time 2017/10/18.
// *
// * @author threeBears
// */
//
//public abstract class BaseRecyclerView<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
//
//    private static final int TYPE_ITEM = 1;
//    private static final int TYPE_FOOTER = 2;
//
//    private Context mContext;
//    private List<T> mList = new ArrayList<>();
//
//    /**
//     * item单击事件
//     */
//    private OnItemClickListener<T> mOnItemClickListener;
//    /**
//     * 网络问题重新加载时点击回调
//     */
//    private OnReloadClickListener mOnReloadClickListener;
//
//    private FooterViewHolder mFooterViewHolder;
//
//    public BaseRecyclerView(Context context) {
//        mContext = context;
//    }
//
//    /**
//     * item 点击事件监听
//     *
//     * @param itemClickListener 监听listener
//     */
//    public void setOnItemClickListener(OnItemClickListener<T> itemClickListener) {
//        this.mOnItemClickListener = itemClickListener;
//    }
//
//    /**
//     * 重试点击监听
//     *
//     * @param onReloadClickListener 监听listener
//     */
//    public void setOnReloadClickListener(OnReloadClickListener onReloadClickListener) {
//        this.mOnReloadClickListener = onReloadClickListener;
//    }
//
//    protected View getItemView(@LayoutRes int layoutId, ViewGroup parent) {
//        return LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
//    }
//
//    @Override
//    public int getItemCount() {
//        int itemCount = mList.size();
//        return itemCount == 0 ? 0 : itemCount + 1;
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        if (position + 1 == getItemCount() && mList.size() > 0) {
//            return TYPE_FOOTER;
//        }
//        return TYPE_ITEM;
//    }
//
//    @Override
//    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        if (viewType == TYPE_FOOTER) {
//            View view = getItemView(R.layout.rv_item_footer, parent);
//            mFooterViewHolder = new FooterViewHolder(view);
//            return mFooterViewHolder;
//        }
//        return null;
//    }
//
//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        if (holder instanceof FooterViewHolder && mList.size() != 0) {
//
//        }
//    }
//
//    /**
//     * 加载更多 ViewHolder
//     */
//    public class FooterViewHolder extends BaseViewHolder {
//
//        ProgressBar progressBar;
//        TextView prompt;
//
//        public FooterViewHolder(View itemView) {
//            super(itemView);
//            progressBar = getView(R.id.progress_bar);
//            prompt = getView(R.id.tv_prompt);
//        }
//    }
//
//    public void setLoading() {
//        mFooterViewHolder.prompt.setText("正在加载更多");
//        mFooterViewHolder.prompt.setVisibility(View.VISIBLE);
//        mFooterViewHolder.progressBar.setVisibility(View.VISIBLE);
//    }
//
//    public void setNotMore() {
//        mFooterViewHolder.prompt.setText("没有更多了");
//        mFooterViewHolder.progressBar.setVisibility(View.GONE);
//    }
//
//    public void setNetError() {
//        mFooterViewHolder.prompt.setText("加载失败，点击重试");
//        mFooterViewHolder.prompt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (mOnReloadClickListener != null) {
//                    mOnReloadClickListener.onClick();
//                }
//            }
//        });
//        mFooterViewHolder.progressBar.setVisibility(View.GONE);
//    }
//
//}
