package base;

import java.util.ArrayDeque;
import java.util.Deque;

public class BFS {
    public static void main(String args[]) {
        int[][] grid = new int[8][13];
        grid[0] = new int[]{0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0};
        grid[1] = new int[]{0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0};
        grid[2] = new int[]{0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0};
        grid[3] = new int[]{0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0};
        grid[4] = new int[]{0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 0};
        grid[5] = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0};
        grid[6] = new int[]{0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0};
        grid[7] = new int[]{0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0};
        int result = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                result = Math.max(result, bfs(grid, i, j));
            }
        }
        System.out.println(result);
    }

    /**
     * bfs是每一个都将周围加入，dfs是一直往下找然后通过递归退栈往回走
     * @param grid
     * @param r
     * @param c
     * @return
     */
    public static int bfs(int[][] grid, int r, int c) {
        Deque<Integer> rq=new ArrayDeque<>();
        Deque<Integer> cq=new ArrayDeque<>();
        rq.push(r);
        cq.push(c);
        int sum=0;
        while(!rq.isEmpty()){
            int ri=rq.pollLast();
            int ci=cq.pollLast();
            if (ci < 0 ||  ci>= grid[0].length || ri < 0 || ri >= grid.length|| grid[ri][ci] != 1) {
                continue;
            }
            sum++;
            grid[ri][ci] = 0;
            rq.push(ri-1);
            cq.push(ci);
            rq.push(ri+1);
            cq.push(ci);
            rq.push(ri);
            cq.push(ci-1);
            rq.push(ri);
            cq.push(ci+1);

        }
        return sum;
    }
}
