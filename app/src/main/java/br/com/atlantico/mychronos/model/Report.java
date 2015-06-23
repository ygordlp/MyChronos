package br.com.atlantico.mychronos.model;

/**
 * Created by pereira_ygor on 19/06/2015.
 */
public class Report {

    private long id;
    private long task_id;
    private long startTime;
    private long endTime;

    public Report(long task_id) {
        this.task_id = task_id;
    }

    public Report(long task_id, long startTime) {
        this.task_id = task_id;
        this.startTime = startTime;
    }

    public Report(long id, long task_id, long startTime, long endTime) {
        this.id = id;
        this.task_id = task_id;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTask_id() {
        return task_id;
    }

    public void setTask_id(long task_id) {
        this.task_id = task_id;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getTotalTime(){
        long res = 0;
        if(startTime > 0 && endTime > startTime){
            res = endTime - startTime;
        }
        return  res;
    }
}
