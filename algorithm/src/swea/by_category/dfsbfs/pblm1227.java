package swea.by_category.dfsbfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 문제개요
 * 1. 맵이주어지면
 * 2. 2에서 출발해서 3으로 도착할 수 있는지 가능 여부 판단.
 * 
 * 아이디어
 * 1. 한번만 찾으면 되서 dfs 로 하는게 좋을 것 같다.
 * 
 * 문제풀이
 * 1. 10개의 테스트케이스가 주어지고,
 * 2. 케이스마다 맵이 주어진다. ( toCharArray)
 * 3. 출발좌표와 도착좌표를 기록한다.
 * 4. dfs 를 구현한다. (in : 좌표, out : boolean )
 *  기저조건 : 3을 찾으면, return
 *  4-1. 4방향으로 탐색하면서,
 *  4-2. 맵밖으로 안나가고 방문 안한곳인,
 *  4-3. 0인 곳을 찾아 간다.
 */
public class pblm1227 {
  static BufferedReader br;
  static StringTokenizer st;
  static StringBuilder sb;

  static char[][] map;
  static boolean[][] visited;
  static int[] deltaRow = {0,0,1,-1};
  static int[] deltaCol = {1,-1,0,0};

  static int[] startIndex;
  static int[] endIndex;
  public static void main(String[] args) throws IOException {
    // 1. 10개의 테스트케이스가 주어지고,
    br = new BufferedReader(new InputStreamReader(System.in));
    int testCaseNum = 10;
    for (int testCase = 1; testCase <= testCaseNum; testCase++) {
      int testCaseNumber = Integer.parseInt(br.readLine().trim());
      // 2. 케이스마다 맵이 주어진다. ( toCharArray)
      map = new char[100][100];
      visited = new boolean[100][100];
      for (int row = 0; row < 100; row++) {
        map[row] = br.readLine().trim().toCharArray(); 
      }

      // 3. 출발좌표와 도착좌표를 기록한다.
      for (int row = 0; row < 100; row++) {
        for (int col = 0; col < 100; col++) {
          if (map[row][col] == '2') { startIndex = new int[]{row,col};}
          if (map[row][col] == '3') { endIndex = new int[]{row,col};}
        }
      }

      // 5. 시작좌표를 bfs 에 넣는다.
      int answer = 0;
      if (dfs(startIndex[0], startIndex[1])) answer = 1;

      sb = new StringBuilder();
      sb.append("#").append(testCaseNumber).append(" ").append(answer);
      System.out.println(sb);
    }
  }
  public static boolean dfs(int row, int col) {
    if(map[row][col] == '3' ) return true;
    
    visited[row][col] = true;

    for (int deltaIndex = 0; deltaIndex < 4; deltaIndex++) {
      int newRow = row + deltaRow[deltaIndex];
      int newCol = col + deltaCol[deltaIndex];
      if (newRow < 0 || newRow >= 100 || newCol < 0 || newCol >= 100) continue;
      if (map[newRow][newCol] == '1') continue;
      if (visited[newRow][newCol]) continue;
      if (dfs(newRow, newCol)) return true;
    }
    return false;
  }
}
