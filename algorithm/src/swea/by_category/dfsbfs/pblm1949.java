package swea.by_category.dfsbfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 
 * @author daehun 
 * 문제 개요 
 * 1. 높이가 적힌 맵이 들어오면, 
 * 2. 규칙에 따라 만들 수 있는 등산로의 길이.
 * 
 * 아이디어 
 * 1. 가장 높은 봉우리마다 탐색을 하는데, 
 * 2. 가장 높은 봉우리를 선택할때마다 봉우리 하나를 깎아본다. 
 * 3. 깎은맵을 따라 dfs depth 를 기억해서, 끝을 도달했을때 ( 더이상 탐색할 곳이 없을때를 보고 탈출 ) 
 * 	3-1. 더 갈곳이 있다면 true, 더 갈곳이 없다면 false 반환 
 * 
 * 문제 풀이 
 * 1. 테스트 케이스 개수를 받는다. 
 * 2. 맵의 크기와 최대 공사 가능 깊이를 받는다. 
 * 3. 맵 배열을 받는다. 
 * 	3-1. 최대 봉우리는 인덱스를 기록 해둔다. 
 * 4. 최대봉우리를 시작으로 dfs 
 * 5. dfs 를 구현한다. 
 * 	5-1. 4방향으로 탐색하면서, 
 * 	5-2. 만약 탐색 불가능하다면 continue 
 * 	5-3. 탐색이 가능하다면 방문 체크하고 depth+1 하면서 다시탐색, 방문 해제.
 * 
 */
public class pblm1949 {
	static BufferedReader br;
	static StringTokenizer st;
	static StringBuilder sb;

	static int mapSize;
	static int maxDeep;
	static int testCaseNum;
	static int[][] map;
	static boolean[][] visited;

	static List<int[]> highestMountains;
	static int maxLen;

	static final int[] deltaRow = { 0, 0, 1, -1 };
	static final int[] deltaCol = { 1, -1, 0, 0 };

	public static void main(String[] args) throws IOException {
		// 1. 테스트 케이스 개수를 받는다.
		br = new BufferedReader(new InputStreamReader(System.in));
		st = new StringTokenizer(br.readLine().trim());
		testCaseNum = Integer.parseInt(st.nextToken());
		for (int testCase = 1; testCase <= testCaseNum; testCase++) {
			st = new StringTokenizer(br.readLine().trim());
			
			// 2. 맵의 크기와 최대 공사 가능 깊이를 받는다.
			mapSize = Integer.parseInt(st.nextToken());
			maxDeep = Integer.parseInt(st.nextToken());
			
			int maxValue = 0;
			map = new int[mapSize][mapSize];
			// 3. 맵 배열을 받는다
			for (int row = 0; row < mapSize; row++) {
				st = new StringTokenizer(br.readLine().trim());
				for (int col = 0; col < mapSize; col++) {
					int tmpHeight = Integer.parseInt(st.nextToken());
					map[row][col] = tmpHeight;
					maxValue = Math.max(maxValue, tmpHeight);
				}
			}
			
			highestMountains = new ArrayList<>();
			// 3-1. 최대 봉우리는 인덱스를 기록 해둔다.
			for (int row = 0; row < mapSize; row++) {
				st = new StringTokenizer(br.readLine().trim());
				for (int col = 0; col < mapSize; col++) {
					if (map[row][col] == maxValue) {
						highestMountains.add(new int[]{row,col});
					}
				}
			}
			
			maxLen = 0;
			//4. 최대봉우리를 시작으로 dfs
			for (int[] mountainIndex : highestMountains) {
				int mountainRow = mountainIndex[0];
				int mountainCol = mountainIndex[1];
				for (int row = 0; row < mapSize; row++) {
					for (int col = 0; col < mapSize; col++) {
						for (int diffHeight = 1; diffHeight <= maxDeep ; diffHeight++) {
							map[row][col] -= diffHeight;
							dfs(mountainRow, mountainCol, 1);
							map[row][col] += diffHeight;
						}
					}
				}
			}
			
			System.out.println("#" + testCase + " " + maxLen);
		}
	}

	public static boolean dfs(int row, int col, int depth) {
		for (int deltaIndex = 0; deltaIndex < 4; deltaIndex++) {
			int newRow = row + deltaRow[deltaIndex];
			int newCol = col + deltaCol[deltaIndex];
			if (newRow < 0 || newRow >= mapSize || newCol < 0 || newCol >= mapSize)
				continue;
			if (visited[newRow][newCol])
				continue;
			if (!dfs(newRow, newCol, depth + 1)) {
				maxLen = Math.max(maxLen, depth + 1);
				return true;
			}
			visited[newRow][newCol] = true;
			dfs(newRow, newCol, depth + 1);
			visited[newRow][newCol] = false;
		}
		return false;
	}
}
