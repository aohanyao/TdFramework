#Intent拓展函数相关说明
com.td.framework.expand
这里面有一些拓展的封装，
例如：直接启动一个Activity，可以这样调用

    launcherActivity<Activity>()
    
直接将Activity作为泛型传入即可

启动Activity，传递参数，

    launcherActivity<Activity>("key" to "value")

直接将 key to value 传入即可，接收的是一个params的可变参数，可以选择
传递多个和不传。

另外还有其他启动service，startActivityForResult。具体看
    
    com.td.framework.expand.Intents


    