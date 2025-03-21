package base;

import java.util.Arrays;

/**
 * 二维数组排序
 * @author yjl
 * @since 2024/12/11
 */
public class TwoDimensionSortTest {
    public static void main(String[] args) {
        int [][] array=new int[][]{{1,2},{2,3},{3,4},{1,3}};
        Arrays.sort(array, (o1, o2) -> o1[0]-o2[0]==0?o1[1]-o2[1]:o1[0]-o2[0]);
        System.out.println(array.length);
    }
}
