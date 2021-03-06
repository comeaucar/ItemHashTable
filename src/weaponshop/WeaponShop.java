package weaponshop;

import java.util.Scanner;
public class WeaponShop {

    public static int getInteger(Scanner sc, String message) {
        System.out.print(message);
        while (!sc.hasNextInt()) {
            sc.nextLine(); //clear the invalid input ...
            System.out.print(message);
        }
        return sc.nextInt();
    }

    public static double getDouble(Scanner sc, String message) {
        System.out.print(message);
        while (!sc.hasNextDouble()) {
            sc.nextLine(); //clear the invalid input ...
            System.out.print(message);
        }
        return sc.nextDouble();
    }

    // used for range to validate 0-10
    public static int getIntegerInRange(Scanner sc, String message, int lower, int upper) {
        System.out.print(message);
        while (!sc.hasNextInt()) {
            sc.nextLine(); //clear the invalid input ...
            System.out.print(message);
        }
        int choice = sc.nextInt();
        while (true) {
            if (lower <= choice && upper >= choice) {
                return choice;
            } else {
                System.out.println("Range value is out of range");
                return getIntegerInRange(sc, message, lower, upper);
            }
        }
    }

    public static void addWeapons(ArrayManager h, Scanner sc) {
        System.out.println("***********WELCOME TO THE WEAPON ADDING MENU*********");
        String weaponName;
        int weaponRange;
        int weaponDamage;
        double weaponWeight;
        double weaponCost;
        int quantity;
        System.out.print("Please enter the NAME of the Weapon('end' to quit):");
        weaponName = sc.next().toLowerCase();
        while (weaponName.compareTo("end") != 0) {
            if (!weaponName.matches("[0-9]")) { // check if weapon name contains integer
                if (h.exists(weaponName) != null && h.get(weaponName).item.deleted == false) { // if weapon exists then only add quantity
                    System.out.println("That weapon already exists");
                    quantity = getInteger(sc, "Please enter amount of stock you would like to add: ");
                    h.addQuantity(weaponName, quantity);
                    System.out.println("\n** Quantity successfully added ** \n");
                } else {
                    weaponRange = getIntegerInRange(sc, "Please enter the Range of the Weapon (0-10):", 0, 10); // if weapon doesnt exist then continue on grabbing information
                    weaponDamage = getInteger(sc, "Please enter the Damage of the Weapon:");
                    weaponWeight = getDouble(sc, "Please enter the Weight of the Weapon (in pounds):");
                    weaponCost = getDouble(sc, "Please enter the Cost of the Weapon:");
                    Weapon w = new Weapon(weaponName, weaponRange, weaponDamage, weaponWeight, weaponCost);
                    quantity = getInteger(sc, "Please enter the quantity in stock:");
                    h.put(w, quantity); // pass info to create shop item
                }
            }
            System.out.print("Please enter the NAME of another Weapon ('end' to quit):");
            weaponName = sc.next().toLowerCase();
        }
    }

    public static void showRoomMenu(ArrayManager ht, Player p) {
        System.out.println("WELCOME TO THE SHOWROOM!!!!");
        if (ht.getNumItems() == 0) {
            System.out.println(" \n** There are no weapons in the shop\n"); // if no weapons are in the shop then show a message
            System.out.println("Type 'end' to return to the main menu");
            return;
        }
        ht.printTable();
        System.out.println("You have " + p.money + " money.");
        System.out.println("Please select a weapon to buy by entering ITEM NUMBER or NAME('end' to quit):");
    }

    public static void showRoom(ArrayManager ht, Player p, Scanner sc) {
        String choice;
        showRoomMenu(ht, p);
        int choiceNum = -1;
        Object[][] indexKeyPair = ht.getItemIndexArr(); // this returns an array that includes weapon name, index and item number
        choice = sc.next().toLowerCase();
        while (choice.compareTo("end") != 0) {
            if (choice.matches(".*\\d.*")) { // check if choice is an integer
                choiceNum = Integer.parseInt(choice);

                for (int i = 0; i < ht.numItems; i++) {
                    if ((Integer) indexKeyPair[i][0] == choiceNum) { // if it is an integer then check if that item number exists
                        choiceNum = (Integer) indexKeyPair[i][1];
                        break;
                    }
                }
            } else {
                for (int i = 0; i < ht.numItems; i++) {
                    String weapName = (String) indexKeyPair[i][2]; // check if weapon name exists
                    if (weapName.compareTo(choice) == 0) {
                        choiceNum = (Integer) indexKeyPair[i][1];
                        break;
                    }
                }
            }
            if (choiceNum != -1 && choiceNum <= ht.maxItems && choiceNum >= 0) {
                ShopItem si = ht.getByNum(choiceNum);
                if (si != null && ht.searchItem(si) != -1) {
                    if (0 > (p.money - si.item.cost)) { // check if buying item will put money below zero
                        System.out.println("\n** You can not afford this item! (" + si.item.weaponName + ")\n");
                    } else if (p.backpack.itemsMaxed(1)) { // check if backpack has space
                        System.out.println("\n** You don't have space for this item! (" + si.item.weaponName + ")\n");
                    } else if (p.backpack.weightMaxed(si.item.weight)) { //check if backpack will go overweight 
                        System.out.println("\n** Your backpack is too heavy for this item! (" + si.item.weaponName + ")\n");
                    } else {
                        if (si.numberInStock > 0) { //check if item has enough stock
                            p.buy(si);
                            p.withdraw(si.item.cost);
                            si.numberInStock--;
                        } else {
                            System.out.println("\n** Item is out of stock!");
                        }
                    }
                } else {
                    System.out.println("\n ** " + choice + " not found!! **");
                }
            } else {
                System.out.println("\n ** " + choice + " not found!! **\n");
            }
            showRoomMenu(ht, p);
            choice = sc.next();
            choiceNum = -1;
        }
        System.out.println("");
    }

    public static void deleteWeaponsMenu(ArrayManager ht, Scanner sc) {
        System.out.println("***DELETE ITEMS FROM SHOP***");
        if (ht.getNumItems() == 0) {
            System.out.println("\n ** There are no weapons in the shop\n");
            System.out.println("Type 'end' to return to the main menu");
            return;
        }
        ht.printTable();
        System.out.println("Please select a weapon to delete by entering Item Number('end' to quit):");
    }

    public static void deleteWeapons(ArrayManager ht, Player p, Scanner sc) {
        String choice;
        Object[][] indexKeyPair = ht.getItemIndexArr();
        int choiceNum = -1;
        String confirmation;
        deleteWeaponsMenu(ht, sc);
        choice = sc.next().toLowerCase();
        while (choice.compareTo("end") != 0) {
            if (choice.matches(".*\\d.*")) {
                choiceNum = Integer.parseInt(choice);
                for (int i = 0; i < ht.numItems; i++) {
                    if ((Integer) indexKeyPair[i][0] == choiceNum) {
                        choiceNum = (Integer) indexKeyPair[i][1];
                        break;
                    }
                }
            } else {
                for (int i = 0; i < ht.numItems; i++) {
                    String weapName = (String) indexKeyPair[i][2];
                    if (weapName.compareTo(choice) == 0) {
                        choiceNum = (Integer) indexKeyPair[i][1];
                        break;
                    }
                }
            }
            if (choiceNum != -1 && choiceNum <= ht.maxItems && choiceNum >= 0) {
                ShopItem si = ht.getByNum(choiceNum);
                if (si != null && ht.searchItem(si) != -1) {
                    System.out.println("Are you sure you would like to delete the following ('y' or any character for no)\n"); // confirmation for deletion 
                    System.out.println(si.toString());
                    confirmation = sc.next().toLowerCase();
                    if (confirmation.compareTo("y") == 0) {
                        ht.delete(si);
                        System.out.println("\n ** " + si.item.weaponName + " successfully deleted ** \n");
                    }
                } else {
                    System.out.println("\n ** Could not find item\n");
                }
            } else {
                System.out.println("\n ** " + choice + " not found!! ** \n");
            }

            deleteWeaponsMenu(ht, sc);
            choice = sc.next();
            choiceNum = -1;
        }
        System.out.println("");
    }

    public static void viewPlayer(Player p, Scanner sc) {
        String choice = "";
        System.out.println("Here is " + p.name + "! ('end' to return to main menu)");
        System.out.println("----------------------------------------");
        while (choice.compareTo("end") != 0) {
            p.printCharacter();
            choice = sc.next();
        }
        System.out.println("");
    }

    public static void mainMenu(ArrayManager ht, Player p, Scanner sc) { // main menu 
        System.out.println("** WELCOME TO WEAPON QUEST **");
        System.out.println("Please choose an option. ('end' to quit)");
        System.out.println("1. Add items to the shop");
        System.out.println("2. Delete items from the shop");
        System.out.println("3. Buy items from the shop");
        System.out.println("4. View backpack");
        System.out.println("5. View player");
        System.out.println("6. Exit");
    }

    public static void viewBackpack(Player p, Scanner sc) {
        String choice = "";
        System.out.println("Here is " + p.name + "'s backpack! ('end' to return to main menu)");
        System.out.println("----------------------------------------");
        while (choice.compareTo("end") != 0) {
            double weightPercent = Math.round(p.backpack.currWeight / p.backpack.maxWeight * 100); // shows percentage of weight and items in backpack
            double itemPercent;
            if (p.backpack.itemsMaxed(1)) {
                itemPercent = 100.00;
            } else {
                itemPercent = Math.ceil(p.backpack.numItems / (p.backpack.maxItems * p.backpack.loadFactor) * 100);
            }
            System.out.println("Current Weight: " + p.backpack.currWeight + "(" + weightPercent + "% full)");
            System.out.println("Current Number of items: " + p.backpack.numItems + "(" + itemPercent + "% full)");
            p.backpack.printBackpack();
            choice = sc.next();
        }
        System.out.println("");
    }

    public static void mainMenuChoice(ArrayManager ht, Player p, Scanner sc) {
        String choice;
        mainMenu(ht, p, sc);
        choice = sc.next();
        while (choice.compareTo("end") != 0 && choice.compareTo("6") != 0) {

            switch (choice) { // switch statement to determine menu choice
                case "1":
                    addWeapons(ht, sc);
                    break;
                case "2":
                    deleteWeapons(ht, p, sc);
                    break;
                case "3":
                    showRoom(ht, p, sc);
                    break;
                case "4":
                    viewBackpack(p, sc);
                    break;
                case "5":
                    viewPlayer(p, sc);
                    break;
                default:
                    System.out.println("\n** Invalid choice **");
                    break;
            }
            mainMenu(ht, p, sc);
            choice = sc.next();
        }
        System.out.println("");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String pname;
        System.out.println("Please enter Player name:");
        pname = sc.next();
        Player pl = new Player(pname, 45, 37, 0.82);
        ArrayManager ht = new ArrayManager(101, 0.8);
        mainMenuChoice(ht, pl, sc);
    }

}
