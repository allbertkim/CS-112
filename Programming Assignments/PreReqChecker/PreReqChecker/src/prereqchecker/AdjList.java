package prereqchecker;

import java.util.*;
@SuppressWarnings("unchecked")
/**
 * Steps to implement this class main method:
 * 
 * Step 1:
 * AdjListInputFile name is passed through the command line as args[0]
 * Read from AdjListInputFile with the format:
 * 1. a (int): number of courses in the graph
 * 2. a lines, each with 1 course ID
 * 3. b (int): number of edges in the graph
 * 4. b lines, each with a source ID
 * 
 * Step 2:
 * AdjListOutputFile name is passed through the command line as args[1]
 * Output to AdjListOutputFile with the format:
 * 1. c lines, each starting with a different course ID, then 
 *    listing all of that course's prerequisites (space separated)
 */
public class AdjList {

    public static void main(String[] args) {
        if ( args.length < 2 ) {
            StdOut.println("Execute: java -cp bin prereqchecker.AdjList <adjacency list INput file> <adjacency list OUTput file>");
            return;
        }
        
        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);

        int a=StdIn.readInt();
        Graph myClassMap=new Graph(a);

        for(int i=0;i<a;i++) {
            String course=StdIn.readString();
            myClassMap.setClass(course, i);
        }

        int b=StdIn.readInt();
        for(int j=0;j<b;j++) {
            String course=StdIn.readString();
            String prereq=StdIn.readString();
            int n=myClassMap.getClassIndex(course);
            myClassMap.addPrereq(prereq, n);
        }

        for(int k=0;k<a;k++) {
            StdOut.print(myClassMap.getClassAtIndex(k));
            ArrayList<String> myList=(ArrayList<String>) myClassMap.getClassPrereqs(k);
            for(int l=0;l<myList.size();l++) {
                StdOut.print(" "+myList.get(l));
            }
            StdOut.println();
        }

        
	// WRITE YOUR CODE HERE
    }
}
