package swea.by_category.ssafy_a;
import java.io.*;
import java.util.*;

public class pblm20505 {
    static int H, W;
    static int[][] map;
    static int[][] dist; // 각 좌표까지 도달하는 '최소 난이도'를 저장 (최대 점프 높이)
    static int sr, sc, er, ec;

    // 상, 하, 좌, 우
    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine().trim());
        for (int tc = 1; tc <= T; tc++) {
            st = new StringTokenizer(br.readLine());
            H = Integer.parseInt(st.nextToken());
            W = Integer.parseInt(st.nextToken());

            map = new int[H][W];
            dist = new int[H][W];
            for (int r = 0; r < H; r++) {
                Arrays.fill(dist[r], Integer.MAX_VALUE);
            }

            for (int r = 0; r < H; r++) {
                st = new StringTokenizer(br.readLine());
                for (int c = 0; c < W; c++) {
                    map[r][c] = Integer.parseInt(st.nextToken());
                    if (map[r][c] == 2) { sr = r; sc = c; }
                    if (map[r][c] == 3) { er = r; ec = c; }
                }
            }

            int ans = bfs();
            sb.append("#").append(tc).append(" ").append(ans).append("\n");
        }
        System.out.print(sb);
    }

    static int bfs() {
        Queue<int[]> q = new ArrayDeque<>();
        
        // 시작점의 난이도는 0
        dist[sr][sc] = 0;
        q.offer(new int[]{sr, sc});

        // 최종 정답 (도착지에 도달한 경로들의 난이도 중 최솟값)
        int minRankResult = Integer.MAX_VALUE;

        while (!q.isEmpty()) {
            int[] current = q.poll();
            int r = current[0];
            int c = current[1];
            int curRank = dist[r][c];

            // 🎯 도착지에 도달했다면, 최종 정답을 갱신
            if (r == er && c == ec) {
                minRankResult = Math.min(minRankResult, curRank);
                // 여기서 멈추지 않고 계속 탐색합니다.
                // 왜? 나중에 도착하는 다른 경로의 난이도가 더 낮을 수 있기 때문입니다.
                continue;
            }

            // ✂️ 가지치기(Pruning): 현재 경로의 난이도가 이미 찾은 답보다 나쁘다면 더 탐색할 필요가 없음
            if (curRank >= minRankResult) {
                continue;
            }

            // 4방향 탐색
            for (int d = 0; d < 4; d++) {
                int nr = r + dr[d];
                int nc = c + dc[d];

                // 맵 범위를 벗어나면 스킵
                if (nr < 0 || nc < 0 || nr >= H || nc >= W) {
                    continue;
                }

                int nextRank = curRank; // 다음 경로의 난이도는 일단 현재 난이도를 계승

                // ↕️ 상하 이동 (d: 0(상), 1(하))
                if (d < 2) {
                    int landingR = nr; // 착지 지점

                    // 착지 지점이 숲(0)이라면, 길이 나올 때까지 계속 점프
                    while (landingR >= 0 && landingR < H && map[landingR][nc] == 0) {
                        landingR += dr[d];
                    }

                    // 점프 결과가 맵 밖으로 나가면 해당 경로는 실패
                    if (landingR < 0 || landingR < 0 || landingR >= H) {
                        continue;
                    }
                    
                    int jumpHeight = Math.abs(r - landingR); // 이번 점프의 높이
                    // 다음 난이도는 (기존 최대 난이도)와 (이번 점프 높이) 중 더 큰 값
                    nextRank = Math.max(curRank, jumpHeight);
                    nr = landingR; // 실제 이동할 위치는 점프 후의 착지 지점
                }
                // ↔️ 좌우 이동 (d: 2(좌), 3(우))
                else {
                    // 숲(0)으로는 좌우 이동 불가
                    if (map[nr][nc] == 0) {
                        continue;
                    }
                    // 좌우 이동은 난이도가 변하지 않으므로, nextRank는 curRank 그대로 사용
                }

                // 갱신될 난이도(nextRank)가 기존에 기록된 최소 난이도보다 낮을 경우에만 큐에 추가
                if (nextRank < dist[nr][nc]) {
                    dist[nr][nc] = nextRank;
                    q.offer(new int[]{nr, nc});
                }
            }
        }
        
        // 도착한 적이 없다면 -1, 있다면 기록된 최소 난이도를 반환
        return minRankResult == Integer.MAX_VALUE ? -1 : minRankResult;
    }
}