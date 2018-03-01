package cn.com.hrl.core.database.mybatis;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: MybatisConfiguration <br>
 * Function: 数据源上下文对象<br>
 * Date:  2017-06-09 14:01 <br>
 */
public class DbContextHolder {

    public enum DbType {
        MASTER, SLAVE
    }

    // 使用本地线程进行缓存
    private static final ThreadLocal<DbType> contextHolder = new ThreadLocal<>();

    /*
     * 管理所有的数据源id;
     * 主要是为了判断数据源是否存在;
     */
    public static List<String> dataSourceIds = new ArrayList<String>();

    public static void setDbType(DbType dbType) {
        if (dbType == null) throw new NullPointerException();
        contextHolder.set(dbType);
    }


    // 获取数据源
    public static DbType getDbType() {
        return contextHolder.get() == null ? DbType.MASTER : contextHolder.get();
    }

    // 删除数据源
    public static void clearDbType() {
        contextHolder.remove();
    }

    /**
     * 判断指定DataSrouce当前是否存在
     *
     * @param dataSourceId
     * @return
     * @author SHANHY
     * @create  2016年1月24日
     */
    public static boolean containsDataSource(String dataSourceId){
        return dataSourceIds.contains(dataSourceId);
    }
}
