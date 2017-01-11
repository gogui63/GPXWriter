package fr.gautierragueneau.gpxwriter;

import android.location.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gautier on 11/01/2017.
 */

public class GPX {

    private final String name;
    private final String description;
    private final String author;
    private final List<Location> points;

    private GPX(GPXBuilder gpxBuilder) {
        this.name = gpxBuilder.name;
        this.description = gpxBuilder.description;
        this.author = gpxBuilder.author;
        this.points = gpxBuilder.points;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getAuthor() {
        return author;
    }

    public List<Location> getPoints() {
        return points;
    }

    public void generateGPX(IGPX gpxListner) {
        new AsyncTaskGenerateGPX(this, gpxListner).execute();
    }

    public static class GPXBuilder {

        private final String name;
        private String description;
        private String author;
        private List<Location> points = new ArrayList<>();

        public GPXBuilder(String name) {
            this.name = name;
        }

        public GPXBuilder description(String description) {
            this.description = description;
            return this;
        }

        public GPXBuilder author(String author) {
            this.author = author;
            return this;
        }

        public GPXBuilder addPoints(List<Location> points) {
            this.points = points;
            return this;
        }

        public GPXBuilder addPoint(Location point) {
            this.points.add(point);
            return this;
        }

        public GPX build() {
            return new GPX(this);
        }
    }

}
