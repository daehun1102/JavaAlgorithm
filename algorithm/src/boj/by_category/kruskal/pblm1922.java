package boj.by_category.kruskal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 문제개요
 * 1. 컴퓨터가 들어오고,
 * 2. 간선들이 주어지면 최소비용으로 모든 컴퓨터를 연결하자.
 * 
 * 아이디어
 * 1. 크루스칼
 * 
 * 문제풀이
 * 1. 컴퓨터수를 받는다. 
 * 2. 연결할 수 있는 선의 수를 받는다.
 * 3. 선들을 받고, 정렬한다.
 * 4. make, find, union 구현
 * 5. 연결 선마다 union 되는지 확인하고,
 *  5-1. 된다면 가중치 ++;
 *  5-2. 컴퓨터수++;
 *  5-3. 컴퓨터가 다 연결되었다면 ( 간선 수 == 컴퓨터 수-1)
 */
public class pblm1922 {
    static BufferedReader br;
    static StringTokenizer st;
    
    static int nodeNum;
    static int edgeNum;
    static List<Edge> edgeList;
    
    static int[] parents;
    static class Edge implements Comparable<Edge>{
        int from, to, weight;

        public Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
        
        @Override
        public int compareTo(Edge o) {
            return Integer.compare(this.weight, o.weight);
        }
    }

    // 4. make, find, union 구현
    public static void make() {
        for (int index = 0; index < nodeNum; index++) {
            parents[index] = index;
        }
    } 

    public static int find(int node) {
        if (parents[node] == node) return node;
        return parents[node] = find(parents[node]);
    } 
    
    public static boolean union(int node1, int node2) {
        int root1 = find(node1);
        int root2 = find(node2);
        
        // 만약 부모가 같다면,
        if (root1 == root2) return false;
        // 부모가 다르다면
        parents[root1] = root2;
        return  true; 
    } 
    
    public static void main(String[] args) throws  IOException{
        // 1. 컴퓨터수를 받는다.
        br = new BufferedReader(new InputStreamReader(System.in));
        nodeNum = Integer.parseInt(br.readLine().trim());
        // 2. 연결할 수 있는 선의 수를 받는다.
        edgeNum = Integer.parseInt(br.readLine().trim());
        edgeList = new ArrayList<>(nodeNum+1);
        parents = new int[nodeNum+1];
        // 3. 선들을 받고, 정렬한다.
        for (int edge = 0; edge < edgeNum; edge++) {
            st = new StringTokenizer(br.readLine().trim());
            int curFrom = Integer.parseInt(st.nextToken());
            int curTo = Integer.parseInt(st.nextToken());
            int curWeight = Integer.parseInt(st.nextToken());
            Edge curEdge = new Edge(curFrom, curTo, curWeight);
            edgeList.add(curEdge);
        }
        
        Collections.sort(edgeList);
        int totalWeight = 0;
        int mstComputer = 0;
        
        // 초기화
        make();

        // 5. 연결 선마다 union 되는지 확인하고,
        for (Edge curEdge : edgeList) {
            if (union(curEdge.from, curEdge.to)){
                // 5-1. 된다면 가중치 ++;
                totalWeight += curEdge.weight;
                // 5-2. 컴퓨터 수 ++;
                mstComputer++;
                if (mstComputer == nodeNum-1) break; 
            }
        }

        System.out.println(totalWeight);
    }
}
