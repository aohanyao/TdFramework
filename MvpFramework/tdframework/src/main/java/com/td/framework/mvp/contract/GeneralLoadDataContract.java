package com.td.framework.mvp.contract;

import com.td.framework.biz.ApiSubscriber;
import com.td.framework.mvp.presenter.BasePresenter;
import com.td.framework.mvp.view.BaseView;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by 江俊超
 * <p>版本:1.0.0</p>
 * <b>说明<b>通用加载数据合同<br/>
 * <li></li>
 */
public interface GeneralLoadDataContract {
    /**
     * 通用加载数据的View层
     *
     * @param <T> 泛型 返回的实体数据
     */
    interface GeneralLoadDataView<T> extends BaseView {

        /**
         * 刷新数据
         *
         * @param datas
         */
        void refreshSuccess(List<T> datas);

        /**
         * 加载更多数据
         *
         * @param datas
         */
        void loadMoreSuccess(List<T> datas);

        /**
         * 已经没有数据了
         */
        void noMore();
    }

    /**
     * 通用的加载数据P层
     *
     * @param <V> View 层，必须继承GeneralLoadDataView
     * @param <T>
     */
    abstract class GeneralLoadDataPresenter<V extends GeneralLoadDataView, T> extends BasePresenter<V> {
        /**
         * 每页大小
         */
        private int mPagerSize = 10;

        public GeneralLoadDataPresenter(V view) {
            super(view);


        }

        /**
         * 刷新数据
         * <p>自动判断PageSize和PageIndex</p>
         * 可覆写getPageSize() 方法 设置页大小
         */
        public void refreshData() {

           /* setSubscribe(*/getRefreshObservable()
                    .compose(getCompose())
                    .subscribeWith(new ApiSubscriber<Object, V>(getV()) {
                        @Override
                        public void onNext(Object o) {
                            //强转类型
                            List<T> list = (List<T>) (o);
                            //返回数据
                            getV().refreshSuccess(list);
                            //计算是否有更多
                            if (list != null && list.size() != getPageSize()) {
                                getV().noMore();
                            }
                        }

                    })/*)*/;
        }

        /**
         * <p>自动判断PageSize和PageIndex</p>
         * 可覆写getPageSize() 方法 设置页大小
         */
        public void loadMoreData() {

            Flowable<String> flowable = Flowable.just("1");
            flowable.subscribe(new Consumer<String>() {
                @Override
                public void accept(@NonNull String s) throws Exception {

                }
            });

            /*setSubscribe(*/getLoadMoreObservable()
                    .compose(getCompose())
                    .subscribe(new ApiSubscriber<Object, V>(getV()) {
                        @Override
                        public void onNext(Object o) {
                            //强转类型
                            List<T> list = (List<T>) (o);
                            //返回数据
                            getV().loadMoreSuccess(list);
                            //计算是否有更多
                            if (list != null && list.size() != getPageSize()) {
                                getV().noMore();
                            }
                        }

                    })/*)*/;
        }


        /**
         * 返回刷新的Observable
         *
         * @return
         */
        protected abstract Flowable<List<T>> getRefreshObservable();

        /**
         * 返回加载更多的Observable
         *
         * @return
         */
        protected abstract Flowable<List<T>> getLoadMoreObservable();

        /**
         * 获取每页大小
         *
         * @return
         */
        protected int getPageSize() {
            return mPagerSize;
        }

    }
}
