package com.example.moguhaian.easyshop.Utils;

import java.util.Random;

public class CommonUtils {

    public static int[] getRandom(int n) {
        int[] x = new int[n];
        for (int i = 0; i < n; i++) {
            x[i] = i;
        }
        Random r = new Random();
        for (int i = 0; i < n; i++) {
            int in = r.nextInt(n - i) + i;
            int t = x[in];
            x[in] = x[i];
            x[i] = t;
        }
        return x;
    }
}
