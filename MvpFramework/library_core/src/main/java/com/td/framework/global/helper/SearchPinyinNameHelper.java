package com.td.framework.global.helper;


import com.td.framework.utils.PinyinUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by jc on 2017/3/24 0024.
 * <p>版本:1.0.0</p>
 * <b>说明<b>筛选帮组类<br/>
 * <li></li>
 */
public class SearchPinyinNameHelper {
    /**
     * 模糊查询
     *
     * @param str
     * @return
     */
    public static   List<SortKeyInf> search(String str, List<SortKeyInf> mDatas) {
        List<SortKeyInf> filterList = new ArrayList<>();// 过滤后的list
        //if (str.matches("^([0-9]|[/+])*$")) {// 正则表达式 匹配号码
        if (str.matches("^([0-9]|[/+]).*")) {// 正则表达式 匹配以数字或者加号开头的字符串(包括了带空格及-分割的号码)
            String simpleStr = str.replaceAll("\\-|\\s", "");
            for (SortKeyInf contact : mDatas) {
                if (contact.getSortName() != null) {
                    if (contact.getSortName().contains(simpleStr) || contact.getSortName().contains(str)) {
                        if (!filterList.contains(contact)) {
                            filterList.add(contact);
                        }
                    }
                }
            }
        } else {
            for (SortKeyInf contact : mDatas) {
                if (contact.getSortName() != null) {
                    //姓名全匹配,姓名首字母简拼匹配,姓名全字母匹配
                    if (contact.getSortName().toLowerCase(Locale.CHINESE).contains(str.toLowerCase(Locale.CHINESE))

                            || PinyinUtil.getSortLetterBySortKey(contact.getSortName()).toLowerCase(Locale.CHINESE).replace(" ", "").contains(str.toLowerCase(Locale.CHINESE))
                            || PinyinUtil.getSortLetterBySortKey(PinyinUtil.getPingYin(contact.getSortName())).toLowerCase(Locale.CHINESE).contains(str.toLowerCase(Locale.CHINESE))
                            /*|| contact.sortToken.wholeSpell.toLowerCase(Locale.CHINESE).contains(str.toLowerCase(Locale.CHINESE))*/) {
                        if (!filterList.contains(contact)) {
                            filterList.add(contact);
                        }
                    }
                }
            }
        }
        return filterList;
    }
    public static interface SortKeyInf {
        public String getSortName();
    }
}
