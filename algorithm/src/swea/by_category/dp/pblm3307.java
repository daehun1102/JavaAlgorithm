package swea.by_category.dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 문제 개요
 * 1. 배열이 들어오면 제일 긴 증가수열을 
 * 만들 수 있는 부분집합의 길이를 구한다. 
 * 
 * 아이디어
 * 1. 
 * 
 * 문제 풀이
 * 1. 테스트 케이스 개수를 받고,
 * 2. 테스트 케이스 마다,
 *  2-1. 입력의 길이를 받는다.
 *  2-2. 숫자배열을 받는다.
 *  2-3. dp 배열을 생성한다. dp 의 기준은 i 번째 숫자까지 고려했을때 i번째 숫자가 제일 앞에 위치한 경우 만들 수 있는 최장 길이이다.
 * 
 */
public class pblm3307 {
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static int[] numberArray, dp;
    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        int testCaseNum = Integer.parseInt(br.readLine().trim());
        sb = new StringBuilder();

        for (int testCase = 1; testCase <= testCaseNum; testCase++) {
            // 숫자 배열의 길이를 받는다.
            int numberArrayLength = Integer.parseInt(br.readLine().trim());
            numberArray = new int[numberArrayLength+1];
            st = new StringTokenizer(br.readLine().trim());
            for (int numberIndex = 1; numberIndex <= numberArrayLength; numberIndex++) {
                numberArray[numberIndex] = Integer.parseInt(st.nextToken());
            }

            // dp 배열을 생성한다. dp 의 기준은 i 번째 숫자까지 고려했을때 i번째 숫자가 제일 앞에 위치한 경우 만들 수 있는 최장 길이이다.
            // 본인을 앞으로 세웠을때 무조건 1개는 만들 수 있으므로 초기값 1
            dp = new int[numberArrayLength+1];
            for (int number = 1; number <= numberArrayLength; number++) {
                dp[number] = 1;
            }

            // dp[i] => numberArray[i] 보다 작은 값들(인덱스 j) 중 dp[j] 가 제일 큰값
            for (int frontIndex = 1; frontIndex <= numberArrayLength; frontIndex++) {
                for (int beforeIndex = 1; beforeIndex < frontIndex; beforeIndex++) {
                    if (numberArray[beforeIndex] > numberArray[frontIndex]) continue; // 뒤에 값이 큰건 신경쓰지 말자
                    // 작으면 dp 값이 큰걸 넣기
                    dp[frontIndex] = Math.max(dp[frontIndex],dp[beforeIndex] + 1);
                }
            }

            // dp 배열 중 최대값 출력
            sb.append("#").append(testCase).append(" ").append(Arrays.stream(dp).max().getAsInt()).append("\n");
        }
        System.out.println(sb.toString());
    }
}
