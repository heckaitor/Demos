package com.heckaitor.demo.utils;

import android.support.annotation.NonNull;

/**
 * 一个特殊的单链表
 * <ul>
 *     <li>节点不为空，且元素不重复。类似{@link java.util.Vector}</li>
 *     <li>只允许添加和删除，外部不能访问存储的节点</li>
 *     <li>非线程安全，但也非常容易增加</li>
 * </ul>
 *
 * 适用场景
 * <ul>
 *     <li>全局配置类，外部只能修改配置项，但对外隐藏配置逻辑</li>
 *     <li>添加、删除元素具有随机性，且自动排重、纠错</li>
 *     <li>使用单链表，存储简单，添加、删除的效率较高</li>
 * </ul>
 */
public class SingleLinkedList<T> {
    
    private Node mHead;
    
    /**
     * 不存在时添加
     * @param item
     */
    public void addIfNonExist(@NonNull T item) {
        if (item == null) {
            return;
        }
        
        if (mHead == null) {
            mHead = new Node(item);
            return;
        }
        
        Node p = mHead;
        Node last = mHead;
        while (p != null) {
            if (item.equals(p.item)) {
                return; // exist
            }
            
            last = p;
            p = p.next;
        }
    
        Node node = new Node(item);
        last.next = node;
    }
    
    /**
     * 存在时删除
     * @param item
     */
    public void removeIfExist(@NonNull T item) {
        if (item == null) {
            return;
        }
        
        Node p = mHead, prev = null;
        while (p != null) {
            if (item.equals(p.item)) {
                break;
            }
    
            if (p.next == null) {
                return;
            }
            
            prev = p;
            p = p.next;
        }
    
        if (p != null) {
            // exist, remove it
            if (prev == null) {
                mHead = p.next;
            } else {
                prev.next = p.next;
            }
        }
    }
    
    private static class Node<T> {
        T item;
        Node next;
    
        public Node(T item) {
            this.item = item;
        }
    
        @Override
        public String toString() {
            return item != null ? item.toString() : null;
        }
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("[");
        Node p = mHead;
        while (p != null) {
            builder.append(p.toString());
            if (p.next != null) {
                builder.append(" -> ");
            }
            p = p.next;
        }
        builder.append("]");
        return builder.toString();
    }
}
