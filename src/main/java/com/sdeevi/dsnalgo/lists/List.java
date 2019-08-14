package com.sdeevi.dsnalgo.lists;

import java.util.HashSet;
import java.util.Set;

public class List {

    private Node root;

    public List(int... elements) {
        if (elements.length == 0) return;
        int i = 0;
        root = new Node(elements[i++]);
        Node curr = root;

        while (i < elements.length) {
            Node temp = new Node(elements[i++]);
            curr.next = temp;
            curr = temp;
        }
    }

    public void deleteAlternate() {
        Node curr = root;

        while (curr != null) {
            Node next = curr.next;

            if (next != null) {
                Node temp = next.next;
                curr.next = temp;
                curr = temp;
            } else {
                curr = null;
            }
        }
    }

    public boolean isPalindrome() {
        if (root == null) return true;
        Node curr1 = root, curr2 = root;
        int count = 0;
        while (curr2 != null) {
            curr1 = curr1.next;
            curr2 = curr2.next;
            if (curr2 != null) curr2 = curr2.next;
            count++;
        }
        Node mid = reverse(curr1);
        curr2 = mid;
        curr1 = root;
        int i = 0;
        boolean palindrome = true;
        while ((curr1 != null && curr2 != null) && i < count) {
            if (curr1.data != curr2.data) {
                palindrome = false;
                break;
            }
            curr1 = curr1.next;
            curr2 = curr2.next;
        }
        reverse(mid);
        return palindrome;
    }

    public void reverse() {
        root = reverse(root);
    }

    public Node reverse(Node n) {
        Node curr = n;
        Node prev = null;
        while (curr != null) {
            Node next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }

    public void removeDuplicates() {
        root = removeDuplicates(root);
    }

    private Node removeDuplicates(Node head) {
        Set<Integer> s = new HashSet<>();
        Node curr = head, prev = null;
        while (curr != null) {
            if (s.contains(curr.data)) {
                Node temp = curr.next;
                prev.next = curr.next;
                curr = temp;
            } else {
                s.add(curr.data);
                prev = curr;
                curr = curr.next;
            }
        }
        return head;
    }

    public void segregate012() {
        root = segregate012(root);
    }

    private Node segregate012(Node head) {
        if (head == null) return null;
        Node curr = head;

        Node zCurr = null, oCurr = null, tCurr = null;
        Node zHead = null, oHead = null, tHead = null;
        while (curr != null) {
            if (curr.data == 0) {
                if (zHead == null) zHead = curr;
                if (zCurr != null) zCurr.next = curr;
                zCurr = curr;
            } else if (curr.data == 1) {
                if (oHead == null) oHead = curr;
                if (oCurr != null) oCurr.next = curr;
                oCurr = curr;
            } else if (curr.data == 2) {
                if (tHead == null) tHead = curr;
                if (tCurr != null) tCurr.next = curr;
                tCurr = curr;
            }
            curr = curr.next;
        }
        head = zHead != null ? zHead : oHead != null ? oHead : tHead;

        if (zHead != null)
            zCurr.next = oHead != null ? oHead : tHead;
        if (oHead != null)
            oCurr.next = tHead;
        if (tHead != null) tCurr.next = null;
        return head;
    }

    @Override
    public String toString() {
        Node curr = root;
        StringBuilder sb = new StringBuilder();
        while (curr != null) {
            sb.append(curr.data).append(",");
            curr = curr.next;
        }
        return sb.deleteCharAt(sb.length() - 1).toString();
    }

    public static final class Node {
        public Node next;
        public int data;

        public Node(int data) {
            this.data = data;
        }
    }
}