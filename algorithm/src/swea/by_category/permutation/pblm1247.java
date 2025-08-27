package swea.by_category.permutation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 문제개요
 * 1. 방문할 집 기록해놓고 최단거리로 찾기
 * 
 * 아이디어
 * 1 . 순열로 완전탐색
 * 
 * 문제풀이
 * 1. 테스트케이스 개수를 받는다.
 * 2. 방문할 고객 수를 받고,
 *  2-1. 집좌표, 회사좌표, 방문 고객 좌표를 각각 저장한다.
 * 3. permutation 구현
 *  3-1. 생성된 순열 순서대로 거리를 구해보고
 *  3-2. 최대값 갱신. 
 */
public class pblm1247 {
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static int targetCount;
    static boolean[] usedArray;

    static int minDistance;
    static int[] selectedArray;
    static int[] nodeX, nodeY;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));

        // 1. 테스트케이스 개수를 받는다.
        int testCaseNum = Integer.parseInt(br.readLine().trim());
        for (int testCase = 1; testCase <= testCaseNum; testCase++) {
            // 2. 방문할 고객 수를 받고,
            targetCount = Integer.parseInt(br.readLine());
            usedArray = new boolean[targetCount+2];
            selectedArray = new int[targetCount];
            
            // 2-1. 집좌표, 회사좌표, 방문 고객 좌표를 각각 저장한다.
            nodeX = new int[targetCount+2];
            nodeY = new int[targetCount+2];
            st = new StringTokenizer(br.readLine().trim());
            nodeX[0] = Integer.parseInt(st.nextToken());
            nodeY[0] = Integer.parseInt(st.nextToken());
            nodeX[targetCount+1] = Integer.parseInt(st.nextToken());
            nodeY[targetCount+1] = Integer.parseInt(st.nextToken());
            
            for (int index = 1; index <= targetCount; index++) {
                nodeX[index] = Integer.parseInt(st.nextToken());
                nodeY[index] = Integer.parseInt(st.nextToken());
            }

            minDistance = Integer.MAX_VALUE;
            permutation(0);

            sb = new StringBuilder();
            sb.append('#').append(testCase).append(' ').append(minDistance);
            System.out.println(sb);

        }
    }

    // 3. permutation 구현
    public static void permutation(int selectCount) {
        if (selectCount == targetCount) {
            // 3-1. 생성된 순열 순서대로 거리를 구해보고
            getMin();
            return;
        }
        for (int numIndex = 1; numIndex <= targetCount; numIndex++) {
            if (usedArray[numIndex]) continue;

            usedArray[numIndex] = true;
            selectedArray[selectCount] = numIndex;
            permutation(selectCount+1);
            usedArray[numIndex] = false;
        }
    }

    public static void  getMin(){
        int tmpDistance = 0;
        int tmpX = nodeX[0];
        int tmpY = nodeY[0];

        // 선택된 순서대로 node 방문
        for (int index : selectedArray) {
            int curDistance = Math.abs(nodeX[index] - tmpX) + Math.abs(nodeY[index] - tmpY);
            tmpDistance += curDistance;
            tmpX = nodeX[index];
            tmpY = nodeY[index];
        }

        tmpDistance += Math.abs(nodeX[targetCount+1]- tmpX) + Math.abs(nodeY[targetCount+1]- tmpY); 

        // 3-2. 최대값 갱신. 
        minDistance = Math.min(tmpDistance,minDistance);
    }
}
