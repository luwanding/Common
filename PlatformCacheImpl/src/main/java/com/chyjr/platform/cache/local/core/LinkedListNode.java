package com.chyjr.platform.cache.local.core;

/**
 * @ProjectName:  [PlatformCacheImpl]
 * @Package:      [com.chyjr.platform.cache.local.core.LinkedList.java] 
 * @ClassName:    [LinkedList]  
 * @Description:  [local Cache storage structure node]  
 * @Author:       [jx_xudelin]  
 * @CreateDate:   [Oct 15, 2014 1:18:58 PM]  
 * @UpdateUser:   [jx_xudelin]  
 * @UpdateDate:   [Oct 15, 2014 1:18:58 PM]  
 * @UpdateRemark: [说明本次修改内容] 
 * @Version:      [v1.0]
 *
 */
public class LinkedListNode {

    public LinkedListNode previous;
    public LinkedListNode next;
    public Object object;

    /**
     * This class is further customized for the CoolServlets cache system. It
     * maintains a timestamp of when a Cacheable object was first added to
     * cache. Timestamps are stored as long values and represent the number
     * of milleseconds passed since January 1, 1970 00:00:00.000 GMT.<p>
     * <p/>
     * The creation timestamp is used in the case that the cache has a
     * maximum lifetime set. In that case, when
     * [current time] - [creation time] > [max lifetime], the object will be
     * deleted from cache.
     */
    public long timestamp;

    /**
     * Constructs a new linked list node.
     *
     * @param object   the Object that the node represents.
     * @param next     a reference to the next LinkedListNode in the list.
     * @param previous a reference to the previous LinkedListNode in the list.
     */
    public LinkedListNode(Object object, LinkedListNode next,
                          LinkedListNode previous) {
        this.object = object;
        this.next = next;
        this.previous = previous;
    }

    /**
     * Removes this node from the linked list that it is a part of.
     */
    public void remove() {
        previous.next = next;
        next.previous = previous;
    }

    /**
     * Returns a String representation of the linked list node by calling the
     * toString method of the node's object.
     *
     * @return a String representation of the LinkedListNode.
     */
    @Override
    public String toString() {
        return object == null ? "null" : object.toString();
    }
}