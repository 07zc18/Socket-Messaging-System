import java.util.*;

public class FCFSScheduling 
{
	public static class Process
	{
		String ID;
		int arrivalTime;
		int burstTime;
		int waitingTime;
		int completionTime;
		int turnaroundTime;
		int startTime;
		
		public Process(String ID, int arrivalTime, int burstTime)
		{
			this.ID = ID;
			this.arrivalTime = arrivalTime;
			this.burstTime = burstTime;
		}
	}
	
	//arrayList to store the added processes
	static ArrayList<Process> process = new ArrayList<>();
	static Scanner input = new Scanner(System.in);
	static boolean schedulerRun = false;
	
	public static void mainMenu()
	{
        int choice;
        
        do 
        {
        	//display process scheduler
            System.out.println("\n==================================");
            System.out.println("      FCFS PROCESS SCHEDULER      ");
            System.out.println("==================================");
            System.out.println("1. Add Process");
            System.out.println("2. View Process List");
            System.out.println("3. Run Scheduler");
            System.out.println("4. Display Results");
            System.out.println("5. Exit");
            System.out.println("==================================");
            System.out.print("Enter your choice: ");
            
            choice = input.nextInt();
            
            //switch case
            switch (choice) {
                case 1:
                    addProcess();
                    break;

                case 2:
                    viewProcessList();
                    break;

                case 3:
                    runScheduler();
                    break;
                    
                case 4:
                	displayResults();
                	break;
                	
                case 5:
                    System.out.println("Exiting program...");
                    System.out.println("Program exited.");
                    break;

                default:
                    System.out.println("Invalid choice! Please try again.");
                    break;
            }
            
        } while (choice != 5);
        
        input.close();
	}

	public static void addProcess()
	{
		System.out.print("Enter Process ID: ");
        String id = input.next();
        
        //check if the process id is already exist
        for (Process p : process) 
        {
            if (p.ID.equals(id)) 
            {
                System.out.println("Process ID already exists! Cannot add duplicate.");
                System.out.println("Please enter another Process ID.");
                return;
            }
        }

        System.out.print("Enter Arrival Time: ");
        int at = input.nextInt();
        
        if (at < 0)
        {
        	System.out.println("Please enter a valid number!");
        	return;
        }

        System.out.print("Enter Burst Time: ");
        int bt = input.nextInt();
        
        if (bt <= 0)
        {
            System.out.println("Burst Time must be greater than 0.");
            return;
        }
        
        process.add(new Process(id, at, bt));
        
        schedulerRun = false;
        
        System.out.println("Process added successfully!");
	}
	
	public static void viewProcessList()
	{
		if (process.isEmpty()) 
    	{
            System.out.println("\nNo process available!");
            return;
        } 
		System.out.println("\nProcess List:");

        for (Process p : process) 
        {
        	System.out.println("Process ID: " + p.ID +
                        " | Arrival Time: " + p.arrivalTime +
                        " | Burst Time: " + p.burstTime);
        }
	}
	
	public static void runScheduler()
	{
		if (process.isEmpty()) {
	        System.out.println("\nNo processes to schedule!");
	        return;
	    }

	    //sort by arrival time
	    process.sort(Comparator.comparingInt(p -> p.arrivalTime));

	    int currentTime = 0;

	    System.out.println("\n=== FCFS Execution Order ===");

	    for (int i = 0; i < process.size(); i++) {

	        Process p = process.get(i);

	        if (currentTime < p.arrivalTime) {
	            currentTime = p.arrivalTime;
	        }

	        p.startTime = currentTime;
	        p.waitingTime = p.startTime - p.arrivalTime;
	        p.completionTime = currentTime + p.burstTime;
	        p.turnaroundTime = p.completionTime - p.arrivalTime;

	        currentTime = p.completionTime;

	        System.out.print(p.ID);

	        if (i != process.size() - 1) {
	            System.out.print(" -> ");
	        }
	    }

	    System.out.println();
	    System.out.println("Scheduling completed.");

	    schedulerRun = true;
	}
	
	public static void displayResults()
	{
	    if (process.isEmpty())
	    {
	        System.out.println("\nNo data available!");
	        return;
	    }

	    if (!schedulerRun)
	    {
	        System.out.println("\nPlease run the scheduler first.");
	        return;
	    }

	    System.out.println("\n======================== FCFS Scheduling Results ========================");

	    System.out.printf("%-10s %-8s %-8s %-8s %-12s %-10s %-12s%n",
	            "Process", "Arrival", "Burst", "Start",
	            "Completion", "Waiting", "Turnaround");

	    System.out.println("-------------------------------------------------------------------------");

	    double totalWT = 0;
	    double totalTAT = 0;

	    for (Process p : process)
	    {
	        System.out.printf("%-10s %-8d %-8d %-8d %-12d %-10d %-12d%n",
	                p.ID,
	                p.arrivalTime,
	                p.burstTime,
	                p.startTime,
	                p.completionTime,
	                p.waitingTime,
	                p.turnaroundTime);

	        totalWT += p.waitingTime;
	        totalTAT += p.turnaroundTime;
	    }

	    System.out.println("-------------------------------------------------------------------------");

	    System.out.printf("Average Waiting Time    : %.2f%n", totalWT / process.size());
	    System.out.printf("Average Turnaround Time : %.2f%n", totalTAT / process.size());
	}
	
	public static void main (String[] args)
	{
		mainMenu();
	}
}
