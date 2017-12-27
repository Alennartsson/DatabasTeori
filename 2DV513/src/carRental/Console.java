package carRental;

import java.io.IOException;
import java.util.Scanner;

public class Console {

    private String input;
    private String name;
    private String pNumber;
    private String carLicenceNumber;
    private String endDate;
    private String carBrand;
    private String mileage;
    private String carColor;
    private String carSeats;
    private String carProcutionYear;
    private String carDamage;

    public void startPage() throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("|=============== Start page ================|");
        System.out.println("| Select a number to get to the             |");
        System.out.println("| corresponding page.                       |");
        System.out.println("| 1. Customer menu                          |");
        System.out.println("| 2. Manage company                         |");
        System.out.println("| 3. Load database                          |");
        System.out.println("| 4. Save database                          |");
        System.out.println("| 5. Exit                                   |");
        System.out.println("|===========================================|");
        System.out.print(":");

        input = scanner.nextLine();

        if (input.equals("1") || input.equals("2") || input.equals("3") || input.equals("4") || input.equals("5")) {
            pageSwitcher(input);
        } else {
            System.err.println("You can only press one of the keys listed above, try again.");
            startPage();
        }
    }

    private void pageOne() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("|============== Customer menu ==============|");
        System.out.println("| Select a number to get to the             |");
        System.out.println("| corresponding page.                       |");
        System.out.println("| 1. Rent a car                             |");
        System.out.println("| 2. List available cars and prices         |");
        System.out.println("| 3. Search for a specific car brand        |");
        System.out.println("| 0. Return                                 |");
        System.out.println("|===========================================|");
        System.out.print(":");

        input = scanner.nextLine();
        if (input.equals("1")) {
            System.out.println("|=============== Rent a car ================|");
            System.out.println("| Press ENTER to continue or press 0 to     |");
            System.out.println("| return.                                   |");
            System.out.println("| 0. Return                                 |");
            System.out.println("|===========================================|");
            System.out.print("Waiting for key press...");

            if (!scanner.nextLine().equals("0")) {
                System.out.print("Enter name: ");
                name = scanner.nextLine();
                if (name.isEmpty() || name.matches(".*\\d+.*")) {
                    System.err.println("The name can't be empty or contain digits, please try again.");
                    pageSwitcher("1");
                }
                System.out.print("Enter personal number: ");
                pNumber = scanner.nextLine();
                String formatPnumber = pNumber.replaceAll("-", "");
                if (!formatPnumber.matches("\\d+")) {
                    System.err.println("The personal number can only contain digits and \"-\", please try again.");
                    pageSwitcher("1");
                }
                System.out.print("Enter the licence number of the car you want to rent: ");
                carLicenceNumber = scanner.nextLine();
                /*
                if(check if licenumber is correct and not rented);
                show car information
                */
                System.out.println("For how long do you want to rent the car?");
                System.out.print("Enter the end date for your rental period (YYYYMMDD): ");
                endDate = scanner.nextLine();

                System.out.println("This will cost you .... + extra cost for mileage and damage");
                System.out.println("Are you sure that you want to rent this car? ");
                //Visa något sorts kvitto på bilen och all information om personen
                System.out.print("Enter Yes/No: ");
                input = scanner.nextLine();
                if (input.toLowerCase().equals("yes")) { // If input equals yes
                    //Lägg till kod som skickar all info till databasen och sparar det
                    //Lägg till kod som skickar all info till databasen och sparar det
                    //Lägg till kod som skickar all info till databasen och sparar det
                    System.out.println("You are now renting the car");

                    startPage();
                } else if (input.toLowerCase().equals("no")) { // If input equals no
                    System.out.println("You pressed no.");
                    System.out.println("Going back....");
                    pageSwitcher("1");
                } else { // Prints error message if something other than yes or no is inputted
                    System.err.println("You can only type Yes or no");
                    pageSwitcher("1");
                }
            } else { // If 0, rerun start
                pageSwitcher("1");
            }
        } else if(input.equals("2")){
            System.out.println("|============= Available cars ==============|");
            System.out.println("| Press Enter to return                     |");
            System.out.println("| 0. Return                                 |");
            System.out.println("|===========================================|");
            //Lägg till kod som hämtar info från databasen och skriver ut någon sorts tabell med bilarna som finns lediga
            //Lägg till kod som hämtar info från databasen och skriver ut någon sorts tabell med bilarna som finns lediga
            //Lägg till kod som hämtar info från databasen och skriver ut någon sorts tabell med bilarna som finns lediga
            scanner.nextLine();
            pageSwitcher("1");

        }
        else if(input.equals("3")){
            System.out.println("|======== Search for a specific car ========|");
            System.out.println("| Choose car brand                          |");
            System.out.println("| 1. Audi                                   |");
            System.out.println("| 2. BMW                                    |");
            System.out.println("| 3. Volvo                                  |");
            System.out.println("| 4. Ford                                   |");
            System.out.println("| 0. Return                                 |");
            System.out.println("|===========================================|");
            input = scanner.nextLine();
            if(!input.equals("0")){
                if(input.equals("1")){
                    carBrand = "Audi";
                }else if(input.equals("2")){
                    carBrand = "BMW";
                }else if(input.equals("3")){
                    carBrand = "Volvo";
                }if(input.equals("4")){
                    carBrand = "Ford";
                }else {
                    System.err.println("You can only press one of the keys listed above.");
                    pageSwitcher("1");
                }
                //Lägg till kod som hämtar info från databasen och skriver ut någon sorts tabell med bilarna som finns lediga av den biltypen som är vald
                //Lägg till kod som hämtar info från databasen och skriver ut någon sorts tabell med bilarna som finns lediga av den biltypen som är vald
                //Lägg till kod som hämtar info från databasen och skriver ut någon sorts tabell med bilarna som finns lediga av den biltypen som är vald
                System.out.println("Press 0 to return: ");
                input = scanner.nextLine();
            }if(input.equals("0")){
                pageSwitcher("1");
            }

        }else if(input.equals("0")){
            startPage();
        }else{
            System.err.println("You can only press one of the keys listed above, try again.");
            pageSwitcher("1");
        }

    }

    private void pageTwo() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("|============== Manage company =============|");
        System.out.println("| Select a number to get to the             |");
        System.out.println("| corresponding page.                       |");
        System.out.println("| 1. Register new car                       |");
        System.out.println("| 2. Manage car                             |");
        System.out.println("| 3. List of cars                           |");
        System.out.println("| 0. Return                                 |");
        System.out.println("|===========================================|");
        System.out.print(":");
        input = scanner.nextLine();
        if (input.equals("1")) {
            System.out.println("|============ Register new car =============|");
            System.out.println("| Choose car brand                          |");
            System.out.println("| 1. Audi                                   |");
            System.out.println("| 2. BMW                                    |");
            System.out.println("| 3. Volvo                                  |");
            System.out.println("| 4. Ford                                   |");
            System.out.println("| 0. Return                                 |");
            System.out.println("|===========================================|");
            input = scanner.nextLine();
            if(!input.equals("0")){
                if(input.equals("1")){
                    carBrand = "Audi";
                }else if(input.equals("2")){
                    carBrand = "BMW";
                }else if(input.equals("3")){
                    carBrand = "Volvo";
                }else if(input.equals("4")){
                    carBrand = "Ford";
                }else {
                    System.err.println("You can only press one of the keys listed above.");
                    pageSwitcher("2");
                }

                System.out.println("Type the milage of the car(km): ");
                mileage = scanner.nextLine();
                if (mileage.isEmpty() || !mileage.matches(".*\\d+.*")) { // If name is empty or contains letters
                    System.err.println("mileage cant be empty and should be digits");
                    pageSwitcher("2");
                }
                System.out.println("|============== Choose color ===============|");
                System.out.println("| 1. Red                                    |");
                System.out.println("| 2. Blue                                   |");
                System.out.println("| 3. Yellow                                 |");
                System.out.println("| 4. Black                                  |");
                System.out.println("|===========================================|");
                input = scanner.nextLine();
                if(input.equals("1")){
                    carColor = "Red";
                }else if(input.equals("2")){
                    carColor = "Blue";
                }else if(input.equals("3")){
                    carColor = "Yellow";
                }else if(input.equals("4")){
                    carColor = "Black";
                }else {
                    System.err.println("You can only press one of the keys listed above.");
                    pageSwitcher("2");
                }
                System.out.println("How many seats does the car contain?: ");
                carSeats = scanner.nextLine();
                if (carSeats.isEmpty() || !carSeats.matches(".*\\d+.*")) { // If name is empty or contains letters
                    System.err.println("car seats cant be empty and should be a digit number");
                    pageSwitcher("2");
                }
                System.out.println("Type the production year of the car: ");
                carProcutionYear = scanner.nextLine();
                if (carProcutionYear.isEmpty() || !carProcutionYear.matches(".*\\d+.*")) { // If name is empty or contains letters
                    System.err.println("Production year cant be empty and should be a digit number");
                    pageSwitcher("2");
                }

                System.out.println("Type the licence number of the car: ");
                carLicenceNumber = scanner.nextLine();
                if (carLicenceNumber.isEmpty() || !carLicenceNumber.matches(".*\\d+.*")) { // If name is empty or contains letters
                    System.err.println("car licence number cant be empty and should be a digit number");
                    pageSwitcher("2");
                }

                System.out.println("Do you want to add this car?");
                System.out.println("Brand: "+carBrand);
                System.out.println("Production year: "+carProcutionYear);
                System.out.println("Mileage: "+mileage);
                System.out.println("Seats: "+carSeats);
                System.out.println("Color: "+carColor);
                System.out.println("Type yes/no");
                input = scanner.nextLine();

                if (input.toLowerCase().equals("yes")) { // If input equals yes
                    //Skicka iväg all information till metoden som ska lägga till bilar
                    //Skicka iväg all information till metoden som ska lägga till bilar
                    //Skicka iväg all information till metoden som ska lägga till bilar
                    System.out.println("Car was registred");
                    pageSwitcher("2");
                } else if (input.toLowerCase().equals("no")) { // If input equals no
                    System.out.println("You pressed no, nothing was added.");
                    pageSwitcher("2");
                } else { // Prints error message if something other than yes or no is inputted
                    System.err.println("You can only press Yes/No");
                    pageSwitcher("2");
                }
            } else { // If 0, rerun start
                pageSwitcher("2");
            }
        } else if(input.equals("2")){
            System.out.println("|=============== Manage car ================|");
            System.out.println("| Enter the licence number of the car       |");
            System.out.println("| that you want to manage.                  |");
            System.out.println("| 0. Return                                 |");
            System.out.println("|===========================================|");
            carLicenceNumber = scanner.nextLine();
            //Kalla på en metod som kontrollerar så att detta registeringsnumret finns
            //Kalla på en metod som kontrollerar så att detta registeringsnumret finns
            //Kalla på en metod som kontrollerar så att detta registeringsnumret finns
            if(!carLicenceNumber.equals("0")){
                System.out.println("|=============== "+carLicenceNumber+ "================|");
                System.out.println("| 1. Set available for rental               |");
                System.out.println("| 2. Change mileage                         |");
                System.out.println("| 3. See information about this car         |");
                System.out.println("| 4. Report damage and set on service       |");
                System.out.println("| 0. Return                                 |");
                System.out.println("|===========================================|");
                input = scanner.nextLine();

                if(input.equals("1")){
                    //Kalla på metoden som sätter bilen till ledig
                    //Kalla på metoden som sätter bilen till ledig
                    //Kalla på metoden som sätter bilen till ledig
                    System.out.println("Car status is now (available for renting).");
                    pageSwitcher("2");
                }else if(input.equals("2")){
                    System.out.println("Enter the new milage: ");
                    mileage = scanner.nextLine();
                    if (mileage.isEmpty() || !mileage.matches(".*\\d+.*")) { // If name is empty or contains letters
                        System.err.println("Mileage cant be updated to this value");
                        pageSwitcher("2");
                    }
                    //Kontrollerar ifall det nya milage är högre än det vi läser från databasen
                    //Kontrollerar ifall det nya milage är högre än det vi läser från databasen
                    //Kontrollerar ifall det nya milage är högre än det vi läser från databasen

                    //Uppdaterar miltalet i databasen
                    //Uppdaterar miltalet i databasen
                    //Uppdaterar miltalet i databasen

                    System.out.println("Mileage was changed.");
                    pageSwitcher("2");
                }else if(input.equals("3")){
                    System.out.println("Press Enter to return");
                    //Hämta all information som finns om bilen med detta registreringsnumret
                    //Hämta all information som finns om bilen med detta registreringsnumret
                    //Hämta all information som finns om bilen med detta registreringsnumret
                    scanner.nextLine();
                    pageSwitcher("2");
                }else if(input.equals("4")){
                    System.out.println("Write short information about the damage: ");
                    carDamage = scanner.nextLine();

                    //Kalla på funktionen som lämnar in bilen på service och uppdatera informationen om skadorna
                    //Kalla på funktionen som lämnar in bilen på service och uppdatera informationen om skadorna
                    //Kalla på funktionen som lämnar in bilen på service och uppdatera informationen om skadorna

                }else if(input.equals("0")){
                    pageSwitcher("2");
                }else {
                    System.err.println("You can only press one of the keys listed above.");
                    pageSwitcher("2");
                }
            }
        }else if(input.equals("3")){
            System.out.println("Press Enter to return");
            //Hämta all info om alla sparade bilar och skriv ut
            //Hämta all info om alla sparade bilar och skriv ut
            //Hämta all info om alla sparade bilar och skriv ut
            scanner.nextLine();
            pageSwitcher("2");
        }else if(input.equals("0")){
            startPage();
        }else{
            System.err.println("You can only press one of the keys listed above, try again.");
            pageSwitcher("2");
        }

    }

    private void pageThree() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("|============ Load new registry ============|");
        System.out.println("| Press YES to load a new registry          |");
        System.out.println("| Press NO to return                        |");
        System.out.println("|===========================================|");
        System.out.println("Load new registry? Unsaved data will be lost! " + " Yes/No");
        input = scanner.nextLine();
        if (input.toLowerCase().equals("yes")) {  /*User wants to load registry. Loading and return to start*/
            System.out.println("Write your filepath and filename");
            System.out.println("on mac: /Users/test/Desktop/registry.txt");
            System.out.println("on windows: \\Users\\test\\Desktop\\registry.txt");
            System.out.print(": ");
            String filepath= scanner.nextLine();
           //Kalla på metoden som laddar data till databasen
            //Kalla på metoden som laddar data till databasen
            //Kalla på metoden som laddar data till databasen
            System.out.println("Database was loaded");
            startPage();
        }else if (input.toLowerCase().equals("no")) {   /*User dint want to load. returning to start*/
            startPage();
        } else {      /*User typed wrong, yes/no*/
            System.err.println("You can only write Yes/No");
            pageSwitcher("3");
        }
    }

    private void pageFour() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("|============ Save new registry ============|");
        System.out.println("| Press YES to save registry                |");
        System.out.println("| Press NO to return                        |");
        System.out.println("|===========================================|");
        System.out.println("Save registry?" + " Yes/No");
        input = scanner.nextLine();
        if (input.toLowerCase().equals("yes")) {  /*User wants to save registry. Saving and returning to start*/
            System.out.println("Write your filepath and filename");
            System.out.println("on mac: /Users/test/Desktop/registry.txt");
            System.out.println("on windows: \\Users\\test\\Desktop\\registry.txt");
            System.out.print(": ");
            String filepath= scanner.nextLine();
            //Kalla på metoden som sparar data från databasen
            //Kalla på metoden som sparar data från databasen
            //Kalla på metoden som sparar data från databasen
            System.out.println("Database was saved");
            startPage();
        } else if (input.toLowerCase().equals("no")) {   /*User didnt want to save registry. returning to start*/
            startPage();
        } else {      /*User typed wrong, yes/no*/
            System.err.println("You can only write Yes/No");
            pageSwitcher("6");
        }
    }



    private void pageFive() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Are you sure that you want to exit? Yes/No");
        input = scanner.nextLine();
        if (input.toLowerCase().equals("yes")) {  /*User wants to exit program*/
            System.out.println("Stopping program...");
            System.exit(0);
        } else if (input.toLowerCase().equals("no")) {    /*User didn't want to exit program. return to start page*/
            startPage();
        } else {            /*User typed wrong, yes/no*/
            System.err.println("You can only type yes or no, try again.");
            pageSwitcher("5");
        }

    }


    private void pageSwitcher(String s) throws IOException {
        switch (s) {
            case "1": // Add member
                pageOne();
                break;
            case "2": // Select member
                pageTwo();
                break;
            case "3": // Show verbose list
                pageThree();
                break;
            case "4": // Show compact list
                pageFour();
                break;
            case "5": // Load registry
                pageFive();
                break;
        }
    }



}
