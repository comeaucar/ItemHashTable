/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weaponshop;

/**
 *
 * @author carte
 */
public class ArrayManager {

    private int maxItems;    // records the max size of the table
    private int numItems;     // records number of items in the list
    private ShopItem[] table; //hashtable itself
    private Hashing hash;
    private double loadFactor;

    public ArrayManager(int size) {
        maxItems = size;
        numItems = 0;
        table = new ShopItem[maxItems];
        hash = new Hashing(maxItems);
        loadFactor = 0.8;
    }

    public int getNumItems() {
        return numItems;
    }

    public void addQuantity(String wp, int quantity) {
        Weapon tempWep = new Weapon(wp);
        table[search(tempWep)].numberInStock += quantity;
    }

    public void put(Weapon wp, int quantity) {

        if ((numItems / maxItems) < loadFactor) {
            int startLoc = hash.hashFunction(wp);
            int count = 1;
            int loc = startLoc;
            while (table[loc] != null && table[loc].item.deleted == false) {
                loc = (startLoc + count * count) % maxItems;
                count++;
            }
            table[loc] = new ShopItem(wp, quantity);
            numItems++;
        }
    }

    public int search(Weapon wp) {
        int startLoc = hash.hashFunction(wp);
        int count = 1;
        int loc = startLoc;
        while (table[loc] != null && table[loc].item.weaponName.compareTo(wp.weaponName) != 0) {
            loc = (startLoc + count * count) % maxItems;
            count++;
        }
        if (table[loc] == null) {
            return -1;
        }
        return loc;
    }

    public ShopItem get(String weaponName) {
        Weapon tempWep = new Weapon(weaponName);
        if (search(tempWep) != -1) {
            return table[search(tempWep)];
        }

        return null;
    }

    public boolean delete(String weaponName) {
        Weapon tempWep = new Weapon(weaponName);
        if (search(tempWep) != -1) {
            this.table[search(tempWep)].item.deleted = true;
            numItems--;
            return true;
        }
        return false;
    }

    public boolean exists(String weaponName) {
        Weapon tempWep = new Weapon(weaponName);
        return search(tempWep) != -1;
    }

    public void printTable() {
        int count = 0;
        for (int x = 0; x < maxItems; x++) {
            if (table[x] != null && table[x].item.deleted == false) {
                System.out.println("Name: " + table[x].item.weaponName + "   Damage:" + table[x].item.damage + "    Cost:" + table[x].item.cost + "     Quantity in stock:" + table[x].numberInStock);
            }
        }
    }

}
