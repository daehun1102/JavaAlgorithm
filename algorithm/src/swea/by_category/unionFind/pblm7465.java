package swea.by_category.unionFind;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
/**
 * 문제풀이
 * 1. 그룹개수 찾기
 * 
 * 아이디어
 * 1. 유니온 파인드로 서로소 그룹만들고
 * 2. find 로 대표자 개수 찾자.
 * 
 * 문제풀이.
 * 1. 테스트 케이스 개수를 받는다.
 * 2. 노드, 엣지 개수를 받는다.
 * 3. 모든 관계에 대해 합집합을 만든다.
 * 4. make 를 구현한다.
 * 5. union을 구현한다.
 * 6. find 를 구현한다.
 * 7. 대표자 개수를 찾자.
 */
public class pblm7465 {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static int testCaseNum;
    static int[] parentArray;
    static int[] rankArray;

    static boolean[] repArray;
    // 4. make 를 구현한다.
    static void make(int size) {
        parentArray = new int[size+1];
        rankArray = new int[size+1];
        repArray = new boolean[size+1];
    
        for (int index = 1; index <= size; index++) {
            parentArray[index] = index;
            rankArray[index] = 0;
        }
    }

    
    // 5. union을 구현한다.
    static  void union(int node1, int node2) {
        // 루트 2개를 찾아서,
        int root1 = find(node1);
        int root2 = find(node2);
        // 루트 2개가 같다면, union 불가능 하므로 return
        if (root1 == root2) return;
        
        // 루트 2개가 다르다면,
        // 랭크를 확인하고
        if (rankArray[root1] > rankArray[root2]) {
            parentArray[root2] = root1;
        } else {
            if (rankArray[root1] == rankArray[root2]) {
                rankArray[root2]++;
            }
            parentArray[root2] = root1;
        }
    }
    
    // 6. find 를 구현한다.
    static int  find(int node) {
        // 루트를 찾았을때
        if (parentArray[node] == node) return node;
        
        // 루트를 못찾았을때
        // 경로을 압축한다.
        parentArray[node] = find(parentArray[node]);
        return parentArray[node];
    }

    public static void main(String[] args) throws IOException{
        br = new BufferedReader(new InputStreamReader(System.in));
        testCaseNum = Integer.parseInt(br.readLine().trim());
        // 1. 테스트 케이스 개수를 받는다.
        for (int testCase = 1; testCase <= testCaseNum; testCase++) {
            sb = new StringBuilder();
            sb.append('#').append(testCase).append(' ');
            st = new StringTokenizer(br.readLine().trim());
            
            // 2. 노드, 엣지 개수를 받는다.
            int nodeNum = Integer.parseInt(st.nextToken());
            int edgeNum = Integer.parseInt(st.nextToken());
            
            // make 로 초기화
            make(nodeNum);
            
            // 3. 모든 관계에 대해 합집합을 만든다.
            for (int edge = 0; edge < edgeNum; edge++) {
                st = new StringTokenizer(br.readLine().trim());
                int node1 = Integer.parseInt(st.nextToken());
                int node2 = Integer.parseInt(st.nextToken());
                union(node1, node2);
            }
            int answer = 0;
            for (int node = 1; node <= nodeNum; node++) {
                repArray[find(node)] = true;
            }
            for (boolean isRep : repArray) {
                if (isRep) answer++;
            }
            // 7. 대표자 개수를 찾자.
            sb.append(answer);
            System.out.println(sb);
        }
    }

}
