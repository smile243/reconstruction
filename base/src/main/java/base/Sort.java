package base;

/**
 * @Description: 
 * @author: scott
 * @date: 2021年06月29日 13:59
 */
public class Sort {
    public int[] maopao(int[] nums){
        int len=nums.length;
        for(int i=0;i<len;i++){
            for(int j=0;j<len-1-1;j++){
                if(nums[j]>nums[j+1])
                {
                    int temp=nums[j];
                    nums[j]=nums[j+1];
                    nums[j+1]=temp;
                }
            }
        }
        return nums;
    }

    public static void main(String[] args) {
        Sort s=new Sort();
        int[] nums={1,7,8,4,5,7,11};
        s.maopao(nums);
    }
}
