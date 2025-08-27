package swea.by_category.kruskal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;


/**
 * 문제개요
 * 1. 섬의 좌표, 세율이 주어지면 환경부담금을 계산할 수 있고,
 *  1-1. 환경부담금 = 세율 * 거리^2
 * 2. 총 환경 부담금을 최소로 지불하며, N 개의 모든 섬을 연결 할 수 있는 방법.
 *  
 * 아이디어
 * 1. 섬마다 엣지를 만들어서 (100만개)
 * 2. 100만개의 엣지를 가지고 정렬 후 유니온 파인드.
 * 
 * 문제풀이
 * 1. 테스트케이스 개수를 받는다.
 * 2. 섬의 개수가 주어지면,
 *  2-1. 섬을 리스트에 담는다.
 * 3. 엣지 리스트를 만든다. (x,y,weight)
 * 4. union find 하기
 * 
 */
public class pblm1251 {
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static int[] parents;
    static int nodeNum;

    static int[] nodeX;
    static int[] nodeY;

    static List<Edge> edgeList;

    static class  Edge implements Comparable<Edge>{
        int from,to;
        double weight;
        public Edge(int from, int to, double  weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
        
        @Override
        public int compareTo(Edge o) {
            // TODO Auto-generated method stub
            return Double.compare(this.weight, o.weight);
        }
    }

    private static void make(){
        for (int nodeIndex = 0; nodeIndex < nodeNum; nodeIndex++) {
            parents[nodeIndex] = nodeIndex;
        }
    }

    private static int find(int node){
        if (node == parents[node]) return node;
        
        return parents[node] = find(parents[node]);
    }

    private static boolean union(int node1, int node2){
        int root1 = find(node1);
        int root2 = find(node2);
        if (root1 == root2) return false;

        parents[root1] = root2; 
        return true;
    }

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        int testCaseNum = Integer.parseInt(br.readLine().trim());
        for (int testCase = 1; testCase <= testCaseNum; testCase++) {
            nodeNum = Integer.parseInt(br.readLine().trim());
            nodeX = new int[nodeNum+1];
            nodeY = new int[nodeNum+1];
            parents = new int[nodeNum+1];
            // x 좌표를 받는다
            st = new StringTokenizer(br.readLine().trim());
            for (int node = 1; node <= nodeNum; node++) {
                nodeX[node] = Integer.parseInt(st.nextToken());
            }

            // y 좌표를 받는다
            st = new StringTokenizer(br.readLine().trim());
            for (int node = 1; node <= nodeNum; node++) {
                nodeY[node] = Integer.parseInt(st.nextToken());
            }

            double ww = Double.parseDouble(br.readLine().trim());

            edgeList = new ArrayList<>(nodeNum+1);
            // 엣지를 등록하자.
            for (int node1 = 1; node1 <= nodeNum-1; node1++) {
                for (int node2 = node1+1; node2 <= nodeNum; node2++) {
                    int curForm = node1;
                    int curTo = node2;
                    // 거리^2 * ww
                    double distance = Math.sqrt(Math.pow(nodeX[curForm] - nodeX[curTo], 2) + Math.pow(nodeY[curForm] - nodeY[curTo], 2) );
                    double curWeight = ww * distance*distance;
                    Edge curEdge = new Edge(curForm, curTo, curWeight);
                    edgeList.add(curEdge);
                }
            }

            Collections.sort(edgeList);
            
            make();

            double totalWeight = 0;
            int mstEdgeCount = 0;
            for (Edge curEdge : edgeList) {
                // 되면
                if (union(curEdge.from, curEdge.to)) {
                    totalWeight += curEdge.weight;
                    mstEdgeCount++;
                    if (mstEdgeCount == nodeNum-1) {
                        break;
                    }
                }
            }

            long answer = (long) Math.round(totalWeight);

            sb = new StringBuilder();
            sb.append("#").append(testCase).append(' ').append(answer);
            System.out.println(sb);
        }

    }
}
