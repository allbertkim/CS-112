package prereqchecker;

import java.util.*;
@SuppressWarnings("unchecked")
/**
 * 
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
 * EligibleInputFile name is passed through the command line as args[1]
 * Read from EligibleInputFile with the format:
 * 1. c (int): Number of courses
 * 2. c lines, each with 1 course ID
 * 
 * Step 3:
 * EligibleOutputFile name is passed through the command line as args[2]
 * Output to EligibleOutputFile with the format:
 * 1. Some number of lines, each with one course ID
 */
public class Eligible {
    public static void main(String[] args) {

        if ( args.length < 3 ) {
            StdOut.println("Execute: java -cp bin prereqchecker.Eligible <adjacency list INput file> <eligible INput file> <eligible OUTput file>");
            return;
        }

        StdIn.setFile(args[0]);
        StdOut.setFile(args[2]);

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
        
        Queue<String> classesTaken=new Queue<String>();

        StdIn.setFile(args[1]);
        int c=StdIn.readInt();
        for(int k=0;k<c;k++) {
            classesTaken.enqueue(StdIn.readString());
        }

        boolean[] marked=new boolean[a];

        
        while(!classesTaken.isEmpty()) {
            String v=classesTaken.dequeue();
            int n=myClassMap.getClassIndex(v);
            if(marked[n]!=true) {
                marked[n]=true;
                ArrayList<String> myPrereqs=(ArrayList<String>) myClassMap.getClassPrereqs(n);
                for(int l=0;l<myPrereqs.size();l++) {
                    classesTaken.enqueue(myPrereqs.get(l));
                    
                }
            }
        }

        ArrayList<String> eligible= new ArrayList<String>();

        for(int d=0;d<a;d++){
            if(!marked[d]) {
                boolean prereqsDone=true;
                ArrayList<String> myPrereqs=(ArrayList<String>) myClassMap.getClassPrereqs(d);
                for(int e=0;e<myPrereqs.size();e++) {
                    int x=myClassMap.getClassIndex(myPrereqs.get(e));
                    if(!marked[x]) {
                        prereqsDone=false;
                    }
                }
                if(prereqsDone) eligible.add(myClassMap.getClassAtIndex(d));
            }
        }

        for(int y=0;y<eligible.size();y++) {
            StdOut.println(eligible.get(y));
        }

	// WRITE YOUR CODE HERE
    }
}
