/*
 *  Language
 *  @author: Minhhieuano
 *  @created 11/2/2024 9:08 AM
 * */


package com.vibio.search.lib;

public class Language {
    public static String removeVietnameseTones(String str) {
        str = str.replaceAll("[àáạảãâầấậẩẫăằắặẳẵ]", "a");
        str = str.replaceAll("[èéẹẻẽêềếệểễ]", "e");
        str = str.replaceAll("[ìíịỉĩ]", "i");
        str = str.replaceAll("[òóọỏõôồốộổỗơờớợởỡ]", "o");
        str = str.replaceAll("[ùúụủũưừứựửữ]", "u");
        str = str.replaceAll("[ỳýỵỷỹ]", "y");
        str = str.replaceAll("[đ]", "d");
        str = str.replaceAll("[ÀÁẠẢÃÂẦẤẬẨẪĂẰẮẶẲẴ]", "A");
        str = str.replaceAll("[ÈÉẸẺẼÊỀẾỆỂỄ]", "E");
        str = str.replaceAll("[ÌÍỊỈĨ]", "I");
        str = str.replaceAll("[ÒÓỌỎÕÔỒỐỘỔỖƠỜỚỢỞỠ]", "O");
        str = str.replaceAll("[ÙÚỤỦŨƯỪỨỰỬỮ]", "U");
        str = str.replaceAll("[ỲÝỴỶỸ]", "Y");
        str = str.replaceAll("[Đ]", "D");

        // Remove combining diacritical marks
        str = str.replaceAll("[\u0300\u0301\u0303\u0309\u0323]", ""); // huyền, sắc, ngã, hỏi, nặng
        str = str.replaceAll("[\u02C6\u0306\u031B]", ""); // Â, Ê, Ă, Ơ, Ư

        // Remove extra spaces
        str = str.replaceAll(" +", " ").trim();

        // Remove punctuations and special characters
        str = str.replaceAll("[!@%\\^\\*\\(\\)\\+\\=<>\\?/,:;'\"]", " ");
        str = str.replaceAll("[&\\#\\[\\]~\\$`\\-_{}|\\\\]", " ");

        return str;
    }
}
