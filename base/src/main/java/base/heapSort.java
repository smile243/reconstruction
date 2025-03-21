package base;

import java.util.Arrays;

/**
 * 堆排序,堆是完全二叉树
 *  大顶堆 arr[i]>=arr[2i+1]&&arr[i]>=arr[2i+2]
 *  小顶堆 arr[i]<=arr[2i+1]&&arr[i]<=arr[2i+2]
 *  升序采用大顶堆，降序采用小顶堆
 *  每次上浮或者下沉只能保证单路径的父>子，所以一开始要从非叶子节点循环去构建一棵相对父>子的堆，注意第一阶段和第二阶段的adjust的入参
 */
public class heapSort {

    public static void main(String[] args) {
        int[] arr = new int[] {1,3,2,6,5,7,8,9,10,0};
        heapSort(arr);
        System.out.println(Arrays.toString(arr));
    }
    /**
     * 堆排序（降序）
     * 实现步骤：
     * 1. 构建最大堆(这里只不过粗略保证了父>子，不保证左子>右子) -> 2. 循环交换堆顶与末尾元素 -> 3. 调整新堆
     */
    public static void heapSort(int[] array) {
        // 第一阶段：构建最大堆（从最后一个非叶子节点开始调整）,该阶段结束能够保证相对的父>子
        for (int i = array.length/2-1; i >= 0; i--) {
            upAdjust(array, i, array.length);
        }
        System.out.println(Arrays.toString(array));
        // 第二阶段：排序核心逻辑（每次把当前最大值放到数组末尾）
        for (int i = array.length - 1; i > 0; i--) {
            // 每次循环将堆顶元素放到当前末尾
            int temp = array[i];
            array[i] = array[0];
            array[0] = temp;
            // 继续对剩下的(除了已经确定的堆节点)进行下沉
            upAdjust(array, 0, i);
        }
    }

    /**
     * 大顶堆下沉调整（核心方法）
     * 工作流程：
     * 1. 保存父节点值 -> 2. 寻找最大子节点 -> 3. 比较决定是否下沉 -> 4. 循环直到合适位置
     */
     public static void upAdjust(int[] array, int parentIndex, int length) {
         // temp 保存父节点值，用于最后的赋值
         int temp = array[parentIndex];
         int childIndex = 2 * parentIndex + 1;
         while (childIndex < length) {
            // 如果有右孩子，且右孩子大于于左孩子的值，则定位到右孩子
            if (childIndex + 1 < length && array[childIndex + 1] > array[childIndex]) {
                childIndex++;
            }
            // 如果父节点大于任何一个孩子的值，则直接跳出
            if (temp >= array[childIndex])
                break;
            //无须真正交换，单向赋值即可
            array[parentIndex] = array[childIndex];
            parentIndex = childIndex;
            childIndex = 2 * childIndex + 1;
         }
         array[parentIndex] = temp;
     }


    public static void downAdjust(int[] array, int parentIndex, int length) {
        // 暂存需要下沉的值
        int temp = array[parentIndex];
        // 计算左子节点位置（完全二叉树特性）
        int childIndex = 2 * parentIndex + 1;

        // 当子节点在有效范围内时循环
        while (childIndex < length) {
            // 找出左右子节点中的较小者
            if (childIndex + 1 < length && array[childIndex + 1] < array[childIndex]) {
                childIndex++; // 切换到右子节点
            }

            // 终止条件：父节点值 <= 子节点值
            if (temp <= array[childIndex])
                break;

            // 执行下沉：将子节点值提升到父节点位置
            array[parentIndex] = array[childIndex];
            // 更新索引继续向下比较
            parentIndex = childIndex;
            // 计算新的左子节点位置
            childIndex = 2 * childIndex + 1;
        }
        // 最终定位插入位置
        array[parentIndex] = temp;
    }


}
