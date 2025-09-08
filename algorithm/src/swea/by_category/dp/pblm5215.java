package swea.by_category.dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 1. dp 로 풀이
 * dp[c]의 의미: 현재까지 고려한 재료들을 사용하여 
 * 칼로리 합이 c일 때 얻을 수 있는 최대 맛 점수
 */
public class pblm5215 {
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;
    static int foodNum, calLimit;
    static int[][] foodInfo;
    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        int testCaseNum = Integer.parseInt(br.readLine().trim());
        for (int testCase = 1; testCase <= testCaseNum; testCase++) {
            st = new StringTokenizer(br.readLine().trim());
            foodNum = Integer.parseInt(st.nextToken());
            calLimit = Integer.parseInt(st.nextToken());
            foodInfo = new int[foodNum][2];
            for (int foodIndex = 0; foodIndex < foodNum; foodIndex++) {
                st = new StringTokenizer(br.readLine().trim());
                // 맛점수
                foodInfo[foodIndex][0] = Integer.parseInt(st.nextToken());
                // 칼로리
                foodInfo[foodIndex][1] = Integer.parseInt(st.nextToken());
            }
            // 
            int[] dp = new int[calLimit+1];

            for (int foodIndex = 0; foodIndex < foodNum; foodIndex++) {
                int taste = foodInfo[foodIndex][0];
                int calorie = foodInfo[foodIndex][1];
                for (int c = calLimit; c >= calorie; c--) {

                    // 현재 재료 포함하지 않았을때, 했을때 최댓값 연산
                    dp[c] = Math.max(dp[c], dp[c-calorie] + taste);
                } 
                // 테스트용
                // System.out.println(Arrays.toString(dp));
            }

            sb = new StringBuilder();
            sb.append('#').append(testCase).append(' ').append(dp[calLimit]);
            System.out.println(sb);
        }
        

    }
    
}
