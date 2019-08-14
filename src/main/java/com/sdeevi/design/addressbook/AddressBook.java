package com.sdeevi.design.addressbook;

import com.google.common.collect.Lists;
import com.sdeevi.dsnalgo.indexmappers.CaseInsensitiveIndexMapper;
import com.sdeevi.dsnalgo.indexmappers.NumberKeyIndexMapper;
import com.sdeevi.dsnalgo.trie.Trie;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class AddressBook {

    private Map<String, Set<Integer>> numIndex;
    private Map<String, Set<Integer>> charIndex;

    private Map<Integer, Contact> contacts;
    private Trie numTrie;
    private Trie charTrie;

    private AtomicInteger idGenerator;

    public AddressBook() {
        numIndex = new HashMap<>();
        charIndex = new HashMap<>();
        contacts = new HashMap<>();

        numTrie = new Trie(new NumberKeyIndexMapper());
        charTrie = new Trie(new CaseInsensitiveIndexMapper());

        idGenerator = new AtomicInteger();
    }

    public void addContact(String firstName, String lastName, String number) {
        Integer id = idGenerator.incrementAndGet();
        Contact c = new Contact(firstName, lastName, number);

        contacts.put(id, c);

        String indexResult = numTrie.insert(firstName);
        numIndex.putIfAbsent(indexResult, new HashSet<>());
        numIndex.get(indexResult).add(id);

        indexResult = numTrie.insert(lastName);
        numIndex.putIfAbsent(indexResult, new HashSet<>());
        numIndex.get(indexResult).add(id);

        indexResult = numTrie.insert(number.replaceAll("-", ""));
        numIndex.putIfAbsent(indexResult, new HashSet<>());
        numIndex.get(indexResult).add(id);

        indexResult = charTrie.insert(firstName);
        charIndex.putIfAbsent(indexResult, new HashSet<>());
        charIndex.get(indexResult).add(id);

        indexResult = charTrie.insert(lastName);
        charIndex.putIfAbsent(indexResult, new HashSet<>());
        charIndex.get(indexResult).add(id);
    }

    public List<Contact> search(String search) {
        String[] split = search.split("\\s+");
        long time = System.currentTimeMillis();
        try {
            Map<Integer, Contact> result = new HashMap<>();
            for (String s : split) {
                for (String index : numTrie.containsSearch(s)) {
                    if (numIndex.containsKey(index)) {
                        for (Integer id : numIndex.get(index)) {
                            result.put(id, contacts.get(id));
                        }
                    }
                }
                for (String index : charTrie.containsSearch(s)) {
                    if (charIndex.containsKey(index)) {
                        for (Integer id : charIndex.get(index)) {
                            result.put(id, contacts.get(id));
                        }
                    }
                }
            }
            return Lists.newArrayList(result.values());
        } finally {
            System.out.println("Time taken for search with text = " + search + " is = " + (System.currentTimeMillis() - time) + " ms");
        }
    }

    public static final class Contact {
        private String firstName;
        private String lastName;
        private String number;

        public Contact(String firstName, String lastName, String number) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.number = number;
        }

        @Override
        public String toString() {
            return firstName + " " + lastName + " (" + number + ")";
        }
    }
}
