package devin.GameDB_backend;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer gameId;

    @Column(nullable = false)
    private String name;

    @Temporal(TemporalType.DATE)
    private Date releaseDate;

    private Integer requiredAge;
    private Double price;
    private Integer dlcCount;

    @Column(columnDefinition = "TEXT")
    private String detailedDescription;

    @Column(columnDefinition = "TEXT")
    private String aboutTheGame;

    @Column(columnDefinition = "TEXT")
    private String shortDescription;

    @Column(columnDefinition = "TEXT")
    private String reviews;

    private String headerImage;
    private String website;
    private String supportUrl;
    private String supportEmail;

    private Boolean windows;
    private Boolean mac;
    private Boolean linux;

    private Integer metacriticScore;
    private String metacriticUrl;
    private Integer achievements;
    private Integer recommendations;

    @Column(columnDefinition = "TEXT")
    private String notes;

    private Integer userScore;
    private String scoreRank;
    private Integer positive;
    private Integer negative;
    private String estimatedOwners;

    private Integer averagePlaytimeForever;
    private Integer averagePlaytime2Weeks;
    private Integer medianPlaytimeForever;
    private Integer medianPlaytime2Weeks;
    private Integer peakCcu;

    // Getters and Setters
    public Integer getGameId() { return gameId; }
    public void setGameId(Integer gameId) { this.gameId = gameId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Date getReleaseDate() { return releaseDate; }
    public void setReleaseDate(Date releaseDate) { this.releaseDate = releaseDate; }

    public Integer getRequiredAge() { return requiredAge; }
    public void setRequiredAge(Integer requiredAge) { this.requiredAge = requiredAge; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Integer getDlcCount() { return dlcCount; }
    public void setDlcCount(Integer dlcCount) { this.dlcCount = dlcCount; }

    public String getDetailedDescription() { return detailedDescription; }
    public void setDetailedDescription(String detailedDescription) { this.detailedDescription = detailedDescription; }

    public String getAboutTheGame() { return aboutTheGame; }
    public void setAboutTheGame(String aboutTheGame) { this.aboutTheGame = aboutTheGame; }

    public String getShortDescription() { return shortDescription; }
    public void setShortDescription(String shortDescription) { this.shortDescription = shortDescription; }

    public String getReviews() { return reviews; }
    public void setReviews(String reviews) { this.reviews = reviews; }

    public String getHeaderImage() { return headerImage; }
    public void setHeaderImage(String headerImage) { this.headerImage = headerImage; }

    public String getWebsite() { return website; }
    public void setWebsite(String website) { this.website = website; }

    public String getSupportUrl() { return supportUrl; }
    public void setSupportUrl(String supportUrl) { this.supportUrl = supportUrl; }

    public String getSupportEmail() { return supportEmail; }
    public void setSupportEmail(String supportEmail) { this.supportEmail = supportEmail; }

    public Boolean getWindows() { return windows; }
    public void setWindows(Boolean windows) { this.windows = windows; }

    public Boolean getMac() { return mac; }
    public void setMac(Boolean mac) { this.mac = mac; }

    public Boolean getLinux() { return linux; }
    public void setLinux(Boolean linux) { this.linux = linux; }

    public Integer getMetacriticScore() { return metacriticScore; }
    public void setMetacriticScore(Integer metacriticScore) { this.metacriticScore = metacriticScore; }

    public String getMetacriticUrl() { return metacriticUrl; }
    public void setMetacriticUrl(String metacriticUrl) { this.metacriticUrl = metacriticUrl; }

    public Integer getAchievements() { return achievements; }
    public void setAchievements(Integer achievements) { this.achievements = achievements; }

    public Integer getRecommendations() { return recommendations; }
    public void setRecommendations(Integer recommendations) { this.recommendations = recommendations; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public Integer getUserScore() { return userScore; }
    public void setUserScore(Integer userScore) { this.userScore = userScore; }

    public String getScoreRank() { return scoreRank; }
    public void setScoreRank(String scoreRank) { this.scoreRank = scoreRank; }

    public Integer getPositive() { return positive; }
    public void setPositive(Integer positive) { this.positive = positive; }

    public Integer getNegative() { return negative; }
    public void setNegative(Integer negative) { this.negative = negative; }

    public String getEstimatedOwners() { return estimatedOwners; }
    public void setEstimatedOwners(String estimatedOwners) { this.estimatedOwners = estimatedOwners; }

    public Integer getAveragePlaytimeForever() { return averagePlaytimeForever; }
    public void setAveragePlaytimeForever(Integer averagePlaytimeForever) { this.averagePlaytimeForever = averagePlaytimeForever; }

    public Integer getAveragePlaytime2Weeks() { return averagePlaytime2Weeks; }
    public void setAveragePlaytime2Weeks(Integer averagePlaytime2Weeks) { this.averagePlaytime2Weeks = averagePlaytime2Weeks; }

    public Integer getMedianPlaytimeForever() { return medianPlaytimeForever; }
    public void setMedianPlaytimeForever(Integer medianPlaytimeForever) { this.medianPlaytimeForever = medianPlaytimeForever; }

    public Integer getMedianPlaytime2Weeks() { return medianPlaytime2Weeks; }
    public void setMedianPlaytime2Weeks(Integer medianPlaytime2Weeks) { this.medianPlaytime2Weeks = medianPlaytime2Weeks; }

    public Integer getPeakCcu() { return peakCcu; }
    public void setPeakCcu(Integer peakCcu) { this.peakCcu = peakCcu; }
}
