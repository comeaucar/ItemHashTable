
package weaponshop;


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
        curr.next = newNode;
        numItems++;
    }
    
    public Weapon search(Weapon data){
        if(head == null){
            return null;
        }
        Node curr = head;
        while(curr != null && curr.data != data){
            curr = curr.next;
        }
        if(curr == null){
            return null;
        }
        return curr.data;
    }
    
    public void printList(){
        Object[] arr;
        Node curr = head;
        while(curr != null){
            System.out.println(curr.data.toString());
            curr = curr.next;
        }
    }  
}
