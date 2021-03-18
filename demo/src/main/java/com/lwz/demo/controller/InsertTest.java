package com.lwz.demo.controller;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Formatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InsertTest {

    private static Logger logger = Logger.getLogger(InsertTest.class.getName());

    //驱动程序名
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    //URL指向要访问的数据库名mysql
    private static final String DB_URL = "jdbc:mysql://localhost:3306/lv?rewriteBatchedStatements=true&useSSL=false&serverTimezone=UTC";
    //MySQL配置时的用户名
    private static final String DB_USERNAME = "root";
    //MySQL配置时的密码
    private static final String DB_PASSWORD = "lwz123456";

    private static Random random = new Random(10000);
    //我们要测试的表的名称
    private static final String TABLE_NAME = "upmsg";
    private int batchSize;//一批提交的事务数
    private int concurrent;//
    private int sampling;//
@Test
    public void test() throws Exception {
        printHeader();

        int[] concurrentList = new int[]{1, 5, 10, 20};//默认测试1，5，10，20个并发
        int[] batchSizeList = new int[] {100, 200, 500, 1000};//一批提交的事务数
        for (int concurrent : concurrentList) {
            for (int batchSize : batchSizeList) {
                //对以上每种组合都run一次
                new InsertTest(batchSize, concurrent).run(true);
            }
            Thread.sleep(10000);
        }
    }
    /*-----------InsertTest类的构造函数一-------------*/
    public InsertTest(final int batchSize, final int concurrent) throws Exception {
        this.batchSize = batchSize;
        this.concurrent = concurrent;
        this.sampling = 100;
    }
    /*-----------InsertTest类的构造函数二-------------*/
    public InsertTest(final int batchSize, final int concurrent, final int sampling) throws Exception {
        this.batchSize = batchSize;
        this.concurrent = concurrent;
        this.sampling = sampling;
    }

    /*-----------开始运行run方法-------------*/
    public void run(boolean printResult) throws Exception {
        final List<Long> results = Collections.synchronizedList(new ArrayList<Long>());
        final CountDownLatch startGate = new CountDownLatch(concurrent);
        final CountDownLatch endGate = new CountDownLatch(concurrent);

        for (int idxConcurrent = 0; idxConcurrent < concurrent; idxConcurrent++) {
            new Thread(new Runnable() {
                public void run() {
                    startGate.countDown();
                    try {
                        long time = execute();
                        long avg = batchSize * sampling * 1000 / time;;
                        results.add(Long.valueOf(avg));
                    } catch(Exception ex) {
                        ex.printStackTrace();
                    } finally {
                        endGate.countDown();
                    }
                }
            }).start();
        }
        endGate.await();

        Collections.sort(results);
        //每种组合跑完之后都打印出一行数据
        if (printResult) {
            printResult(batchSize, concurrent, results);
        }
    }

    public long execute() throws Exception {
        Connection conn = getConnection();
        Map<String, Integer> columns = queryTableColumns(conn);
        String insertSQL = generateInsertSQL(columns);
        PreparedStatement ps = conn.prepareStatement(insertSQL);
        try {
            long start = System.currentTimeMillis();
            for (int i = 0; i < sampling; i++) {
                execute(conn, ps, columns);
            }
            long stop = System.currentTimeMillis();
            return stop - start;
        } catch(Exception ex) {
            logger.log(Level.SEVERE, null, ex);
            conn.rollback();
            conn.close();
            throw ex;
        } finally {
            conn.close();
        }
    }
    //执行插入语句出错。  ps：INSERT INTO student1(name,goal)VALUES(** NOT SPECIFIED **,** NOT SPECIFIED **)
    public void execute(Connection conn, PreparedStatement ps, Map<String, Integer> columns) throws Exception {
        try {
            for (int idx = 0; idx < batchSize; idx++) {
                int idxColumn = 1;
                //这个地方实际上是对每一列进行循环。（1）如果该列是key对应的列………… （2）该列为普通列
                for (String column : columns.keySet()) {
                    //如果该列为name列即key对应的列，就单独为他生成一个主键。
                    //为了执行忽略大小写的比较，使用equalsIgnoreCase
                    if (column.equalsIgnoreCase("name")) {
                        //给JDBC的SQL语句的占位符赋值的,即是下面的“？   connection.prepareStatement("insert into t_user values (?,?)");
                        //UUID.randomUUID().toString()是java JDK提供的一个自动生成主键的方法。
                        ps.setObject(idxColumn, UUID.randomUUID().toString());
                    }
                    //否则就是普通列，随便填充点东西进去就好了。
                    else {
                        ps.setObject(idxColumn, generateColumnValue(columns.get(column)));
                    }
                    idxColumn ++;
                }
                ps.addBatch();
            }
            //批量执行SQL语句
            ps.executeBatch();
            conn.commit();

            ps.clearBatch();
        }
        //如果上面出错了就捕获其异常
        catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
            if (null != ex.getNextException()) {
                logger.log(Level.SEVERE, null, ex.getNextException());
            }
            conn.rollback();
            throw ex;
        }
    }
    //根据获取的列信息，生成插入的sql语句。
    private String generateInsertSQL(Map<String, Integer> columns) throws SQLException {
        StringBuilder sb = new StringBuilder();
        StringBuffer sbColumns = new StringBuffer();
        StringBuffer sbValues = new StringBuffer();

        sb.append("INSERT INTO ").append(TABLE_NAME);

        for (String column : columns.keySet()) {
            if (sbColumns.length() > 0) {
                sbColumns.append(",");
                sbValues.append(",");
            }
            sbColumns.append(column);
            sbValues.append("?");
        }
        sb.append("(").append(sbColumns).append(")");
        sb.append("VALUES");
        sb.append("(").append(sbValues).append(")");
        return sb.toString();
    }

    private Map<String, Integer> queryTableColumns(Connection conn) throws Exception {
        Map<String, Integer> columns = new LinkedHashMap<String, Integer>();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE 1=0";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        ResultSetMetaData rsmd = rs.getMetaData();
        for (int i = 1; i <= rsmd.getColumnCount(); i++) {
            columns.put(rsmd.getColumnName(i), rsmd.getColumnType(i));
        }
        return columns;
    }

    //生成列值
    private Object generateColumnValue(int type) {
        Object obj = null;
        switch (type) {
            case Types.DECIMAL:
            case Types.NUMERIC:
            case Types.DOUBLE:
            case Types.FLOAT:
            case Types.REAL:
            case Types.BIGINT:
            case Types.TINYINT:
            case Types.SMALLINT:
            case Types.INTEGER:
                obj = random.nextInt(10000);
                break;
            case Types.DATE:
                obj = Calendar.getInstance().getTime();
                break;
            case Types.TIMESTAMP:
                obj = new Timestamp(System.currentTimeMillis());
                break;
            default:
                obj = String.valueOf(random.nextInt(10000));
                break;
        }
        return obj;
    }
    //连接MYSQL数据库。
    private Connection getConnection() throws Exception {
        Class.forName(DB_DRIVER);
        Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        conn.setAutoCommit(false);
        return conn;
    }
    //打印出表头（即列的信息）
    private static void printHeader() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append(new Formatter().format("%15s|%15s|%15s|%15s|%15s", "BATCH_SIZE", "CONCURRENT", "AVG (r/s)", "MIN (r/s)", "MAX (r/s)"));
        System.out.println(sb.toString());
    }
    //打印每次跑完后的统计信息
    private static void printResult(int batch, int concurrent, List<Long> results) {
        Long total = Long.valueOf(0);
        for (Long result : results) {
            total += result;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(new Formatter().format("%15s|%15s|%15s|%15s|%15s", batch, concurrent, (total/results.size()), results.get(0), results.get(results.size() - 1)));
        System.out.println(sb.toString());
    }
}
