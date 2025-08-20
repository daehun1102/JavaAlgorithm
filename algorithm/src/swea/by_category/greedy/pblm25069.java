package swea.by_category.greedy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;


/**
 * 문제개요
 * 1. 문제개수, 문제 맞은 개수, 카운터 수가 주어지면,
 * 2. 가능한 모든 경우의 수 중 가장 낮은 점수를 출력한다.
 * 
 * 아이디어.
 * 1. dfs 는 N 이 3~500 이고,
 *  1-1. 각 N 마다 틀린경우, 맞은경우, 2가지로 나뉘기 때문에 시간복잡도과 굉장히 클 것으로 예상해야한다.
 *  1-2. 백트래킹이 있을 수도 있지만 굉장히 갈길이 멀다.
 * 2. 그리디가 되는가?
 *  2-1. 카운터가 전체 점수의 2배이기 때문에 뒤로갈수록 카운터가 점수를 매우 높인다.
 *  2-2. 카운터의 배수 자리를 뒤쪽부터 채운다.
 * 
 * 문제풀이.
 * 1. 테스트케이스를 받는다.
 *  1-1. 모두 일단 다 맞았다는 배열을 하나 만든다. (크기는 N+1)
 *  1-2. 카운터 배수 인덱스 개수를 구하고 틀린개수와의 차이를 구한다. 뺄 점수 = (틀린개수 - 카운터 배수 인덱스 개수)
 *      1-2-2. "뺄 점수" 가 만약에 1이상이라면 저장 아니면 0
 *  2. 뒤부터 카운터 배수 인덱스 자리에 1 채워넣기 ( 나중에 1 자리는 점수계산 x)
 *  3. 문제 배열 돌면서 점수계산 한 후 "뺄 점수" 빼주기
 */
public class pblm25069 {
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static int problemNum;
    static int problemArray;
    static int correctNum;
    static int counter;


    public static void main(String[] args) throws IOException {
        // 1. 테스트케이스를 받는다.
        br = new BufferedReader(new InputStreamReader(System.in));
        int testCaseNum = Integer.parseInt(br.readLine().trim());
        for (int testCase = 1; testCase <= testCaseNum; testCase++) {
            st = new StringTokenizer(br.readLine().trim());
            problemNum = Integer.parseInt(st.nextToken());
            correctNum = Integer.parseInt(st.nextToken());
            counter = Integer.parseInt(st.nextToken());

            // 1-1. 모두 일단 다 맞았다는 배열을 하나 만든다. (크기는 N+1)
            int[] problemArray = new int[problemNum+1];
            
            // 1-2. 카운터 배수 인덱스 개수를 구하고 틀린개수와의 차이를 구한다. ( 틀린개수 - 카운터 배수 인덱스 개수 )
            int counterIndexNum = problemNum / counter ;
            int incorrectNum = problemNum - correctNum;
            int diffPoint = (incorrectNum - counterIndexNum);  // x 를 카운터 배수 인덱스에 전부 놓고 남은 경우
            
            // 1-2-1. 만약에 0이하라면, 0으로
            if (diffPoint <=0){
                diffPoint = 0;
            } 

            // 2. 뒤부터 카운터 배수 인덱스 자리에 1 채워넣기 ( 나중에 1 자리는 점수계산 x)
            // 틀린 갯수가 같거나 부족한 경우
            if (incorrectNum < counterIndexNum) {
                // 채울 수 있을만큼만 채우기
                int incorrectCount = incorrectNum;
                int couterIndex = counterIndexNum*counter;
                while (incorrectCount > 0) {
                    problemArray[couterIndex] = 1;
                    couterIndex -= counter; 
                    incorrectCount--;
                }
            }

            // 틀린 갯수가 넘치거나 같은 경우
            if (incorrectNum >= counterIndexNum) {
                for (int couterIndex = counterIndexNum*counter; couterIndex > 0; couterIndex -= counter) {
                    problemArray[couterIndex] = 1;
                }
            }

            // 3. 문제 배열 돌면서 점수계산 한 후 "뺄 점수" 빼주기
            int point = 0;
            int counting = 0;

            for (int problemIndex = 1; problemIndex <= problemNum; problemIndex++) {
                if (problemArray[problemIndex] == 1) {
                    //틀린경우
                    counting = 0;
                } else {
                    // 맞은경우
                    point +=1;
                    counting +=1;
                    if (counting == counter) {
                        point *= 2;
                        counting = 0;
                    }
                }
            }

            point -= diffPoint;
            sb = new StringBuilder();
            sb.append("#").append(testCase).append(" ").append(point);
            System.out.println(sb);
        }
    }
}
