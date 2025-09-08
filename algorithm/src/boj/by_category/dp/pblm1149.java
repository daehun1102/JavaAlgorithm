package boj.by_category.dp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 
 * 아이디어
 * 1. dp 풀이로 
 * dp[i][c]: i번째 집을 특정 색으로 칠했을 때
 * 0번 집부터 i번 집까지의 최소 비용.
 */
public class pblm1149 {
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int houseNum = Integer.parseInt(br.readLine());

        int[][] cost = new int[houseNum+1][3];
        for (int houseIndex = 0; houseIndex < houseNum; houseIndex++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            cost[houseIndex][0] = Integer.parseInt(st.nextToken()); 
            cost[houseIndex][1] = Integer.parseInt(st.nextToken()); 
            cost[houseIndex][2] = Integer.parseInt(st.nextToken()); 
        }

        // N 번째 집을 0~2 색으로 칠했을때, 0번집부터 N번 집까지의 최소비용
        int[][] dp = new int[houseNum][3];
        
        // 초기값 등록을 한다.
        dp[0][0] = cost[0][0];
        dp[0][1] = cost[0][1];
        dp[0][2] = cost[0][2];

        // 이번집 한개를 칠할땐 이전집이 다른색이어야 한다.
        for (int houseIndex = 1; houseIndex < houseNum; houseIndex++) {
            dp[houseIndex][0] = Math.min(dp[houseIndex-1][1], dp[houseIndex-1][2]) + cost[houseIndex][0];
            dp[houseIndex][1] = Math.min(dp[houseIndex-1][0], dp[houseIndex-1][2]) + cost[houseIndex][1];
            dp[houseIndex][2] = Math.min(dp[houseIndex-1][0], dp[houseIndex-1][1]) + cost[houseIndex][2];
        }

        int ans = Math.min(dp[houseNum-1][0], Math.min(dp[houseNum-1][1], dp[houseNum-1][2]));
        System.out.println(ans);
    }
}
