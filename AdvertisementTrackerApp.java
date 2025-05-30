package Project;
public class AdvertisementTrackerApp {
    public static void main(String[] args) {
        AdvertisementManager manager = new AdvertisementManager();
        manager.openFile();
        manager.displayMenu();
    }
}
