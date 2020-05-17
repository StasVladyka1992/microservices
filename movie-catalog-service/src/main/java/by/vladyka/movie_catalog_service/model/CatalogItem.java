package by.vladyka.movie_catalog_service.model;

public class CatalogItem {
    private int rating;
    private String name;
    private String description;

    public CatalogItem(int rating, String name, String description) {
        this.rating = rating;
        this.name = name;
        this.description = description;
    }

    public CatalogItem() {
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
