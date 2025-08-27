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
 * 크루스칼로 풀어보자
 * 
 * 아이디어
 * 크루스칼로 풀어보자. 경로압축, 랭크
 * 
 * 문제풀이
 * 1. 테스트케이스 개수를 받고,
 * 2. 정점과 간선 개수를 받는다.
 * 3. make, union, find 를 구현한다.
 * 4. 간선 리스트 만들어서, 정렬한다.
 * 5. union 이 된다면,
 *  5-1. node cnt ++
 *  5-2. 가중치 ++
 *  5-3. cnt 개수 = 전체 노드 -1 이라면 중단.
 * 
 */
public class pblm3124 {
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static int[] parents;
    static int nodeNum;
    static int edgeNum;

    static List<Edge> edgeList;

    public static class Edge implements Comparable<Edge> {
        int from,to,weight;

        public Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
        
        @Override
        public int compareTo(Edge o) {

            return this.weight - o.weight;
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
            st = new StringTokenizer(br.readLine().trim());
            nodeNum = Integer.parseInt(st.nextToken());
            edgeNum = Integer.parseInt(st.nextToken());
            edgeList = new ArrayList<>(edgeNum);
            parents = new int[nodeNum+1];

            for (int edge = 0; edge < edgeNum; edge++) {
                st = new StringTokenizer(br.readLine().trim());
                int curForm = Integer.parseInt(st.nextToken());
                int curTo = Integer.parseInt(st.nextToken());
                int curWeight = Integer.parseInt(st.nextToken());
                Edge curEdge = new Edge(curForm, curTo, curWeight);
                edgeList.add(curEdge);
            }

            make();
            Collections.sort(edgeList);

            long totalWeight = 0;
            int mstEdgeCount = 0;
            for (Edge curEdge : edgeList) {
                if (union(curEdge.from, curEdge.to)) {
                    totalWeight += curEdge.weight;
                    mstEdgeCount++;
                    if(mstEdgeCount == nodeNum-1) break;
                }
            }

            sb = new StringBuilder();
            sb.append('#').append(testCase).append(' ').append(totalWeight);
            System.out.println(sb);

        }
    }
}
