package com.github.featzhang.flake.client.utils;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 *
 */
public class TextUtils {

    public static Pair<List<String>, String> splitTags(String title) {
        Stack<String> stack = new Stack<>();
        boolean startPair = false;
        boolean waitNext = true;
        boolean noNeedMatch = false;
        StringBuilder p = new StringBuilder();
        for (char c : title.toCharArray()) {
            if (noNeedMatch) {
                p.append(c);
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
                stack.add(p.toString());
                p = new StringBuilder();
                waitNext = true;
                continue;
            }
            p.append(c);
        }
        List<String> tags = new ArrayList<>(stack);
        return new Pair<>(tags, p.toString());
    }

}
