package com.skyvolt.quiztime;

import android.content.Context;

import shared.Dp;
import shared.ItemColor;

public class InitializeSystem {
    public static void where(Context context) {
        Dp.setContext(context);
        ItemColor.setContext(context);
    }
}
