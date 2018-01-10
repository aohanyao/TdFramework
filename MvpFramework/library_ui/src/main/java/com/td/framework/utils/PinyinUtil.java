package com.td.framework.utils;


import com.github.promeg.pinyinhelper.Pinyin;

import java.util.Locale;

/**
 * Created by jc on 2017/1/5 0005.
 * <p>Gihub https://github.com/aohanyao</p>
 * <p>拼音工具类</p>
 */
public class PinyinUtil {
    private PinyinUtil() {

    }

    /**
     * 取sort_key的首字母
     *
     * @param sortKey
     * @return
     */
    public static String getSortLetterBySortKey(String sortKey) {
        if (sortKey == null || "".equals(sortKey.trim())) {
            return null;
        }
        String letter = "#";
        //汉字转换成拼音
        String sortString = sortKey.trim().substring(0, 1).toUpperCase(Locale.CHINESE);
        // 正则表达式，判断首字母是否是英文字母
        if (sortString.matches("[A-Z]")) {
            letter = sortString.toUpperCase(Locale.CHINESE);
        }
        return letter;
    }

    /**
     * 汉字转拼音
     */
    public static String getPingYin(String inputString) {
        if (inputString == null) {
            return "";
        }
        StringBuilder pySb = new StringBuilder();
        for (int i1 = 0; i1 < inputString.length(); i1++) {
            //利用TinyPinyin将char转成拼音
            //查看源码，方法内 如果char为汉字，则返回大写拼音
            //如果c不是汉字，则返回String.valueOf(c)
            pySb.append(Pinyin.toPinyin(inputString.charAt(i1)).toUpperCase());
        }

//
//        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
//        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
//        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
//        format.setVCharType(HanyuPinyinVCharType.WITH_V);
//        char[] input = inputString.trim().toCharArray();
//        String output = "";
//        try {
//            for (char curchar : input) {
//                if (java.lang.Character.toString(curchar).matches("[\\u4E00-\\u9FA5]+")) {
//                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(curchar, format);
//                    output += temp[0];
//                } else
//                    output += java.lang.Character.toString(curchar);
//            }
//        } catch (BadHanyuPinyinOutputFormatCombination e) {
//            e.printStackTrace();
//        }
        return pySb.toString();
    }
}
