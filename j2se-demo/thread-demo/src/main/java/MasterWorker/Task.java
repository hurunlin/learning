package MasterWorker;

/**
 * 任务对象
 */
public class Task {
    public int id;
    public String name;
    public String merony;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMerony() {
        return merony;
    }

    public void setMerony(String merony) {
        this.merony = merony;
    }

    @Override
    public String toString() {
        return "金额等于:"+merony;
    }
}
