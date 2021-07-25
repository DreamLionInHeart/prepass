import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.ProtocolSignature;
import org.apache.hadoop.ipc.RPC;

import java.io.IOException;

public class MyInterfaceImp1 implements MyInterface{

        @Override
        public String findName(long number1){
            if (number1 == 20210123456789L){
                System.out.println("心心");
                return "心心";
            }
            if (number1 == 20190389010002L){
                System.out.println("奋斗中的小神仙");
                return "奋斗中的小神仙";
            }
            System.out.println("null");
            return "null";
        }

        @Override
        public long getProtocolVersion(String protocol, long clientVersion) {
                return MyInterface.versionID;
        }

    @Override
    public ProtocolSignature getProtocolSignature(String s, long l, int i) throws IOException {
        return null;
    }


    public static void main(String[] args) {
        RPC.Builder builder = new RPC.Builder(new Configuration());
        //服务器地址
        builder.setBindAddress("127.0.0.1");
        //端口号
        builder.setPort(12345);

        builder.setProtocol(MyInterface.class);
        builder.setInstance(new MyInterfaceImp1());

        try {
            RPC.Server server = builder.build();
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
