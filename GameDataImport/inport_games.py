import mysql.connector
import json
from datetime import datetime


# --- Helper Function: Get or Create an ID in a Lookup Table ---
def get_or_create_id(cursor, table, name, id_field, name_field):
    query = f"SELECT {id_field} FROM {table} WHERE {name_field} = %s"
    cursor.execute(query, (name,))
    row = cursor.fetchone()
    if row:
        return row[0]
    else:
        insert_query = f"INSERT INTO {table} ({name_field}) VALUES (%s)"
        cursor.execute(insert_query, (name,))
        return cursor.lastrowid

# --- Insertion Functions ---

def insert_game(cursor, game_id, game):
    # Convert release_date to YYYY-MM-DD if available
    release_date_str = game.get("release_date")
    if release_date_str:
        try:
            # Assuming format like "Oct 21, 2008"
            release_date = datetime.strptime(release_date_str, "%b %d, %Y").strftime("%Y-%m-%d")
        except ValueError:
            # Handle error if the date format is unexpected
            release_date = None
    else:
        release_date = None

    
    sql = """
        INSERT INTO Games (
            game_id, name, release_date, required_age, price, dlc_count, detailed_description,
            about_the_game, short_description, reviews, header_image, website, support_url,
            support_email, windows, mac, linux, metacritic_score, metacritic_url, achievements,
            recommendations, notes, user_score, score_rank, positive, negative, estimated_owners,
            average_playtime_forever, average_playtime_2weeks, median_playtime_forever,
            median_playtime_2weeks, peak_ccu
        ) VALUES (
            %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s,
            %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s
        )
        ON DUPLICATE KEY UPDATE 
            name=VALUES(name), release_date=VALUES(release_date), required_age=VALUES(required_age),
            price=VALUES(price), dlc_count=VALUES(dlc_count), detailed_description=VALUES(detailed_description),
            about_the_game=VALUES(about_the_game), short_description=VALUES(short_description),
            reviews=VALUES(reviews), header_image=VALUES(header_image), website=VALUES(website),
            support_url=VALUES(support_url), support_email=VALUES(support_email), windows=VALUES(windows),
            mac=VALUES(mac), linux=VALUES(linux), metacritic_score=VALUES(metacritic_score),
            metacritic_url=VALUES(metacritic_url), achievements=VALUES(achievements),
            recommendations=VALUES(recommendations), notes=VALUES(notes), user_score=VALUES(user_score),
            score_rank=VALUES(score_rank), positive=VALUES(positive), negative=VALUES(negative),
            estimated_owners=VALUES(estimated_owners), average_playtime_forever=VALUES(average_playtime_forever),
            average_playtime_2weeks=VALUES(average_playtime_2weeks), median_playtime_forever=VALUES(median_playtime_forever),
            median_playtime_2weeks=VALUES(median_playtime_2weeks), peak_ccu=VALUES(peak_ccu)
    """
    values = (
        game_id,
        game.get("name"),
        release_date,
        game.get("required_age", 0),
        game.get("price", 0.0),
        game.get("dlc_count", 0),
        game.get("detailed_description"),
        game.get("about_the_game"),
        game.get("short_description"),
        game.get("reviews"),
        game.get("header_image"),
        game.get("website"),
        game.get("support_url"),
        game.get("support_email"),
        game.get("windows", False),
        game.get("mac", False),
        game.get("linux", False),
        game.get("metacritic_score", 0),
        game.get("metacritic_url"),
        game.get("achievements", 0),
        game.get("recommendations", 0),
        game.get("notes"),
        game.get("user_score", 0),
        game.get("score_rank"),
        game.get("positive", 0),
        game.get("negative", 0),
        game.get("estimated_owners"),
        game.get("average_playtime_forever", 0),
        game.get("average_playtime_2weeks", 0),
        game.get("median_playtime_forever", 0),
        game.get("median_playtime_2weeks", 0),
        game.get("peak_ccu", 0)
    )
    cursor.execute(sql, values)


def insert_languages(cursor, game_id, languages, full_audio_languages):
    if not languages:
        print(f"No supported_languages for game {game_id}")
        return
    for lang in languages:
        print(f"Inserting language {lang} for game {game_id}")  # Debug message
        lang_id = get_or_create_id(cursor, "Languages", lang, "lang_id", "name")
        # Check if full audio is available for this language
        full_audio = False
        if full_audio_languages and lang in full_audio_languages:
            full_audio = True
        query = """
            INSERT INTO Game_Languages (game_id, lang_id, full_audio)
            VALUES (%s, %s, %s)
            ON DUPLICATE KEY UPDATE full_audio=VALUES(full_audio)
        """
        cursor.execute(query, (game_id, lang_id, full_audio))

def insert_developers(cursor, game_id, developers):
    if not developers:
        return
    for dev in developers:
        dev_id = get_or_create_id(cursor, "Developers", dev, "dev_id", "name")
        query = """
            INSERT INTO Game_Developers (game_id, dev_id)
            VALUES (%s, %s)
            ON DUPLICATE KEY UPDATE dev_id=dev_id
        """
        cursor.execute(query, (game_id, dev_id))

def insert_publishers(cursor, game_id, publishers):
    if not publishers:
        return
    for pub in publishers:
        pub_id = get_or_create_id(cursor, "Publishers", pub, "pub_id", "name")
        query = """
            INSERT INTO Game_Publishers (game_id, pub_id)
            VALUES (%s, %s)
            ON DUPLICATE KEY UPDATE pub_id=pub_id
        """
        cursor.execute(query, (game_id, pub_id))

def insert_genres(cursor, game_id, genres):
    if not genres:
        return
    for genre in genres:
        genre_id = get_or_create_id(cursor, "Genres", genre, "genre_id", "name")
        query = """
            INSERT INTO Game_Genres (game_id, genre_id)
            VALUES (%s, %s)
            ON DUPLICATE KEY UPDATE genre_id=genre_id
        """
        cursor.execute(query, (game_id, genre_id))

def insert_categories(cursor, game_id, categories):
    if not categories:
        return
    for category in categories:
        cat_id = get_or_create_id(cursor, "Categories", category, "cat_id", "name")
        query = """
            INSERT INTO Game_Categories (game_id, cat_id)
            VALUES (%s, %s)
            ON DUPLICATE KEY UPDATE cat_id=cat_id
        """
        cursor.execute(query, (game_id, cat_id))

def insert_tags(cursor, game_id, tags):
    if not tags:
        return
    # 'tags' is expected to be a dictionary: tag name -> weight
    for tag_name, weight in tags.items():
        tag_id = get_or_create_id(cursor, "Tags", tag_name, "tag_id", "name")
        query = """
            INSERT INTO Game_Tags (game_id, tag_id, weight)
            VALUES (%s, %s, %s)
            ON DUPLICATE KEY UPDATE weight=VALUES(weight)
        """
        cursor.execute(query, (game_id, tag_id, weight))

def insert_screenshots(cursor, game_id, screenshots):
    if not screenshots:
        return
    for url in screenshots:
        query = """
            INSERT INTO Screenshots (game_id, image_url)
            VALUES (%s, %s)
            ON DUPLICATE KEY UPDATE image_url=VALUES(image_url)
        """
        cursor.execute(query, (game_id, url))

def insert_movies(cursor, game_id, movies):
    if not movies:
        return
    for url in movies:
        query = """
            INSERT INTO Movies (game_id, video_url)
            VALUES (%s, %s)
            ON DUPLICATE KEY UPDATE video_url=VALUES(video_url)
        """
        cursor.execute(query, (game_id, url))

def insert_packages(cursor, game_id, packages):
    for package in packages:
        title = package.get("title")
        description = package.get("description")
        # Insert into Packages table
        sql_package = """
            INSERT INTO Packages (game_id, title, description)
            VALUES (%s, %s, %s)
            ON DUPLICATE KEY UPDATE title=VALUES(title), description=VALUES(description)
        """
        cursor.execute(sql_package, (game_id, title, description))
        package_id = cursor.lastrowid

        # If package_id not returned (duplicate), retrieve it
        if not package_id:
            query = "SELECT package_id FROM Packages WHERE game_id = %s AND title = %s"
            cursor.execute(query, (game_id, title))
            row = cursor.fetchone()
            package_id = row[0] if row else None

        if not package_id:
            continue

        # Insert subpackages
        subs = package.get("subs", [])
        for sub in subs:
            sub_text = sub.get("text")
            sub_description = sub.get("description")
            price = sub.get("price", 0.0)
            sql_sub = """
                INSERT INTO SubPackages (package_id, sub_text, description, price)
                VALUES (%s, %s, %s, %s)
                ON DUPLICATE KEY UPDATE sub_text=VALUES(sub_text), description=VALUES(description), price=VALUES(price)
            """
            cursor.execute(sql_sub, (package_id, sub_text, sub_description, price))

# --- Main Script Execution ---

def main():
    # Connect to the MySQL database
    conn = mysql.connector.connect(
        host="localhost",
        user="devin",
        password="abc@devin",
        database="game_db"
    )
    cursor = conn.cursor()

    # Load JSON data (make sure the JSON file is in the same folder as this script)
    with open("games.json", "r", encoding="utf-8") as file:
        data = json.load(file)

    # Iterate through each game in the JSON file
    for i, (game_id_str, game) in enumerate(data.items(), start=1):
        try:
            game_id = int(game_id_str)
        except ValueError:
            continue

        # Insert main game data
        insert_game(cursor, game_id, game)

        # Insert languages if available
        languages = game.get("supported_languages")
        full_audio_languages = game.get("full_audio_languages")
        if languages:
            insert_languages(cursor, game_id, languages, full_audio_languages)

        # Insert developers if available
        developers = game.get("developers")
        if developers:
            insert_developers(cursor, game_id, developers)

        # Insert publishers if available
        publishers = game.get("publishers")
        if publishers:
            insert_publishers(cursor, game_id, publishers)

        # Insert genres if available
        genres = game.get("genres")
        if genres:
            insert_genres(cursor, game_id, genres)

        # Insert categories if available
        categories = game.get("categories")
        if categories:
            insert_categories(cursor, game_id, categories)

        # Insert tags if available (expected as an object/dict of tag: weight)
        tags = game.get("tags")
        if tags:
            insert_tags(cursor, game_id, tags)

        # Insert screenshots if available
        screenshots = game.get("screenshots")
        if screenshots:
            insert_screenshots(cursor, game_id, screenshots)

        # Insert movies if available
        movies = game.get("movies")
        if movies:
            insert_movies(cursor, game_id, movies)
        
         # Insert packages and subpackages
        if game.get("packages"):
            insert_packages(cursor, game_id, game.get("packages"))
        
        # Print progress every 100 records (or any interval you choose)
        if i % 100 == 0:
            print(f"Inserted {i} games...")

    # Commit all changes and close the connection
    conn.commit()
    cursor.close()
    conn.close()
    print("✅ Data import complete!")

if __name__ == "__main__":
    main()
