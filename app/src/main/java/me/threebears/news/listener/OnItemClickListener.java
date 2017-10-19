package me.threebears.news.listener;

import android.view.View;

/**
 * Created by threebears on 2017/10/12.
 * adapter item click
 */

public interface OnItemClickListener<T> {

    void onItemClick(View view, T data);

}
