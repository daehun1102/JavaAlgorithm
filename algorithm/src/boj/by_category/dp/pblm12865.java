package boj.by_category.dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 전형적인 DP 문제
 * 1. 물건이 들어오는게 부피 V 와 가치 C 가 들어오는데 
 * 2. 부피가 작은거부터, 혹은 가치가 높은 것 부터 넣는 그리디방식으로는 해결이 안된다. 
 * 3. 따라서 매번 둘다 고려하는 동적계획법을 생각해볼 수 있다.
 * 
 * 둘다 고려한다는 것은 V,C 를 매번 둘다 고려해서 최적해를 구하는 것인데
 * 점화식으로 표현하면
 * dp[i][v] = max(dp[i][v], dp[i-1][v-c]+C
 *  
 */
public class pblm12865 {
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static int itemNum, vLimit;
    static int[][] itemInfoArray, dp;
    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));

        // 입력을 받자
        st = new StringTokenizer(br.readLine().trim());
        itemNum = Integer.parseInt(st.nextToken());
        vLimit = Integer.parseInt(st.nextToken());
        
        itemInfoArray = new int[itemNum+1][2];
        for (int itemIndex = 1; itemIndex <= itemNum; itemIndex++) {
            st = new StringTokenizer(br.readLine().trim());
            // 가치
            itemInfoArray[itemIndex][0] = Integer.parseInt(st.nextToken());
            
            // 부피
            itemInfoArray[itemIndex][1] = Integer.parseInt(st.nextToken());
        }

        // dp 가 뜻하는건 i번째까지의 가방을 고려할때 무게가 V인 가방일때 만들 수 있는 최대의 가치이다.
        dp = new int[itemNum+1][vLimit+1];

        for (int itemIndex = 1; itemIndex <= itemNum; itemIndex++) {
            int curVolume = itemInfoArray[itemIndex][0];
            int curValue = itemInfoArray[itemIndex][1];

            for (int v = 0; v <= vLimit; v++) {
                dp[itemIndex][v] = dp[itemIndex-1][v];
                if (v-curVolume <0) continue;
                dp[itemIndex][v] = Math.max(dp[itemIndex][v], dp[itemIndex-1][v-curVolume] + curValue);
            }
        }
        System.out.println(dp[itemNum][vLimit]);
    }
}
