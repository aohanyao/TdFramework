package com.td.framework.biz;


import rx.Subscriber;


/**
 * 封装Subscriber
 */
public abstract class RxSubscriber<T> extends Subscriber<T> {

    public RxSubscriber( ) {

    }

    public RxSubscriber(Subscriber<?> subscriber ) {
        super(subscriber);
    }

    public RxSubscriber(Subscriber<?> subscriber, boolean shareSubscriptions ) {
        super(subscriber, shareSubscriptions);
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onNext(T t) {
        onResult(t);
    }

    public abstract void onResult(T t);


}