title: ConcurrentLinkedQueue详解
author: John Doe
date: 2020-04-27 09:59:10
tags:
categories: 多线程
---
### ConcurrentLinkedQueue简介

> ConcurrentLinkedQueue是一种无边界非堵塞线程安全的队列，底层通过单向链表实现，线程安全通过CAS操作实现。下图是它的类图关系，它继承了AbstractQueue类，实现Queue接口，具有Queue的基本特性。

<image src="/images/ConcurrentLinkedQueue.png"/>

> 该类内部通过两个volatile类型的Node节点来分别指向队列的首，尾节点。

### 构造方法

```
//无参构造方法首尾分别指向item为null的哨兵节点
public ConcurrentLinkedQueue() {
        head = tail = new Node<E>(null);
    }
```

```
	 //根据指定集合创建队列
    public ConcurrentLinkedQueue(Collection<? extends E> c) {
        Node<E> h = null, t = null;
        for (E e : c) {
            checkNotNull(e);//判断元素是否为null
            Node<E> newNode = new Node<E>(e);//根据item创建Node·
            if (h == null)
                h = t = newNode;//首次插入设置首尾节点指向
            else {
                t.lazySetNext(newNode);t的next指向新节点
                t = newNode;//尾部节点指向新节点
            }
        }
        //防止指定集合为空
        if (h == null)
            h = t = new Node<E>(null);
        head = h;
        tail = t;
    }
```

### offer()入队操作

> offer()方法向队列尾部添加一个新节点，由于ConcurrentLinkedQueue是无界队列因此offer()方法放回一直是true，还有多线程是基于CAS操作因此该方法不会堵塞。方法参数不能为null，否则会抛出NPE异常。

```
    public boolean offer(E e) {
        checkNotNull(e);//检测e是否为空
        final Node<E> newNode = new Node<E>(e);//创建新节点
			
        //从尾部开始插入
        for (Node<E> t = tail, p = t;;) {
            Node<E> q = p.next;
            //判断q是否为空，如果是则说明p为尾节点
            if (q == null) {
                // 尝试把p的next节点指向新节点
                if (p.casNext(null, newNode)) {

                    if (p != t) // 
                        casTail(t, newNode);  // 尝试将尾部节点设置为新节点，失败也没事也就说法其他线程已经设置了。
                    return true;
                }
                // Lost CAS race to another thread; re-read next
            }
            else if (p == q)
            	  //为了处理poll()方法导致的。。
                p = (t != (t = tail)) ? t : head;
            else
            		
                // 寻找尾部节点
                p = (p != t && t != (t = tail)) ? t : q;
        }
    }
```
> 该入队方法可以分为两步:
1. 定位到尾部节点：
> 因为为了节省CAS操作更新tail节点的次数，所以tail并不总是尾节点，可能他的netx才是尾节点。因此在循环体中需要判断tail是否有next节点。
2. 通过CAS操作尝试将入队节点设置为尾节点的next节点
> p.casNext(null, newNode)方法用于将入队节点设置为当前队列尾节点的next节点，q如果是null表示p是当前队列的尾节点，如果不为null表示有其他线程更新了尾节点，则需要重新获取当前队列的尾节点。

### 为啥tail不总是为尾部节点

> 普通队列中总是把入队节点设置为尾部节点，在offer方法我们可以写成。

```
public boolean offer(E e) {
    checkNotNull(e);
    final Node<E> newNode = new Node<E>(e);
    
    for (;;) {
        Node<E> t = tail;
        
        if (t.casNext(null ,newNode) && casTail(t, newNode)) {
            return true;
        }
    }
}
```
> 入队节点总是尾节点，这样的代码简单易读。但是每次入队的时候都要循环设置tail节点为入队节点，影响效率。
 · 引用:在JDK 1.7的实现中，doug lea使用hops变量来控制并减少tail节点的更新频率，并不是每次节点入队后都将 tail节点更新成尾节点，而是当tail节点和尾节点的距离大于等于常量HOPS的值（默认等于1）时才更新tail节点，tail和尾节点的距离越长使用CAS更新tail节点的次数就会越少，但是距离越长带来的负面效果就是每次入队时定位尾节点的时间就越长，因为循环体需要多循环一次来定位出尾节点，但是这样仍然能提高入队的效率，因为从本质上来看它通过增加对volatile变量的读操作来减少了对volatile变量的写操作，而对volatile变量的写操作开销要远远大于读操作，所以入队效率会有所提升。
 在JDK 1.8的实现中，tail的更新时机是通过p和t是否相等来判断的，其实现结果和JDK 1.7相同，即当tail节点和尾节点的距离大于等于1时，更新tail。
 
 ### poll()方法出队操作
 
 ```
     public E poll() {
        restartFromHead:
        for (;;) {
            for (Node<E> h = head, p = h, q;;) {
                E item = p.item;
						//item不为空和尝试设置p节点值为null
                if (item != null && p.casItem(item, null)) {
                    // Successful CAS is the linearization point
                    // for item to be removed from this queue.
                    if (p != h) // hop two nodes at a time
                        updateHead(h, ((q = p.next) != null) ? q : p);
                    return item;
                }
                //如果item为空或尝试设置失败就获得p的下一个节点，如果为空则说明队列为空
                else if ((q = p.next) == null) {
                    updateHead(h, p);
                    return null;
                }
                //重新开始循环
                else if (p == q)
                    continue restartFromHead;
                //设置p为p的下一个节点
                else
                    p = q;
            }
        }
    }
 ```
 > 该方法的主要逻辑就是首先获取头节点的元素，然后判断头节点元素是否为空，如果为空，表示另外一个线程已经进行了一次出队操作将该节点的元素取走，如果不为空，则使用CAS的方式将头节点的引用设置成null，如果CAS成功，则直接返回头节点的元素，如果不成功，表示另外一个线程已经进行了一次出队操作更新了head节点，导致元素发生了变化，需要重新获取头节点。
 
 ### peek()方法获得头部节点
 
 ```
     public E peek() {
        restartFromHead:
        for (;;) {
            for (Node<E> h = head, p = h, q;;) {
                E item = p.item;
                if (item != null || (q = p.next) == null) {
                    updateHead(h, p);
                    return item;
                }
                else if (p == q)
                    continue restartFromHead;
                else
                    p = q;
            }
        }
    }
 ```
 > 获得头部节点不会弹出，执行该方法会将head节点指向第一个非空的节点
 
 ### size()获得队列元素数量
 
 ```
     public int size() {
        int count = 0;
        for (Node<E> p = first(); p != null; p = succ(p))
            if (p.item != null)
                // Collection.size() spec says to max out
                if (++count == Integer.MAX_VALUE)
                    break;
        return count;
    }
 ```
 > 由于没有加锁，所以返回可能不准确
 
 ### 小结
 
 > ConcurrentLinkedQueue 的底层使用单向链表数据结构来保存队列元素，每个元素被 包装成一个 Node 节点。队列是靠头、尾节点来维护的，创建队列时头、尾节点指向一个 item 为 null 的哨兵节点。
