package com.github.zuofengzhang.flake.client.utils;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author averyzhang
 * @date 2021/2/22
 */
public class TextUtils {
//    private static Pattern pattern = Pattern.compile("(\\[[^\\]]*\\])");

    public static Pair<List<String>, String> splitTags(String title) {
        Stack<String> stack = new Stack<>();
        boolean startPair = false;
        boolean waitNext = true;
        boolean noNeedMatch = false;
        String p = "";
        for (char c : title.toCharArray()) {
            if (noNeedMatch) {
                p += c;
                continue;
            }
            if (!startPair && c == '[') {
                startPair = true;
                waitNext = false;
                continue;
            } else {
                if (waitNext) {
                    waitNext = false;
                    noNeedMatch = true;
                }
            }
            if (startPair && c == ']') {
                startPair = false;
                stack.add(p);
                p = "";
                waitNext = true;
                continue;
            }
            p += c;
        }
        List<String> tags = new ArrayList<>(stack);
        return new Pair<>(tags, p);
    }

    public static void main(String[] args) {
        String title = "0[[123][1][456]1[fdsfs]shi是非得失";
        Pair<List<String>, String> pair = TextUtils.splitTags(title);
        System.out.println("tag:");
        for (String tag : pair.getKey()) {
            System.out.println(tag);
        }
        System.out.println("value:");
        System.out.println(pair.getValue());
    }
}
