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
public class Node {

    public Weapon data;
    public Node next;

    public Node(Weapon data) {
        this.data = data;
        next = null;
    }
}
