import java.util.*;

public class DeadlockDetection {

    static Scanner input = new Scanner(System.in);

    static int n;

    static String[] process;
    static String[] hold;
    static String[] request;

    static ArrayList<Integer>[] graph;

    public static void menu() {

        int choice;

        do {
        	// main menu
            System.out.println("\n==================================");
            System.out.println("    DEADLOCK DETECTION SYSTEM     ");
            System.out.println("==================================");
            System.out.println("1. Add Process");
            System.out.println("2. View Current Scenario");
            System.out.println("3. Display Wait-For Graph");
            System.out.println("4. Detect Deadlock");
            System.out.println("5. Exit");
            System.out.println("==================================");
            System.out.print("Enter your choice: ");

            choice = input.nextInt();

            switch (choice) {

                case 1:
                    inputData();
                    break;

                case 2:
                    if (process == null) {
                        System.out.println("Please add processes first.");
                    } else {
                        viewScenario();
                    }
                    break;

                case 3:
                    if (process == null) {
                        System.out.println("Please add processes first.");
                    } else {
                        createWaitForGraph();
                        displayGraph();
                    }
                    break;

                case 4:
                    if (process == null) {
                        System.out.println("Please add processes first.");
                    } else {
                        createWaitForGraph();
                        detectDeadlock();
                    }
                    break;
                    
                case 5:
                    System.out.println("Exiting program...");
                    System.out.println("Program exited.");
                    break;
                    
                default:
                    System.out.println("Invalid choice! Please try again.");
            }

        } while (choice != 5);

        input.close();
    }

    public static void main(String[] args) {
    	
    	menu();
    	
    }

    //function to add process
    public static void inputData() {

    	System.out.print("Enter number of processes: ");
    	n = input.nextInt();

    	// make sure process number entered is greater than 0
    	if (n <= 0) {
    	    System.out.println("Number of processes must be greater than 0.");
    	    return;
    	}

        process = new String[n];
        hold = new String[n];
        request = new String[n];


        for (int i = 0; i < n; i++) {


            System.out.println("\nProcess " + (i + 1));


            System.out.print("Process ID: ");
            process[i] = input.next();


            System.out.print("Holding Resource: ");
            hold[i] = input.next();


            System.out.print("Requesting Resource: ");
            request[i] = input.next();

        }

    }

    //build wait-for-graph base on requested resources
    public static void createWaitForGraph() {


        graph = new ArrayList[n];


        for (int i = 0; i < n; i++) {

            graph[i] = new ArrayList<>();

        }


        // Compare every process with every other process
        for (int i = 0; i < n; i++) {


            for (int j = 0; j < n; j++) {

                // If Process i requests a resource held by Process j,
                // add an edge from Process i to Process j
                if (i != j) {


                    // Process i waits for resource held by process j

                    if (request[i].equals(hold[j])) {


                        graph[i].add(j);

                    }

                }

            }

        }

    }
    
    // display processes with their held and requested resources
    public static void viewScenario() {

    	if (process == null || n == 0) {
    	    System.out.println("\nNo process has been added.");
    	    return;
    	}

        System.out.println("\n=========== CURRENT SCENARIO ===========");

        System.out.printf("%-10s %-15s %-15s%n",
                "Process", "Holding", "Requesting");

        System.out.println("----------------------------------------");

        for (int i = 0; i < n; i++) {
            System.out.printf("%-10s %-15s %-15s%n",
                    process[i],
                    hold[i],
                    request[i]);
        }
    }
    
    // display wait-for graph
    public static void displayGraph() {

    	if (process == null || n == 0) {
    	    System.out.println("\nNo process has been added.");
    	    return;
    	}

        System.out.println("\n===== WAIT-FOR GRAPH =====");


        for (int i = 0; i < n; i++) {


            System.out.print(process[i]);


            if (!graph[i].isEmpty()) {


                System.out.print(" --> ");


                for (int j = 0; j < graph[i].size(); j++) {


                    System.out.print(
                        process[graph[i].get(j)]
                    );


                    if (j < graph[i].size() - 1) {

                        System.out.print(", ");

                    }

                }

            }


            System.out.println();

        }

    }

    //detect whether a deadlock cycle exists
    public static void detectDeadlock() {

    	if (process == null || n == 0) {
    	    System.out.println("\nNo process has been added.");
    	    return;
    	}
    	
    	// Keep track of visited processes
        boolean[] visited = new boolean[n];

        // Keep track of the current DFS recursion stack
        boolean[] stack = new boolean[n];

    	// Store the deadlock cycle path
        ArrayList<Integer> path = new ArrayList<>();


        System.out.println("\n===== DEADLOCK CHECK =====");



        for (int i = 0; i < n; i++) {

        	// If DFS finds a cycle, a deadlock exists
            if (dfs(i, visited, stack, path)) {


                System.out.println("Deadlock Detected!");


                System.out.print("Deadlock Cycle: ");


                for (int p : path) {

                    System.out.print(process[p] + " -> ");

                }


                System.out.println(process[path.get(0)]);


                return;

            }

        }



        System.out.println("No Deadlock Detected.");

    }

    // Perform Depth First Search (DFS) to detect cycles
    public static boolean dfs(
            int node,
            boolean[] visited,
            boolean[] stack,
            ArrayList<Integer> path) {



        // Cycle found
        if (stack[node]) {

            return true;

        }

        if (visited[node]) {

            return false;

        }

        visited[node] = true;

        stack[node] = true;


        path.add(node);



        for (int next : graph[node]) {


            if (dfs(next, visited, stack, path)) {

                return true;

            }

        }



        stack[node] = false;


        path.remove(path.size() - 1);


        return false;

    }

}