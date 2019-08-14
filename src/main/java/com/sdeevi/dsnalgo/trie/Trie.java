package com.sdeevi.dsnalgo.trie;

import com.sdeevi.dsnalgo.indexmappers.CharIndexMapper;
import com.sdeevi.dsnalgo.indexmappers.DefaultIndexMapper;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class Trie {

    private CharIndexMapper charIndexMapper;
    private TrieNode root;

    public Trie() {
        this(new DefaultIndexMapper());
    }

    public Trie(CharIndexMapper charIndexMapper) {
        this.root = new TrieNode();
        this.charIndexMapper = charIndexMapper;
    }

    public String insert(String word) {
        TrieNode curr = root;
        for (int i = 0; i < word.length(); i++) {
            Character c = charIndexMapper.map(word.charAt(i));

            if (!curr.trieNodes.containsKey(c)) {
                TrieNode trieNode = new TrieNode();
                curr.trieNodes.put(c, trieNode);
                curr = trieNode;
            } else {
                curr = curr.trieNodes.get(c);
            }
        }
        curr.leaf = true;

        return charIndexMapper.map(word);
    }

    public boolean wordSearch(String word) {
        TrieNode n = searchNode(word);
        return n != null && n.leaf;
    }

    private TrieNode searchNode(String word) {
        TrieNode curr = root;
        for (int i = 0; i < word.length(); i++) {
            Character c = charIndexMapper.map(word.charAt(i));

            if (curr.trieNodes.containsKey(c)) {
                curr = curr.trieNodes.get(c);
            } else {
                return null;
            }
        }
        if (curr == root) return null;
        return curr;
    }

    public Set<String> containsSearch(String search) {
        return containsSearch(root, new StringBuffer(), search);
    }

    private Set<String> containsSearch(TrieNode node, StringBuffer pre, String search) {
        Set<String> words = prefixSearch(node, pre, search);

        for (Map.Entry<Character, TrieNode> entry : node.trieNodes.entrySet()) {
            StringBuffer sb = new StringBuffer(pre);
            sb.append(entry.getKey());
            words.addAll(containsSearch(entry.getValue(), sb, search));
        }
        return words;
    }

    public Set<String> prefixSearch(String prefix) {
        return prefixSearch(this.root, new StringBuffer(), prefix);
    }

    private Set<String> prefixSearch(TrieNode node, StringBuffer pre, String prefix) {
        Set<String> words = new LinkedHashSet<>();
        Character firstChar = charIndexMapper.map(prefix.charAt(0));

        TrieNode curr = node.trieNodes.get(firstChar);

        if (curr != null) {
            StringBuffer sb = new StringBuffer(pre);
            sb.append(firstChar);
            int count = 1;
            for (int i = 1; i < prefix.length(); i++) {
                Character c = charIndexMapper.map(prefix.charAt(i));

                if (curr.trieNodes.containsKey(c)) {
                    sb.append(c);
                    curr = curr.trieNodes.get(c);
                    count++;
                } else {
                    break;
                }
            }
            if (count == prefix.length()) {
                completeWordsAfterSearchFound(curr, sb, words);
            }
        }
        return words;
    }

    private void completeWordsAfterSearchFound(TrieNode trieNode, StringBuffer sb, Set<String> words) {
        if (trieNode == null) return;
        if (trieNode.leaf) {
            words.add(sb.toString());
        }

        for (Map.Entry<Character, TrieNode> entry : trieNode.trieNodes.entrySet()) {
            StringBuffer pre = new StringBuffer(sb);
            pre.append(entry.getKey());
            completeWordsAfterSearchFound(entry.getValue(), pre, words);
        }
    }

    public static class TrieNode {
        private Map<Character, TrieNode> trieNodes;
        private boolean leaf;

        TrieNode() {
            this.trieNodes = new HashMap<>();
        }
    }
}
