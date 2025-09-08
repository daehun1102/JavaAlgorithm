package boj.by_category.dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class pblm17845 {
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static int limitTime, subjectNum;
    static int[][] subjectInfoArray, dp;
    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine().trim());
        limitTime = Integer.parseInt(st.nextToken());
        subjectNum = Integer.parseInt(st.nextToken());
        
        subjectInfoArray = new int[subjectNum+1][2];

        // dp 의 의미는 최대 공부시간을 넘기지 않으면서 얻을 수 있는 최대의 만족도 이다.
        for (int subjectIndex = 1; subjectIndex <= subjectNum; subjectIndex++) {
            st = new StringTokenizer(br.readLine().trim());
            // 만족도
            subjectInfoArray[subjectIndex][0] = Integer.parseInt(st.nextToken());
            // 공부시간
            subjectInfoArray[subjectIndex][1] = Integer.parseInt(st.nextToken());
        }
        
        dp = new int[subjectNum+1][limitTime+1];
        for (int subjectIndex = 1; subjectIndex <= subjectNum; subjectIndex++) {
            
            int subjectGood = subjectInfoArray[subjectIndex][0];
            int subjectTime = subjectInfoArray[subjectIndex][1];
            
            // 시간 내의 최적해 = 이번걸 선택하는것과 선택안하는것의 최대 만족도
            for (int curLimitTime = 0; curLimitTime <= limitTime; curLimitTime++) {
                
                dp[subjectIndex][curLimitTime] = dp[subjectIndex-1][curLimitTime];
                // 만약에 이번과목 선택했을때 시간 넘어가면 무시
                if (subjectTime - curLimitTime > 0) continue;
                dp[subjectIndex][curLimitTime] = Math.max(dp[subjectIndex][curLimitTime], dp[subjectIndex-1][curLimitTime - subjectTime]+subjectGood);
            }            
        }
        System.out.println(dp[subjectNum][limitTime]);
    }
}
