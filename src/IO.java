/**
 * Created by Mr. Robot on 18/04/16.
 */
public class IO {

    private Queue IOQueue;
    private Process active;
    private Statistics stats;
    private Gui gui;
    private long avgIOTime;


    public IO(Gui gui, Queue q, long avg, Statistics stats) {
        this.gui = gui;
        this.IOQueue = q;
        this.avgIOTime = avg;
        this.stats = stats;
    }

    public Event addIORequest(Process p, long c) {
        // Add process to IO queue
        IOQueue.insert(p);
        p.timeToNextIO();
        // Try to start new IO request
        return startIO(c);
    }

    public Event startIO(long c) {
        // If no active IO, start new
        if(active == null) {
            // If the queue is not empty, start new active process
            if(!IOQueue.isEmpty()) {
                active = (Process)IOQueue.removeNext();
                active.enterIO(c);
                gui.setIoActive(active);

                stats.nofProcessedIOOperations++;

                // Calc random IO process time and return end event
                int operationTime = 1 + (int)(2 * Math.random() * avgIOTime);
                return new Event(Constants.END_IO, c + operationTime);
            }
            // No processes in IO queue
            return null;
        }
        // IO is busy
        return null;
    }

    public Process removeActive() {
        Process p = active;
        active = null;
        gui.setIoActive(active);
        return p;
    }

    public void timePassed(long time) {
        stats.IOQueueTime += IOQueue.getQueueLength() * time;

        // Update largest queue
        if(IOQueue.getQueueLength() > stats.IOLargestQueue) {
            stats.IOLargestQueue = IOQueue.getQueueLength();
        }
    }
}
