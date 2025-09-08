package swea.by_category;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;

/**
 * 아이디어
 * 1. 회전하는 메서드 - deque 구현 
 *  1-1. 끝에서 빼서 앞으로 넣어주기.
 * 2. 회전하는건 한변 길이만큼만 해주면 된다.
 * 3. 돌릴때마다, 숫자 만들어주기. - sb 활용
 *  2-1. 숫자 만들어서 set에 넣고,
 */
public class pblm5658 {
    static BufferedReader br;
    static StringBuilder sb;

    // 시계방향 한 칸 회전 메서드
    private static void rotate(Deque<Character> deque) {
        char last = deque.pollLast();   
        deque.offerFirst(last);        
    }

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        
        int testCaseNum = Integer.parseInt(br.readLine().trim());
        
        for (int testCase = 1; testCase <= testCaseNum; testCase++) {
            sb = new StringBuilder(); 
            StringTokenizer st = new StringTokenizer(br.readLine().trim());
            int totalNum = Integer.parseInt(st.nextToken()); 
            int targetIndex = Integer.parseInt(st.nextToken()); 
            String goldString = br.readLine().trim(); 

            int sideLength = totalNum / 4; 

            // 보물상자 숫자 Deque에 넣고
            Deque<Character> treasureBox = new ArrayDeque<>();
            for (char ch : goldString.toCharArray()) {
                treasureBox.offer(ch);
            }

            Set<Integer> numberSet = new HashSet<>();

            // 한변의 길이만큼 회전하면 모든 경우를 다 본 거 
            for (int r = 0; r < sideLength; r++) {
                // 현재 회전 상태에서 네 변의 숫자 추출
                
                Iterator<Character> it = treasureBox.iterator();
                for (int rect = 0; rect < 4; rect++) {
                    StringBuilder tempString = new StringBuilder();
                    // 만들어주고
                    for (int j = 0; j < sideLength; j++) {
                        tempString.append(it.next());
                    }

                    int decimalValue = Integer.parseInt(tempString.toString(), 16);
                    numberSet.add(decimalValue);
                }

                // 시계방향으로 한 칸 회전
                rotate(treasureBox);
            }

            // 내림차순 정렬
            List<Integer> sortedList = new ArrayList<>(numberSet);
            Collections.sort(sortedList, Collections.reverseOrder());
            int answer = sortedList.get(targetIndex - 1);

            sb.append("#").append(testCase).append(" ").append(answer);
            System.out.println(sb);
        }
        
    }
}
