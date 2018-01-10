/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.td.framework.mvp.view;

import android.support.annotation.StringRes;

import com.td.framework.biz.NetError;

public interface BaseView {
    /**
     * 发生错误
     *
     * @param error
     */
    void onFail(NetError error);

    /**
     * 完成
     *
     * @param msg
     */
    void complete(String msg);

    /**
     * 显示
     *
     * @param msg
     */
    void showLoading(@StringRes int msg);

    /**
     * 显示空布局
     */
//    void showEmpty();
}
