import com.wust.springcloud.common.util.GenEntityUtil;

/**
 * Created by WST on 2019/6/13.
 */
public class GenEntityUtilMain {
    private static final String URL = "jdbc:mysql://localhost:3306/wust?useSSL=false&allowPublicKeyRetrieval=true&allowMultiQueries=true";
    private static final String NAME = "root";
    private static final String PASS = "123456";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    public static void main(String[] args) {
        try {
            GenEntityUtil.generate(URL,DRIVER,NAME,PASS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
