package swea.by_category.backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 
 * 문제 조건.
 * 1. 테스트케이스
 * 2. 점원의 숫자, 선반의 높이
 * 3. output : 선반의 높이보다는 높은데 가장 낮은 탑
 * 
 * 문제풀이
 * 1. 테스트케이스를 받는다.
 * 2. 점원의 숫자, 선반의 높이를 받는다.
 * 3. 점원의 키 배열을 받는다.
 * 4. 조합을 구현한다. 조합의 값을 선택할때마다 값을 더해본다.
 *  4-1 백트래킹 조건 : 조합의 합이 이미 선반의 높이를 넘겼을때,
 *      4-1-1 (탑높이의 합 - 선반 높이) 의 최소값을 찾는다.
 *      4-1-2 기저조건 : 재료를 더이상 뽑을게 없을때
 */
public class pblm1486 {
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static int[] staffArray;
    static int height;
    static int staffNum;
    static int answer;
    public static void combination(int elementIndex, int sum) {
        // 4-1 백트래킹 조건 : 조합의 합이 이미 선반의 높이를 넘겼을때,
        if (sum >= height) {
            answer = Math.min(answer, sum-height);
        }
        // 기저조건  : 재료 더이상 뽑을게 없을때 
        if (elementIndex == staffNum){
            return;
        }

        // 선택했을때 
        combination(elementIndex+1, sum+staffArray[elementIndex]);
        
        // 선택 안했을때
        combination(elementIndex+1, sum);
    }

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        // 1. 테스트케이스를 받는다.
        int testCaseNum = Integer.parseInt(br.readLine());

        for (int testCase = 1; testCase <= testCaseNum; testCase++) {
            sb = new StringBuilder();
            // 2. 점원의 숫자, 선반의 높이를 받는다.
            st = new StringTokenizer(br.readLine().trim());
            staffNum = Integer.parseInt(st.nextToken());
            height = Integer.parseInt(st.nextToken());

            st = new StringTokenizer(br.readLine().trim());
            staffArray = new int[staffNum];
            answer = Integer.MAX_VALUE;
            
            // 3. 점원의 키 배열을 받는다.
            for (int index = 0; index < staffArray.length; index++) {
                staffArray[index] = Integer.parseInt(st.nextToken());
            }
            // 4. 조합을 구현한다. 조합의 값을 선택할때마다 값을 더해본다.
            combination(0,0);
            
            sb.append("#").append(testCase).append(" ").append(answer);
            System.out.println(sb);
        }
    }
}
