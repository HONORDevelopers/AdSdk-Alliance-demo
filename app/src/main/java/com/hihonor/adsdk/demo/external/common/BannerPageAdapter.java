package com.hihonor.adsdk.demo.external.common;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.hihonor.adsdk.demo.external.utils.CollectionUtils;

import java.util.List;

/**
 * PagerAdapter 实现，用于在 ViewPager 中显示轮播视图列表。
 */
public class BannerPageAdapter extends PagerAdapter {

    /**
     * 子视图列表
     */
    private List<View> childViewList;

    /**
     * 默认构造函数，不初始化子视图列表。
     */
    public BannerPageAdapter() {
    }

    /**
     * 构造函数，初始化子视图列表。
     *
     * @param childViewList 包含轮播视图的列表
     */
    public BannerPageAdapter(List<View> childViewList) {
        this.childViewList = childViewList;
    }

    /**
     * 更新子视图列表并通知数据集变化，用于刷新视图内容。
     *
     * @param childViewList 包含轮播视图的更新后的列表
     */
    public void updateAndNotify(List<View> childViewList) {
        this.childViewList = childViewList;
        notifyDataSetChanged();
    }

    /**
     * 获取子视图列表的大小，用于确定 ViewPager 的页面数量。
     *
     * @return 子视图列表的大小
     */
    @Override
    public int getCount() {
        return CollectionUtils.size(childViewList);
    }

    /**
     * 判断视图是否来自指定的对象，用于确定视图与对象的关联性。
     *
     * @param view   ViewPager 中的视图
     * @param object 实例化的对象
     * @return 如果视图来自指定对象，则返回 true，否则返回 false
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * 从容器中移除指定位置的视图，用于释放资源。
     *
     * @param viewGroup 容器视图组
     * @param position  要移除的视图位置
     * @param arg2      实例化的对象
     */
    @Override
    public void destroyItem(@NonNull ViewGroup viewGroup, int position, @NonNull Object arg2) {
        if (CollectionUtils.isNotEmpty(childViewList)) {
            viewGroup.removeView(childViewList.get(position));
        }
    }

    /**
     * 获取视图的位置，用于触发 instantiateItem。
     * 这里通过返回 PagerAdapter.POSITION_NONE 实现强制刷新所有页面。
     *
     * @param object 实例化的对象
     * @return PagerAdapter.POSITION_NONE，用于强制刷新页面
     */
    @Override
    public int getItemPosition(@NonNull Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    /**
     * 实例化指定位置的视图并添加到容器中，用于显示视图。
     *
     * @param viewGroup 容器视图组
     * @param position  要实例化的视图位置
     * @return 实例化的视图
     */
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup viewGroup, int position) {
        View view = childViewList.get(position);
        ((ViewPager) viewGroup).addView(view);
        return view;
    }
}
