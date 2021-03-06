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
public class Player {

    public String name;
    public double money;
    public Backpack backpack;

    public Player(String n, double m, int bpSize, double lf) {
        name = n;
        money = m;
        //numItems = 0;
        backpack = new Backpack(bpSize, lf);
    }

    public void buy(ShopItem si) {
        if (backpack.itemsMaxed(1)) {
            System.out.println("You have no more room in your backpack!");
            return;
        }
        Weapon w = si.item;
        System.out.println("\n ** " + w.weaponName + " bought...\n");
        backpack.add(w);
        System.out.println("Number of items you own: " + backpack.numItems);
    }

    public void withdraw(double amt) {
        money = money - amt;
    }

    public void printCharacter() {
        System.out.println(" Name:" + name + "\n Money:" + money);
        backpack.printBackpack();
    }

}
