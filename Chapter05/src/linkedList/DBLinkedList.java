package linkedList;

import java.util.NoSuchElementException;

public class DBLinkedList<T> implements LinkedList<T>{
	private Node<T> head;
	private Node<T> tail;
	private int size;
	
	public DBLinkedList() {
		this.head = null;
		this.tail = null;
		this.size = 0;
	}
	
	private Node<T> search(int index){
		if(index <0 || index >=size)
			throw new IndexOutOfBoundsException();
		
		if(index > size / 2) {
			Node<T> fi = tail;
			for(int i=size-1; i>index; i--) {
				fi = fi.prev;
			}
			return fi;
		}
		else
		{
			Node<T> fi = head;
			for(int i=0; i<index; i++) {
				fi = fi.next;
			}
			return fi;
		}
	}
	
	public void addFirst(T value) {
		Node<T> newNode = new Node<T>(value);
		newNode.next = head;
		
		if(head!=null)
			head.prev = newNode;
		head = newNode;
		size++;
		
		if(head.next==null) {
			tail = head;
		}
	}
	public void addLast(T value) {
		Node<T> newNode = new Node<T>(value);
		
		if(size==0) {
			addFirst(value);
			return;
		}
		
		tail.next = newNode;
		newNode.prev = tail;
		tail = newNode;
		size++;
	}
	@Override
	public boolean add(T value) {
		addLast(value);
		return true;
	}

	@Override
	public void add(int index, T value) {
		if(index < 0 || index > size)
			throw new IndexOutOfBoundsException();
		
		if(index==0) {
			addFirst(value);
			return;
		}
		if(index==size)
		{
			addLast(value);
			return;
		}
		
		Node<T> prevNode = search(index-1);
		Node<T> nextNode = search(index+1);
		Node<T> newNode = new Node<T>(value);
		

		prevNode.next = newNode;
		newNode.prev = prevNode;
		
		nextNode.prev = newNode;
		newNode.next = nextNode;
		size++;
	}
	
	public T remove() {
		Node<T> headNode = head;
		if(headNode==null)
			throw new NoSuchElementException();
		
		T reData = headNode.data;
		Node<T> nextNode = headNode.next;
		
		head.data = null;
		head.next = null;
		
		if(nextNode!=null)
			nextNode.prev = null;
		
		head = nextNode;
		size--;
		
		if(size==0)
			tail = null;
		
		return reData;
	}
	@Override
	public T remove(int index) {
		if(index < 0 || index >= size)
			throw new IndexOutOfBoundsException();
		
		if(index==0) {
			T reData = head.data;
			remove();
			return reData;
		}
		
		Node<T> prevNode = search(index-1);
		Node<T> nextNode = search(index+1);
		Node<T> delNode = prevNode.next;
		
		T reData = delNode.data;
		
		prevNode.next = null;
		delNode.prev = null;
		delNode.next = null;
		delNode.data = null;
		
		if(nextNode!=null) {
			nextNode.prev = null;
			nextNode.prev = prevNode;
			prevNode.next = nextNode;
		}
		else
			tail = prevNode;
		
		size--;
		return reData;
	}

	@Override
	public boolean remove(Object value) {
		Node<T> prevNode = head;
		Node<T> fi = null;
		for(fi = head; fi!=null; fi = fi.next) {
			if(value.equals(fi.data))
				break;
			prevNode = fi;
		}
		if(fi==null)
			return false;
		
		if(fi.equals(head)) {
			remove();
			return true;
		}
		else {
			Node<T> nextNode = fi.next;
			
			prevNode.next = null;
			fi.prev = null;
			fi.next = null;
			fi.data =null;//fi==delNode
			
			if(nextNode != null) {
				nextNode.prev = null;
				nextNode.prev = prevNode;
				prevNode.next = nextNode;
			}
			else
				tail = prevNode;
			size--;
			return true;
		}
	}

	@Override
	public T get(int index) {
		return search(index).data;
	}

	@Override
	public void set(int index, T value) {
		Node<T> replaceNode = search(index);
		replaceNode.data = null;
		replaceNode.data = value;
	}

	@Override
	public int indexOf(Object value) {
		int index = 0;
		Node<T> fi;
		for(fi=head; fi!=null; fi=fi.next) {
			if(value.equals(fi.data))
				return index;
			index++;
		}
		return -1;
	}
	public int indexOfLast(Object value) {
		int index = size-1;
		Node<T> fi;
		for(fi = tail; fi!=null; fi=fi.prev) {
			if(value.equals(fi.data))
				return index;
			index--;
		}
		return -1;
	}
	
	@Override
	public boolean contains(Object value) {
		return indexOf(value) >= 0;
	}


	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size==0;
	}

	@Override
	public void clear() {
		for(Node<T> i = head; i!=null;)
		{
			Node<T> nextNode = i.next;
			i.data = null;
			i.prev = null;
			i.next = null;
			i = nextNode;
		}
		head = tail = null;
		size = 0;
	}
	
}
