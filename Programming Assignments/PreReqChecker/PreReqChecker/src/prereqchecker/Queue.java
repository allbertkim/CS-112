package prereqchecker;

public class Queue<T> {
	private DLL<T> myList;
	
	public Queue() {
		myList=new DLL<T>();
	}
	
	public void enqueue(T element) {
		myList.addFirst(element);
	}
	
	public T dequeue() {
		T element=null;
		if (myList.size()>0) {
			element = myList.getLast();
			myList.deleteLast();
		}
		return element;
	}
	
	public boolean isEmpty() {
		return myList.size()==0;
	}
	
	public void traverse() {
		myList.traverse();
	}

}
