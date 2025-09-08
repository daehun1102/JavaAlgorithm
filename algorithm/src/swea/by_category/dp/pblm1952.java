package swea.by_category.dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 아이디어
 * dp[month] -> month 달 부터 12월까지 쓰는 최소비용
 */
public class pblm1952 {
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static int[] pricesArray;
    static int[] swimmingPlanArray;
    
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
            
            // dp[month] -> month 달 부터 12월까지 쓰는 최소비용
            int[] dp = new int[13]; 
            for (int month = 11; month >= 0; month--) {
                int next = month + 1;
                int nextAfter3 = Math.min(12, month + 3);

                // 이번 달을 일권으로 할지 한달로 처리할지 최솟값으로 결정
                int dayOrMonth = Math.min(
                        pricesArray[0] * swimmingPlanArray[month],
                        pricesArray[1]
                );

                // 이번 달을 위에서 구한거랑 3달권이랑 비교해서 최솟값으로 결정
                int option1 = dayOrMonth + dp[next];
                int option2 = pricesArray[2] + dp[nextAfter3];

                dp[month] = Math.min(option1, option2);
            }
            // 일년권이랑 비교
            int answer = Math.min(dp[0], pricesArray[3]);


            sb = new StringBuilder();
            sb.append("#").append(testCase).append(" ").append(answer);
            System.out.println(sb);
        }
    }

}
