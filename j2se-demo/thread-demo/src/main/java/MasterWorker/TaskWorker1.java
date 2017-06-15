package MasterWorker;

/**
 * Created by moque on 17/5/22.
 */
public class TaskWorker1 extends Worker {


        @Override
        public Object handle(Object input) {

            Task task = (Task)input;
            task.setMerony(String.valueOf(Integer.parseInt(task.getMerony())+100));
            return task;
        }

}
