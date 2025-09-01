package swea.by_category.ssafy_a;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
 
/**
 * 1. dfs 메서드 (in : apIndex)
 *  1-1. 공유기 집합 원소 수 == 전체 개수일때 return
 *  1-2. 이번꺼 선택 o  
 *      1-2-1. 공유기 집합에 추가
 *      1-2-2. dfs(apIndex + 1 ) 
 *  1-3. 이번꺼 선택 x
 *      1-3-1. 공유기 집합에서 제거
 *      1-3-2. dfs(apIndex + 1 )
 */
public class pblm18690 {
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;
 
    static int mapSize, apRange;
    static List<Set<Integer>> portCoverList;
    static int[][] map;
    static List<int[]> ports, routers;
    static int minAp;
    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        int testCaseNum = Integer.parseInt(br.readLine().trim());
        for (int testCase = 1; testCase <= testCaseNum; testCase++) {
            st = new StringTokenizer(br.readLine().trim());
            mapSize = Integer.parseInt(st.nextToken());
            apRange = Integer.parseInt(st.nextToken());
             
            map = new int[mapSize][mapSize];
            minAp = Integer.MAX_VALUE;
            // ports 와 routers 들 추가를 해주고
            ports = new ArrayList<>();
            routers = new ArrayList<>();
            for (int row = 0; row < mapSize; row++) {
                st = new StringTokenizer(br.readLine().trim());
                for (int col = 0; col < mapSize; col++) {
                    int tmpInput = Integer.parseInt(st.nextToken());
                     
                    if (tmpInput >= 1 && tmpInput <= 3 ) routers.add(new int[]{row,col,tmpInput});
                    else if (tmpInput == 9) ports.add(new int[]{row,col});
                    map[row][col] = tmpInput;
                }
            }
             
 
            // ports 들 돌면서 연결 가능한 router 넣어주기
            portCoverList = new ArrayList<>();
            for (int portIndex = 0; portIndex < ports.size(); portIndex++) {
                Set<Integer> covered = new HashSet<>();
                for (int routerIndex = 0; routerIndex < routers.size(); routerIndex++) {
                    // 연결가능한거 넣어주기
                    if (routers.get(routerIndex)[2] + apRange >= getDistance(portIndex, routerIndex)){
                        covered.add(routerIndex);
                    }
                }
                portCoverList.add(covered);
            }
             
            dfs(0,0,new HashSet<>());
            if (minAp == Integer.MAX_VALUE) {
                minAp = -1;
            }
 
            sb = new StringBuilder();
            sb.append('#').append(testCase).append(' ').append(minAp);
            System.out.println(sb);
        }
    }
 
    public static int getDistance(int portIndex, int routerIndex) {
        return (int) Math.abs(ports.get(portIndex)[0] - routers.get(routerIndex)[0]) + Math.abs(ports.get(portIndex)[1] - routers.get(routerIndex)[1]);
    }
 
    public static void dfs(int portIndex, int count, Set<Integer> accCovered) {
        if (count >= minAp || count>5) return;
        if (accCovered.size() == routers.size()){
            minAp = Math.min(minAp,count);
            return;
        } // 만약에 커버가능한 공유기를 다 선택했다면 그만
        if (portIndex == ports.size()) return;
     
         
        Set<Integer> newCovered = new HashSet<>(accCovered);
        // 선택 안한다
        dfs(portIndex+1, count, newCovered);
         
        // 선택 한다.
        newCovered.addAll(portCoverList.get(portIndex));
        dfs(portIndex+1, count +1, newCovered);
        newCovered.removeAll(portCoverList.get(portIndex));
    }
}
