package com.sdeevi.dsnalgo.strings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class Strings {

    private static final int PRIME_NUMBER = 101;
    private static final int NUM_CHARS = 256;

    public static List<Integer> searchRabinKarp(String txt, String search) {
        int m = search.length();
        int n = txt.length();

        int txtHash = 0;
        int searchHash = 0;

        List<Integer> foundAtIndices = new ArrayList<>();

        int multiplier = 1;
        for (int i = 0; i < m - 1; i++) {
            multiplier = (multiplier * NUM_CHARS) % PRIME_NUMBER;
        }

        for (int i = 0; i < m; i++) {
            txtHash = (txtHash * NUM_CHARS + txt.charAt(i)) % PRIME_NUMBER;
            searchHash = (searchHash * NUM_CHARS + search.charAt(i)) % PRIME_NUMBER;
        }

        for (int i = 0; i <= n - m; i++) {
            if (txtHash == searchHash) {
                int j = 0;
                while (j < m && txt.charAt(i + j) == search.charAt(j)) j++;
                if (j == m) foundAtIndices.add(i);
            }

            if (i < n - m) {
                txtHash = ((txtHash - txt.charAt(i) * multiplier) * NUM_CHARS + txt.charAt(i + m)) % PRIME_NUMBER;
                if (txtHash < 0) txtHash += PRIME_NUMBER;
            }
        }
        return foundAtIndices;
    }

    public static List<Integer> searchKMP(String txt, String search) {
        int[] lps = getLPSArray(search);
        int n = txt.length();
        int m = search.length();
        int i = 0;
        int j = 0;
        List<Integer> foundAtIndices = new ArrayList<>();

        while (i < n) {
            if (txt.charAt(i) == search.charAt(j)) {
                i++;
                j++;
            } else if (j == 0) {
                i++;
            } else {
                j = lps[j - 1];
            }
            if (j == m) {
                foundAtIndices.add(i - j);
                j = lps[j - 1];
            }
        }

        return foundAtIndices;
    }

    public static int[] getLPSArray(String search) {
//        int[] result = new int[search.length()]; // array with prefix function values
//
//        result[0] = 0; // the prefix function is always zero for the first symbol (its degenerate case)
//        int k = 0; // current prefix function value
//        for (int i = 1; i < search.length(); i++) {
//            // We're jumping by prefix function values to try smaller prefixes
//            // Jumping stops when we find a match, or reach zero
//            // Case 2 if we stop at a zero-length prefix
//            // Case 3 if we stop at a match
//            while (k > 0 && search.charAt(i) != search.charAt(k)) k = result[k - 1];
//            if (search.charAt(k) == search.charAt(i)) k++; // we've found the longest prefix - case 1
//            result[i] = k; // store this result in the array
//        }
//
//        return result;

        int i = 0, j = 1, m = search.length();
        int[] lps = new int[m];

        while (j < m) {
            if (search.charAt(i) == search.charAt(j)) {
                lps[j] = i + 1;
                i++;
                j++;
            } else if (i == 0) {
                lps[j] = 0;
                j++;
            } else {
                i = lps[i - 1];
            }
        }
        return lps;
    }

    public static List<Integer> searchZ(String txt, String search) {
        int m = search.length();
        String str = search + '$' + txt;

        int[] z = getZArray(str);

        List<Integer> foundAtIndices = new ArrayList<>();
        for (int i = 0; i < z.length; i++) {
            if (z[i] == m) foundAtIndices.add(i - m - 1);
        }
        return foundAtIndices;
    }

    public static int[] getZArray(String search) {
        int m = search.length();
        int[] z = new int[m];

//        int left = 0, right = 0, k;
//        int i = 1;
//        while (i < m) {
//            if (i > right) {
//                left = right = i;
//
//                while (right < m && search.charAt(right - left) == search.charAt(right)) right++;
//
//                z[i] = right - left;
//                right--;
//            } else {
//                k = i - left;
//
//                if (z[k] < right - i + 1) {
//                    z[i] = z[k];
//                } else {
//                    left = i;
//
//                    while (right < m && search.charAt(right) == search.charAt(right - left)) right++;
//
//                    z[i] = right - left;
//                    right--;
//                }
//            }
//            i++;

        int i = 0, j = 1;
        while (j < m) {
            if (search.charAt(i) == search.charAt(j)) {
                i++;
                j++;
            } else {
                z[j - i] = i;
                j = j - i + 1;
                i = 0;
            }
            if (j == m && i != 0) {
                z[j - i] = i;
                j = j - i + 1;
                i = 0;
            }
        }
        return z;
    }

    public static List<Integer> searchBoyerMoore(String txt, String search) {
        int m = search.length();
        int n = txt.length();
        List<Integer> foundAtIndices = new ArrayList<>();

        int[] badChar = badCharHeuristic(search);

        int shift = 0;

        while (shift <= n - m) {
            int j = m - 1;
            while (j >= 0 && search.charAt(j) == txt.charAt(shift + j)) j--;

            if (j < 0) {
                foundAtIndices.add(shift);
                shift += (shift + m < n) ? m - badChar[txt.charAt(shift + m)] : 1;
            } else {
                shift += Math.max(1, j - badChar[txt.charAt(shift + j)]);
            }
        }
        return foundAtIndices;
    }

    public static int[] badCharHeuristic(String search) {
        int[] badChar = new int[256];

        Arrays.fill(badChar, -1);
        for (int i = 0; i < search.length(); i++) {
            badChar[(int) search.charAt(i)] = i;
        }
        return badChar;
    }

    public static List<String> wordBreak(String txt, Set<String> dictionary) {
        List<String> result = new ArrayList<>();
        wordBreak(txt, dictionary, "", result);
        return result;
    }

    private static void wordBreak(String txt, Set<String> dictionary, String res, List<String> result) {
        int n = txt.length();
        for (int i = 1; i <= n; i++) {
            String str = txt.substring(0, i);

            if (dictionary.contains(str)) {
                if (i == n) {
                    res = res + str;
                    result.add(res);
                    return;
                }
                wordBreak(txt.substring(i, n), dictionary, res + str + " ", result);
            }
        }
    }
}
