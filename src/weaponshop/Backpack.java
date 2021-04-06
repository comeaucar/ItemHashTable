package weaponshop;

public class Backpack {

    public int numItems;
    public int maxItems;
    public double currWeight;
    public double maxWeight;
    private LinkedList[] table;
    private Hashing hash;
    public double loadFactor;

    public Backpack(int size, double lf) {
        numItems = 0;
        maxItems = size;
        currWeight = 0;
        maxWeight = 90;
        loadFactor = lf;
        table = new LinkedList[maxItems];
        hash = new Hashing(maxItems);
    }

    public void add(Weapon wp) {

        Double dNumItems = Double.valueOf(numItems);
        Double dMaxItems = Double.valueOf(maxItems);
        if ((dNumItems / dMaxItems) < loadFactor) {
            int loc = hash.hashFunction(wp);
            if(table[loc] == null){
                table[loc] = new LinkedList();
                table[loc].addFront(wp);
                currWeight += wp.weight;
                numItems++;
                return;
            }
            table[loc].addFront(wp);
            currWeight += wp.weight;
            numItems++;
        }
    }

    public int search(Weapon wp) {
        int loc = hash.hashFunction(wp);
        if(table[loc] == null){
            return -1;
        }
        if(table[loc].search(wp) != null){
            return loc;
        }
        return -1;
    }

    public boolean weightMaxed(double inWeight) { // checks if weight of weapon to be purchased will make backpack go overweight
        return currWeight + inWeight >= maxWeight;
    }

    public boolean itemsMaxed(int inItem) { // checks if item being added will make backpack be out of room
        Double dNumItems = Double.valueOf(numItems + inItem);
        Double dMaxItems = Double.valueOf(maxItems);
        if ((dNumItems / dMaxItems) < loadFactor) {
            return false;
        }
        return true;
    }

    public void printBackpack() {
        if(numItems == 0){
            System.out.println("\n ** You currently don't own any items **\n");
            return;
        }
        for (int i = 0; i < maxItems; i++) {
            if (table[i] != null) {
                table[i].printList();
            }
        }
    }
}
