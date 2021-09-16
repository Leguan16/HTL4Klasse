package at.noah.points;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {


    public static void main(String[] args) {
        List<Quadrant> quadrants = readFile(Path.of("src/main/resources/points/punkte.dat"));
        writeFile(Path.of("src/main/resources/points/punkteValidated.dat"), quadrants);
        var groupedQuadrants = group(quadrants);

        soutMap(groupedQuadrants);
    }

    private static void soutMap(Map<Integer, List<Quadrant>> groupedQuadrants) {
        groupedQuadrants.forEach((integer, quadrants) -> {
            System.out.println("GOUP: " + integer);
            quadrants.stream()
                    .limit(5)
                    .forEach(System.out::println);
        });
    }

    private static Map<Integer, List<Quadrant>> group(List<Quadrant> quadrants) {
        Map<Integer, List<Quadrant>> groupedQuadrants = new HashMap<>();

        quadrants.forEach(quadrant -> {
            if (groupedQuadrants.containsKey(quadrant.getQuadrant())) {
                groupedQuadrants.get(quadrant.getQuadrant()).add(quadrant);
            } else {
                List<Quadrant> newList = new ArrayList<>();
                newList.add(quadrant);
                groupedQuadrants.put(quadrant.getQuadrant(),newList);
            }
        });

        return groupedQuadrants;
    }

    private static Quadrant validateQuadrant(Quadrant quadrant) {
        if (quadrant.getX() > 0 && quadrant.getY() > 0) {
            if (quadrant.getQuadrant() != 1) {
                quadrant.setQuadrant(1);
            }
        } else
        if (quadrant.getX() < 0 && quadrant.getY() > 0) {
            if (quadrant.getQuadrant() != 2) {
                quadrant.setQuadrant(2);
            }
        } else
        if (quadrant.getX() < 0 && quadrant.getY() < 0) {
            if (quadrant.getQuadrant() != 3) {
                quadrant.setQuadrant(3);
            }
        } else
        if (quadrant.getX() > 0 && quadrant.getY() < 0) {
            if (quadrant.getQuadrant() != 4) {
                quadrant.setQuadrant(4);
            }
        }

        return quadrant;
    }

    private static List<Quadrant> readFile(Path path) {
        List<Quadrant> quadrants = new ArrayList<>();
        try(DataInputStream dataInputStream = new DataInputStream(Files.newInputStream(path))) {
            while(dataInputStream.available() != 0) {
                int quadrantInt = dataInputStream.readInt();
                double x = dataInputStream.readDouble();
                double y = dataInputStream.readDouble();
                quadrants.add(validateQuadrant(new Quadrant(quadrantInt, x, y)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return quadrants;
    }

    private static void writeFile(Path path, List<Quadrant> quadrants) {
        try(DataOutputStream dataOutputStream = new DataOutputStream(Files.newOutputStream(path))) {
            for (Quadrant quadrant : quadrants) {
                dataOutputStream.writeInt(quadrant.getQuadrant());
                dataOutputStream.writeDouble(quadrant.getX());
                dataOutputStream.writeDouble(quadrant.getY());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
