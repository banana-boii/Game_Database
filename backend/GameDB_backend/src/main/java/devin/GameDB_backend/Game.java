package devin.GameDB_backend;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name = "Games") // Match the table name in the SQL file
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id") // Match the column name in the SQL file
    private Integer gameId;

    @Column(nullable = false)
    private String name;

    @Column(name = "release_date")
    private Date releaseDate;

    @Column(name = "required_age")
    private Integer requiredAge;

    private BigDecimal price;

    @Column(name = "dlc_count")
    private Integer dlcCount;

    @Column(name = "detailed_description", columnDefinition = "MEDIUMTEXT")
    private String detailedDescription;

    @Column(name = "about_the_game", columnDefinition = "MEDIUMTEXT")
    private String aboutTheGame;

    @Column(name = "short_description")
    private String shortDescription;

    private String reviews;

    @Column(name = "header_image")
    private String headerImage;

    private String website;

    @Column(name = "support_url")
    private String supportUrl;

    @Column(name = "support_email")
    private String supportEmail;

    private Boolean windows;
    private Boolean mac;
    private Boolean linux;

    @Column(name = "metacritic_score")
    private Integer metacriticScore;

    @Column(name = "metacritic_url")
    private String metacriticUrl;

    private Integer achievements;

    private Integer recommendations;

    private String notes;

    @Column(name = "user_score")
    private Integer userScore;

    @Column(name = "score_rank")
    private String scoreRank;

    private Integer positive;
    private Integer negative;

    @Column(name = "estimated_owners")
    private String estimatedOwners;

    @Column(name = "average_playtime_forever")
    private Integer averagePlaytimeForever;

    @Column(name = "average_playtime_2weeks")
    private Integer averagePlaytime2weeks;

    @Column(name = "median_playtime_forever")
    private Integer medianPlaytimeForever;

    @Column(name = "median_playtime_2weeks")
    private Integer medianPlaytime2weeks;

    @Column(name = "peak_ccu")
    private Integer peakCcu;

    // Getters and Setters
    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getRequiredAge() {
        return requiredAge;
    }

    public void setRequiredAge(Integer requiredAge) {
        this.requiredAge = requiredAge;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getDlcCount() {
        return dlcCount;
    }

    public void setDlcCount(Integer dlcCount) {
        this.dlcCount = dlcCount;
    }

    public String getDetailedDescription() {
        return detailedDescription;
    }

    public void setDetailedDescription(String detailedDescription) {
        this.detailedDescription = detailedDescription;
    }

    public String getAboutTheGame() {
        return aboutTheGame;
    }

    public void setAboutTheGame(String aboutTheGame) {
        this.aboutTheGame = aboutTheGame;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }

    public String getHeaderImage() {
        return headerImage;
    }

    public void setHeaderImage(String headerImage) {
        this.headerImage = headerImage;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getSupportUrl() {
        return supportUrl;
    }

    public void setSupportUrl(String supportUrl) {
        this.supportUrl = supportUrl;
    }

    public String getSupportEmail() {
        return supportEmail;
    }

    public void setSupportEmail(String supportEmail) {
        this.supportEmail = supportEmail;
    }

    public Boolean getWindows() {
        return windows;
    }

    public void setWindows(Boolean windows) {
        this.windows = windows;
    }

    public Boolean getMac() {
        return mac;
    }

    public void setMac(Boolean mac) {
        this.mac = mac;
    }

    public Boolean getLinux() {
        return linux;
    }

    public void setLinux(Boolean linux) {
        this.linux = linux;
    }

    public Integer getMetacriticScore() {
        return metacriticScore;
    }

    public void setMetacriticScore(Integer metacriticScore) {
        this.metacriticScore = metacriticScore;
    }

    public String getMetacriticUrl() {
        return metacriticUrl;
    }

    public void setMetacriticUrl(String metacriticUrl) {
        this.metacriticUrl = metacriticUrl;
    }

    public Integer getAchievements() {
        return achievements;
    }

    public void setAchievements(Integer achievements) {
        this.achievements = achievements;
    }

    public Integer getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(Integer recommendations) {
        this.recommendations = recommendations;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getUserScore() {
        return userScore;
    }

    public void setUserScore(Integer userScore) {
        this.userScore = userScore;
    }

    public String getScoreRank() {
        return scoreRank;
    }

    public void setScoreRank(String scoreRank) {
        this.scoreRank = scoreRank;
    }

    public Integer getPositive() {
        return positive;
    }

    public void setPositive(Integer positive) {
        this.positive = positive;
    }

    public Integer getNegative() {
        return negative;
    }

    public void setNegative(Integer negative) {
        this.negative = negative;
    }

    public String getEstimatedOwners() {
        return estimatedOwners;
    }

    public void setEstimatedOwners(String estimatedOwners) {
        this.estimatedOwners = estimatedOwners;
    }

    public Integer getAveragePlaytimeForever() {
        return averagePlaytimeForever;
    }

    public void setAveragePlaytimeForever(Integer averagePlaytimeForever) {
        this.averagePlaytimeForever = averagePlaytimeForever;
    }

    public Integer getAveragePlaytime2weeks() {
        return averagePlaytime2weeks;
    }

    public void setAveragePlaytime2weeks(Integer averagePlaytime2weeks) {
        this.averagePlaytime2weeks = averagePlaytime2weeks;
    }

    public Integer getMedianPlaytimeForever() {
        return medianPlaytimeForever;
    }

    public void setMedianPlaytimeForever(Integer medianPlaytimeForever) {
        this.medianPlaytimeForever = medianPlaytimeForever;
    }

    public Integer getMedianPlaytime2weeks() {
        return medianPlaytime2weeks;
    }

    public void setMedianPlaytime2weeks(Integer medianPlaytime2weeks) {
        this.medianPlaytime2weeks = medianPlaytime2weeks;
    }

    public Integer getPeakCcu() {
        return peakCcu;
    }

    public void setPeakCcu(Integer peakCcu) {
        this.peakCcu = peakCcu;
    }
}
