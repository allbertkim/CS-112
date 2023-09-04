package prereqchecker;

import java.util.ArrayList;
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
 * ValidPreReqInputFile name is passed through the command line as args[1]
 * Read from ValidPreReqInputFile with the format:
 * 1. 1 line containing the proposed advanced course
 * 2. 1 line containing the proposed prereq to the advanced course
 * 
 * Step 3:
 * ValidPreReqOutputFile name is passed through the command line as args[2]
 * Output to ValidPreReqOutputFile with the format:
 * 1. 1 line, containing either the word "YES" or "NO"
 */
public class ValidPrereq {
    public static void main(String[] args) {

        if ( args.length < 3 ) {
            StdOut.println("Execute: java -cp bin prereqchecker.ValidPrereq <adjacency list INput file> <valid prereq INput file> <valid prereq OUTput file>");
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

        StdIn.setFile(args[1]);
        String mainCourse=StdIn.readString();
        String susPrereq=StdIn.readString();

        boolean[] marked=new boolean[a];

        Queue<String> suspect=new Queue<String>();
        suspect.enqueue(susPrereq);
        while(!suspect.isEmpty()) {
            String v=suspect.dequeue();
            int n=myClassMap.getClassIndex(v);
            if(marked[n]!=true) {
                marked[n]=true;
                ArrayList<String> myPrereqs=(ArrayList<String>) myClassMap.getClassPrereqs(n);
                for(int l=0;l<myPrereqs.size();l++) {
                    suspect.enqueue(myPrereqs.get(l));
                    StdOut.print(myPrereqs.get(l));
                }
            }
        }

        boolean[] visited=new boolean[a];

        Queue<String> existingPrereqs=new Queue<String>();
        existingPrereqs.enqueue(mainCourse);
        while(!existingPrereqs.isEmpty()) {
            String w=existingPrereqs.dequeue();
            int m=myClassMap.getClassIndex(w);
            if(visited[m]==false){
            marked[m]=false;
            visited[m] = true;
            ArrayList<String> myPrereqs=(ArrayList<String>) myClassMap.getClassPrereqs(m);
            for(int k=0;k<myPrereqs.size();k++) {
                existingPrereqs.enqueue(myPrereqs.get(k));
            }
        }

        }

        boolean isThereNewPrereq = false;
        for(int c=0;c<marked.length;c++) {
            if (marked[c]==true) {
                isThereNewPrereq=true;
            }
        }

        if (isThereNewPrereq==true) {
            StdOut.print("NO");
        }
        if (isThereNewPrereq==false) {
            StdOut.print("YES");
        }
	// WRITE YOUR CODE HERE
    }
}
