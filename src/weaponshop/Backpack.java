package weaponshop;

public class Backpack {

    public int numItems;
    public int maxItems;
    public double currWeight;
    public double maxWeight;
    public LinkedList[] table;
    public Hashing hash;
    public double loadFactor;

    public Backpack(int size) {
        numItems = 0;
        maxItems = size;
        currWeight = 0;
        maxWeight = 90;
        loadFactor = 0.82;
        table = new LinkedList[maxItems];
        hash = new Hashing(maxItems);
    }

    public void add(Weapon wp) {

        Double dNumItems = Double.valueOf(numItems);
        Double dMaxItems = Double.valueOf(maxItems);
        if ((dNumItems / dMaxItems) < loadFactor) {

            if (search(wp) != -1) {
                table[search(wp)].addLast(wp);
                currWeight += wp.weight;
                numItems++;
                return;
            }

            int startLoc = hash.hashFunction(wp);
            int count = 1;
            int loc = startLoc;
            while (table[loc] != null && table[loc].head.data.weaponName.compareTo(wp.weaponName) != 0) {
                loc = (startLoc + count * count) % maxItems;
                count++;
            }
            table[loc] = new LinkedList();
            table[loc].addLast(wp);
            currWeight += wp.weight;
            numItems++;
        }
    }

    public int search(Weapon wp) {
        int startLoc = hash.hashFunction(wp);
        int count = 1;
        int loc = startLoc;
        while (table[loc] != null && table[loc].head.data.weaponName.compareTo(wp.weaponName) != 0) {
            loc = (startLoc + count * count) % maxItems;
            count++;
        }
        if (table[loc] == null) {
            return -1;
        }
        return loc;
    }

    public int searchByName(String weap) {
        Weapon wp = new Weapon(weap);
        int startLoc = hash.hashFunction(wp);
        int count = 1;
        int loc = startLoc;
        while (table[loc] != null && table[loc].head.data.weaponName.compareTo(wp.weaponName) != 0) {
            loc = (startLoc + count * count) % maxItems;
            count++;
        }
        if (table[loc] == null) {
            return -1;
        }
        return loc;
    }

    public boolean weightMaxed(double inWeight) {
        return currWeight + inWeight >= maxWeight;
    }

    public boolean itemsMaxed(int inItem) {
        Double dNumItems = Double.valueOf(numItems + inItem);
        Double dMaxItems = Double.valueOf(maxItems);
        if ((dNumItems / dMaxItems) < loadFactor) {
            return false;
        }
        return true;
    }

    public void printBackpack() {
        for (int i = 0; i < maxItems; i++) {
            if (table[i] != null) {
                System.out.println("\nWeapon:" + table[i].head.data.weaponName + "\nRange:" + table[i].head.data.range
                        + " Damage:" + table[i].head.data.damage + " Weight:" + table[i].head.data.weight + " Number owned:" + table[i].numItems);
            }
        }
    }
}
