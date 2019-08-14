package com.sdeevi.dsnalgo.problems;

import org.hamcrest.Matchers;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ArrayPracticeTest {

    @Test
    public void testRotateArray() {
        int[] a = {1, 2, 3, 4, 5, 6};
        ArrayPractice.rotateArrayOrderNk(a, 2);

        assertThat(a, Matchers.equalTo(new int[]{3, 4, 5, 6, 1, 2}));
    }

    @Test
    public void testRotateArrayOn() {
        int[] a = {1, 2, 3, 4, 5, 6, 7};
        ArrayPractice.rotateArrayOrderN(a, 2);

        assertThat(a, Matchers.equalTo(new int[]{3, 4, 5, 6, 7, 1, 2}));
    }

    @Test
    public void testTrappedRainWater() {
        assertThat(ArrayPractice.getTrappedRainWater(new int[]{7, 4, 0, 9}), Matchers.equalTo(10));
        assertThat(ArrayPractice.getTrappedRainWater(new int[]{6, 9, 9}), Matchers.equalTo(0));
        assertThat(ArrayPractice.getTrappedRainWater(new int[]{8, 8, 2, 4, 5, 5, 1}), Matchers.equalTo(4));
    }

    @Test
    public void testPythagoreanTriplet() {
        assertThat(ArrayPractice.containsPythagoreanTriplet(new int[]{3, 2, 4, 6, 5}), Matchers.equalTo("Yes"));
        assertThat(ArrayPractice.containsPythagoreanTriplet(new int[]{3, 2, 4}), Matchers.equalTo("No"));
    }

    @Test
    public void testMinDiff() {
        assertThat(ArrayPractice.getMinDiffDistribution(new int[]{3, 4, 1, 56, 9, 7, 9, 12}, 5), Matchers.equalTo(6));
        assertThat(ArrayPractice.getMinDiffDistribution(new int[]{88, 57, 44, 92, 28, 66, 60, 37, 33, 52, 38, 29, 76, 8, 75}, 2), Matchers.equalTo(1));
        assertThat(ArrayPractice.getMinDiffDistribution(new int[]{52, 55, 100, 33}, 1), Matchers.equalTo(0));
    }

    @Test
    public void testBuyAndSellStocks() {
        assertThat(ArrayPractice.getBuySellStockOptimization(new int[]{100, 180, 260, 310, 40, 535, 695}), Matchers.equalTo("(0 3) (4 6)"));
        assertThat(ArrayPractice.getBuySellStockOptimization(new int[]{23, 13, 25, 29, 33, 19, 34, 45, 65, 67}), Matchers.equalTo("(1 4) (5 9)"));
    }

    @Test
    public void testBuyAndSellStocksInFixedTransactions() {
        assertThat(ArrayPractice.getBuySellStockOptimizationInFixedTransactions(new int[]{100, 180, 260, 310, 40, 535, 695}, 3), Matchers.equalTo("(0 3) (4 6)"));
        assertThat(ArrayPractice.getBuySellStockOptimizationInFixedTransactions(new int[]{23, 13, 25, 29, 33, 19, 34, 45, 65, 67}, 2), Matchers.equalTo("(1 4) (5 9)"));
    }


    @Test
    public void testQuickSort() {
        int[] a = new int[]{100, 180, 260, 310, 40, 535, 695};
        ArrayPractice.quickSort(a);
        assertThat(a, Matchers.equalTo(new int[]{40, 100, 180, 260, 310, 535, 695}));
    }

    @Test
    public void findKthLargest() {
        int[] a = new int[]{100, 180, 260, 310, 40, 535, 695};
        int val = ArrayPractice.getKthLargestElement(a, 3, 2, 6);
        assertThat(val, Matchers.equalTo(310));
    }

    @Test
    public void findKthLargestUsingMinHeap() {
        int[] a = new int[]{100, 180, 260, 310, 40, 535, 695};
        //Sorted array = 40, 100, 180, 260, 310, 535, 695
        int val = ArrayPractice.getKthLargestElementUsingMinHeap(a, 3, 2, 6);
        assertThat(val, Matchers.equalTo(310));
    }

    @Test
    public void knapsackSolution() {
        assertThat(ArrayPractice.knapsackSolution(9, new int[]{1, 2, 3, 5}, new int[]{4, 3, 1, 5}), is(new int[]{1, 1, 0, 1}));
        assertThat(ArrayPractice.knapsackSolution(45, new int[]{5, 10, 15, 18, 20}, new int[]{10, 4, 5, 7, 3}), is(new int[]{1, 0, 1, 1, 0}));
    }
}