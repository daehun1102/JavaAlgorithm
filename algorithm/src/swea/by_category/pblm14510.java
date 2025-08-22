package swea.by_category;

import java.io.*;
import java.util.*;

/**
 * 문제 개요
 * 1. 홀수날은 1, 짝수날은 2의 물을 줄 수 있다. 
 * 2. 모든 나무를 키가 제일 큰 나무와 같은 높이로 만들어 보자.
 * 
 * 아이디어
 * 1. 높이 차 배열을 저장
 * 2. 남은 높이가 2일때 
 *  2-1. 현재 물주는게 1이라면
        2-1-1. 다음거 물 줄게 있다면 다음거 주기,
        2-1-2. 다음거 물 줄게 없다면 주지말고 한턴 쉬기
 *  2-2. 현재 물주는게 2라면 그냥 주면됨.
 *  
 * 
 * 풀이 순서
 */
public class pblm14510 {
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static int treeNum;
    static int testCaseNum;
    static int[] treeArray;
    static int[] diffArray;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine().trim());
        testCaseNum = Integer.parseInt(st.nextToken());
        
        
        
    }

    
}
