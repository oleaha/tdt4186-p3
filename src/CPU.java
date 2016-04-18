import java.util.*;

public class CPU {

    private Queue CPUQueue;
    private Process active;
    private Statistics stats;
    private Gui gui;
    private long maxCPUTime;


    public CPU(Gui gui, Queue CPUQueue, long maxCPUTime, Statistics stats) {
        this.gui = gui;
        this.CPUQueue = CPUQueue;
        this.maxCPUTime = maxCPUTime;
        this.stats = stats;
    }

    public Event insertProcess(Process p, long clock) {
        // Add process to queue
        this.CPUQueue.insert(p);

        // Check if the CPU is idle, insert a process that is in the CPU Queue
        if(active == null) {
            return switchProcess(clock);
        }

        return null;
    }

    public Event switchProcess(long clock) {

        // Check if the CPU is active
        if(active != null) {
            // The CPU is active, check if there is a process in the queue
            if(!CPUQueue.isEmpty()) {
                // Place the active process in the last queueing pos
                active.leaveCPU(clock);
                CPUQueue.insert(active);

                // Take a new request from the CPU queue and put in CPU
                active = (Process)CPUQueue.removeNext();
                active.enterCPU(clock);
                gui.setCpuActive(active);
                stats.nofForcedProcessSwitches++;
            }
        } else {
            if(!CPUQueue.isEmpty()) {
                // Cast to process
                active = (Process)CPUQueue.removeNext();
                // Log the time when process enters CPU
                active.enterCPU(clock);
                gui.setCpuActive(active);
            }
        }

        if(active != null) {
            return active.getNextEvent(clock, maxCPUTime);
        }
        return null;
    }

    public Process getActive() {
        return active;
    }

    public Event activeLeftCPU(long c) {
        active = null;
        gui.setCpuActive(active);
        return switchProcess(c);
    }

    public void timePassed(long time) {
        if(active != null) {
            active.CPUTimePassed(time);
            stats.totalCPUUsage += time;
        }
        stats.CPUQueueTime += CPUQueue.getQueueLength() * time;
        if(CPUQueue.getQueueLength() > stats.CPULargestQueue) {
            stats.CPULargestQueue = CPUQueue.getQueueLength();
        }
    }
}
