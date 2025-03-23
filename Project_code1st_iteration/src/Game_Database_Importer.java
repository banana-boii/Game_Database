import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import org.json.*;

public class Game_Database_Importer {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/game_database";
    private static final String DB_USER = "devin";
    private static final String DB_PASS = "abc@devin";

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java GameDatabaseImporter <\"G:\\test\\.vscode\\GAME_DATABASE_PROJECT\\Game_Database\\games.json\">");
            return;
        }
        String filePath = "G:\\test\\.vscode\\GAME_DATABASE_PROJECT\\Game_Database\\games.json";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            String jsonData = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONObject jsonObject = new JSONObject(jsonData);

            for (String gameId : jsonObject.keySet()) {
                JSONObject gameData = jsonObject.getJSONObject(gameId);
                insertGame(conn, gameId, gameData);
            }

            System.out.println("Data import completed successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void insertGame(Connection conn, String gameId, JSONObject gameData) throws SQLException {
        String query = "INSERT IGNORE INTO games (game_id, name, release_date, required_age, price, dlc_count, short_description, header_image, website, windows, mac, linux) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setLong(1, Long.parseLong(gameId));
            stmt.setString(2, gameData.optString("name", ""));
            stmt.setString(3, gameData.optString("release_date", null));
            stmt.setInt(4, gameData.optInt("required_age", 0));
            stmt.setDouble(5, gameData.optDouble("price", 0.0));
            stmt.setInt(6, gameData.optInt("dlc_count", 0));
            stmt.setString(7, gameData.optString("short_description", ""));
            stmt.setString(8, gameData.optString("header_image", ""));
            stmt.setString(9, gameData.optString("website", ""));
            stmt.setBoolean(10, gameData.optBoolean("windows", false));
            stmt.setBoolean(11, gameData.optBoolean("mac", false));
            stmt.setBoolean(12, gameData.optBoolean("linux", false));
            stmt.executeUpdate();
        }

        insertDevelopers(conn, gameId, gameData.optJSONArray("developers"));
        insertPublishers(conn, gameId, gameData.optJSONArray("publishers"));
        insertGenres(conn, gameId, gameData.optJSONArray("genres"));
        insertCategories(conn, gameId, gameData.optJSONArray("categories"));
        insertTags(conn, gameId, gameData.optJSONObject("tags"));
        insertLanguages(conn, gameId, gameData.optJSONArray("supported_languages"), "game_languages", "language");
        insertAudioLanguages(conn, gameId, gameData.optJSONArray("full_audio_languages"), "game_audio_languages", "language");
        insertScreenshots(conn, gameId, gameData.optJSONArray("screenshots"));
    }

    private static void insertDevelopers(Connection conn, String gameId, JSONArray developers) throws SQLException {
        if (developers == null) return;
        String devQuery = "INSERT INTO developers (name) VALUES (?) ON DUPLICATE KEY UPDATE name=name";
        String joinQuery = "INSERT INTO game_developers (game_id, developer_id) VALUES (?, (SELECT developer_id FROM developers WHERE name = ?))";

        for (int i = 0; i < developers.length(); i++) {
            String devName = developers.getString(i);
            try (PreparedStatement stmt = conn.prepareStatement(devQuery)) {
                stmt.setString(1, devName);
                stmt.executeUpdate();
            }
            try (PreparedStatement stmt = conn.prepareStatement(joinQuery)) {
                stmt.setLong(1, Long.parseLong(gameId));
                stmt.setString(2, devName);
                stmt.executeUpdate();
            }
        }
    }

    private static void insertPublishers(Connection conn, String gameId, JSONArray publishers) throws SQLException {
        if (publishers == null) return;
        String pubQuery = "INSERT INTO publishers (name) VALUES (?) ON DUPLICATE KEY UPDATE name=name";
        String joinQuery = "INSERT INTO game_publishers (game_id, publisher_id) VALUES (?, (SELECT publisher_id FROM publishers WHERE name = ?))";

        for (int i = 0; i < publishers.length(); i++) {
            String pubName = publishers.getString(i);
            try (PreparedStatement stmt = conn.prepareStatement(pubQuery)) {
                stmt.setString(1, pubName);
                stmt.executeUpdate();
            }
            try (PreparedStatement stmt = conn.prepareStatement(joinQuery)) {
                stmt.setLong(1, Long.parseLong(gameId));
                stmt.setString(2, pubName);
                stmt.executeUpdate();
            }
        }
    }

    private static void insertGenres(Connection conn, String gameId, JSONArray genres) throws SQLException {
        if (genres == null) return;
        String genreQuery = "INSERT INTO genres (name) VALUES (?) ON DUPLICATE KEY UPDATE name=name";
        String joinQuery = "INSERT INTO game_genres (game_id, genre_id) VALUES (?, (SELECT genre_id FROM genres WHERE name = ?))";

        for (int i = 0; i < genres.length(); i++) {
            String genreName = genres.getString(i);
            try (PreparedStatement stmt = conn.prepareStatement(genreQuery)) {
                stmt.setString(1, genreName);
                stmt.executeUpdate();
            }
            try (PreparedStatement stmt = conn.prepareStatement(joinQuery)) {
                stmt.setLong(1, Long.parseLong(gameId));
                stmt.setString(2, genreName);
                stmt.executeUpdate();
            }
        }
    }

    private static void insertCategories(Connection conn, String gameId, JSONArray categories) throws SQLException {
        if (categories == null) return;
        String catQuery = "INSERT INTO categories (name) VALUES (?) ON DUPLICATE KEY UPDATE name=name";
        String joinQuery = "INSERT INTO game_categories (game_id, category_id) VALUES (?, (SELECT category_id FROM categories WHERE name = ?))";

        for (int i = 0; i < categories.length(); i++) {
            String catName = categories.getString(i);
            try (PreparedStatement stmt = conn.prepareStatement(catQuery)) {
                stmt.setString(1, catName);
                stmt.executeUpdate();
            }
            try (PreparedStatement stmt = conn.prepareStatement(joinQuery)) {
                stmt.setLong(1, Long.parseLong(gameId));
                stmt.setString(2, catName);
                stmt.executeUpdate();
            }
        }
    }

    private static void insertTags(Connection conn, String gameId, JSONObject tags) throws SQLException {
        if (tags == null) return;
        String tagQuery = "INSERT INTO tags (name) VALUES (?) ON DUPLICATE KEY UPDATE name=name";
        String joinQuery = "INSERT INTO game_tags (game_id, tag_id) VALUES (?, (SELECT tag_id FROM tags WHERE name = ?))";

        for (String tag : tags.keySet()) {
            try (PreparedStatement stmt = conn.prepareStatement(tagQuery)) {
                stmt.setString(1, tag);
                stmt.executeUpdate();
            }
            try (PreparedStatement stmt = conn.prepareStatement(joinQuery)) {
                stmt.setLong(1, Long.parseLong(gameId));
                stmt.setString(2, tag);
                stmt.executeUpdate();
            }
        }
    }
    private static void insertLanguages(Connection conn,String gameId, gameData.optJSONArray("supported_languages"), "game_languages", "language");
    private static void insertAudioLanguages(Connection conn,String gameId, gameData.optJSONArray("full_audio_languages"), "game_audio_languages", "language");
    private static void insertScreenshots(Connection conn,String gameId, gameData.optJSONArray("screenshots"));

}