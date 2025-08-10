package swea.by_category.twodimention;
/**
 * 1. 테스트케이스 개수를 받는다.
 * 2. 파리맵 배열의 크기와 파리채 크기가 주어진다.
 * 3. 파리맵이 주어지는데 누적합 배열로 받는다.
 *    3-1. 본인 위치 누적합 = 본인 위치 개수 + 위 누적합 + 아래 누적합 - 왼쪽위 대각선 누적합 
 * 4. 누적합 배열을 파리맵 크기 + 파리채 크기-1 까지 완전 탐색 순회한다. 
 *    4-1. 순회하면서 파리채로 잡는다. 현재 인덱스가 파리채 왼쪽 위, 현재인덱스+파리채 크기-1 한게 오른쪽 아래 
 *    4-2. 파리채 면적 = 전체 사각형 - 현재 인덱스 왼쪽 사각형 - 현재 인덱스 위쪽 사각형 + 대각선 위쪽 사각형 
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class pblm2001 {
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static int testCaseNum;
    static int[][] pariMap;
	static int answer;

    public static void main(String[] args) throws IOException{
        //  1. 테스트케이스 개수를 받는다.
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine().trim());
		sb = new StringBuilder();
        
        testCaseNum = Integer.parseInt(st.nextToken());
        for (int testCase = 1; testCase <= testCaseNum; testCase++) {
			// 2. 파리맵 배열의 크기와 파리채 크기가 주어진다.
        	st = new StringTokenizer(br.readLine().trim());
        	int pariMapSize = Integer.parseInt(st.nextToken());
			int pariChaeSize = Integer.parseInt(st.nextToken());

			// 3. 파리맵이 주어지는데 누적합 배열로 받는다.
			pariMap = new int[pariMapSize+1][pariMapSize+1];
			
			// 3-1. 본인 위치 파리 누적합 = 본인 위치 파리 개수 + 위 누적합 + 아래 누적합 - 왼쪽위 대각선 누적합 
			for (int row = 1; row <= pariMapSize; row++) {
				st = new StringTokenizer(br.readLine().trim());
				for (int col = 1; col <= pariMapSize; col++) {
					pariMap[row][col] = Integer.parseInt(st.nextToken()) + pariMap[row-1][col] + pariMap[row][col-1] - pariMap[row-1][col-1];
				}
			}
			answer = 0;
			// 4. 누적합 배열을 파리맵 크기 + 파리채 크기-1 까지 완전 탐색 순회한다. 
			for (int row = 1; row <= pariMapSize-pariChaeSize+1; row++) {
				for (int col = 1; col <= pariMapSize-pariChaeSize+1; col++){
					int endRow = row + pariChaeSize -1;
					int endCol = col + pariChaeSize -1;
					int tempSum = pariMap[endRow][endCol] - pariMap[row-1][endCol] - pariMap[endRow][col-1] + pariMap[row-1][col-1];
					if (tempSum > answer) answer = tempSum;
				}
			}
			sb.append('#').append(testCase).append(' ').append(answer).append('\n');
        }
		System.out.println(sb);
    }
}
