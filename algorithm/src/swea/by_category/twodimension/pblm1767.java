package swea.by_category.twodimension;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 문제 개요
 * 1. 2차원 배열
 * 2. 코어, 전선 겹치면 안됨 (전선은 직선으로만 설치 가능)
 * 3. 코어에 최대한 연결을 많이 해보고, 그때 최소 전선의 길이를 구해라.
 * 
 * 아이디어
 * 1. 배열을 받고나서 전선은 2 라고 생각을 하자.
 * 2. 코어를 하나씩 최소한의 길이인 일직선으로 연결 해본다. (그리디 가능?)
 * 3. 연결하는 순서를 정할 순 없다. 
 * 4. 완전탐색이 될것같다.
 * 문제 풀이
 * 1. 테스트케이스를 받는다.
 * 2. 맵의 크기를 받는다.
 * 3. 맵을 받는다.
 * 4. 조합을 전부 고려해본다.
 *  5. 많이씩 뽑아보면서 상, 하, 좌, 우 방향으로 전선을 만들 수 있는지 판단.
 *  5-1. 만들 수 있으면, 재귀로 다음 core 로 넘어간다. 
 */
public class pblm1767 {
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static int[][] map;

    public static void findLAN(int row, int col){
        //  5-1. 코어를 4방향으로 끝까지 탐색

    }
    public static void main(String[] args) throws IOException {
        // 초기화
        br = new BufferedReader(new InputStreamReader(System.in));
        
        //1. 테스트케이스를 받는다.
        st = new StringTokenizer(br.readLine().trim());
        int testCaseNum = Integer.parseInt(st.nextToken());

        for (int testCase = 1; testCase <= testCaseNum; testCase++) {
            // 2. 맵의 크기를 받는다.
            st = new StringTokenizer(br.readLine().trim());
            int mapSize = Integer.parseInt(st.nextToken());
            // 3. 맵을 받는다.
            map = new int[mapSize][mapSize];
            for (int row = 0; row < mapSize; row++) {
                st = new StringTokenizer(br.readLine().trim());
                for (int col = 0; col < mapSize; col++) {
                    map[row][col] = Integer.parseInt(st.nextToken());
                }
            }

            for (int row = 0; row < mapSize; row++) {
                for (int col = 0; col < mapSize; col++) {
                    if (map[row][col] == 1) {
                        findLAN(row,col);
                    }
                }
            }
        }
    }

}
