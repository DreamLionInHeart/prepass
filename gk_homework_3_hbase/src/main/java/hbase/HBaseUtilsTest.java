package hbase;



import org.apache.hadoop.hbase.util.Pair;
import org.junit.Test;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


public class HBaseUtilsTest {

    private static final String TABLE_NAME = "chixin:student";
    private static final String Name = "name";
    private static final String Info = "info";
    private static final String Score = "score";

//    private static final String STUDENT = "Hbase_01";

    @Test
    public void createTable() {
        //新建表
        List<String> columnFamilies = Arrays.asList(Name, Info, Score);
        boolean table = HBaseUtils.createTable(TABLE_NAME, columnFamilies);
        System.out.println("表创建结果：" + TABLE_NAME);
    }

    @Test
    public void insertData() {
        HBaseUtils.putRow1(TABLE_NAME,"rowkey1",Name,Name,"Tom");
        HBaseUtils.putRow1(TABLE_NAME,"rowkey1",Info,"student_id","20210000000001");
        HBaseUtils.putRow1(TABLE_NAME,"rowkey1",Info,"class","1");
        HBaseUtils.putRow1(TABLE_NAME,"rowkey1",Score,"understanding","75");
        HBaseUtils.putRow1(TABLE_NAME,"rowkey1",Score,"programming","82");
        System.out.println("Tom 数据插入成功");

        HBaseUtils.putRow1(TABLE_NAME,"rowkey2",Name,Name,"Jerry");
        HBaseUtils.putRow1(TABLE_NAME,"rowkey2",Info,"student_id","20210000000002");
        HBaseUtils.putRow1(TABLE_NAME,"rowkey2",Info,"class","1");
        HBaseUtils.putRow1(TABLE_NAME,"rowkey2",Score,"understanding","85");
        HBaseUtils.putRow1(TABLE_NAME,"rowkey2",Score,"programming","67");
        System.out.println("Jerry 数据插入成功");

        HBaseUtils.putRow1(TABLE_NAME,"rowkey3",Name,Name,"Jack");
        HBaseUtils.putRow1(TABLE_NAME,"rowkey3",Info,"student_id","20210000000003");
        HBaseUtils.putRow1(TABLE_NAME,"rowkey3",Info,"class","2");
        HBaseUtils.putRow1(TABLE_NAME,"rowkey3",Score,"understanding","80");
        HBaseUtils.putRow1(TABLE_NAME,"rowkey3",Score,"programming","80");
        System.out.println("Jack 数据插入成功");

        HBaseUtils.putRow1(TABLE_NAME,"rowkey4",Name,Name,"Rose");
        HBaseUtils.putRow1(TABLE_NAME,"rowkey4",Info,"student_id","20210000000004");
        HBaseUtils.putRow1(TABLE_NAME,"rowkey4",Info,"class","2");
        HBaseUtils.putRow1(TABLE_NAME,"rowkey4",Score,"understanding","60");
        HBaseUtils.putRow1(TABLE_NAME,"rowkey4",Score,"programming","61");
        System.out.println("Rose 数据插入成功");

        HBaseUtils.putRow1(TABLE_NAME,"rowkey5",Name,Name,"迟鑫");
        HBaseUtils.putRow1(TABLE_NAME,"rowkey5",Info,"student_id","20190389010002");
        HBaseUtils.putRow1(TABLE_NAME,"rowkey5",Info,"class","3");
        HBaseUtils.putRow1(TABLE_NAME,"rowkey5",Score,"understanding","90");
        HBaseUtils.putRow1(TABLE_NAME,"rowkey5",Score,"programming","90");
        System.out.println("迟鑫 数据插入成功");

        System.out.println("数据全部插入完成！");
    }

}
