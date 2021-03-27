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
public class LinkedList {

    public Node head;
    public int numItems;

    public LinkedList() {
        head = null;
    }

    public void addFront(Weapon data) {
        Node newNode = new Node(data);
        newNode.next = head;
        head = newNode;
        numItems++;
    }

    public void addLast(Weapon data) {
        Node newNode = new Node(data);
        if (head == null) {
            head = newNode;
            numItems++;
            return;
        }
        Node curr = head;
        while (curr.next != null) {
            curr = curr.next;
        }
        numItems++;
        curr.next = newNode;
    }
}
