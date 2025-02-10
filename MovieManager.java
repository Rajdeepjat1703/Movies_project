package org.example;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class MovieManager {
    static Map<Integer, Movie> movies = new HashMap<>();
    static Map<Integer, Actor> actors = new HashMap<>();
    static Map<Integer, Director> directors = new HashMap<>();

    public static void loadMovies(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",", -1);
                if (data.length < 8) continue;

                int id = Integer.parseInt(data[0]);
                String title = data[1];
                int year = Integer.parseInt(data[2]);
                String genre = data[3];
                float rating = Float.parseFloat(data[4]);
                int duration = Integer.parseInt(data[5]);
                int dirID = Integer.parseInt(data[6]);

                List<Integer> actorIDs = new ArrayList<>();
                if (!data[7].isEmpty()) {
                    for (String actorId : data[7].replaceAll("[\"\\[\\]]", "").split(",")) {
                        actorIDs.add(Integer.parseInt(actorId.trim()));
                    }
                }

                movies.put(id, new Movie(id, title, year, genre, rating, duration, dirID, actorIDs));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadActors(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",", -1);
                if (data.length < 3) continue;

                int id = Integer.parseInt(data[0]);
                String name = data[1];
                String birthYear = data[2]; // Treat birthYear as a String

                actors.put(id, new Actor(id, name, birthYear));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadDirectors(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",", -1);
                if (data.length < 2) continue;

                int id = Integer.parseInt(data[0]);
                String name = data[1];

                directors.put(id, new Director(id, name));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getMovieInfo(Scanner scanner) {
        System.out.print("Enter movie ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Movie movie = movies.get(id);
        if (movie != null) {
            System.out.println(movie);
        } else {
            System.out.println("Movie not found!");
        }
    }

    public static void getTopRatedMovies() {
        movies.values().stream()
                .sorted((m1, m2) -> Float.compare(m2.rating, m1.rating))
                .limit(10)
                .forEach(System.out::println);
    }

    public static void getMoviesByGenre(Scanner scanner) {
        System.out.print("Enter genre: ");
        String genre = scanner.nextLine();

        movies.values().stream()
                .filter(m -> m.genre.equalsIgnoreCase(genre))
                .forEach(System.out::println);
    }

    public static void getMoviesByDirector(Scanner scanner) {
        System.out.print("Enter director ID: ");
        int dirID = scanner.nextInt();
        scanner.nextLine();

        movies.values().stream()
                .filter(m -> m.dirID == dirID)
                .forEach(System.out::println);
    }

    public static void getMoviesByReleaseYear(Scanner scanner) {
        System.out.print("Enter release year: ");
        int year = scanner.nextInt();
        scanner.nextLine();

        movies.values().stream()
                .filter(m -> m.year == year)
                .forEach(System.out::println);
    }

    public static void getMoviesByYearRange(Scanner scanner) {
        System.out.print("Enter start year: ");
        int startYear = scanner.nextInt();
        System.out.print("Enter end year: ");
        int endYear = scanner.nextInt();
        scanner.nextLine();

        movies.values().stream()
                .filter(m -> m.year >= startYear && m.year <= endYear)
                .forEach(System.out::println);
    }

    public static void addMovie(Scanner scanner) {
        System.out.print("Enter movie ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter title: ");
        String title = scanner.nextLine();
        System.out.print("Enter year: ");
        int year = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter genre: ");
        String genre = scanner.nextLine();
        System.out.print("Enter rating: ");
        float rating = scanner.nextFloat();
        System.out.print("Enter duration: ");
        int duration = scanner.nextInt();
        System.out.print("Enter director ID: ");
        int dirID = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter actor IDs (comma-separated): ");
        List<Integer> actorIDs = new ArrayList<>();
        String[] actorIdsStr = scanner.nextLine().split(",");
        for (String actorId : actorIdsStr) {
            actorIDs.add(Integer.parseInt(actorId.trim()));
        }

        movies.put(id, new Movie(id, title, year, genre, rating, duration, dirID, actorIDs));
        System.out.println("Movie added successfully!");
    }

    public static void updateMovieRating(Scanner scanner) {
        System.out.print("Enter movie ID: ");
        int id = scanner.nextInt();
        System.out.print("Enter new rating: ");
        float rating = scanner.nextFloat();
        scanner.nextLine();

        Movie movie = movies.get(id);
        if (movie != null) {
            movie.rating = rating;
            System.out.println("Rating updated successfully!");
        } else {
            System.out.println("Movie not found!");
        }
    }

    public static void deleteMovie(Scanner scanner) {
        System.out.print("Enter movie ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        if (movies.remove(id) != null) {
            System.out.println("Movie deleted successfully!");
        } else {
            System.out.println("Movie not found!");
        }
    }

    public static void getTopMoviesByYear() {
        movies.values().stream()
                .sorted(Comparator.comparingInt(m -> -m.year))
                .limit(15)
                .forEach(System.out::println);
    }

    public static void getTopDirectors() {
        Map<Integer, Long> directorCount = movies.values().stream()
                .collect(Collectors.groupingBy(m -> m.dirID, Collectors.counting()));

        directorCount.entrySet().stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                .forEach(e -> System.out.println("Director ID: " + e.getKey() + " | Movies: " + e.getValue()));
    }

    public static void getActorsWithMostMovies() {
        Map<Integer, Long> actorCount = movies.values().stream()
                .flatMap(m -> m.actorIDs.stream())
                .collect(Collectors.groupingBy(a -> a, Collectors.counting()));

        actorCount.entrySet().stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                .forEach(e -> System.out.println("Actor ID: " + e.getKey() + " | Movies: " + e.getValue()));
    }

    public static void getYoungestActorMovies() {
        Optional<Actor> youngestActor = actors.values().stream()
                .min((a1, a2) -> {
                    // Parse birthYear strings to integers for comparison
                    int year1 = Integer.parseInt(a1.birthYear);
                    int year2 = Integer.parseInt(a2.birthYear);
                    return Integer.compare(year1, year2);
                });

        youngestActor.ifPresent(actor -> {
            System.out.println("Youngest Actor: " + actor.name + " (Born: " + actor.birthYear + ")");
            movies.values().stream()
                    .filter(m -> m.actorIDs.contains(actor.id))
                    .forEach(System.out::println);
        });
    }
}