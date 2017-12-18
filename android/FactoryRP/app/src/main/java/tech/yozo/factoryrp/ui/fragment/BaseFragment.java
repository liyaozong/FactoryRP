package tech.yozo.factoryrp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

public abstract class BaseFragment extends Fragment {
    //Fragment的View加载完毕的标记
    private boolean isViewCreated;
    //Fragment是否可见
    private boolean isUIVisible;
    //是否已经加载过数据
    private boolean isLoaded;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewCreated = true;
        lazyLoad();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //isVisibleToUser这个boolean值表示:该Fragment的UI 用户是否可见
        isUIVisible = isVisibleToUser;
        lazyLoad();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //页面销毁,恢复标记
        isViewCreated = false;
    }

    private void lazyLoad() {
        if(isUIVisible && isViewCreated) {
            if(!isLoaded) {
                loadData();
                isLoaded = true;
            }
            buildUI();
        }
    }

    protected abstract void loadData();

    protected abstract void buildUI();
}
