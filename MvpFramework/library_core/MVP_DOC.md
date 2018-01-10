# 说明文档
网络请求相关的封装，差不多已经是极限封装了，只需要填充相关的接口，值等

## 网络相关
### 1. 单纯加载数据列表
    适用情况，只有下拉刷新，下拉加载更多
    
#### Step1
    1. Activity继承MvpLoadListBaseActivity
    2. Fragment继承MvpLoadListBaseFragment

基类中有两个泛型参数P和T

	1. P presenter 需要继承或者直接使用GeneralLoadDataContract.GeneralLoadDataPresenter
	2. T 返回的数据对象，仅仅是数据对象，不是List


继承后需要实现以下方法：

			
    /**
     * 获取刷新布局
     */
    protected abstract fun getSwipeRefreshLayout(): View?

    /**
     * 获取适配器
     */
    protected abstract fun getAdapter(): BaseQuickAdapter<T, *>?

    /**
     * 获取RecyclerView
     */
    protected abstract fun getRecyclerView(): RecyclerView?

    /**
     * 点击事件

     * @param adapter  适配器
     * *
     * @param view     点击的View
     * *
     * @param position 下标
     * *
     * @param data     点击的数据
     */
    protected abstract fun onRecyclerViewItemChildClick(view: View, position: Int, data: T)
    /**
     * 获取布局ID
     */
    protected abstract fun getLayoutId(): Int


Presenter

	可以直接继承
		GeneralLoadDataContract.GeneralLoadDataPresenter
	开始实现相关

	也可以重新编写Contract，但是Presenter必须要是
		GeneralLoadDataContract.GeneralLoadDataPresenter
	的子类


## 工具类相关