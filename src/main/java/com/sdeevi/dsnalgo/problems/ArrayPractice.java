package com.sdeevi.dsnalgo.problems;

import com.sdeevi.dsnalgo.trees.MinHeap;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class ArrayPractice {

    public static int[] knapsackSolution(int totalWeight, int[] weights, int[] values) {
        int[][] a = new int[weights.length][totalWeight + 1];
        int[] solution = new int[weights.length];
        Arrays.fill(solution, 0);

        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < totalWeight + 1; j++) {
                if (j == 0) {
                    a[i][j] = 0;
                } else if (i == 0) {
                    a[i][j] = j < weights[0] ? 0 : values[0];
                } else if (j < weights[i]) {
                    a[i][j] = a[i - 1][j];
                } else {
                    int remWeight = j - weights[i];
                    a[i][j] = Math.max(a[i - 1][j], values[i] + a[i - 1][remWeight]);
                }
            }
        }
        int i = weights.length - 1;
        int j = totalWeight;
        while (i >= 0) {
            if (i == 0) {
                solution[i] = 1;
            } else if (a[i][j] != a[i - 1][j]) {
                solution[i] = 1;
                int weightDiff = a[i][j] - values[i];

                j = lastIndex(a[i - 1], weightDiff);
            }
            i--;
        }
        return solution;
    }

    private static int lastIndex(int[] a, int key) {
        int k = a.length - 1;
        while (k >= 0) {
            if (a[k] == key) return k;
            k--;
        }
        return -1;
    }

    public static void rotateArrayOrderNk(int[] a, int k) {
        while (k > 0) {
            int i;
            int temp = a[0];
            for (i = 0; i < a.length - 1; i++) {
                a[i] = a[i + 1];
            }
            a[i] = temp;
            k--;
        }
    }

    public static void rotateArrayOrderN(int[] a, int k) {
        reverseArray(a, 0, k - 1);
        reverseArray(a, k, a.length - 1);
        reverseArray(a, 0, a.length - 1);
    }

    private static void reverseArray(int[] a, int start, int end) {
        int temp;
        while (start < end) {
            temp = a[start];
            a[start] = a[end];
            a[end] = temp;
            start++;
            end--;
        }
    }

    public static int getTrappedRainWater(int[] elevationMap) {
        int n = elevationMap.length;
        int[] left = new int[n];
        int[] right = new int[n];

        int trappedWater = 0;
        int maximumLeft = elevationMap[0];
        int maximumRight = elevationMap[n - 1];
        for (int i = 0; i < n; i++) {
            left[i] = maximumLeft = Math.max(maximumLeft, elevationMap[i]);
            right[n - i - 1] = maximumRight = Math.max(maximumRight, elevationMap[n - i - 1]);
        }

        for (int i = 0; i < n; i++) {
            trappedWater += Math.min(left[i], right[i]) - elevationMap[i];
        }
        return trappedWater;
    }

    public static String containsPythagoreanTriplet(int[] a) {
        Arrays.sort(a);
        int k = a.length - 1;
        while (k > 2) {
            int i = 0;
            int j = k - 1;
            while (j > i) {
                int sq = a[k] * a[k];
                int sq2 = (a[i] * a[i]) + (a[j] * a[j]);
                if (sq2 > sq) {
                    j--;
                } else if (sq2 < sq) {
                    i++;
                } else {
                    return "Yes";
                }
            }
            k--;
        }
        return "No";
    }

    public static String getBuySellStockOptimization(int[] prices) {
        int i = 0;
        int n = prices.length;

        int numTransactions = 0;
        StringBuilder sb = new StringBuilder();

        int buy, sell;
        while (i < n - 1) {

            while (i < n - 1 && prices[i + 1] <= prices[i]) {
                i++;
            }
            buy = i++;

            while (i < n && prices[i] > prices[i - 1]) {
                i++;
            }
            sell = i - 1;
            numTransactions++;

            if (buy < sell) {
                sb.append(String.format("(%d %d) ", buy, sell));
            }
        }
        return numTransactions > 0 ? sb.toString().trim() : "No Profit";
    }

    // TODO: Finish dp solution
    public static String getBuySellStockOptimizationInFixedTransactions(int[] prices, int k) {
        return null;
    }

    public static int getMinDiffDistribution(int[] chocolatePacks, int numberOfStudents) {
        int i = 0;
        Arrays.sort(chocolatePacks);

        int min = Integer.MAX_VALUE;
        int j;
        do {
            j = i + numberOfStudents - 1;
            min = Math.min(chocolatePacks[j] - chocolatePacks[i++], min);
        } while (j < chocolatePacks.length - 1);
        return min;
    }

    // TODO: Fix this
    public static int getKthLargestElement(int[] a, int k, int low, int high) {
        if (k > 0 && k <= high - low + 1) {
            int pos = partition(a, low, high);

            if (pos == k) {
                return a[k];
            }

            if (pos > k) return getKthLargestElement(a, k, low, pos - 1);

            return getKthLargestElement(a, k - (pos - low + 1), pos + 1, high);
        }
        return Integer.MAX_VALUE;
    }

    public static int getKthLargestElementUsingMinHeap(int[] a, int k, int low, int high) {
        MinHeap<Integer> minHeap = new MinHeap<>(high - low + 1);
        for (int i = low; i <= high; i++) {
            minHeap.add(a[i]);
        }
        int popped = Integer.MAX_VALUE;
        while (k > 0) {
            popped = minHeap.remove();
            k--;
        }
        return popped;
    }

    public static void quickSort(int[] a) {
        quickSort(a, 0, a.length - 1);
    }

    private static void quickSort(int[] a, int low, int high) {
        if (high < low) return;

        int pivot = partition(a, low, high);

        quickSort(a, 0, pivot - 1);
        quickSort(a, pivot + 1, high);
    }

    private static int partition(int arr[], int low, int high) {
        int pivot = arr[high];
        int i = low;
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                swap(arr, i, j);
                i++;
            }
        }
        swap(arr, i, high);
        return i;
    }

    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int t = Integer.parseInt(br.readLine());

        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine());

            int[] a = new int[n];
            String[] values = br.readLine().split("\\s+");

            for (int i = 0; i < values.length; i++) {
                a[i] = Integer.parseInt(values[i]);
            }
        }
    }
}
