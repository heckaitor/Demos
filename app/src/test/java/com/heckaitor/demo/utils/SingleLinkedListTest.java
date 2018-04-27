package com.heckaitor.demo.utils;

import com.heckaitor.demo.util.SingleLinkedList;

import org.junit.Test;

import static org.junit.Assert.*;

public class SingleLinkedListTest {
    
    @Test
    public void testAddIfNonExist() throws Exception {
        SingleLinkedList list = new SingleLinkedList();
        
        list.addIfNonExist(null);
        assertEquals("[]", list.toString());
        
        list.addIfNonExist("A");
        list.addIfNonExist("B");
        assertEquals("[A -> B]", list.toString());
        
        list.addIfNonExist("A");
        assertEquals("[A -> B]", list.toString());
    
        list.addIfNonExist("B");
        assertEquals("[A -> B]", list.toString());
    }
    
    @Test
    public void testRemoveIfExist() throws Exception {
        SingleLinkedList list = new SingleLinkedList();
    
        list.removeIfExist(null);
        assertEquals("[]", list.toString());
    
        list.addIfNonExist("A");
        list.removeIfExist("A");
        assertEquals("[]", list.toString());
    
        list.addIfNonExist("A");
        list.addIfNonExist("B");
        list.addIfNonExist("C");
        
        list.removeIfExist("D");
        assertEquals("[A -> B -> C]", list.toString());
        
        list.removeIfExist("A");
        assertEquals("[B -> C]", list.toString());
        
        list.removeIfExist("C");
        assertEquals("[B]", list.toString());
        
        list.removeIfExist("B");
        assertEquals("[]", list.toString());
    }
    
    @Test
    public void testToString() throws Exception {
        SingleLinkedList list = new SingleLinkedList();
        assertEquals("[]", list.toString());
        
        list.addIfNonExist("A");
        assertEquals("[A]", list.toString());
        
        list.addIfNonExist("B");
        assertEquals("[A -> B]", list.toString());
        
        list.addIfNonExist("C");
        assertEquals("[A -> B -> C]", list.toString());
    }
    
}