package com.sdeevi.dsnalgo.problems;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AngryAnimals {

    /*
    Pi's father, Danny, runs the Hackerville Zoo. He is moving to Rookieville and wants to take all of the zoo animals with him via ship.
    He is confused about how to arrange them because a few of the species cannot be kept together in the same cabin.
    There are n animals placed in a straight line. Each animal is identified by a unique number from 1 to n in order.
    There are m pairs (a[i], b[i]) which imply that animals a[i] and b[i] are enemies and should not be kept in the same cabin.
    Pi is good at solving problems and he came up with following challenge: count the number of different groups that do not contain any pair such that they are enemies.
    A group is defined as an interval (x, y) such that all animals in the range from x to y form a group.
    Determine the number of groups that can be formed according to the Pi's challenge.
    For example, given n = 3 animals and m = 3 pairs of enemies, a = [1, 2, 3] and b = [3, 3, 1],
    animal 1 is the enemy of animal 3, and animal 3 is the enemy of animals 1 and 2. Because 3 is an enemy of both 1 and 2, it must be in its own cabin.
    Animals 1 and 2 can be roomed together or separately. There are four possible groupings meeting the constraints: {1, 2} , {1}, {2}, {3}.
    Note that the intervals are along the original line of animals numbered consecutively from 1 to n, i.e. [1, 2, 3] in this case.
    The animals cannot be reordered and animals cannot be skipped, e.g. {2, 1} and {1, 3} are invalid intervals.

    Function Description
    Complete the function angryAnimals in the editor below. The function must return the number of groups that can be formed according to Pi's challenge.
    angryAnimals has the following parameters:

    n: an integer that denotes the number of unique animals
    a[a[0],...a[m-1]]: an array of integers
    b[b[0],...b[m-1]]: an array of integers

    Constraints

    1 ≤ n ≤ 105
    1 ≤ m ≤ 106
    1 ≤ a[i], b[i] ≤ n
     */

    public static long angryAnimals(int n, List<Integer> a, List<Integer> b) {
        long time = System.nanoTime();
        try {
            Map<Integer, Integer> highestEnemy = new HashMap<>();

            // Store the closest enemy to the left.
            for (int i = 0; i < Math.min(a.size(), b.size()); i++) {
                int ele = Math.max(a.get(i), b.get(i));

                highestEnemy.put(ele, Math.max(highestEnemy.getOrDefault(ele, Integer.MIN_VALUE), Math.min(a.get(i), b.get(i))));
            }

            long count = 0;
            int startIndex = 1;
            int endIndex = 1;
            int highest = Integer.MIN_VALUE;

            for (int i = 1; i <= n; i++) {
                if (highestEnemy.containsKey(i)) {
                    // Count number of combinations before any enemy encounter.
                    count += (endIndex - startIndex) * (endIndex - startIndex + 1) / 2;

                    highest = Math.max(highest, highestEnemy.get(i) + 1);

                    // Move start index beyond the highest enemy as no combinations can be formed if an enemy is present.
                    startIndex = highest;

                    // Eliminate repetitions of count
                    count -= (endIndex - startIndex) * (endIndex - startIndex + 1) / 2;
                }
                endIndex++;
            }

            count += (endIndex - startIndex) * (endIndex - startIndex + 1) / 2;
            return count;
        } finally {
            System.out.println(String.format("optimized, n=%s, a=%s, b=%s, time=%d", n, a, b, System.nanoTime() - time));
        }
    }

    public static long angryAnimalsBruteForce(int n, List<Integer> a, List<Integer> b) {
        long time = System.nanoTime();
        try {
            int i = 1;
            int j;
            long count = 0;

            while (i <= n) {
                j = i;
                while (j <= n) {
                    boolean valid = true;
                    for (int k = 0; k < a.size(); k++) {
                        if (a.get(k) >= i && a.get(k) <= j && b.get(k) >= i && b.get(k) <= j) {
                            valid = false;
                            break;
                        }
                    }
                    if (valid) count++;
                    j++;
                }
                i++;
            }
            return count;
        } finally {
            System.out.println(String.format("brute, n=%s, a=%s, b=%s, time=%d", n, a, b, System.nanoTime() - time));
        }
    }
}
