package MasterWorker;

import java.util.Map;
import java.util.Set;

/**
 * Created by moque on 17/5/23.
 */
public class Main {

    public static void main(String[] args) {
        // 创建一个主控制类,并且初始化集合,任务个数,指定任务执行worker
        Master master = new Master(new TaskWorker1(),5);
        for (int i =0 ; i<100 ;i++){
            Task task = new Task();
            task.setMerony(String.valueOf(i));
            master.submit(task);
        }
        master.excute();
        int re= 0;
        Map<String,Object> resultMap = master.getResultMap();
        while (resultMap.size()>0 || !master.isComplete()){
            Set<String> keys = resultMap.keySet();
            String key =null;
            for(String k:keys){
                key=k;
                break;
            }
            if(key!=null){
                Task task = (Task) resultMap.get(key);
                System.out.println(resultMap.size()+":"+task);
            }
            if(key!=null){
                //移除已经被计算过的项
                resultMap.remove(key);
            }

        }

    }
}
