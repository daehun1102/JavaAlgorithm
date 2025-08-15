package swea.by_category.deque;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

/**
 * 1. 테스트 케이스를 받는다.
 * 2. 8개의 숫자를 Deque 에 넣는다.
 * 3. 앞에 숫자를 뺀다.
 *  3-1. 빼온 숫자를 minusNum에 빼보고 0보다 작은지 확인한다. (minusNum 은 사이클 처리)
 *      3-1-1. 0보다 작다면 뒤에 넣고 종료
 *      3-1-2. 0보다 크다면 뒤에 넣기
 */
public class pblm1225 {
    static int testCaseNum;
    static Deque<Integer> secretDeque;
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;
    static int minusNum;
    public static void main(String[] args) throws IOException{
        // 1. 테스트 케이스를 받는다.
        br = new BufferedReader(new InputStreamReader(System.in));
        sb = new StringBuilder();
        for (int testCase = 0; testCase < 10; testCase++) {
            st = new StringTokenizer(br.readLine());
            secretDeque = new ArrayDeque<>(8);
            int testCaseNum = Integer.parseInt(st.nextToken());
            st = new StringTokenizer(br.readLine());

            // 2. 8개의 숫자를 Deque 에 넣는다.
            for (int index = 0; index < 8; index++) {
                int tmpNum = Integer.parseInt(st.nextToken());
                secretDeque.add(tmpNum);
            }
            minusNum = 1;
            
            while (true) {
                // 3-1. 빼온 숫자를  를 하고 0보다 작은지 확인한다.
                int frontNum = secretDeque.pollFirst();
                frontNum = frontNum - minusNum++;
                if (minusNum>5) minusNum = 1;

                // 3-1-2. 0보다 크다면 뒤에 넣기
                if (frontNum > 0) {
                    secretDeque.add(frontNum);
                } else {
                    secretDeque.add(0);
                    break;
                }
            }
            sb.append("#").append(testCaseNum).append(" ");
            for (int secret : secretDeque) {
                sb.append(secret).append(" ");
            }
            sb.append("\n");
        }
        System.out.println(sb);
    }
    
}
