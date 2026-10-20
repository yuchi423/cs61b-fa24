import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Percolation {

    /*
    public class WeightedQuickUnionUF {
        public WeightedQuickUnionUF(int n)    // 创建包含 n 个元素的并查集
        public int count()                    // 返回互不相交的集合数量
        public int find(int p)                // 返回 p 所属集合的根节点
        public boolean connected(int p, int q) // p 和 q 是否属于同一个集合？
        public void union(int p, int q)       // 合并 p 和 q 所属的集合
    }
     */

    private int[][] grids;
    private int size;
    private int openCnt;

    private WeightedQuickUnionUF unionUF;
    private WeightedQuickUnionUF fullUnionUF;

    private static final int BLOCKED = 0;
    private static final int OPEN = 1;


    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("The Size N Must Be Greater Than 0 !");
        }
        grids = new int[N][N];
        this.size = N;
        this.openCnt = 0;

        unionUF = new WeightedQuickUnionUF(N * N + 2); // represents every element in the grid and the virtual bottom & top
        fullUnionUF = new WeightedQuickUnionUF(N * N + 1);
    }

    private boolean isValid(int row, int col) {
        return 0 <= row && row < size && 0 <= col && col < size;
    }

    private int findIndex(int row, int col) {
        return row * size + col + 1;
    }

    public void open(int row, int col) {
        if (!isValid(row, col)) {
            throw new IndexOutOfBoundsException("The argument have beyond the boundary!");
        }
        if (grids[row][col] == OPEN) return;

        grids[row][col] = OPEN;
        openCnt++;

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};
        int curIdx = findIndex(row, col);

        if (row == 0) {
            unionUF.union(0, curIdx);
            fullUnionUF.union(0, curIdx);
        }
        if (row == size - 1) unionUF.union(size * size + 1, curIdx);

        for (int i = 0; i < 4; i++) {
            int r = row + dr[i];
            int c = col + dc[i];

            if (isValid(r, c) && isOpen(r, c)) {
                int nxtIdx = findIndex(r, c);
                unionUF.union(curIdx, nxtIdx);
                fullUnionUF.union(curIdx, nxtIdx);
            }
        }

    }

    public boolean isOpen(int row, int col) {
        if (!isValid(row, col)) {
            throw new IndexOutOfBoundsException("The argument have beyond the boundary!");
        }
        return grids[row][col] == OPEN;
    }

    public boolean isFull(int row, int col) {
        if (!isValid(row, col)) {
            throw new IndexOutOfBoundsException("The argument have beyond the boundary!");
        }

        int realIdx = findIndex(row, col);
        return fullUnionUF.connected(0, realIdx);
    }

    public int numberOfOpenSites() {
        return openCnt;
    }

    public boolean percolates() {
        return unionUF.connected(0, size * size + 1);
    }

}
