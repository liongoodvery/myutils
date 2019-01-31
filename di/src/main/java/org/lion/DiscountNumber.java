package org.lion;

import java.util.ArrayList;
import java.util.List;

public class DiscountNumber {
    public static void main(String[] args) {
        ArrayList<Double> nums = new ArrayList<Double>() {{
            add(26.9);
            add(21.9);
            add(19.9);
        }};
        ArrayList<Integer> count = new ArrayList<>();
        for (int i = 0; i < nums.size(); i++) {
            count.add(0);
        }
        foo(nums, count, 188);
    }


    private static void foo(List<Double> nums, List<Integer> count, double result) {
        if (result < 0) {
            double sum = 0;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < nums.size(); i++) {
                Double num = nums.get(i);
                Integer c = count.get(i);
                sb.append("+").append(num).append("*").append(c);
                sum = sum + num * c;
            }

            sb.append("=").append(sum).append("\n");
            System.out.println(sb);
            return;
        }

        for (int i = 0; i < nums.size(); i++) {
            count.set(i, count.get(i) + 1);
            foo(nums, count, result - nums.get(i));
            count.set(i, count.get(i) - 1);
        }
    }
}
