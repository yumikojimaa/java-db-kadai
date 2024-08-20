package kadai_007;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Posts_Chapter07 {

    public static void main(String[] args) {
        // DB接続情報
        String url = "jdbc:mysql://127.0.0.1:3307/challenge_java";
        String user = "root"; // データベースのユーザー名を設定
        String password = "Yumi1096915milk"; // データベースのパスワードを設定

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("データベース接続成功：" + conn);

            // Step 3: データの追加
            String insertSql = "INSERT INTO posts (user_id, posted_at, post_content, likes) VALUES " +
                    "(1003, '2023-02-08', '昨日の夜は徹夜でした・・', 13), " +
                    "(1002, '2023-02-08', 'お疲れ様です！', 12), " +
                    "(1003, '2023-02-09', '今日も頑張ります！', 18), " +
                    "(1001, '2023-02-09', '無理は禁物ですよ！', 17), " +
                    "(1002, '2023-02-10', '明日から連休ですね！', 20);";
            try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
                int insertedRows = pstmt.executeUpdate();
                System.out.println(insertedRows + "件のレコードが追加されました");
            }

            // Step 4: データの検索
            String selectSql = "SELECT posted_at, post_content, likes FROM posts WHERE user_id = 1002";
            try (PreparedStatement pstmt = conn.prepareStatement(selectSql)) {
                ResultSet result = pstmt.executeQuery();
                System.out.println("ユーザーIDが1002のレコードを検索しました");

                int recordNum = 1;
                while (result.next()) {
                    java.sql.Date postedAt = result.getDate("posted_at");
                    String postContent = result.getString("post_content");
                    int likes = result.getInt("likes");
                    System.out.println(recordNum + "件目：投稿日時=" + postedAt + "／投稿内容=" + postContent + "／いいね数=" + likes);
                    recordNum++;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
