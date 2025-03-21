package base;

import java.util.Arrays;

/** 计数排序
 * @author yjl
 * @since 2024/5/31
 */
public class CountSort {
    public static int[] countSort(int[] array){
        int max=array[0];
        int len=array.length;
        for(int i=1;i<len;i++){
            if(array[i]>max){
                max=array[i];
            }
        }
        int[] countArray=new int[max+1];
        for (int k : array) {
            countArray[k]++;
        }
        int index=0;
        int[] result=new int[len];
        for(int i=0;i<max+1;i++){
            for(int j=0;j<countArray[i];j++){
                result[index++]=i;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] array = new int[] {4,4,6,5,3,2,8,1,7,5,6,0,10};
        int[] sortedArray = countSort(array);
        System.out.println(Arrays.toString(sortedArray));
    }
}
