package com.example.kniffelblock;

import java.util.ArrayList;

public class PointCalculator {

    private ArrayList<String> row;

    public PointCalculator(ArrayList<String> row) {
        this.row = row;
    }

    public ArrayList<Integer> addPoints() {
        ArrayList<Integer> result = new ArrayList<>();
        int sum = 0;
        for(int i = 0; i < 6; i++) {
                sum += Integer.parseInt(row.get(i));
                result.add(i, Integer.parseInt(row.get(i)));
            }
            if(sum >= 65) {
                sum += 35;
                result.add(6, 35);
                result.add(7, sum);
            }
            else {
                result.add(6, 0);
                result.add(7, sum);
            }

        sum = 0;
        for(int i = 6; i < 13; i++) {
            int j = i + 2;
            sum += Integer.parseInt(row.get(i));
            result.add(j, Integer.parseInt(row.get(i)));
        }

        result.add(15, sum);

        sum = result.get(7) + result.get(15);
        result.add(16, sum);
        return result;
    }

}
