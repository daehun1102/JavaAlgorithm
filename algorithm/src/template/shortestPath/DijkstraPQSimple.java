package template.shortestPath;

import java.util.*;

/**
 * 우선순위 큐를 이용한 다익스트라 (핵심만!)
 *
 * <문제 설정>
 * - 정점은 0..n-1
 * - 간선 가중치 >= 0 (음수 금지)
 * - 시작 정점 start에서 각 정점까지 최단거리 구하기
 *
 * <알고리즘 흐름(슬라이드 4단계와 1:1)>
 * 1) 우선순위 큐에 (거리=0, 정점=start) 추가
 * 2) 큐에서 "가장 거리가 작은" 원소를 뽑는다
 *    - 만약 뽑은 거리 > 현재 거리 테이블 값이면, 낡은 정보이므로 건너뛴다
 * 3) 뽑은 정점 u의 모든 이웃 v에 대해,
 *    - dist[u] + w(u→v)가 dist[v]보다 작으면 갱신하고
 *      (새 거리, 정점 v)를 우선순위 큐에 넣는다
 * 4) 큐가 빌 때까지 2)~3) 반복
 *
 * 시간복잡도: O((V+E) log V)
 */
public class DijkstraPQSimple {

    /** 간선: 목적지 정점(to)과 가중치(w) */
    static class Edge {
        int to, w;
        Edge(int to, int w) { this.to = to; this.w = w; }
    }

    /**
     * 다익스트라 핵심 함수
     * @param n    정점 개수(0..n-1)
     * @param g    인접리스트 g[u] = u에서 나가는 간선 목록
     * @param start 시작 정점
     * @return     start에서 각 정점까지 최단거리 배열 dist
     */
    static long[] dijkstra(int n, List<List<Edge>> g, int start) {
        final long INF = Long.MAX_VALUE / 4;

        // 거리 테이블: "지금까지 알려진" 최단거리
        long[] dist = new long[n];
        Arrays.fill(dist, INF);
        dist[start] = 0;

        // (거리, 정점) 쌍을 거리 오름차순으로 꺼내는 최소 힙
        PriorityQueue<long[]> pq = new PriorityQueue<>(Comparator.comparingLong(a -> a[0]));

        // 1) 시작 정점을 (거리 0)으로 큐에 넣기
        pq.offer(new long[]{0L, start}); // {현재까지의 거리, 정점}

        // 4) 큐가 빌 때까지 반복
        while (!pq.isEmpty()) {
            // 2) 가장 거리가 작은 원소 뽑기
            long[] cur = pq.poll();
            long curDist = cur[0];
            int u = (int) cur[1];

            // 이미 더 짧은 값으로 갱신된 적이 있다면(=낡은 정보) 건너뜀
            if (curDist != dist[u]) continue;

            // 3) 이웃 정점들 완화(relax)
            for (Edge e : g.get(u)) {
                int v = e.to;
                long nd = dist[u] + e.w; // u를 거쳐 v로 가는 후보 거리
                if (nd < dist[v]) {      // 더 짧으면 갱신!
                    dist[v] = nd;
                    pq.offer(new long[]{nd, v}); // 갱신된 v를 큐에 넣어 이후 전파
                }
            }
        }
        return dist;
    }

    /** 간선 추가 유틸: undirected=true면 양방향 */
    static void addEdge(List<List<Edge>> g, int u, int v, int w, boolean undirected) {
        g.get(u).add(new Edge(v, w));
        if (undirected) g.get(v).add(new Edge(u, w));
    }

    /** 간단 사용 예시 */
    public static void main(String[] args) {
        int n = 5; // 정점 0..4
        List<List<Edge>> g = new ArrayList<>();
        for (int i = 0; i < n; i++) g.add(new ArrayList<>());

        // 무향 그래프 예시
        addEdge(g, 0, 1, 2, true);
        addEdge(g, 0, 2, 5, true);
        addEdge(g, 1, 2, 1, true);
        addEdge(g, 1, 3, 3, true);
        addEdge(g, 2, 3, 2, true);
        addEdge(g, 3, 4, 1, true);

        long[] dist = dijkstra(n, g, 0);
        System.out.println("start=0 최단거리: " + Arrays.toString(dist));
        // 출력 예) [0, 2, 3, 5, 6]
    }
}
