package project;

/**
 *
 * @author BBence
 */
public class Movie {

    private String title;
    private String releaseYear;
    private String director;
    private int duration;
    private String genre;
    private String review;
    private float rating;
    private int ratingNum;

    public Movie(String title, String director, String releaseYear, String review, String genre, int duration, float rating, int ratingNum) {
        this.title = title;
        this.releaseYear = releaseYear;
        this.director = director;
        this.duration = duration;
        this.genre = genre;
        this.review = review;
        this.rating = rating;
        this.ratingNum = ratingNum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) throws IllegalArgumentException {
        if (duration < 0) {
            throw new IllegalArgumentException("Duration cannot be negative.");
        }
        this.duration = duration;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) throws IllegalArgumentException {
        if (rating < 0) {
            throw new IllegalArgumentException("Rating cannot be negative.");
        }
        this.rating = rating;
    }

    public int getRatingNum() {
        return ratingNum;
    }

    public void setRatingNum(int ratingNum) throws IllegalArgumentException {
        if (ratingNum < 0) {
            throw new IllegalArgumentException("number of ratings cannot be negative.");
        }
        this.ratingNum = ratingNum;
    }

    public void printMovieInfo() {
        System.out.printf("Title: %s\nDirector: %s\nRelease Year: %s\nReview: %s\nGenre: %s\nDuration: %d\nRating: %.1f\nNumber of Ratings: %d\n", getTitle(), getDirector(), getReleaseYear(), getReview(), getGenre(), getDuration(), getRating(), getRatingNum());
    }
}
