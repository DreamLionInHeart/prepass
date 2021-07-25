import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;
import org.apache.hadoop.ipc.VersionedProtocol;

import java.io.IOException;

public interface MyInterface extends VersionedProtocol {
    long versionID = 1L;
//    int add(int number1);
    String findName(long number1);

    long getProtocolVersion(String protocol, long clientVersion);
}




