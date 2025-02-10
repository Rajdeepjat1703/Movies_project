package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        MovieManager.loadMovies("C:\\Users\\dell\\IdeaProjects\\myMoviesProject\\src\\main\\java\\org\\example\\data\\movies_large.csv");
        MovieManager.loadActors("C:\\Users\\dell\\IdeaProjects\\myMoviesProject\\src\\main\\java\\org\\example\\data\\actors_large.csv");
        MovieManager.loadDirectors("C:\\Users\\dell\\IdeaProjects\\myMoviesProject\\src\\main\\java\\org\\example\\data\\directors_large.csv");

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nMovie Data Management System");
            System.out.println("1. Get Movie Information");
            System.out.println("2. Get Top 10 Rated Movies");
            System.out.println("3. Get Movies by Genre");
            System.out.println("4. Get Movies by Director");
            System.out.println("5. Get Movies by Release Year");
            System.out.println("6. Get Movies by Year Range");
            System.out.println("7. Add a New Movie");
            System.out.println("8. Update Movie Rating");
            System.out.println("9. Delete a Movie");
            System.out.println("10. Get Top 15 Movies Sorted by Release Year");
            System.out.println("11. Get Directors with Most Movies");
            System.out.println("12. Get Actors Who Worked in Most Movies");
            System.out.println("13. Get Movies of Youngest Actor (as of 10-02-2025)");
            System.out.println("14. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    MovieManager.getMovieInfo(scanner);
                    break;
                case 2:
                    MovieManager.getTopRatedMovies();
                    break;
                case 3:
                    MovieManager.getMoviesByGenre(scanner);
                    break;
                case 4:
                    MovieManager.getMoviesByDirector(scanner);
                    break;
                case 5:
                    MovieManager.getMoviesByReleaseYear(scanner);
                    break;
                case 6:
                    MovieManager.getMoviesByYearRange(scanner);
                    break;
                case 7:
                    MovieManager.addMovie(scanner);
                    break;
                case 8:
                    MovieManager.updateMovieRating(scanner);
                    break;
                case 9:
                    MovieManager.deleteMovie(scanner);
                    break;
                case 10:
                    MovieManager.getTopMoviesByYear();
                    break;
                case 11:
                    MovieManager.getTopDirectors();
                    break;
                case 12:
                    MovieManager.getActorsWithMostMovies();
                    break;
                case 13:
                    MovieManager.getYoungestActorMovies();
                    break;
                case 14:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }
}