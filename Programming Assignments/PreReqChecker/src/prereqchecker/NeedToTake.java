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
 * NeedToTakeInputFile name is passed through the command line as args[1]
 * Read from NeedToTakeInputFile with the format:
 * 1. One line, containing a course ID
 * 2. c (int): Number of courses
 * 3. c lines, each with one course ID
 * 
 * Step 3:
 * NeedToTakeOutputFile name is passed through the command line as args[2]
 * Output to NeedToTakeOutputFile with the format:
 * 1. Some number of lines, each with one course ID
 */
public class NeedToTake {
    public static void main(String[] args) {

        if ( args.length < 3 ) {
            StdOut.println("Execute: java NeedToTake <adjacency list INput file> <need to take INput file> <need to take OUTput file>");
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
        String targetCourse=StdIn.readString();
        int nCoursesTaken=StdIn.readInt();
        for(int i=0;i<nCoursesTaken;i++) {
            classesTaken.enqueue(StdIn.readString());
        }

        boolean[] marked=new boolean[a];
        boolean[] visited=new boolean[a];

        
        while(!classesTaken.isEmpty()) {
            String v=classesTaken.dequeue();
            int n=myClassMap.getClassIndex(v);
            if(marked[n]!=true) {
                marked[n]=true;
                visited[n]=true;
                ArrayList<String> myPrereqs=(ArrayList<String>) myClassMap.getClassPrereqs(n);
                for(int l=0;l<myPrereqs.size();l++) {
                    classesTaken.enqueue(myPrereqs.get(l));
                    
                }
            }
        }

        Queue<String> preReqCollector=new Queue<String>();

        int targetIDNum=myClassMap.getClassIndex(targetCourse);
        ArrayList<String> targetPrereqs=(ArrayList<String>) myClassMap.getClassPrereqs(targetIDNum);
        for(int j=0;j<targetPrereqs.size();j++) {
            preReqCollector.enqueue(targetPrereqs.get(j));
        }

        ArrayList<String> neededPrereqs=new ArrayList<String>();

        while(!preReqCollector.isEmpty()) {
            String v=preReqCollector.dequeue();
            int n=myClassMap.getClassIndex(v);
            if(visited[n]!=true) {
                visited[n]=true;
                if(!marked[n]) neededPrereqs.add(v);
                ArrayList<String> myPrereqs=(ArrayList<String>) myClassMap.getClassPrereqs(n);
                for(int l=0;l<myPrereqs.size();l++) {
                    preReqCollector.enqueue(myPrereqs.get(l));
                    
                }
            }
        }

        for(int g=0;g<neededPrereqs.size();g++) {
            StdOut.println(neededPrereqs.get(g));
        }

	// WRITE YOUR CODE HERE
    }
}
