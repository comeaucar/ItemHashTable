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
public class Weapon {

    public String weaponName;
    public int range;
    public int damage;
    public double weight;
    public double cost;
    public boolean deleted; // uses boolean to mark if its deleted, this way it will delete from store but remain in backpack without jepordizing hashing

    public Weapon(String n, int rang, int dam, double w, double c) {
        weaponName = n;
        damage = dam;
        range = rang;
        weight = w;
        cost = c;
        deleted = false;
    }

}
