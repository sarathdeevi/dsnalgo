package com.sdeevi.dsnalgo.lists;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ListTest {

    private List list;

    @Before
    public void setUp() throws Exception {
        list = new List(10, 200, 15, 34, 45);
    }

    @Test
    public void deleteAlt() {
        assertThat(list.toString(), is("10,200,15,34,45"));

        list.deleteAlternate();
        assertThat(list.toString(), is("10,15,45"));

        List b = new List(991, 20, 1000, 19, 1001, 1);
        b.deleteAlternate();
        assertThat(b.toString(), is("991,1000,1001"));
    }

    @Test
    public void reverse() {
        list.reverse();
        assertThat(list.toString(), is("45,34,15,200,10"));
    }

    @Test
    public void palindrome() {
        assertThat(list.isPalindrome(), is(false));
        assertThat(new List().isPalindrome(), is(true));
        assertThat(new List(1, 2, 1).isPalindrome(), is(true));
        assertThat(new List(1, 3, 3, 1).isPalindrome(), is(true));
        assertThat(new List(1, 3, 3, 1, 3).isPalindrome(), is(false));

        assertThat(list.toString(), is("10,200,15,34,45"));
    }

    @Test
    public void removeDuplicates() {
        List a = new List(1, 2, 5, 2, 6, 8, 6, 9, 10, 1);
        a.removeDuplicates();
        assertThat(a.toString(), is("1,2,5,6,8,9,10"));
    }

    @Test
    public void segregate() {
        List a = new List(1, 2, 2, 1, 2, 0, 2, 2);
        a.segregate012();
        assertThat(a.toString(), is("0,1,1,2,2,2,2,2"));

        a = new List(1, 1, 2, 1);
        a.segregate012();
        assertThat(a.toString(), is("1,1,1,2"));

        a = new List(0, 1, 0);
        a.segregate012();
        assertThat(a.toString(), is("0,0,1"));
    }
}