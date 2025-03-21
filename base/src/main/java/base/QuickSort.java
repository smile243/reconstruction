package base;

import java.util.Arrays;

/**
 * @Description: 双边循环法快排
 * @author: yjl
 * @date: 2021/8/17
 */
public class QuickSort {
    public void quicksort(int[] nums, int start, int end) {
        if (start >= end)
            return;
        int pivot = sorted(nums, start, end);

        quicksort(nums, start, pivot - 1);
        quicksort(nums, pivot + 1, end);

    }

    public int sorted(int[] nums, int start, int end) {
        //基准,小于基准的上限;
        int pivot = nums[start];
        int mark = start;
        for (int i = start + 1; i <= end; i++) {
            if (nums[i] < pivot) {
                mark++;
                int temp = nums[i];
                nums[i] = nums[mark];
                nums[mark] = temp;
            }
        }
        nums[start] = nums[mark];
        nums[mark] = pivot;
        return mark;

    }

    public static void main(String[] args) {
        QuickSort q = new QuickSort();
        int[] a = {4, 6, 8, 2, 4, 5, 11, 7, 1, 3};
        q.quicksort(a, 0, a.length - 1);
        System.out.println(Arrays.toString(a));
    }

    //    public int sorted(int[] nums,int start,int end){
//        int left=start;
//        int right=end;
//        int pivot=nums[start];
//        while(left!=right){
//            while(left<right&&nums[right]>pivot){
//                right--;
//            }
//            while(left<right&&nums[left]<=pivot){
//                left++;
//            }
//            if(left<right){
//                int temp=nums[left];
//                nums[left]=nums[right];
//                nums[right]=temp;
//            }
//        }
//        nums[start]=nums[left];
//        nums[left]=pivot;
//        return left;
//    }
//    public int sorted(int[] nums,int start,int end){
//        //基准,小于基准的上限;
//        int pivot=nums[start];
//        int mark=start;
//        for(int i=start+1;i<=end;i++){
//            if(nums[i]<pivot){
//                mark++;
//                int temp=nums[mark];
//                nums[mark]=nums[i];
//                nums[i]=temp;
//            }
//        }
//        nums[start]=nums[mark];
//        nums[mark]=pivot;
//        return mark;
//    }
}
