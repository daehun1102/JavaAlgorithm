package boj.by_category.permutation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 1. 원소개수를 받는다.
 * 2. 숫자를 받는다.
 * 3. 다음 순열을 구한다.
 * 4. 꼭대기 index 를 찾는다.
 * 5. 내림차순을 깬 위치를 찾는다.
 * 6. broken 값보다 크면서 그 중에서 가장 작은 값 을 찾는다.
 * 7. 두개 swap
 * 8. 꼭대기부터 끝까지 오름차순 정렬
 */
public class pblm10972 {
    static int ELEMENT_COUNT;
    static int[] elementArray;
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;
    
    public static void swap(int leftIndex, int rightIndex) {
		int temp = elementArray[leftIndex];
		elementArray[leftIndex] = elementArray[rightIndex];
		elementArray[rightIndex] = temp;
	}

    public static boolean getNext() {
        int head = 0;
        int tail = ELEMENT_COUNT -1;
        int highestElementIndex = tail;

        // 뒤에서부터 꼭대기를 찾는다. 
        while (
            highestElementIndex > 0 &&
            elementArray[highestElementIndex-1] >= elementArray[highestElementIndex]
        ) {
            highestElementIndex--;
        }
        // 꼭대기가 맨앞이라면 다음 순열은 없다.
        if (highestElementIndex == head) {
            return false;
        }


        // 내림차순을 깬 인덱스 (= 꼭대기 바로앞 인덱스)
        int brokenNaelimIndex = highestElementIndex - 1;
        int nextLargerThanBrokenIdx = tail;

        // 내림차순을 깬 요소 (= 꼭대기 바로앞 인덱스) 보다 큰 값을 뒷 배열에서 찾는다. 꼭대기가 있기 때문에 무조건 찾는다.
        while (elementArray[brokenNaelimIndex] >= elementArray[nextLargerThanBrokenIdx] ) {
            nextLargerThanBrokenIdx--;
        }
        // 둘을 바꾼다.
        swap(brokenNaelimIndex, nextLargerThanBrokenIdx);

        // 꼭대기 뒷 배열을 오름차순으로 변경시킨다. 이미 내림차순이기 때문에 아래 로직이 작동한다.
        int fairIndex = tail;
        while (highestElementIndex < fairIndex) {
			swap(highestElementIndex, fairIndex);
			highestElementIndex++;
			fairIndex--;
		}
        return true;
    }
    
    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        sb = new StringBuilder();
        
        ELEMENT_COUNT = Integer.parseInt(br.readLine().trim());
        elementArray = new int[ELEMENT_COUNT];

        st = new StringTokenizer(br.readLine()); 
        for (int elementIndex = 0; elementIndex < ELEMENT_COUNT; elementIndex++) {
            int tmpElement = Integer.parseInt(st.nextToken());
            elementArray[elementIndex] = tmpElement;
        }
        if (getNext()) {
            for (int answerIndex = 0; answerIndex < ELEMENT_COUNT; answerIndex++) {
                sb.append(elementArray[answerIndex]).append(" ");
            }
            System.out.println(sb);
        } else {
            System.out.println(-1);
        }
        
    }
}
