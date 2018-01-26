import java.sql.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by Viki on 2018/1/26.
 */
public class ShowInvokeTree {

    public static void main(String[] args) {
        try {
            Class.forName(com.mysql.jdbc.Driver.class.getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/observer", "root", "a");
            PreparedStatement preparedStatement = connection.prepareStatement("select * from t_observer_log order by start_time");
            ResultSet resultSet = preparedStatement.executeQuery();
            String last_parent_method, last_cur_method = null;
            String tab = "\t";
            int tagCnt = 0, maxTagCnt = 32;
            int max = 0;
            HashMap<String, Integer> mthds = new HashMap<>(10000);
            while (resultSet.next()){
                String parent_method = resultSet.getString("parent_method");
                if(!mthds.containsKey(parent_method)){
                    mthds.put(parent_method, 0);
                }
                if(!mthds.containsKey(last_cur_method)){
                    mthds.put(last_cur_method, 0);
                }
                String cur_method = resultSet.getString("cur_method");
                String cur_parent_method = resultSet.getString("parent_method");
                if(!mthds.containsKey(cur_parent_method)){
                    mthds.put(cur_parent_method, -1);
                }
                if(!mthds.containsKey(cur_method)){
                    mthds.put(cur_method, 0);
                }
                String elapse = resultSet.getString("elapse");
                tagCnt = mthds.get(cur_parent_method)+1;
                mthds.put(cur_method, tagCnt);
                String s = String.join("", Collections.nCopies(tagCnt, tab));
                System.out.println(s + cur_method + String.join("", Collections.nCopies(maxTagCnt - tagCnt <= 0 ? 1 : maxTagCnt - tagCnt, tab)) + elapse);
                max = Math.max(max, tagCnt);
                last_cur_method = cur_method;
                try {
                    TimeUnit.MILLISECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(max);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
