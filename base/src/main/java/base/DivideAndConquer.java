package base;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @Description:
 * @author: scott
 * @date: 2021年04月23日 15:01
 */
public class DivideAndConquer {
    public static class ListNode {
        int val;
        public ListNode next;

        ListNode() {
        }

        public ListNode(int val) {
            this.val = val;
        }

        public ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    /**
     * 归并排序
     * @param lists
     * @param start
     * @param end
     * @return
     */
    public ListNode mergeKListsV1(ListNode[] lists, int start, int end) {
        if (start == end) {
            return lists[start];
        } else if (start > end) {
            return null;
        }
        int mid = (start + end) >> 1;
        return mergeTwoList(mergeKListsV1(lists, start, mid), mergeKListsV1(lists, mid + 1, end));
    }

    /**
     * 优先队列
     *
     * @param lists
     * @return
     */
    public ListNode mergeKListsV2(ListNode[] lists) {
        ListNode result = new ListNode();
        if (null == lists || lists.length == 0) {
            return result.next;
        }
        PriorityQueue<ListNode> queue = new PriorityQueue<>(3, new Comparator<ListNode>() {

            @Override
            public int compare(ListNode o1, ListNode o2) {
                if (o1.val < o2.val) {
                    return -1;
                } else if (o1.val == o2.val) {
                    return 0;
                } else {
                    return 1;
                }
            }

        });
        ListNode p = result;
        for (ListNode node : lists) {
            if (node != null) queue.add(node);
        }
        while (!queue.isEmpty()) {
            p.next = queue.poll();
            p = p.next;
            if (p.next != null) queue.add(p.next);
        }
        return result.next;

    }

    public ListNode mergeTwoList(ListNode a, ListNode b) {
        if (null == a || null == b) {
            return null == a ? b : a;
        }
        ListNode result = new ListNode(0);
        ListNode tail = result, l1 = a, l2 = b;
        while (l1 != null && l2 != null) {
            if (l1.val > l2.val) {
                tail.next = l2;
                l2 = l2.next;
            } else {
                tail.next = l1;
                l1 = l1.next;
            }
            tail = tail.next;
        }
        tail.next = (l1 == null ? l2 : l1);
        return result.next;
    }

}
