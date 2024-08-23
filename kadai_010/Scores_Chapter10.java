package kadai_010;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Scores_Chapter10 {
    @SuppressWarnings("resource")
	public static void main(String[] args) {
        // データベース接続情報
    	String url = "jdbc:mysql://127.0.0.1:3307/challenge_java";
        String user = "root"; 
        String password = "Yumi1096915milk"; 
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // データベースへの接続
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("データベース接続成功：" + conn);

            // 点数の更新
            System.out.println("レコード更新を実行します");
            String updateSql = "UPDATE scores SET score_math = ?, score_english = ? WHERE name = ?";
            pstmt = conn.prepareStatement(updateSql);
            pstmt.setInt(1, 95); // 数学の点数
            pstmt.setInt(2, 80); // 英語の点数
            pstmt.setString(3, "武者小路勇気");

            int updatedRows = pstmt.executeUpdate();
            System.out.println(updatedRows + "件のレコードが更新されました");

            // 点数順に並べ替え
            System.out.println("数学・英語の点数が高い順に並べ替えました");
            String selectSql = "SELECT id, name, score_math, score_english FROM scores ORDER BY score_math DESC, score_english DESC";
            pstmt = conn.prepareStatement(selectSql);
            rs = pstmt.executeQuery();

            int count = 1;
            while (rs.next()) {
                System.out.println(count + "件目：生徒ID=" + rs.getInt("id") + "／氏名=" + rs.getString("name") +
                                   "／数学=" + rs.getInt("score_math") + "／英語=" + rs.getInt("score_english"));
                count++;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // リソースの解放
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
