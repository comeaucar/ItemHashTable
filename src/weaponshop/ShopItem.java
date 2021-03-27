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
public class ShopItem {

    Weapon item;
    int numberInStock;

    public ShopItem(Weapon w, int nInStock) {
        item = w;
        numberInStock = nInStock;
    }

    @Override
    public String toString() {
        return ("Name: " + item.weaponName + "   Damage:" + item.damage + "    Cost:" + item.cost + "     Quantity in stock:" + numberInStock);
    }
}
