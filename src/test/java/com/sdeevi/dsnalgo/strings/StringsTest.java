package com.sdeevi.dsnalgo.strings;

import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class StringsTest {

    @Test
    public void searchRabinKarp_returnsIndicesOfMatches() {
        assertThat(Strings.searchRabinKarp("saratchandrandeevi", "and"), is(Arrays.asList(7, 11)));
        assertThat(Strings.searchRabinKarp("saratchandrandeevi", "a"), is(Arrays.asList(1, 3, 7, 11)));
        assertThat(Strings.searchRabinKarp("this is a string with values other than some values", "th"), is(Arrays.asList(0, 19, 30, 35)));
    }

    @Test
    public void searchKMP_returnsIndicesOfMatches() {
        assertThat(Strings.searchKMP("saratchandrandeevi", "and"), is(Arrays.asList(7, 11)));
        assertThat(Strings.searchKMP("saratchandrandeevi", "a"), is(Arrays.asList(1, 3, 7, 11)));
        assertThat(Strings.searchKMP("this is a string with values other than some values", "th"), is(Arrays.asList(0, 19, 30, 35)));
        assertThat(Strings.searchKMP("ababdababdefabcdef", "abab"), is(Arrays.asList(0, 5)));
        assertThat(Strings.searchKMP("acbacbacbacbacdacbacbacbacbacbacbacbacb", "acbacbacbacbacb"), is(Arrays.asList(15, 18, 21, 24)));
    }

    @Test
    public void getLpsArray() {
        assertThat(Strings.getLPSArray("abcdefabcabefg"), is(new int[]{0, 0, 0, 0, 0, 0, 1, 2, 3, 1, 2, 0, 0, 0}));
        assertThat(Strings.getLPSArray("acbacdabcy"), is(new int[]{0, 0, 0, 1, 2, 0, 1, 0, 0, 0}));
        assertThat(Strings.getLPSArray("acbacbacbacbacb"), is(new int[]{0, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12}));
    }

    @Test
    public void searchZ_returnsIndicesOfMatches() {
        assertThat(Strings.searchZ("saratchandrandeevi", "and"), is(Arrays.asList(7, 11)));
        assertThat(Strings.searchZ("saratchandrandeevi", "a"), is(Arrays.asList(1, 3, 7, 11)));
        assertThat(Strings.searchZ("this is a string with values other than some values", "th"), is(Arrays.asList(0, 19, 30, 35)));
        assertThat(Strings.searchZ("ababdababdefabcdef", "abab"), is(Arrays.asList(0, 5)));
        assertThat(Strings.searchZ("acbacbacbacbacdacbacbacbacbacbacbacbacb", "acbacbacbacbacb"), is(Arrays.asList(15, 18, 21, 24)));
    }

    @Test
    public void getZArray() {
        assertThat(Strings.getZArray("abcdefabcabefg"), is(new int[]{0, 0, 0, 0, 0, 0, 3, 0, 0, 2, 0, 0, 0, 0}));
        assertThat(Strings.getZArray("acbacdabcy"), is(new int[]{0, 0, 0, 2, 0, 0, 1, 0, 0, 0}));
        assertThat(Strings.getZArray("acbacbacbacbacb"), is(new int[]{0, 0, 0, 12, 0, 0, 9, 0, 0, 6, 0, 0, 3, 0, 0}));
    }

    @Test
    public void searchBoyerMoore_returnsIndicesOfMatches() {
        assertThat(Strings.searchBoyerMoore("saratchandrandeevi", "and"), is(Arrays.asList(7, 11)));
        assertThat(Strings.searchBoyerMoore("saratchandrandeevi", "a"), is(Arrays.asList(1, 3, 7, 11)));
        assertThat(Strings.searchBoyerMoore("this is a string with values other than some values", "th"), is(Arrays.asList(0, 19, 30, 35)));
        assertThat(Strings.searchBoyerMoore("ababdababdefabcdef", "abab"), is(Arrays.asList(0, 5)));
        assertThat(Strings.searchBoyerMoore("acbacbacbacbacdacbacbacbacbacbacbacbacb", "acbacbacbacbacb"), is(Arrays.asList(15, 18, 21, 24)));
    }

    @Test
    public void wordBreak_returnsString() {
        assertThat(Strings.wordBreak("ilikemango", Sets.newHashSet("i", "like", "man", "go", "mango")), is(Arrays.asList("i like man go", "i like mango")));
        assertThat(Strings.wordBreak("thereisasolutiontoeveryproblem",
                Sets.newHashSet("the",
                        "there",
                        "is",
                        "a",
                        "solution",
                        "to",
                        "toe",
                        "very",
                        "every",
                        "eve",
                        "prob",
                        "problem")), is(Arrays.asList("there is a solution to every problem", "there is a solution toe very problem")));
    }
}