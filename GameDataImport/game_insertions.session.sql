-- Drop existing tables if they exist
DROP TABLE IF EXISTS Game_Tags, Tags, Movies, Screenshots, Game_Genres, Genres, Game_Categories, Categories, Game_Publishers, Publishers, Game_Developers, Developers, SubPackages, Packages, Game_Languages, Languages, Games;

-- Create Games table
CREATE TABLE Games (
   game_id INT AUTO_INCREMENT PRIMARY KEY, 
   name VARCHAR(255) NOT NULL,
   release_date DATE,
   required_age INT CHECK (required_age >= 0),
   price DECIMAL(10,2) CHECK (price >= 0),
   dlc_count INT DEFAULT 0,
   detailed_description TEXT,
   about_the_game TEXT,
   short_description TEXT,
   reviews TEXT,
   header_image VARCHAR(500),
   website VARCHAR(500),
   support_url VARCHAR(500),
   support_email VARCHAR(255),
   windows BOOLEAN DEFAULT FALSE,
   mac BOOLEAN DEFAULT FALSE,
   linux BOOLEAN DEFAULT FALSE,
   metacritic_score INT CHECK (metacritic_score >= 0),
   metacritic_url VARCHAR(500),
   achievements INT CHECK (achievements >= 0),
   recommendations INT DEFAULT 0,
   notes TEXT,
   user_score INT DEFAULT 0,
   score_rank VARCHAR(100),
   positive INT DEFAULT 0,
   negative INT DEFAULT 0,
   estimated_owners VARCHAR(50),
   average_playtime_forever INT DEFAULT 0 CHECK (average_playtime_forever >= 0),
   average_playtime_2weeks INT DEFAULT 0 CHECK (average_playtime_2weeks >= 0),
   median_playtime_forever INT DEFAULT 0 CHECK (median_playtime_forever >= 0),
   median_playtime_2weeks INT DEFAULT 0 CHECK (median_playtime_2weeks >= 0),
   peak_ccu INT DEFAULT 0
);

-- Create Languages table
CREATE TABLE Languages (
    lang_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL
);

-- Create Game_Languages table
CREATE TABLE Game_Languages (
    game_id INT,
    lang_id INT,
    full_audio BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (game_id, lang_id),
    FOREIGN KEY (game_id) REFERENCES Games(game_id) ON DELETE CASCADE,
    FOREIGN KEY (lang_id) REFERENCES Languages(lang_id) ON DELETE CASCADE
);

CREATE INDEX idx_game_languages_game_id ON Game_Languages(game_id);
CREATE INDEX idx_game_languages_lang_id ON Game_Languages(lang_id);

-- Create Packages table
CREATE TABLE Packages (
    package_id INT AUTO_INCREMENT PRIMARY KEY,
    game_id INT,
    title VARCHAR(255),
    description TEXT,
    FOREIGN KEY (game_id) REFERENCES Games(game_id) ON DELETE CASCADE
);

-- Create SubPackages table
CREATE TABLE SubPackages (
    sub_id INT AUTO_INCREMENT PRIMARY KEY,
    package_id INT,
    sub_text VARCHAR(255), -- Renamed from 'text' to 'sub_text'
    description TEXT,
    price DECIMAL(10,2),
    FOREIGN KEY (package_id) REFERENCES Packages(package_id) ON DELETE CASCADE
);

-- Create Developers table
CREATE TABLE Developers (
    dev_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL
);

-- Create Game_Developers table
CREATE TABLE Game_Developers (
    game_id INT,
    dev_id INT,
    PRIMARY KEY (game_id, dev_id),
    FOREIGN KEY (game_id) REFERENCES Games(game_id) ON DELETE CASCADE,
    FOREIGN KEY (dev_id) REFERENCES Developers(dev_id) ON DELETE CASCADE
);

-- Create Publishers table
CREATE TABLE Publishers (
    pub_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL
);

-- Create Game_Publishers table
CREATE TABLE Game_Publishers (
    game_id INT,
    pub_id INT,
    PRIMARY KEY (game_id, pub_id),
    FOREIGN KEY (game_id) REFERENCES Games(game_id) ON DELETE CASCADE,
    FOREIGN KEY (pub_id) REFERENCES Publishers(pub_id) ON DELETE CASCADE
);

-- Create Categories table
CREATE TABLE Categories (
    cat_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL -- Reduced VARCHAR length
);

-- Create Game_Categories table
CREATE TABLE Game_Categories (
    game_id INT,
    cat_id INT,
    PRIMARY KEY (game_id, cat_id),
    FOREIGN KEY (game_id) REFERENCES Games(game_id) ON DELETE CASCADE,
    FOREIGN KEY (cat_id) REFERENCES Categories(cat_id) ON DELETE CASCADE
);

-- Create Genres table
CREATE TABLE Genres (
    genre_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL -- Reduced VARCHAR length
);

-- Create Game_Genres table
CREATE TABLE Game_Genres (
    game_id INT,
    genre_id INT,
    PRIMARY KEY (game_id, genre_id),
    FOREIGN KEY (game_id) REFERENCES Games(game_id) ON DELETE CASCADE,
    FOREIGN KEY (genre_id) REFERENCES Genres(genre_id) ON DELETE CASCADE
);

-- Create Screenshots table
CREATE TABLE Screenshots (
    screenshot_id INT AUTO_INCREMENT PRIMARY KEY,
    game_id INT,
    image_url VARCHAR(500),
    FOREIGN KEY (game_id) REFERENCES Games(game_id) ON DELETE CASCADE
);

-- Create Movies table
CREATE TABLE Movies (
    movie_id INT AUTO_INCREMENT PRIMARY KEY,
    game_id INT,
    video_url VARCHAR(500),
    FOREIGN KEY (game_id) REFERENCES Games(game_id) ON DELETE CASCADE
);

-- Create Tags table
CREATE TABLE Tags (
    tag_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL -- Reduced VARCHAR length
);

-- Create Game_Tags table
CREATE TABLE Game_Tags (
    game_id INT,
    tag_id INT,
    weight INT DEFAULT 0,
    PRIMARY KEY (game_id, tag_id),
    FOREIGN KEY (game_id) REFERENCES Games(game_id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES Tags(tag_id) ON DELETE CASCADE
);

ALTER TABLE games MODIFY detailed_description MEDIUMTEXT;

ALTER TABLE games MODIFY about_the_game MEDIUMTEXT;


CREATE TABLE Reviews (
    review_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    game_id INT,
    rating INT CHECK (rating BETWEEN 1 AND 10),
    review_text TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (game_id) REFERENCES Games(game_id) ON DELETE CASCADE
);


CREATE TABLE Saved_Games (
    user_id INT,
    game_id INT,
    saved_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, game_id),
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (game_id) REFERENCES Games(game_id) ON DELETE CASCADE
);


CREATE TABLE Users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    email VARCHAR(255) UNIQUE NOT NULL,
    age INT CHECK (age >= 0),
    username VARCHAR(50) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
