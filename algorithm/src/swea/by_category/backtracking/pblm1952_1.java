package swea.by_category.backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 문제 개요
 * 1. 1~12월까지의 이용일 수가 주어질때
 * 2. 가장 적은 비용으로 수영장 이용
 * 
 * 아이디어
 * 1. 완전탐색 1~12월 까지
 *  1-1. 각 월마다 3가지의 선택지 (1년 사용은 경우의수 1개밖에 없기때문에 최소비용 초기값 )  
 *  1-2. 선택할때마다 1일과 1개월은 비교했을때 큰곳으로만 이동한다.
 *  1-3. 3개월은 depth 3 추가
 * 
 * 문제풀이
 * 1. 테스트케이스를 받는다.
 * 2. 이용권 가격을 받는다.
 * 3. 이용계획을 받는다.
 * 4. dfs(0) 부터 시작해서 최소비용을 갱신한다.
 * 5. dfs 를 구현한다.
 *  기저조건 : 11이상이라면 최소비용인지 확인하고 return 
 *  5-1. 각 월마다 2가지의 선택지를 탐색 (1일 or 1개월,3개월)
 */
public class pblm1952_1 {
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static int[] pricesArray;
    static int[] swimmingPlanArray;

    static int minPrice;
    
    public static void main(String[] args) throws IOException {
        // 1. 테스트케이스를 받는다.
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine().trim());
        int testCaseNum = Integer.parseInt(st.nextToken());
        
        for (int testCase = 1; testCase <= testCaseNum; testCase++) {
            // 2. 이용권 가격을 받는다.
            pricesArray = new int[4];
            swimmingPlanArray = new int[12];
            st = new StringTokenizer(br.readLine().trim());
            for (int priceIndex = 0; priceIndex < 4; priceIndex++) {
                pricesArray[priceIndex] = Integer.parseInt(st.nextToken());
            }

            // 3. 이용계획을 받는다.
            st = new StringTokenizer(br.readLine().trim());
            for (int swimmingIndex = 0; swimmingIndex < 12; swimmingIndex++) {
                swimmingPlanArray[swimmingIndex] = Integer.parseInt(st.nextToken());
            }
            
            minPrice = pricesArray[3]; // 1년 이용권이 시작값
            // 4. dfs(0) 부터 시작해서 최소비용을 갱신한다.
            dfs(0,0);
            
            // 5. 최저값을 출력한다.
            sb = new StringBuilder();
            sb.append("#").append(testCase).append(" ").append(minPrice);
            System.out.println(sb);
        }
    }

    // 5. dfs 를 구현한다.
    static void dfs(int month, int price){
        if (month >= 12) {
            minPrice = Math.min(minPrice, price);
            return;
        }
        if (price >= minPrice) return;
        // 5-1. 각 월마다 2가지의 선택지를 탐색 (1일 or 1개월,3개월)
        int currentPrice = (pricesArray[0]*swimmingPlanArray[month]> pricesArray[1]) ?  pricesArray[1] : pricesArray[0]*swimmingPlanArray[month];
        dfs(month+1, price + currentPrice); // 1개월 or 1일 선택
        dfs(month+3, price + pricesArray[2]); // 3개월 선택

    }
}
