/**
 * This class contains a lot of public variables that can be updated
 * by other classes during a simulation, to collect information about
 * the run.
 */
public class Statistics
{

	/** Process */
	// The number of processes that have exited the system
	public long nofCompletedProcesses = 0;
	// The number of processes that have entered the system
	public long nofCreatedProcesses = 0;
	// Number of forced process switches
	public long nofForcedProcessSwitches = 0;

	/** IO */
	// Number of processed IO requests
	public long nofProcessedIOOperations = 0;
	public long IOQueueTime = 0;
	public long IOLargestQueue = 0;
	public long totalTimeSpentWaitingForIO = 0;
	public long totalTimeSpentInIO = 0;
	public long totalNofTimesInIoQueue = 0;


	/** CPU */
	public long totalCPUUsage = 0;
	public long CPUQueueTime = 0;
	public long CPULargestQueue = 0;
	public long totalTimeSpentInReadyQueue = 0;
	public long totalTimeSpentInCPU = 0;
	public long totalNofTimesInReadyQueue = 0;





	/** The total time that all completed processes have spent waiting for memory */
	public long totalTimeSpentWaitingForMemory = 0;
	/** The time-weighted length of the memory queue, divide this number by the total time to get average queue length */
	public long memoryQueueLengthTime = 0;
	/** The largest memory queue length that has occured */
	public long memoryQueueLargestLength = 0;
    
	/**
	 * Prints out a report summarizing all collected data about the simulation.
	 * @param simulationLength	The number of milliseconds that the simulation covered.
	 */
	public void printReport(long simulationLength) {

		double avgThroughput = (nofCompletedProcesses*1000.0)/simulationLength;
		double fracCpuUsage = (totalCPUUsage*100.0)/simulationLength;
		double fracCpuWaitTime = ((simulationLength-totalCPUUsage)*100.0)/simulationLength;


		System.out.println();
		System.out.println("Simulation statistics:");
		System.out.println();
		System.out.println("Number of completed processes:                                "+nofCompletedProcesses);
		System.out.println("Number of created processes:                                  "+nofCreatedProcesses);
		System.out.println("Number of forced process switches:							  "+nofForcedProcessSwitches);
		System.out.println("Number of completed IO operations:						 	  "+nofProcessedIOOperations);
		System.out.println("Average throughput: 										  "+avgThroughput);
		System.out.println();
		System.out.println("Total CPU usage time:										  "+totalCPUUsage);
		System.out.println("Total CPU wait time:										  "+(simulationLength-totalCPUUsage));
		System.out.println("Fraction of CPU usage time:									  "+fracCpuUsage);
		System.out.println("Fraction of CPU wait time:									  "+fracCpuWaitTime);
		System.out.println();
		System.out.println("Largest memory queue length:								  "+memoryQueueLargestLength);
		System.out.println("Largest CPU queue length									  "+CPULargestQueue);
		System.out.println("Largest IO queue length									  	  "+IOLargestQueue);
		System.out.println("Average memory queue length									  "+memoryQueueLengthTime/simulationLength);
		System.out.println("Average CPU queue length									  "+CPUQueueTime/simulationLength);
		System.out.println("Average IO queue length									  	  "+IOQueueTime/simulationLength);
		System.out.println();

		if(nofCompletedProcesses > 0) {
			System.out.println("Average # of times a process has been placed in memory queue: 		"+1);
			System.out.println("Average # of times a process has been placed in CPU queue: 			"+totalNofTimesInReadyQueue/nofCompletedProcesses);
			System.out.println("Average # of times a process has been placed in IO queue: 			"+totalNofTimesInIoQueue/nofCompletedProcesses);
			System.out.println();
			System.out.println("Average time spent in system per finished process:					"+
					(totalTimeSpentWaitingForMemory + totalTimeSpentInReadyQueue + totalTimeSpentInCPU
							+ totalTimeSpentWaitingForIO + totalTimeSpentInIO)/nofCompletedProcesses);
			System.out.println("Average time spent waiting for memory space per finished process:	"+
					totalTimeSpentWaitingForMemory/nofCompletedProcesses);
			System.out.println("Average time spent waiting for CPU time per finished process:		"+
					totalTimeSpentInReadyQueue/nofCompletedProcesses);
			System.out.println("Average time spent in CPU per finished process:						"+
					totalTimeSpentInCPU/nofCompletedProcesses);
			System.out.println("Average time spent waiting for IO per finished process:				"+
					totalTimeSpentWaitingForIO/nofCompletedProcesses);
			System.out.println("Average time spent in IO per finished process:						"+
					totalTimeSpentInIO/nofCompletedProcesses);
		}
	}
}
