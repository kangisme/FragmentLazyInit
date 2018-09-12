#懒加载Fragment
ViewPager+Fragment这组合用的次数也有很多了
通常可以通过

>mViewPager.setOffscreenPageLimit(num);

设置ViewPager预加载Fragment的数量，但如果num为0，却并不能阻止ViewPager预加载下一个Fragment
```
public void setOffscreenPageLimit(int limit) {
    if (limit < DEFAULT_OFFSCREEN_PAGES) {
        Log.w(TAG, "Requested offscreen page limit " + limit + " too small; defaulting to "
                + DEFAULT_OFFSCREEN_PAGES);
        limit = DEFAULT_OFFSCREEN_PAGES;
    }
    if (limit != mOffscreenPageLimit) {
        mOffscreenPageLimit = limit;
        populate();
    }
}
```
看源码就知道了，有个默认最小的值，这个值为1，设置小于1的数字也会被重置成1，所以ViewPager本身就不支持懒加载Fragment
既然ViewPager不行，那就从Fragment入手吧
```
08-17 09:41:40.631 7321-7321/com.kang.fragmentlazyinit D/kangisme: │ 1setUserVisibleHintfalse
08-17 09:41:40.632 7321-7321/com.kang.fragmentlazyinit D/kangisme: │ 2setUserVisibleHintfalse
08-17 09:41:40.633 7321-7321/com.kang.fragmentlazyinit D/kangisme: │ 1setUserVisibleHinttrue
08-17 09:41:40.644 7321-7321/com.kang.fragmentlazyinit D/kangisme: │ 1onAttach
08-17 09:41:40.645 7321-7321/com.kang.fragmentlazyinit D/kangisme: │ 1onCreate
08-17 09:41:40.937 7321-7321/com.kang.fragmentlazyinit D/kangisme: │ 2onAttach
08-17 09:41:40.938 7321-7321/com.kang.fragmentlazyinit D/kangisme: │ 2onCreate
08-17 09:41:40.938 7321-7321/com.kang.fragmentlazyinit D/kangisme: │ 1onCreateView
08-17 09:41:40.938 7321-7321/com.kang.fragmentlazyinit D/kangisme: │ 1onActivityCreated
08-17 09:41:41.238 7321-7321/com.kang.fragmentlazyinit D/kangisme: │ 1onResume
08-17 09:41:41.242 7321-7321/com.kang.fragmentlazyinit D/kangisme: │ 2onCreateView
08-17 09:41:41.244 7321-7321/com.kang.fragmentlazyinit D/kangisme: │ 2onActivityCreated
08-17 09:41:41.245 7321-7321/com.kang.fragmentlazyinit D/kangisme: │ 2onResume
```

根据以上Fragment的生命周期可以得出结论：onResume调用并不意味着Fragment显示出来了，只是可见，比如说滑动过程中，两个Fragment页面各占一半。现要求滑动时才真正loadData，因此不能再onResume中loadData，只有当setUserVisibleHint(true)调用时，Fragment才真正意义上的可见。

要实现Fragment懒加载，需要满足以下条件：
- getUserVisibleHint()为true时才调用loadData
- 对于第一个Fragment，其在生命周期开始前就会调用setUserVisibleHint(true)，因此不能再setUserVisibleHint函数中调用loadData
- 由上一个条件可知，loadData必须保证在onCreateView之后调用
- 需要一个标志变量，判断Fragment是否调用了onCreateView

以下为具体实现代码：
```
/**
 * tab页面可见时才开始加载数据，支持{@link android.support.v4.app.FragmentPagerAdapter}
 * 和{@link android.support.v4.app.FragmentStatePagerAdapter}
 * @author created by kangren on 2018/7/10 14:39
 */
public abstract class LazyInitFragment extends Fragment {

    private boolean isPrepared = false;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onUserVisible();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isPrepared = true;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        onUserVisible();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isPrepared = false;
    }

    private void onUserVisible() {
        if (isPrepared && getUserVisibleHint()) {
            loadData();
        }
    }

    protected abstract void loadData();
}
```
程序源码：https://github.com/kangisme/FragmentLazyInit