package project;

/**
 *
 * @author BBence
 */
public class Showtime {

    private Movie movie;
    private String date;
    private String time;
    private double ticketPrice;
    private Seat[][] seats;

    public Showtime(Movie movie, String time, String date, double ticketPrice) {
        this.movie = movie;
        this.time = time;
        this.date = date;
        this.ticketPrice = ticketPrice;
        this.seats = new Seat[9][9];
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                this.seats[row][col] = new Seat(row, col, true);
            }
        }
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) throws IllegalArgumentException {
        if (ticketPrice < 0) {
            throw new IllegalArgumentException("Ticket price cannot be negative.");
        }
        this.ticketPrice = ticketPrice;
    }

    public void printInfo() {
        System.out.printf("Movie: %s\nTime: %s\nDate: %s\nPrice: %.1f\n", movie.getTitle(), time, date, ticketPrice);
    }

    public void reserveSeat(int row, int column) {
        try {
            if (seats[row - 1][column - 1].isAvailable() == false) {
                System.out.println("The seat is not available.");
            } else {
                seats[row - 1][column - 1].setAvailable(false);
                System.out.println("Ticket bought!");
            }
        } catch (Exception e) {
            System.out.println("This seat does not exist.");
        }
    }

    public void printSeats() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (seats[row][col].isAvailable() == true) {
                    System.out.print("[0] ");
                } else {
                    System.out.print("[X] ");
                }
            }
            System.out.println();
        }
    }
}
