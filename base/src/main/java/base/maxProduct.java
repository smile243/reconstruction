package base;

/**
 * @Description: 
 * @author: scott
 * @date: 2021年06月29日 10:19
 */
public class maxProduct {
    public int maxProduct(int[] nums) {
        int max = Integer.MIN_VALUE, imax = 1, imin = 1;
        for (int num : nums) {
            if (num < 0) {
                int tmp = imax;
                imax = imin;
                imin = tmp;
            }
            imax = Math.max(imax * num, num);
            imin = Math.min(imin * num, num);

            max = Math.max(max, imax);
        }
        return max;
    }

    public static void main(String[] args) {
        maxProduct m=new maxProduct();
        int[] nums={-2,0,-1};
        m.maxProduct(nums);
    }
}
