package base;

/**
 * <p>一维数组元素是基本类型，值传递</p>
 * <p>二维数组是元素是以为数组的一维数组属于引用传递</p>
 * <p>适合数组比较大的情况</p>
 * @author yjl
 * @since 2024/5/31
 */
public class ArrayCopy {
    public static void main(String[] args) {
        int[] a=new int[]{1,2,3,4,5};
        int[] b=new int[5];
        System.arraycopy(a,0,b,0,5);
        b[1]=5;
//        Integer[] a=new Integer[]{1,2,3,4,5};
//        Integer[] b=new Integer[5];
//        System.arraycopy(a,0,b,0,5);
//        b[1]=5;
        System.out.println(a[1]);
    }
}
