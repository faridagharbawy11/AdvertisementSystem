package Project;
 public class PrintAdvertisement extends AdvertisementCategory {
    public PrintAdvertisement(String categoryID, String name, String description) {
        super(categoryID, name, description);
    }

    public void analyzePerformance() {
        System.out.println("Analyzing response for Print Advertisement: " + getName() +
                " (ID: " + getCategoryID() + ")...");
        System.out.println("Response rates analyzed successfully.");
    }

    @Override
    public void analyzeResponse() {
         System.out.println("Analyzing response for print advertisement " + getName() + " (ID: " + getCategoryID() + ")...");     
    }
}
