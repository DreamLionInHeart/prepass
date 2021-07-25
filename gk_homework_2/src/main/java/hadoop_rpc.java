import org.apache.hadoop.ipc.RPC;
import org.apache.hadoop.conf.Configuration;


import java.io.IOException;
import java.net.InetSocketAddress;

public class hadoop_rpc {
    public static void main(String[] args) {
        try {
            MyInterface proxy = RPC.getProxy(MyInterface.class, 1L, new InetSocketAddress("127.0.0.1", 12345), new Configuration());
            String res = proxy.findName(20210123456799L);
            System.out.println(res);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}