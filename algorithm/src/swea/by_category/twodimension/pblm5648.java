package swea.by_category.twodimension;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * 원자 소멸 시뮬레이션
 * 
 * 문제 개요
 * 1. 원자가 움직이면서 충돌하면 에너지가 방출되고 소멸된다.
 * 2. 시뮬레이션문제
 * 
 * 아이디어
 * 1. 1초마다 방향따라 움직이면 되는데
 * 2. .5초에 부딪히는 경우가 있으니까 좌표 2배해서 1씩 움직이면 0.5 초 간격처럼 보임
 */
public class pblm5648 {
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    
    static class MyElement {
        int row, col, dir, energy;
        public MyElement (int col, int row, int dir, int energy){
            this.row = row;
            this.col = col;
            this.dir = dir;
            this.energy = energy;
        }
    }
    static List<MyElement> myElements;
    
    static int[] deltaRow = {1,-1,0,0}; // 상하좌우 
    static int[] deltaCol = {0,0,-1,1};
    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        int testCaseNum = Integer.parseInt(br.readLine().trim());
        for (int testCase = 1; testCase <= testCaseNum; testCase++) {
            int elementNum = Integer.parseInt(br.readLine().trim());
            myElements = new ArrayList<>();
            for (int elementIndex = 0; elementIndex < elementNum; elementIndex++) {
                st = new StringTokenizer(br.readLine().trim());
                
                MyElement myElement = new MyElement(Integer.parseInt(st.nextToken())*2, 
                                                    Integer.parseInt(st.nextToken())*2, 
                                                    Integer.parseInt(st.nextToken()),
                                                    Integer.parseInt(st.nextToken()));

                myElements.add(myElement);
            }

            int answer = simulate();
            sb = new StringBuilder();
            sb.append("#").append(testCase).append(" ").append(answer);
            System.out.println(sb);
        }
    }

    static int simulate(){
        int totalEnergy = 0;
        while (!myElements.isEmpty()) {
            Map<Integer, List<MyElement>> map = new HashMap<>();

            // 모든 원자 이동 ㄱㄱ
            for (int elementIndex = 0; elementIndex < myElements.size(); elementIndex++) {
                MyElement myElement = myElements.get(elementIndex);

                myElement.row += deltaRow[myElement.dir];
                myElement.col += deltaCol[myElement.dir];
            
                if (myElement.row < -2000 || myElement.row > 2000 || myElement.col < -2000 || myElement.col > 2000 ) continue;

                // map 에 등록
                int key = (myElement.row + 2000) * 4001 + (myElement.col + 2000);
                if (!map.containsKey(key)) {
                    map.put(key, new ArrayList<>());
                }
                map.get(key).add(myElement);
            }
            
            // 충돌 처리
            List<MyElement> survivors = new ArrayList<MyElement>();
            for (Map.Entry<Integer, List<MyElement>> entry : map.entrySet()) {
                List<MyElement> list = entry.getValue();
                if (list.size() == 1) {
                    survivors.add(list.get(0)); 
                } else {
                    for (int i = 0; i < list.size(); i++) {
                        totalEnergy += list.get(i).energy;
                    }
                }
            }
            myElements = survivors;
        }
        return totalEnergy;
    }
}
