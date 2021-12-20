package at.noah.wahl.reworked;

import java.text.DecimalFormat;
import java.util.Objects;

public class Kandidat {

    private String name;
    private int punkte;
    private int platz1;

    public Kandidat(String name) {
        this.name = name;
    }


    public void addPoints(int p) {
        this.punkte += p;
        if (p == 2)
            this.platz1++;
    }

    public String toString() {
        DecimalFormat dc = new DecimalFormat("###0");
        return dc.format(punkte) + " / " + dc.format(platz1) + "   " + this.name;
    }

    public char firstChar() {
        return this.name.toLowerCase().charAt(0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Kandidat kandidat = (Kandidat) o;
        return punkte == kandidat.punkte && platz1 == kandidat.platz1 && Objects.equals(name, kandidat.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, punkte, platz1);
    }
}
