package hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.NamespaceDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.util.Pair;

import javax.swing.text.TabableView;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


public class HBaseUtils {

    private static Connection connection;

    static {
        Configuration configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.property.clientPort", "2181");
        configuration.set("hbase.zookeeper.quorum", "jikehadoop01,jikehadoop02,jikehadoop03");
        try {
            connection = ConnectionFactory.createConnection(configuration);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        HBaseUtilsTest hbase = new HBaseUtilsTest();
        try {
            hbase.createTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 创建 HBase 表
     *
     * @param tableName 表名
     * @oaram columnFamilies   列族的数组
     */
    public static boolean createTable(String tableName, List<String> columnFamilies) {
        try {
            HBaseAdmin admin = (HBaseAdmin) connection.getAdmin();
            if (admin.tableExists(TableName.valueOf(tableName))) {
                return false;
            }

//            // 查看所有的namespace
//            private static String optionNamespace = "ns2"
//            NamespaceDescriptor[] listNamespaceDescriptors = admin.listNamespaceDescriptors();
//            System.out.println(Arrays.toString(listNamespaceDescriptors));
//            boolean exists = false;
//            for (NamespaceDescriptor namespaceDescriptor : listNamespaceDescriptors){
//                if (namespaceDescriptor.getName().equals(optionNamespace)) {
//                    exists = true;
//                    break;
//                }
//            }

            TableDescriptorBuilder tableDescriptor = TableDescriptorBuilder.newBuilder(TableName.valueOf(tableName));
            columnFamilies.forEach(columnFamily -> {
                ColumnFamilyDescriptorBuilder cfDescriptorBuilder = ColumnFamilyDescriptorBuilder.newBuilder(Bytes.toBytes(columnFamily));
                cfDescriptorBuilder.setMaxVersions(1);
                ColumnFamilyDescriptor familyDescriptor = cfDescriptorBuilder.build();
                tableDescriptor.setColumnFamily(familyDescriptor);
            });
            admin.createTable(tableDescriptor.build());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 删除 HBase表
     *
     * @param tableName 表名
     */
    public static boolean deleteTable(String tableName) {
        try {
            HBaseAdmin admin = (HBaseAdmin) connection.getAdmin();
            // 删除表前先禁用表
            admin.disableTable(TableName.valueOf(tableName));
            admin.deleteTable(TableName.valueOf(tableName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 插入数据
     *
     * @param tableName        表名
     * @param rowKey           唯一标识
     * @param columnFamilyName 列族名
     * @param qualifier        列标名
     * @param value            数据
     */
    public static boolean putRow1(String tableName, String rowKey, String columnFamilyName, String qualifier, String value) {
        try {
            Table table = connection.getTable(TableName.valueOf(tableName));
            Put put = new Put(Bytes.toBytes(rowKey));
            put.addColumn(Bytes.toBytes(columnFamilyName), Bytes.toBytes(qualifier), Bytes.toBytes(value));
            table.put(put);
            table.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 插入数据
     *
     * @param tableName        表名
     * @param rowKey           唯一标识
     * @param columnFamilyName 列族名
     * @param pairList         列标识和值的集合
     */
    public static boolean putRow(String tableName, String rowKey, String columnFamilyName, List<Pair<String, String>> pairList) {
        try {
            Table table = connection.getTable(TableName.valueOf(tableName));
            Put put = new Put(Bytes.toBytes(rowKey));
            pairList.forEach(pair -> put.addColumn(Bytes.toBytes(columnFamilyName), Bytes.toBytes(pair.getFirst()), Bytes.toBytes(pair.getSecond())));
            table.put(put);
            table.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 根据rowKey 获取指定行的数据
     *
     * @param tablName 表名
     * @param rowKey   唯一标识
     */
    public static Result getRow(String tablName, String rowKey) {
        try {
            Table table = connection.getTable(TableName.valueOf(tablName));
            Get get = new Get(Bytes.toBytes(rowKey));
            return table.get(get);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}


