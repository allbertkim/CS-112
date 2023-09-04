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
 * SchedulePlanInputFile name is passed through the command line as args[1]
 * Read from SchedulePlanInputFile with the format:
 * 1. One line containing a course ID
 * 2. c (int): number of courses
 * 3. c lines, each with one course ID
 * 
 * Step 3:
 * SchedulePlanOutputFile name is passed through the command line as args[2]
 * Output to SchedulePlanOutputFile with the format:
 * 1. One line containing an int c, the number of semesters required to take the
 * course
 * 2. c lines, each with space separated course ID's
 */
public class SchedulePlan {
    public static void main(String[] args) {

        if (args.length < 3) {
            StdOut.println(
                    "Execute: java -cp bin prereqchecker.SchedulePlan <adjacency list INput file> <schedule plan INput file> <schedule plan OUTput file>");
            return;
        }

        StdIn.setFile(args[0]);
        StdOut.setFile(args[2]);

        int a = StdIn.readInt();
        Graph myClassMap = new Graph(a);

        for (int i = 0; i < a; i++) {
            String course = StdIn.readString();
            myClassMap.setClass(course, i);
        }

        int b = StdIn.readInt();
        for (int j = 0; j < b; j++) {
            String course = StdIn.readString();
            String prereq = StdIn.readString();
            int n = myClassMap.getClassIndex(course);
            myClassMap.addPrereq(prereq, n);
        }

        Queue<String> classesTaken = new Queue<String>();
        Queue<String> preReqCollector = new Queue<String>();
        Queue<Integer>distancing=new Queue<Integer>();

        StdIn.setFile(args[1]);
        String targetCourse = StdIn.readString();
        int nCoursesTaken = StdIn.readInt();
        for (int i = 0; i < nCoursesTaken; i++) {
            classesTaken.enqueue(StdIn.readString());
        }

        boolean[] marked = new boolean[a];
        int[] distances = new int[a];
        for (int c = 0; c < distances.length; c++) {
            distances[c] = -1;
        }

        while (!classesTaken.isEmpty()) {
            String v = classesTaken.dequeue();
            int n = myClassMap.getClassIndex(v);
            if (marked[n] != true) {
                marked[n] = true;
                ArrayList<String> myPrereqs = (ArrayList<String>) myClassMap.getClassPrereqs(n);
                for (int l = 0; l < myPrereqs.size(); l++) {
                    classesTaken.enqueue(myPrereqs.get(l));
                }
            }
        }

        int targetIDNum = myClassMap.getClassIndex(targetCourse);
        ArrayList<String> targetPrereqs = (ArrayList<String>) myClassMap.getClassPrereqs(targetIDNum);
        for (int j = 0; j < targetPrereqs.size(); j++) {
            preReqCollector.enqueue(targetPrereqs.get(j));
            distancing.enqueue(0);
        }

        while (!preReqCollector.isEmpty()) {
            String v = preReqCollector.dequeue();
            int n = myClassMap.getClassIndex(v);
            int distanceFromEnd=distancing.dequeue();
            if (!marked[n]) {
                if(distanceFromEnd>=distances[n]) distances[n]=distanceFromEnd;
                ArrayList<String> myPrereqs = (ArrayList<String>) myClassMap.getClassPrereqs(n);
                for (int l = 0; l < myPrereqs.size(); l++) {
                    preReqCollector.enqueue(myPrereqs.get(l));
                    distancing.enqueue(distanceFromEnd+1);
                }
                
            }

        }

        int maxDistance = -1;
        for (int h = 0; h < distances.length; h++) {
            if (distances[h] > maxDistance)
                maxDistance = distances[h];
        }

        StdOut.println(maxDistance+1);

        for (int o = maxDistance; 0 <= o; o--) {
            for (int p = 0; p < distances.length; p++) {
                if (distances[p] == o)
                    StdOut.print(myClassMap.getClassAtIndex(p) + " ");
            }
            StdOut.println();
        }

    }

    // WRITE YOUR CODE HERE

}
