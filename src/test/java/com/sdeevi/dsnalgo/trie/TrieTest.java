package com.sdeevi.dsnalgo.trie;

import com.sdeevi.dsnalgo.indexmappers.CaseInsensitiveIndexMapper;
import com.sdeevi.dsnalgo.indexmappers.DefaultIndexMapper;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class TrieTest {

    private Trie trie = new Trie(new CaseInsensitiveIndexMapper());

    @Before
    public void setUp() {
        trie.insert("Sarath");
        trie.insert("Swetha");
        trie.insert("sara");
        trie.insert("Sarat");
        trie.insert("sanskriti");
        trie.insert("sanskrit");
        trie.insert("Arya");
        trie.insert("aryannnnn");
        trie.insert("dhruv");
        trie.insert("dhruva");
        trie.insert("andhrapradeshDhr");
    }

    @Test
    public void search_thenReturnsTrueIfExists() {
        assertThat(trie.wordSearch("Sarat"), is(true));
        assertThat(trie.wordSearch("sarath"), is(true));
        assertThat(trie.wordSearch("Dhruvan"), is(false));
        assertThat(trie.wordSearch("Dhruv"), is(true));
        assertThat(trie.wordSearch("arya"), is(true));
        assertThat(trie.wordSearch("thisisaverybigwordar"), is(false));
    }

    @Test
    public void getWordsWithPrefix_thenReturnsWords() {
        assertThat(trie.prefixSearch("sa"), containsInAnyOrder("sara", "sarat", "sarath",
                "sanskrit", "sanskriti"));

        assertThat(trie.prefixSearch("a"), containsInAnyOrder("arya", "aryannnnn", "andhrapradeshdhr"));
    }

    @Test
    public void containsWord_thenReturnsWordsWhichContainTheGivenWord() {
        assertThat(trie.containsSearch("ar"), containsInAnyOrder("arya", "aryannnnn", "sara", "sarat", "sarath"));
        assertThat(trie.containsSearch("dhr"), containsInAnyOrder("dhruv", "dhruva", "andhrapradeshdhr"));
    }
}