package swea.by_category.graph;
/**
 * 문제 개요
 * 1. 방향 그래프가 들어오면,
 * 2. 시작노드에서 시작해서 마지막 연락망중 가장 큰번호를 찾자.
 * 
 * 아이디어
 * 1. N 100 이하여서 인접배열로 해도 될듯
 * 2. BFS 하는데 뎁스도 기록해서 queue 관리
 * 
 * 문제풀이.
 * 
 * 1. 테스트케이스는 10개
 * 2. 노드개수, 시작노드가 주어진다. 인접배열 초기화, 최대 번호 저장하는 뎁스 배열 초기화
 * 3. 인접배열에 기록 row : 진출  col : 진입
 * 3. bfs 구현
 *  3-1. ( 시작노드, 뎁스 0 )에서 시작해서 ( 연결된 노드, 뎁스 ) 큐에 넣기
 *  3-2. 인덱스 (뎁스 ) 마다 최대 번호 숫자를 저장하는 배열에 최대 번호인지 확인하고 저장
 * 4. 뎁스 배열 돌다가 0만나면 멈추고 정답 도출 
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class pblm1238 {
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static int testCaseNum = 10;
    static int inputNum;
    
    static boolean[][] nodeGraph;
    static boolean[] visited;
    static int[] depthArray;
    
    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        for (int testCase = 1; testCase <= testCaseNum; testCase++) {
            st = new StringTokenizer(br.readLine().trim());
            inputNum = Integer.parseInt(st.nextToken());
            int start = Integer.parseInt(st.nextToken());
            
            nodeGraph = new boolean[100+1][100+1];
            visited = new boolean[100+1];
            depthArray = new int[100];
            // 3. 인접배열에 기록 row : 진출  col : 진입
            st = new StringTokenizer(br.readLine().trim());
            for (int index = 0; index < inputNum/2 ; index++) {
                int row = Integer.parseInt(st.nextToken());
                int col = Integer.parseInt(st.nextToken());
                nodeGraph[row][col] = true;
            }

            depthArray[0] = start;
            bfs(start,0);
            // System.out.println(Arrays.deepToString(nodeGraph));
            // System.out.println(Arrays.toString(depthArray));
            int answer = 0;
            for (int depthIndex = 0; depthIndex < depthArray.length; depthIndex++) {
                if (depthArray[depthIndex] == 0) break;
                else answer = depthArray[depthIndex];
            }

            sb = new StringBuilder();
            sb.append("#").append(testCase).append(" ").append(answer);
            System.out.println(sb);
        }
    }

    public static void bfs(int node, int depth) {
        // 3-1. ( 시작노드, 뎁스 0 )에서 시작해서 ( 연결된 노드, 뎁스 ) 큐에 넣기
        Deque<int[]> queue = new ArrayDeque<int[]>();
        queue.offer(new int[]{node,depth});
        visited[node] = true;

        // 3-2. 인덱스 (뎁스 ) 마다 최대 번호 숫자를 저장하는 배열에 최대 번호인지 확인하고 저장
        while (!queue.isEmpty()) {
            int[] nodeInfo= queue.poll(); // 노드번호, 뎁스
            int curNodeNumber = nodeInfo[0];
            int curDepth = nodeInfo[1];

            for (int goNodeIndex = 1; goNodeIndex <= 100; goNodeIndex++) {
                // 진출노드에 연결되어있는 진입노드를 찾아서,
                boolean goNode = nodeGraph[curNodeNumber][goNodeIndex];
                if (goNode){
                    // 진입하려는 노드가 방문 안했던거라면
                    if (!visited[goNodeIndex])  {
                        // 진입하려는 노드를 큐에 넣자
                        visited[goNodeIndex] = true;
                        queue.offer(new int[]{goNodeIndex,nodeInfo[1]+1});
                        // queue 에 있던거 가져와서 뎁스배열에 있는 최대값과 비교하기 
                        depthArray[curDepth+1] = Math.max(goNodeIndex, depthArray[curDepth+1]);
                    }
                }
            }
        }
    }
}
