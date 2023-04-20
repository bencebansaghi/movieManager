package project;

/**
 *
 * @author BBence
 */
import java.util.ArrayList;
import java.util.List;

public class Theater extends Venue {

    private List<Movie> movies;
    private List<Showtime> showtimes;

    public Theater(String name, String location) {
        super(name, location);
        this.movies = new ArrayList<>();
        this.showtimes = new ArrayList<>();

    }

    public void addMovie(Movie movie) {
        if (checkIfMovieExists(movie)) {
            return;
        }
        this.movies.add(movie);
        System.out.println("Movie added successfully!");
    }

    public void removeMovie(String name) {
        for (int i = 0; i < movies.size(); i++) {
            if (name.equals(movies.get(i).getTitle())) {
                movies.remove(i);
                System.out.println(name + " was successfully removed.");
                return;
            }
        }
        System.out.println("No movies found by that name.");

    }

    public void oneMovieInfo(String name) {
        for (int i = 0; i < movies.size(); i++) {
            if (name.equals(movies.get(i).getTitle())) {
                movies.get(i).printMovieInfo();
                return;
            }
        }
        System.out.println("No movies found by that name.");
    }

    public void allMovieInfo() {
        boolean found = false;
        for (int i = 0; i < movies.size(); i++) {
            movies.get(i).printMovieInfo();
            System.out.println();
            found = true;
        }
        if (found == false) {
            System.out.println("No movies have been added yet.");
            System.out.println();
        }
    }

    public void addShowtime(Showtime showtime) {
        if (checkIfShowtimeExists(showtime)) {
            return;
        }
        this.showtimes.add(showtime);
        System.out.println("Showtime added successfully!");
    }

    public void removeShowtime(Showtime showtime) {
        boolean removed = false;
        for (int i = 0; i < showtimes.size(); i++) {
            if (showtime.equals(showtimes.get(i))) {
                showtimes.remove(i);
                removed = true;
                System.out.println("Showtime was successfully removed.");
                break;
            }
        }
        if (removed == false) {
            System.out.println("No showtime found.");
        }
    }

    public Movie findMovie(String name) {
        for (int i = 0; i < movies.size(); i++) {
            if (name.equals(movies.get(i).getTitle())) {
                return movies.get(i);
            }
        }
        return null;
    }

    public void oneShowtimeInfo(String name) {
        boolean found = false;
        for (int i = 0; i < showtimes.size(); i++) {
            if (name.equals(showtimes.get(i).getMovie().getTitle())) {
                showtimes.get(i).printInfo();
                found = true;
            }
        }
        if (found == false) {
            System.out.println("No showtimes found belonging to that movie.");
        }
    }

    public void allShowtimeInfo() {
        for (int i = 0; i < showtimes.size(); i++) {
            showtimes.get(i).printInfo();
            System.out.println();
        }
    }

    public void buyTicket(Showtime showtime, int row, int column) {
        for (int i = 0; i < showtimes.size(); i++) {
            if (showtime.equals(showtimes.get(i))) {
                showtimes.get(i).reserveSeat(row, column);
                break;
            }
        }
    }

    public Showtime returnShowtime(String name, String time, String date) {
        for (int i = 0; i < showtimes.size(); i++) {
            if (findMovie(name).equals(showtimes.get(i).getMovie()) && time.equals(showtimes.get(i).getTime())
                    && date.equals(showtimes.get(i).getDate())) {
                return showtimes.get(i);
            }
        }
        System.out.println("No showtime found by that specification.");
        return null;
    }

    public boolean checkIfShowtimeExists(Showtime showtime) {
        for (int i = 0; i < showtimes.size(); i++) {
            if (showtime.getMovie().equals(showtimes.get(i).getMovie()) && showtime.getDate().equals(showtimes.get(i).getDate()) && showtime.getTime().equals(showtimes.get(i).getTime()) && showtime.getTicketPrice() == (showtimes.get(i).getTicketPrice())) {
                System.out.println("This showtime already exists.");
                return true;
            }
        }
        return false;
    }

    public boolean checkIfMovieExists(Movie movie) {
        for (int i = 0; i < movies.size(); i++) {
            if (movie.getTitle().equals(movies.get(i).getTitle()) && movie.getDirector().equals(movies.get(i).getDirector()) && movie.getDuration() == (movies.get(i).getDuration())) {
                System.out.println("This movie was already added.");
                return true;
            }
        }
        return false;
    }
}
