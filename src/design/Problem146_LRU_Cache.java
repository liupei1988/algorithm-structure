package design;

import java.util.HashMap;
import java.util.Map;

/**
 * LRU内存替换算法
 * Design a data structure that follows the constraints of a Least Recently Used (LRU) cache.
 * <p>
 * Implement the LRUCache class:
 * <p>
 * LRUCache(int capacity) Initialize the LRU cache with positive size capacity.
 * int get(int key) Return the value of the key if the key exists, otherwise return -1.
 * void put(int key, int value) Update the value of the key if the key exists.
 * Otherwise, add the key-value pair to the cache.
 * If the number of keys exceeds the capacity from this operation, evict the least recently used key.
 * Follow up:
 * Could you do get and put in O(1) time complexity?
 */
public class Problem146_LRU_Cache {

    //双向链表中的元素 Node
    private static class Node {
        int key, value;
        Node next, last;

        public Node(int k, int v) {
            key = k;
            value = v;
            next = null;
            last = null;
        }
    }

    //双向链表
    public static class DoubleLinkedList {
        public Node head; //头指针
        public Node tail; //尾指针

        public DoubleLinkedList() {
            head = null;
            tail = null;
        }

        /**
         * 向尾部追加元素
         * 新添加的节点都是放在尾部
         */
        public void addToTail(Node node) {
            if (node == null) {
                return;
            }
            //node != null
            if (head == null) { //表示链表中还没有元素
                head = node;
                tail = node;
                return;
            }
            //node != null && head != null
            tail.next = node;
            node.last = tail;
            tail = node;
        }


        /**
         * 前提：node一定在链表中
         * 将元素移动到尾部(尾部放的是最近使用的节点)
         */
        public void moveToTail(Node node) {
            if (node == tail) {
                return;
            }
            if (node == head) {
                head = head.next;
                head.last = null;
            } else {
                node.last.next = node.next;
                node.next.last = node.last;
            }
            node.last = tail;
            tail.next = node;
            node.next = null;
            tail = node;
        }

        /**
         * 删除头部(当容量已满的情况下、添加元素时，需要调用此方法)
         *
         * @return 返回被删除的头节点
         */
        public Node delHead() {
            if (head == null) {
                return null;
            }
            Node res = head;
            if (head == tail) { //链表中只有一个节点
                head = null;
                tail = null;
                return res;
            }
            head = res.next;
            res.next = null;
            head.last = null;
            return res;
        }

    }

    public static class LRUCache {

        int cap;
        Map<Integer, Node> map;
        DoubleLinkedList list;

        public LRUCache(int capacity) {
            map = new HashMap<>();
            list = new DoubleLinkedList();
            cap = capacity;
        }

        public int get(int key) {
            if (!map.containsKey(key)) {//map中没有说明链表中没有对应元素
                return -1;
            }
            //链表中有对应元素
            Node node = map.get(key);
            list.moveToTail(node);
            return node.value;
        }

        public void put(int key, int value) {
            //链表中有 就是修改
            if (map.containsKey(key)) {
                Node node = map.get(key);
                node.value = value;
                list.moveToTail(node);
                return;
            }
            //链表中没有 对应新增
            if (map.size() == cap) {
                //删头(删除最久没有使用的节点)
                Node delNode = list.delHead();
                map.remove(delNode.key);
            }
            Node newNode = new Node(key, value);
            map.put(key, newNode); //同步添加到map
            list.addToTail(newNode);
        }
    }

}
