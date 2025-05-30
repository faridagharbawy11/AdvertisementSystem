package Project;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;


public class AdvertisementManager {
    private ArrayList <AdvertisementCategory> advertisements;
    private int count;
    private Scanner scanner = new Scanner(System.in);

    public AdvertisementManager() {
        advertisements = new ArrayList<>(); // Initial capacity
        count = 0;
    }

    public void openFile() {
        try {
            File file = new File("advertisements.txt");
            if (!file.exists()) {
                file.createNewFile();
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("-");
                if (parts.length == 4) {
                    String id = parts[0];
                    String type = parts[1];
                    String name = parts[2];
                    String description = parts[3];

                    if (type.equals("Online")) {
                        advertisements.add(new OnlineAdvertisement(id, name, description));
                    } else if (type.equals("Print")) {
                        advertisements.add(new PrintAdvertisement(id, name, description));
                    }
                    count++;
                }
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
    public void saveToFile() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("advertisements.txt"));
            for (AdvertisementCategory ad : advertisements) {
                String type = ad instanceof OnlineAdvertisement ? "Online" : "Print";
                writer.write(ad.getCategoryID() + "-" + type + "-" + ad.getName() + "-" + ad.getDescription());
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public void removeFromFile(String id)
    {
        int lineCount = 0;
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader("advertisements.txt"));
            StringBuilder updated = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null)
            {
                String[] parts = line.split("-");
                String ID = parts[0];
                if(!ID.equals(id))
                {
                    updated.append(line).append("\n");
                }
                lineCount++;
            }
            reader.close();
            BufferedWriter writer = new BufferedWriter(new FileWriter("advertisements.txt"));
            writer.write(updated.toString());
            writer.close();
        }
        catch(Exception e)
        {
            System.out.println("Failed to delete content from file...");
        }
    }

    private boolean idExists(String id) 
    {
        for (int i = 0; i < count; i++) {
            if (advertisements.get(i).getCategoryID().equals(id)) {
                return true;
            }
        }
        return false;
    }

    private boolean isInvalidID(String id)
    {
        if(!id.matches("AD[0-9]+"))
        {
            return true;
        }
        return false;
    }

    private boolean isInvalidName(String name)
    {
        if(!name.matches("[A-Za-z ]+"))
        {
            return true;
        }

        return false;
    }
    private boolean isInvalidDescription(String description)
    {
        String[] words = description.split(" ");

        if(!description.matches("[A-Za-z,.!?'0-9 ]+") || words.length < 5 || !words[words.length-1].endsWith("."))
        {
            return true;
        }

        return false;
    }

    public void createAdvertisement() {
        
        int choice = -1;
        while (choice != 1 && choice != 2) {
            System.out.println("Choose advertisement type: ");
            System.out.println("1. Online");
            System.out.println("2. Print");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                if (choice != 1 && choice != 2) {
                    System.out.println("Invalid choice. Please enter 1 or 2.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Consume invalid input
            }
        }

        String id, name, description, type;
        do {
            System.out.print("Enter advertisement ID: ");
            id = scanner.nextLine().trim();
            if (id.isEmpty() || idExists(id) || isInvalidID(id)) {
                System.out.println("Invalid ID. Either it's empty or already exists or has invalid format. Please try again.");
            }
        } while (id.isEmpty() || idExists(id) || isInvalidID(id));

        do {
            System.out.print("Enter advertisement name: ");
            name = scanner.nextLine();
            if (name.isEmpty() || isInvalidName(name)) {
                System.out.println("Name cannot be empty. Please try again.");
            }
        } while (name.isEmpty() || isInvalidName(name));

        do {
            System.out.print("Enter advertisement description: ");
            description = scanner.nextLine();
            if (description.isEmpty() || isInvalidDescription(description)) {
                System.out.println("Description cannot be empty. Please try again.");
            }
        } while (description.isEmpty() || isInvalidDescription(description));

        if (choice == 1) {
            type = "Online";
            advertisements.add(new OnlineAdvertisement(id, name, description));
            System.out.println("Online Advertisement " + name + " (ID: " + id + ") created successfully!");
        } else if (choice == 2) {
            type = "Print";
            advertisements.add(new PrintAdvertisement(id, name, description));
            System.out.println("Print Advertisement " + name + " (ID: " + id + ") created successfully!");
        }

        saveToFile();
    }

    public void viewAdvertisementDetails() {
        if (count == 0) {
            System.out.println("No advertisements to display.");
            return;
        }
        for (int i = 0; i < count; i++) {
            AdvertisementCategory ad = advertisements.get(i);
            System.out.println("ID: " + ad.getCategoryID() + ", Name: " + ad.getName() + ", Description: " + ad.getDescription());
        }
    }

    public void removeAdvertisment()
    {
        System.out.println("Enter ID of the ad you want to remove: ");
        String id = scanner.nextLine();
        for (AdvertisementCategory advertisementCategory : advertisements) {
            if(advertisementCategory.getCategoryID().equals(id))
                {
                    advertisements.remove(advertisementCategory);
                    removeFromFile(id);
                    System.out.println("Advertisement has been removed...");
                    return;
                }
        }
        System.out.println("Advertisment not found...");
    }

    public void analyzePerformance() {
        if (count == 0) {
            System.out.println("No advertisements to analyze.");
            return;
        }
        System.out.print("Enter advertisement ID: ");
        String id = scanner.nextLine().trim();
        boolean found = false;
        for (int i = 0; i < count; i++) {
            AdvertisementCategory ad = advertisements.get(i);
            if (ad.getCategoryID().equals(id)) {
                found = true;
                ad.analyzeResponse();
                break;
            }
        }
        if (!found) {
            System.out.println("Advertisement with ID " + id + " not found.");
        }
    }

    public void adjustCampaignStrategy() { 
        System.out.println("Adjusting campaign strategy based on performance analysis...");
         for (int i = 0; i < count; i++)
         { 
            AdvertisementCategory ad = advertisements.get(i);
             if (ad instanceof OnlineAdvertisement) 
             { System.out.println("Adjusting strategy for Online Advertisement " + ad.getName() + "...");
              ((OnlineAdvertisement) ad).trackEngagement(); 
            } else if (ad instanceof PrintAdvertisement) 
            {
                 System.out.println("Adjusting strategy for Print Advertisement " + ad.getName() + "...");
                  ((PrintAdvertisement) ad).analyzeResponse(); } 
                } System.out.println("Campaign strategies adjusted."); 
            }

    public void displayMenu() {
        int choice;
        do {
            System.out.println("\nWelcome to Advertisement Tracker System!");
            System.out.println("1. Create Advertisement");
            System.out.println("2. View Advertisement Details");
            System.out.println("3. Analyze Performance");
            System.out.println("4. Adjust Campaign Strategy");
            System.out.println("5. Remove Advertisement");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                switch (choice) {
                    case 1:
                        createAdvertisement();
                        break;
                    case 2:
                        viewAdvertisementDetails();
                        break;
                    case 3:
                        analyzePerformance();
                        break;
                    case 4:
                        adjustCampaignStrategy();
                        break;
                    case 5:
                        removeAdvertisment();
                        break;
                    case 6:
                        System.out.println("Exiting Advertisement Tracker System. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Consume invalid input
                choice = -1; // Reset choice to stay in loop
            }
        } while (choice != 6);
    }
}
