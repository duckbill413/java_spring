package linkedList;

public interface LinkedList<T> {
	boolean add(T value);//리스트에 요소 추가
	void add(int index, T value);//리스트의 특정 위치에 요소를 추가
	T remove(int index);//리스트의 index 요소를 삭제
	boolean remove(Object value);//리스트의  특정 요소를 삭제
	T get(int index);//리스트의 특정 위치 요소를 반환
	void set(int index, T value);//리스트의 특정 위치 요소를 새 요소로 대체
	boolean contains(Object value);//리스트에 특정 요소가 있는지 확인
	int indexOf(Object value);//특정 요소의 index를 반환
	int size();//리스트의 크기를 반환
	boolean isEmpty();//리스트가 비어있는지 반환
	public void clear();//리스트의 비우기	
}
