package prereqchecker;

import java.util.*;
@SuppressWarnings("unchecked")

public class Graph {
	private String[] classList;  // using Object instead of T[]
	private Object[] adjPrereqList;   // using Object instead of DLL<Integer>[]
	private int capacity;
	
	
	public Graph(int capacity) {
        this.capacity=capacity;
		this.classList=new String[capacity];
        this.adjPrereqList=new Object[capacity];
		for (int i=0;i<this.capacity;i++) {
			ArrayList<String> myList=new ArrayList<String>();
			adjPrereqList[i]=(Object) myList;
        }
    }

    public String[] getClassList() {
        return classList;
    }

    public String getClassAtIndex(int index) {
        return classList[index];
    }

    public Object[] getAdjPrereqList(){
        return adjPrereqList;
    }

    public Object getClassPrereqs(int index){
        return adjPrereqList[index];
    }

    public void setClass(String className, int index) {
        classList[index]=className;
    }

    public void addPrereq(String className, int index) {
        ArrayList<String> addedTo = (ArrayList<String>)adjPrereqList[index];
        addedTo.add(className);
    }

    public int getClassIndex(String className) {
        String[] array=getClassList();
        for(int i=0;i<capacity;i++) {
            if(className.equals(array[i])) {
                return i;
            }
        }
        return -1;
    }
    
}
