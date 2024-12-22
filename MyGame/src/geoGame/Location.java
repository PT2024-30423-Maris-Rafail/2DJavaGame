package geoGame;

public class Location {
    private String coordinates;
    private String country;
    public Location() {
    }
    public String getCoordinates() {
        return coordinates;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }
}
