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
 * 아이디어
 * 부분집합으로 AP 선택할때마다 연결 가능한 공유기 추가하고
 * 모든 공유기가 연결되면 return -> AP 선택한 개수 세기 
 */
public class pblm18680 {
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;
    static int[][] map;
    static int mapSize;
    static int apRange;

    static int minAp;
    static List<int[]> ports, routers;
    static List<Set<Integer>> portCoverList;
    public static void main(String[] args) throws IOException{
        br = new BufferedReader(new InputStreamReader(System.in));
        int testCaseNum = Integer.parseInt(br.readLine().trim());

        for (int testCase = 1; testCase <= testCaseNum; testCase++) {
            st = new StringTokenizer(br.readLine().trim());
            mapSize = Integer.parseInt(st.nextToken());
            apRange = Integer.parseInt(st.nextToken());
            minAp = Integer.MAX_VALUE;
            map = new int[mapSize][mapSize];
            for (int row = 0; row < mapSize; row++) {
                st = new StringTokenizer(br.readLine());
                for (int col = 0; col < mapSize; col++) {
                    map[row][col] = Integer.parseInt(st.nextToken());
                }
            }

            // 포트랑 공유기 기록하기
            ports = new ArrayList<>(); 
            routers = new ArrayList<>();
            
            for (int row = 0; row < mapSize; row++) {
                for (int col = 0; col < mapSize; col++) {
                    int tmpInput = map[row][col]; 
                    if (tmpInput == 9) {
                        ports.add(new int[]{row,col});
                    } else if (tmpInput >=1 && tmpInput<=3) {
                        // 공유기
                        routers.add(new int[]{row,col,map[row][col]});
                    }
                }
            }

            // 포트에서 연결가능한 공유기 기록
            portCoverList = new ArrayList<>();
            for (int portIndex = 0; portIndex < ports.size(); portIndex++) {
                Set<Integer> covered = new HashSet<>();
                for (int routerIndex = 0; routerIndex < routers.size(); routerIndex++) {
                    // 공유기, 포트 거리보다 연결세기들 합한게 크거나 같을때 연결가능 
                    if (routers.get(routerIndex)[2] + apRange >= Math.abs(ports.get(portIndex)[0] - routers.get(routerIndex)[0]) +
                                                                Math.abs(ports.get(portIndex)[1] - routers.get(routerIndex)[1])) {
                                                                    covered.add(routerIndex);
                                                                }
                }
                portCoverList.add(covered);
            }

            dfs(0,0,new HashSet<>());
            sb = new StringBuilder();
            sb.append('#').append(testCase).append(' ').append(minAp);
            System.out.println(sb);
        }
    }

    public static void dfs (int portIndex, int count, Set<Integer> coverd){
        if (count >= minAp || count > 5) return;

        // 만약에 공유기를 다 커버했다면
        if (coverd.size() == routers.size()) {
            minAp = Math.min(minAp,count);
            return;
        }

        if (portIndex == ports.size()) return;

        dfs(portIndex+1, count, coverd);

        coverd.addAll(portCoverList.get(portIndex));
        dfs(portIndex+1, count+1, coverd);
        coverd.removeAll(portCoverList.get(portIndex));
    }
}
