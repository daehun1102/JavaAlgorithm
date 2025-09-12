package swea.by_category;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * 문제개요
 * 1. 하나의 줄기 세포, 가로, 세로 크기가 1인 정사각형
 * 2. 생명력 x이면 x 시간동안 비활성, x 시간 지나면 활성
 * 3. 활성 이후에는 X시간동안 살아있고 x시간이 지나면 세포 죽음.
 * 4. 이미 줄기세포가 존재하면 추가적으로 번식하지 않고,
 *  4-1. 하나의 그리드 셀에 동시 번식하려고 하는 경우 생명력 수치가 높은 줄기 세포가 혼자 차지
 * 5. K 시간 후 살아있는 줄기세포의 총개수를 구하는 프로그램 작성
 * 
 * 아이디어
 * 1. 세포는 클래스로 제작.
 * 2. 활성 비활성 처리, 확장 처리, 죽음 처리를 잘해야한다.
 *  2-1. 확장 : 다음단계로 넘어갈때 활성화되어있는 셀은 확장
 *  2-2. 활성화 : 생명력 시간 다됐으면 활성화
 *  2-3. 죽음 : 시간초과되면 죽음.
 * 
 * 문제풀이
 * 1. 테스트 케이스를 받고,
 * 2. 맵크기, 배양시간을 받자.
 * 3. 줄기세포는 다음과 같은 일을 한다.
 *  3-1. 매 시간마다 활성시간이 되면 번식을 한다. 생명력을 얻는다. 살아있고 죽어있음이 있다.
 *  3-2. 
 * 4. 번식시킬땐 생명력이 높은것부터 번식을 시킨다.
 * 
 */
public class pblm5653 {
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static int height, width, map[][], baeyangTime;
    static PriorityQueue<Cell> cellQueue;
    static Map<String,Boolean> cellMap;
    static int[] deltaRow = {0,0,1,-1};
    static int[] deltaCol = {1,-1,0,0};

    private static class Cell implements Comparable<Cell>{
        int row, col, alivePower, aliveTime; 
        boolean isActive, isDead;

        public Cell(int row, int col, int alivePower, int aliveTime, boolean isActive, boolean isDead) {
            this.row = row;
            this.col = col;
            this.alivePower = alivePower;
            this.isActive = isActive;
            this.isDead = isDead;
            this.aliveTime = aliveTime;
        }

        @Override
        public int compareTo(Cell c) {
            // 생명력으로 내림차순 정렬
            return -(this.alivePower - c.alivePower); 
        }
        
    }
    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        int testCaseNum = Integer.parseInt(br.readLine().trim());
        sb = new StringBuilder();
        for (int testCase = 1; testCase <= testCaseNum; testCase++) {
            st = new StringTokenizer(br.readLine().trim());
            
            height = Integer.parseInt(st.nextToken());
            width = Integer.parseInt(st.nextToken());
            baeyangTime = Integer.parseInt(st.nextToken());

            // 맵을 받자.
            map = new int[height][width];
            for (int row = 0; row < height; row++) {
                st = new StringTokenizer(br.readLine().trim());
                for (int col = 0; col < width; col++) {
                    map[row][col] = Integer.parseInt(st.nextToken());
                }
            }
            
            // 줄기리스트를 받고, 생명력을 기준으로 정렬을 하자.
            cellQueue = new PriorityQueue<Cell>();
            cellMap = new HashMap<String,Boolean>();
            for (int row = 0; row < height; row++) {
                for (int col = 0; col < width; col++) {
                    if (map[row][col] != 0) {
                        cellMap.put(new String(row + "," + col), true);
                        cellQueue.add(new Cell(row, col, map[row][col],0, false, false));
                    }
                }
            }

            // 
            int tmpTime = 0;
            while (tmpTime < baeyangTime) {
                // 한시간이 지났을때
                ++tmpTime;
                // 살아있는거 여기에 넣어서 나중에 갱신
                PriorityQueue<Cell> aliveQueue = new PriorityQueue<>();
                // 리스트를 돌면서,
                // System.out.println("cellQueue 사이즈: " + cellQueue.size());
                int tmpQueueSize = cellQueue.size();
                for (int cellIndex = 0; cellIndex < tmpQueueSize; cellIndex++) {
                    Cell cell = cellQueue.poll();
                    // 죽은건 신경 안써도 됨
                    if (cell.isDead) continue;
                    // 살아 있다면, aliveTime +1;
                    cell.aliveTime++;
                    // 확장처리 : 활성화된게 있다면,  
                    if (cell.isActive){
                        // 4방향을로 탐색해서
                        for (int deltaIndex = 0; deltaIndex < 4; deltaIndex++) {
                            int newRow = cell.row + deltaRow[deltaIndex];
                            int newCol = cell.col + deltaCol[deltaIndex];
                            // HashMap 에 새로운 좌표에 세포가 없을때만 생성 (그 좌표에 아무것도 없을때)
                            String newCoord = new String(newRow+ "," + newCol);
                            if (!cellMap.containsKey(newCoord)) {
                                aliveQueue.add(new Cell(newRow, newCol, cell.alivePower, 0, false, false));
                                cellMap.put(newCoord, true);
                            }
                        }

                        // 활성화 되는 순간에만 4방향 탐색
                        cell.isActive = false;
                    }
                    
                    // 활성화 처리 : 생명력보다 살아있는 시간이 크거나 같다면 isActive -> true
                    if (!cell.isActive && cell.aliveTime == cell.alivePower) cell.isActive = true;
                    // 죽음 처리 : 본인 생명력보다 살아있던 시간이 2배이상 크다면 제거
                    if (cell.alivePower*2 <= cell.aliveTime) cell.isDead = true; 
                    if (!cell.isDead) aliveQueue.add(cell);
                }
                cellQueue = aliveQueue;
            }

            // 살아있는것들 카운팅
            int count = 0;
            for (Cell cellInfo : cellQueue) {
                if (!cellInfo.isDead) {
                    // System.out.println(cellInfo.row + " " + cellInfo.col + " " + cellInfo.alivePower + " " + cellInfo.aliveTime);
                    count++;
                } 
                    
            }

            sb.append("#").append(testCase).append(" ").append(count).append('\n');
        }
        System.out.println(sb.toString());
    }
}
