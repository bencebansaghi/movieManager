package project;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;
import org.json.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author BBence
 */
class Main {

    public static void main(String[] args) throws Exception {
        Theater theater = new Theater("LUT Kino", "Yliopistonkatu");
        Scanner input = new Scanner(System.in);
        while (true) {
            menu(theater);
            String choiceStr = input.nextLine();
            int choice;
            try {
                choice = Integer.parseInt(choiceStr);
            } catch (NumberFormatException e) {
                choice = -1;
            }

            switch (choice) {
                case (1):
                    String title;
                    String director;
                    String releaseDate;
                    String review;
                    String genre;
                    int duration;
                    float rating;
                    int ratingNum;
                    JSONObject movie;
                    try {
                        System.out.print("Enter the movie title: ");
                        String name = input.nextLine();
                        title = name;
                        JSONObject json = new JSONObject(getMovieInfo(name).toString());
                        JSONArray results = json.getJSONArray("results");
                        movie = results.getJSONObject(0);
                    } catch (JSONException e) {
                        System.out.println("Movie not found. Please enter a valid movie title.");
                        System.out.println();
                        break;
                    }
                    releaseDate = movie.getString("release_date");
                    review = movie.getString("overview");

                    Object genreidObject = movie.get("genre_ids");
                    JSONArray genreid = (JSONArray) genreidObject;
                    List<Integer> genreIdsList = new ArrayList<>();
                    for (int i = 0; i < genreid.length(); i++) {
                        int genreId = genreid.getInt(i);
                        genreIdsList.add(genreId);
                    }
                    String[] genrelist = getGenreNames(genreIdsList);
                    genre = (Arrays.toString(genrelist));

                    rating = movie.getFloat("vote_average");
                    ratingNum = movie.getInt("vote_count");

                    System.out.print("Enter the director: ");
                    director = input.nextLine();
                    while (true) {
                        try {
                            System.out.print("Enter the duration: ");
                            duration = input.nextInt();
                            if (duration <= 0) {
                                System.out.print("The duration has to be a positive integer. ");
                                throw new Exception();
                            }
                            break;
                        } catch (Exception e) {
                            System.out.println("Give a valid duration.");
                            input.nextLine();
                        }
                    }
                    input.nextLine();
                    Movie mov = new Movie(title, director, releaseDate, review, genre, duration, rating, ratingNum);
                    theater.addMovie(mov);
                    System.out.println();
                    break;

                case (2):
                    System.out.println("Which movie would you like to be removed?");
                    String name2 = input.nextLine();
                    theater.removeMovie(name2);
                    System.out.println();
                    break;

                case (3):
                    System.out.println("Which movie would you like to get information on?");
                    String name3 = input.nextLine();
                    System.out.println();
                    theater.oneMovieInfo(name3);
                    System.out.println();
                    break;

                case (4):
                    System.out.println();
                    theater.allMovieInfo();
                    break;

                case (5):
                    Showtime showtime5 = makeShowtime(theater);
                    if (showtime5 == null) {
                        break;
                    }
                    theater.addShowtime(showtime5);
                    System.out.println();
                    break;

                case (6):
                    Showtime showtime6 = getShowtime(theater, input);
                    theater.removeShowtime(showtime6);
                    break;

                case (7):
                    System.out.print("Which movie's showtimes would you like to get information on? ");
                    String name7 = input.nextLine();
                    theater.oneShowtimeInfo(name7);
                    System.out.println();
                    break;

                case (8):
                    theater.allShowtimeInfo();
                    break;

                case (9):
                    Showtime showtime9 = getShowtime(theater, input);
                    if (showtime9 == null) {
                        break;
                    }
                    System.out.print("Enter the seat (row and column): ");

                    int row, column;
                    while (true) {
                        String seat = input.nextLine();
                        try {
                            row = Integer.parseInt(seat.split(" ")[0]);
                            column = Integer.parseInt(seat.split(" ")[1]);
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Please give two integers seperated by space.");
                            System.out.print("Enter the seat (row and column): ");
                        }
                    }
                    theater.buyTicket(showtime9, row, column);
                    break;

                case (10):
                    Showtime showtime10 = getShowtime(theater, input);
                    if (showtime10 == null) {
                        break;
                    }
                    showtime10.printSeats();
                    break;

                case (0):
                    input.close();
                    System.out.println("Goodbye!");
                    System.exit(0);

                default:
                    System.out.println("Unknown choice, select from the menu.");
                    System.out.println();
                    break;

            }
        }
    }

    private static String[] getGenreNames(List<Integer> genreIds) throws Exception {
        String api_key = "6b5e5d80aa536bf3490e67d8314c6df0";
        String urlbase = "https://api.themoviedb.org/3/genre/movie/list?api_key=" + api_key + "&language=en-US";
        URL url = new URL(urlbase);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();
        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responseCode);
        } else {
            StringBuilder informationString = new StringBuilder();
            Scanner scanner = new Scanner(url.openStream());
            while (scanner.hasNext()) {
                informationString.append(scanner.nextLine());
            }
            scanner.close();
            JSONObject json = new JSONObject(informationString.toString());
            JSONArray genres = json.getJSONArray("genres");
            List<String> genreNames = new ArrayList<>();
            for (int i = 0; i < genres.length(); i++) {
                JSONObject genre = genres.getJSONObject(i);
                int genreId = genre.getInt("id");
                if (genreIds.contains(genreId)) {
                    genreNames.add(genre.getString("name"));
                }
            }
            return genreNames.toArray(new String[genreNames.size()]);
        }
    }

    private static StringBuilder getMovieInfo(String name) throws Exception {
        String api_key = "6b5e5d80aa536bf3490e67d8314c6df0";
        String urlbase = "https://api.themoviedb.org/3/search/movie?api_key=" + api_key + "&query=";
        String encodedName = URLEncoder.encode(name, "UTF-8");
        URL url = new URL(urlbase + encodedName + "&append_to_response=genres");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();
        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responseCode);
        } else {
            StringBuilder informationString = new StringBuilder();
            Scanner scanner = new Scanner(url.openStream());
            while (scanner.hasNext()) {
                informationString.append(scanner.nextLine());
            }
            scanner.close();
            return informationString;
        }
    }

    private static void menu(Theater theater) {
        System.out.printf("Welcome to the %s at %s!\n1. Add a movie\n2. Remove a movie\n3. View a movie\n4. View all movies\n5. Add a showtime\n6. Remove a showtime\n7. View a showtime\n8. View all showtimes\n9. Buy a ticket\n10. View seating\n0. Exit\n", theater.getName(), theater.getLocation());
        System.out.print("Enter your choice: ");
    }

    private static Showtime makeShowtime(Theater theater) throws Exception {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the title of the movie: ");
        String name5 = input.nextLine();
        Movie movie5 = theater.findMovie(name5);
        if (movie5 == null) {
            System.out.println("Movie not found.");
            System.out.println();
            return null;
        }
        System.out.print("Enter the time of the showtime (e.g. 7:00 PM): ");
        String time = input.nextLine();
        System.out.print("Enter the date of the showtime (e.g. 2022-12-15): ");
        String date = input.nextLine();
        float price;
        while (true) {
            try {
                System.out.print("Enter the ticket price for the showtime: ");
                String priceStr = input.nextLine();
                price = Float.parseFloat(priceStr);
                if (price <= 0) {
                    throw new Exception();
                } else {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Please give a valid price.");
            }
        }
        Showtime showtime = new Showtime(movie5, time, date, price);
        return showtime;
    }

    private static Showtime getShowtime(Theater theater, Scanner input) {
        System.out.print("Enter the title of the movie: ");
        String name5 = input.nextLine();
        Movie movie5 = theater.findMovie(name5);
        if (movie5 == null) {
            System.out.println("Movie not found.");
            return null;
        }
        System.out.print("Enter the time of the showtime (e.g. 7:00 PM): ");
        String time = input.nextLine();
        System.out.print("Enter the date of the showtime (e.g. 2022-12-15): ");
        String date = input.nextLine();
        return theater.returnShowtime(name5, time, date);
    }
}
