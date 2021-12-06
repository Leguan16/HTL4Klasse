package at.noah.districts;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static Collection<District> readCSV(Path path) throws IOException {

        if (!Files.exists(path)) {
            System.out.println("File does not exist!");
            return Collections.emptyList();
        }

        List<District> districts = new ArrayList<>();

        try (Stream<String> lines = Files.lines(path).skip(1)) {
            lines.forEach(s -> {
                String[] values = s.split(",");
                int id = Integer.parseInt(values[0]);
                String name = values[1];
                int population = Integer.parseInt(values[2].replace(".", ""));
                districts.add(new District(id, name, population));
            });

        } catch (NumberFormatException e) {
            System.out.println("Invalid arguments");
            return Collections.emptyList();
        }

        Collections.sort(districts);

        return districts;
    }

    public static void main(String[] args) throws IOException {
        Collection<District> districts = readCSV(Path.of("src/main/resources/districts/bezirke_noe.csv"));

        districts.forEach(System.out::println);

        System.out.println("\n\n");

        districts.stream()
                .sorted((o1, o2) -> Comparator
                        .comparing(District::population)
                        .reversed()
                        .compare(o1, o2))
                .collect(Collectors.toList())
                .forEach(System.out::println);

    }
}
