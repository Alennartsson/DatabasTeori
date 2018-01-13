package carRental;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import carRental.CarRental;

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
    private Connection con;
    private CarRental rental = new CarRental(con);
    private List<List<String>> result = new ArrayList<>();

    public Console (Connection con) {
        this.con = con;
        this.rental = new CarRental(con);
    }

    public void startPage() throws IOException, SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("|=============== Start page ================|");
        System.out.println("| Select a number to get to the             |");
        System.out.println("| corresponding page.                       |");
        System.out.println("| 1. Customer menu                          |");
        System.out.println("| 2. Manage company                         |");
        System.out.println("| 5. Exit                                   |");
        System.out.println("|===========================================|");
        System.out.print(":");

        input = scanner.nextLine();

        if (input.equals("1") || input.equals("2") || input.equals("5")) {
            pageSwitcher(input);
        } else {
            System.err.println("You can only press one of the keys listed above, try again.");
            startPage();
        }
        scanner.close();
    }

    @SuppressWarnings("unchecked")
    private void pageOne() throws IOException, SQLException {
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

                System.out.println("The available cars are the following: ");
                result =  rental.carRented(con);

                if(!result.isEmpty()) {
                    outputString(result);
                    rental.removeList(result);
                }else {
                    System.err.println("No cars, please try again.");
                    pageSwitcher("1");
                }


                //Fix that it is possible to rent a car that is already rented
                System.out.print("Enter the licence number of the car you want to rent: ");

                carLicenceNumber = scanner.nextLine();
                result =  rental.selectCarByLicence(con, carLicenceNumber);

                if(!result.isEmpty()) {
                    outputString(result);
                    rental.removeList(result);
                }else {
                    System.err.println("No such car, please try again.");
                    pageSwitcher("1");
                }

                System.out.println("For how long do you want to rent the car?");
                System.out.print("Enter the end date for your rental period (YYYYMMDD): ");
                endDate = scanner.nextLine();


                result = rental.carPrice(con, carLicenceNumber);

                System.out.println("This will cost you " + result + "  + extra cost for mileage and damage");
                System.out.println("Are you sure that you want to rent this car? ");

                rental.removeList(result);

                result =  rental.selectCarByLicence(con, carLicenceNumber);
                outputString(result);
                rental.removeList(result);

                System.out.print("Enter Yes/No: ");
                input = scanner.nextLine();
                if (input.toLowerCase().equals("yes")) {

                    rental.updateCustomer(con, carLicenceNumber, name, pNumber, endDate);
                    System.out.println("You are now renting the car \n");
                    result = rental.showCustomerInfo(con, name, pNumber);
                    result =  rental.selectCarByLicence(con, carLicenceNumber);

                    outputString(result);
                    rental.removeList(result);

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


            result =  rental.carRented(con);

            if(!result.isEmpty()) {
                outputString(result);
                rental.removeList(result);
            }

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
                }else if(input.equals("4")){
                    carBrand = "Ford";
                }else {
                    System.err.println("You can only press one of the keys listed above.");
                    pageSwitcher("1");
                }
                result = rental.showCarByBrand(con, carBrand);

                outputString(result);
                rental.removeList(result);

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
        scanner.close();
    }

    @SuppressWarnings("unchecked")
    private void pageTwo() throws IOException, SQLException {
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

                if (input.toLowerCase().equals("yes")) { // If input equals yesW
                    rental.registerNewCar(con,carLicenceNumber,"200","free","Sedan",carSeats,carBrand,"no",carProcutionYear,carColor,mileage);
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
            result =rental.showLicencePlates(con);

            outputString(result);
            rental.removeList(result);

            carLicenceNumber = scanner.nextLine();

            rental.selectCarByLicence(con, carLicenceNumber);

            if(!result.isEmpty()) {
                outputString(result);
                rental.removeList(result);
            }else {
                System.err.println("No car with that Licence Plate, please try again.");
                pageSwitcher("2");
            }

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

                    rental.updateRented(con, carLicenceNumber);

                    System.out.println("Car status is now (available for renting).");
                    pageSwitcher("2");
                }else if(input.equals("2")){
                    System.out.println("Enter the new milage: ");
                    mileage = scanner.nextLine();
                    if (mileage.isEmpty() || !mileage.matches(".*\\d+.*")) { // If name is empty or contains letters
                        System.err.println("Mileage cant be updated to this value");
                        pageSwitcher("2");
                    }


                    if(rental.isNewMilageHigher(con, carLicenceNumber, mileage)){
                        rental.updateMileage(con, carLicenceNumber, mileage);
                        System.out.println("Mileage was changed.");
                    }
                    else {
                        System.err.println("Mileage is lower or same as before");
                        pageSwitcher("2");
                    }

                    pageSwitcher("2");
                }else if(input.equals("3")){
                    result = rental.selectCarByLicence(con, carLicenceNumber);

                    outputString(result);
                    rental.removeList(result);

                    System.out.println("Press Enter to return");

                    scanner.nextLine();
                    pageSwitcher("2");
                }else if(input.equals("4")){
                    System.out.println("Write short information about the damage: ");
                    carDamage = scanner.nextLine();

                    rental.updateDamageService(con, carLicenceNumber, carDamage);

                    System.out.println("It's updated \n");
                    System.out.println("Press Enter to return");

                    scanner.nextLine();
                    pageSwitcher("2");

                }else if(input.equals("0")){
                    pageSwitcher("2");
                }else {
                    System.err.println("You can only press one of the keys listed above.");
                    pageSwitcher("2");
                }
            }}else if(input.equals("3")){

            result = rental.showAllCars(con);
            outputString(result);
            rental.removeList(result);

            System.out.println("Press Enter to return");

            scanner.nextLine();
            pageSwitcher("2");
        }else if(input.equals("0")){
            startPage();
        }else{
            System.err.println("You can only press one of the keys listed above, try again.");
            pageSwitcher("2");
        }
        scanner.close();
    }

    private void pageFive() throws IOException, SQLException {
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
        scanner.close();
    }

    private void pageSwitcher(String s) throws IOException, SQLException {
        switch (s) {
            case "1":
                pageOne();
                break;
            case "2":
                pageTwo();
                break;
            case "5":
                pageFive();
                break;
        }
    }

    @SuppressWarnings("rawtypes")
    private void outputString(List list) {
        for(int i = 0; i < list.size(); i ++) {
            System.out.println(list.get(i).toString());
        }
    }
}