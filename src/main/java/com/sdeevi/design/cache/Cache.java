package com.sdeevi.design.cache;

public interface Cache<Key, Value> {

    void add(Key key, Value value, long expirationTimeInMillis);

    void remove(Key key);

    Value get(Key key);

    void clear();
}
