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

    public int maxItems;    // records the max size of the table
    public int numItems;     // records number of items in the list
    private ShopItem[] table; //hashtable itself
    private Hashing hash; // hashing class 
    public double loadFactor;

    public ArrayManager(int size, double loadFactor) {
        maxItems = size;
        numItems = 0;
        table = new ShopItem[maxItems];
        hash = new Hashing(maxItems);
        this.loadFactor = loadFactor;
    }

    public int getNumItems() {
        return numItems;
    }

    public void addQuantity(String weaponName, int quantity) { // this just adds quantity to existing weapon
        ShopItem tempWep = get(weaponName);
        table[search(tempWep.item)].numberInStock += quantity;
    }

    public void put(Weapon wp, int quantity) {

        Double dNumItems = Double.valueOf(numItems); // convert to double to compare to load factor
        Double dMaxItems = Double.valueOf(maxItems); // when using integer it was not parsing correctly
        if ((dNumItems / dMaxItems) < loadFactor) {
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

    public int search(Weapon wp) { // returns index in hashtable of weapon
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

    public int searchItem(ShopItem si) { // searches for a weapon by taking a ShopItem
        Weapon wp = si.item;
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

    public ShopItem get(String weaponName) { // returns a ShopItem object
        Weapon tempWep;
        if (exists(weaponName) != null) {
            tempWep = exists(weaponName);
            int loc = search(tempWep);
            if (loc != -1) {
                return table[loc];
            }

        }
        return null;
    }

    public ShopItem getByNum(int index) { // gets shopitem object from index number

        if (table[index] != null) {
            return table[index];
        }

        return null;
    }

    public boolean delete(ShopItem si) { // deletes from hashtable
        Weapon tempWep = si.item;
        if (search(tempWep) != -1) {
            this.table[search(tempWep)].item.deleted = true;
            numItems--;
            return true;
        }
        return false;
    }

    public Weapon exists(String weaponName) { // checks if shopitem exists
        ShopItem[] arr = availableItemsArray();
        for (int i = 0; i < numItems; i++) {
            if (arr[i].item.weaponName.compareTo(weaponName) == 0) {
                return arr[i].item;
            }
        }
        return null;
    }

    public ShopItem[] availableItemsArray() { // returns array of just available items
        ShopItem[] retArr = new ShopItem[numItems];
        int index = 0;
        for (int i = 0; i < maxItems; i++) {
            if (table[i] != null && table[i].item.deleted == false) {
                retArr[index] = table[i];
                index++;
            }
        }
        return retArr;
    }

    /* this was essential for hashing all weapon properties but allowing user to only enter weapon name when buying.
    one problem I encountered was user would ask to buy a weapon only using a weapon name but I was hashing all properties of a weapon.
     so I had to find a way to get all weapon properties by only using weapon name as input
    this solution includes creating an array of available weapons and matching the index of weapon with weapon name.
    although this solution uses linear search, it performed extremely fast when testing on a hashtable of 1M+ tableSize */
    public Object[][] getItemIndexArr() { 
        Object[][] indexArr = new Object[numItems][];
        int itemNum = 1;
        for (int x = 0; x < maxItems; x++) {
            if (table[x] != null && table[x].item.deleted == false && table[x].numberInStock > 0) {
                Object[] indexItemPair = {itemNum, x, table[x].item.weaponName.toLowerCase()};
                indexArr[itemNum - 1] = indexItemPair;
                itemNum++;
            }
        }
        return indexArr;
    }

    public void printTable() {
        System.out.println("\n");
        int itemNum = 1;
        for (int x = 0; x < maxItems; x++) {
            if (table[x] != null && table[x].item.deleted == false && table[x].numberInStock > 0) {
                System.out.println("Item Number: " + itemNum + "   Name: " + table[x].item.weaponName + "   Damage:" + table[x].item.damage + "    Cost:" + table[x].item.cost + "     Quantity in stock:" + table[x].numberInStock);
                itemNum++;
            }
        }
        System.out.println("\n");
    }
}
