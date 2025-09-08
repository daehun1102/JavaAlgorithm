package boj.by_category.dp;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class pblm1463 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        int[] dp = new int[N + 1];
        dp[1] = 0; 

        for (int number = 2; number <= N; number++) {
            // 각 단계마다 1 빼기, 2나누기, 3나누기 다해봐야한다.
            // 최솟값이면 나누기가 유리하므로 1빼기, 2나누기, 3나누기 순으로 한다.
            int best = dp[number - 1] + 1;

            if (number % 2 == 0) {
                best = Math.min(best, dp[number / 2] + 1);
            }
            
            if (number % 3 == 0) {
                best = Math.min(best, dp[number / 3] + 1);
            }
            dp[number] = best;
        }

        System.out.println(dp[N]);
    }
}
