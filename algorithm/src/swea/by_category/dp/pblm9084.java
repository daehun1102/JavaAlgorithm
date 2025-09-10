package swea.by_category.dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 문제개요
 * 1. 동전 개수와 종류가 주어진다.
 * 2. 특정 금액을 만들때 만들 수 있는 경우의 수를 구해야한다.
 * 
 * 아이디어
 * 1. f(n) : 금액 n 을 만들 수 있는 경우의 수
 * 2. n = 가장 동전의 단위가 큰수부터 분해 551 = 100 * 5 + 50*1 + 1*1
 * 3. f(n) = f(n보다 작은 가장큰 단위 k) * (n//k) + f(k 다음 q) * (n//q) ...   
 * 
 * 문제풀이
 * 1. 입력을 받고 
 * 2. f(n) = f(n보다 작은 가장큰 단위 k) * (n//k) + f(k 다음 q) * (n//q) ...   적용한다.
 */
public class pblm9084 {
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;
    static int[] coinArray,dp;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        int testCaseNum = Integer.parseInt(br.readLine().trim());
        for (int testCase = 1; testCase <= testCaseNum ; testCase++) {
            st = new StringTokenizer(br.readLine().trim());
            int coinNum = Integer.parseInt(st.nextToken());
            coinArray = new int[coinNum];
            
            // 코인 배열 받고,
            st = new StringTokenizer(br.readLine().trim());
            for (int coinIndex = 0; coinIndex < coinNum; coinIndex++) {
                coinArray[coinIndex] = Integer.parseInt(st.nextToken());
            }

            int targetMoney = Integer.parseInt(br.readLine().trim());

            dp = new int[targetMoney+1];
            dp[0] = 1;
            
            // {5 10 100} ...
            for (int coin : coinArray) {
                for (int amount = coin; amount <= targetMoney; amount++) {
                    dp[amount] += dp[amount-coin];
                }
            }

            // 다끝나면 정답 도출
            System.out.println(dp[targetMoney]);
        }
    }
}
