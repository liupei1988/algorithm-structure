package array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Given a collection of intervals, merge all overlapping intervals.
 * <p>
 * Example 1:
 * <p>
 * Input: intervals = [[1,3],[2,6],[8,10],[15,18]]
 * Output: [[1,6],[8,10],[15,18]]
 * Explanation: Since intervals [1,3] and [2,6] overlaps, merge them into [1,6].
 * Example 2:
 * <p>
 * Input: intervals = [[1,4],[4,5]]
 * Output: [[1,5]]
 * Explanation: Intervals [1,4] and [4,5] are considered overlapping.
 * NOTE: input types have been changed on April 15, 2019. Please reset to default code definition to get new method signature.
 * <p>
 * <p>
 * <p>
 * Constraints:
 * <p>
 * intervals[i][0] <= intervals[i][1]
 */
public class Problem_56_Merge_Intervals {
    //有排序行为：O(N)
    public static int[][] merge(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return new int[0][2];
        }
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] - o2[0]; //按L升序排序
            }
        });
        List<int[]> merge = new ArrayList<>();
        for (int i = 0; i < intervals.length; i++) {
            int[] sub = intervals[i];
            int left = sub[0], right = sub[1];
            if (merge.size() == 0 /*第一个加入*/
                    || merge.get(merge.size() - 1)[1] < left/*merge中的最后一个的R < 当前区间的L*/
            ) { //此时放心大胆的添加
                merge.add(sub);
            } else {
                int[] last = merge.get(merge.size() - 1);
                last[1] = Math.max(last[1], right);
            }
        }
        int[][] ans = new int[merge.size()][2];
        int index = 0;
        for (int[] arr : merge) {
            ans[index++] = arr;
        }
        return ans;
    }

    //[[1,3],[2,6],[8,10],[15,18]]
    public static int[][] merge2(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return new int[0][2];
        }
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] - o2[0]; //按L升序排序
            }
        });

        int[][] help = new int[intervals.length][2];
        int size = 0; //表示help中有效元素的个数
        for (int i = 0; i < intervals.length; i++) {
            int[] sub = intervals[i];
            if (size == 0 || help[size - 1][1] < sub[0]) { //此时放心大胆的添加
                help[size++] = sub;
            } else {
                int[] last = help[size - 1]; //help中最后一个元素
                last[1] = Math.max(last[1], sub[1]);
            }
        }

        return Arrays.copyOf(help, size);
    }


    //[[1,3],[2,6],[8,10],[15,18]]
    public static int[][] merge3(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return new int[0][2];
        }
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] - o2[0];
            }
        });

        int[][] help = new int[intervals.length][2];
        int size = 0;
        int s = intervals[0][0]; //1
        int e = intervals[0][1]; //3
        for (int i = 1; i < intervals.length; i++) {
            int[] interval = intervals[i];
            int start = interval[0];//2
            int end = interval[1];//6
            if (start <= e) {
                e = Math.max(e, end);
            } else {
                help[size++] = new int[]{s, e};
                s = start;
                e = end;
            }
        }
        help[size++] = new int[]{s, e};//最后一组

        return Arrays.copyOf(help, size);
    }
}
