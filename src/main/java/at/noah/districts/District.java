package at.noah.districts;

import java.util.Comparator;

public record District(int id, String name, int population) implements Comparable<District> {

    public District(int id, String name, int population) {
        this.id = id;
        this.name = name;
        this.population = population;
    }

    @Override
    public int compareTo(District o) {
        return Comparator.comparing(District::name).compare(this, o);
    }


    @Override
    public String toString() {
        return "District{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", population=" + population +
                '}';
    }
}
