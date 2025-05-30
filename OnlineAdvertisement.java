package Project;
public class OnlineAdvertisement extends AdvertisementCategory {
    public OnlineAdvertisement(String categoryID, String name, String description) {
        super(categoryID, name, description);
    }

    @Override
    public void analyzeResponse() {
        System.out.println("Analyzing engagement for online advertisement " + getName() + " (ID: " + getCategoryID() + ")...");
        // Simulate engagement tracking
    }

    public void trackEngagement() {
        System.out.println("Tracking engagement for online advertisement " + getName() + " (ID: " + getCategoryID() + ")...");
    }
}
