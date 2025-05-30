package Project;
public abstract class AdvertisementCategory {
    private String categoryID;
    private String name;
    private String description;

    public AdvertisementCategory(String categoryID, String name, String description) {
        this.categoryID = categoryID;
        this.name = name;
        this.description = description;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public abstract void analyzeResponse();
}
