package boj.by_step.step04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
// import java.util.HashMap;
// import java.util.Map;
import java.util.StringTokenizer;

/**
 * 1. HashMap 을 만든다. <String, Boolean>
 * 2. 들어오는 입력을 42로 나눈 나머지값이 HashMap 에 없다면 등록하고 cnt +=1 
 */

/**
 * 1. 크기 42 짜리 배열을 만든다. boolean
 * 2. 들어오는 입력을 42로 나누고 배열에 저장한다. true 라면 있고 false 라면 없다.
//  */


public class pblm3052 {
    static BufferedReader br;
    static StringTokenizer st;       
    // 1. 크기 42 짜리 배열을 만든다. boolean
    static boolean[] array42 = new boolean[42];

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        int answer = 0;
        for (int index = 0; index < 10; index++) {
            st = new StringTokenizer(br.readLine().trim());
            int tmpNum42 = Integer.parseInt(st.nextToken()) % 42;
            array42[tmpNum42] = true;
        }
        for (int index = 0; index < 42; index++) {
            if (array42[index]) answer++;
        }

        System.out.println(answer);
    }
}

// public class pblm3052 {
//     static BufferedReader br;
//     static StringTokenizer st;
//     static Map<Integer, Boolean> hashMap42;

//     public static void main(String[] args) throws IOException {
//         br = new BufferedReader(new InputStreamReader(System.in));
//         // 1. HashMap 을 만든다. <String, Boolean>
//         hashMap42 = new HashMap<>();
//         int answer = 0;
//         for (int index = 0; index < 10; index++) {
//             st = new StringTokenizer(br.readLine().trim());
//             int tmpNum42 = Integer.parseInt(st.nextToken()) % 42;

//             // 2. 들어오는 입력을 42로 나눈 나머지값이 HashMap 에 없다면 등록하고 cnt +=1 
//             if (!hashMap42.getOrDefault(tmpNum42, false)){
//                 hashMap42.put(tmpNum42, true);
//                 answer++;
//             }
//         }
//         System.out.println(answer);
//     }
// }

