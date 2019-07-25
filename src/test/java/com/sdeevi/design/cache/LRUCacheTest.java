package com.sdeevi.design.cache;

import org.hamcrest.Matchers;
import org.junit.Test;

import static org.junit.Assert.assertThat;

public class LRUCacheTest {

    @Test
    public void testLRUCache() {
        LRUCache lruCache = new LRUCache(3);

        lruCache.add(1, 1, 0);
        lruCache.add(2, 2, 0);
        lruCache.add(3, 3, 0);
        lruCache.add(1, 4, 0);
        lruCache.add(4, 5, 0);

        assertThat(lruCache.get(2), Matchers.nullValue());
        assertThat(lruCache.get(1), Matchers.equalTo(4));
        assertThat(lruCache.get(4), Matchers.equalTo(5));
        assertThat(lruCache.get(3), Matchers.equalTo(3));
    }
}