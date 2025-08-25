package swea.by_category.unionFind;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 문제개요
 * 1. 유니온파인드를 구현하자
 * 2. 1일때 find 로 부모를 확인해서 부모 같다면 sb 에 1 넣기, 부모 다르면 0 넣기.
 * 
 * 아이디어
 * 1. 랭크와 경로압축도 같이하자. 
 * 
 * 문제풀이
 * 1. 테스트케이스를 개수를 받는다.
 * 2. 노드 수, 엣지수를 받는다.
 *  2-1. 노드 수 만큼 make, 
 * 3. 엣지 수만큼,
 *  3-1. 0이라면 union 을,
 *  3-2. 1이라면 find 를 호출한다.
 * 4. make 를 구현한다.
 * 5. union을 구현한다.
 * 6. find 를 구현한다.
 * 
 */
public class pblm3289 {
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static int testCaseNum;
    static int[] parentArray;
    static int[] rankArray;
    
    // 4. make 를 구현한다.
    static void make(int size) {
        parentArray = new int[size+1];
        rankArray = new int[size+1];
    
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

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        testCaseNum = Integer.parseInt(br.readLine().trim());
        for (int testCase = 1; testCase <= testCaseNum; testCase++) {
            sb = new StringBuilder();
            sb.append('#').append(testCase).append(' ');
            st = new StringTokenizer(br.readLine().trim());
            int nodeNum = Integer.parseInt(st.nextToken());
            int edgeNum = Integer.parseInt(st.nextToken());

            // make 로 초기화
            make(nodeNum);

            for (int edge = 0; edge < edgeNum; edge++) {
                st = new StringTokenizer(br.readLine().trim());
                int methodCode = Integer.parseInt(st.nextToken());
                int node1 = Integer.parseInt(st.nextToken());
                int node2 = Integer.parseInt(st.nextToken());
                
                // 3-1. 0이라면 union 을,
                // 3-2. 1이라면 find 를 호출한다.
                if(methodCode == 0) {
                    union(node1,node2);
                } else {
                    // rank 가 같은 집합이라면
                    if (find(node1) == find(node2)) {
                        sb.append(1);
                    } else {
                        sb.append(0);
                    }
                    
                }
            }
            System.out.println(sb);
        }   
    }
}
