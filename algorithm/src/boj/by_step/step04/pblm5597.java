package boj.by_step.step04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 1. 1~30 까지의 배열을 만든다. boolean
 * 2. 입력을 받아서 true 로 만든다.
 * 3. 인덱스 1부터 순회해서 false 인것 출력한다.
 */
public class pblm5597 {
    static BufferedReader br;
    static StringTokenizer st;
    static boolean[] studentArray = new boolean[31];
    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));

        for (int index = 0; index < 28; index++) {
            st = new StringTokenizer(br.readLine().trim());
            int tmpStudent = Integer.parseInt(st.nextToken());
            studentArray[tmpStudent] = true;
        }

        for (int index = 1; index < 31; index++) {
            if (!studentArray[index]) System.out.println(index);
        }
    }
}
