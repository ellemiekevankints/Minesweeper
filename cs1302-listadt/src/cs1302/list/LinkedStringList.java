

package cs1302.list;

import cs1302.listadt.StringList;

/**
 * This class extends {@code AbstractStringList}.
 */
public class LinkedStringList extends AbstractStringList {

    StringList.Node head;

    /**
     * This methods constructs a new object.
     */
    public LinkedStringList() {
        this.head = new StringList.Node();
    }

    /**
     * This method constructs a deep copy of the other list.
     * @param other The list to be deep-copied.
     */
    public LinkedStringList(StringList other) {
        this.head = new StringList.Node(other.get(0));
        for (int i = 0; i < other.size(); i++) {
            this.add(i, other.get(i));
        }
    }

    /** {@inheritDoc} */
    public boolean add(int index, String s) {
        // check exceptions
        this.addCheckBounds(index);
        this.checkEmpty(s);
        this.checkNull(s);
        StringList.Node newNode = new StringList.Node(s, null); // create a new node
        StringList.Node temp = this.head;
        if (index == 0 && this.size == 0) {
            head = newNode; // assigns head to the new node
        } else if (index == 0) {
            newNode.setNext(head); // set the next value of the new node to head
            head = newNode;
        } else {
            // get the node before the specified index
            for (int i = 0; i < index - 1; i++) {
                temp = temp.getNext();
            }
            // set the next value of new node to the next value of temp
            newNode.setNext(temp.getNext());
            temp.setNext(newNode);
        }
        size++;
        return true;
    } // add

    /** {@inheritDoc} */
    public boolean add(int index, StringList sl) {
        // check exceptions
        this.addCheckBounds(index);
        this.checkListExceptions(sl);
        if (sl.isEmpty()) {
            return false;
        }
        for (int i = 0; i < sl.size(); i++) {
            this.add(i + index, sl.get(i));
        }
        return true;
    } // add

    /** {@inheritDoc} */
    public boolean add(String s) {
        // check exceptions
        this.checkEmpty(s);
        this.checkNull(s);
        this.add(this.size, s); // add the string to the end of the list
        return true;
    } // add

    /** {@inheritDoc} */
    public boolean add(StringList sl) {
        // check exceptions
        this.checkListExceptions(sl);
        if (sl.isEmpty()) {
            return false;
        }
        for (int i = 0; i < sl.size(); i++) {
            // add the element at the specified index to the end of the list
            this.add(this.size, sl.get(i));
        }
        return true;
    } // add

    /** {@inheritDoc} */
    public void clear() {
        StringList.Node temp = this.head;
        for (int i = 0; i < this.size; i++) {
            temp.setStr(null);
            temp = temp.getNext();
        }
        size = 0;
    } // clear

    /** {@inheritDoc} */
    public String get(int index) {
        // check exceptions
        this.otherCheckBounds(index);
        StringList.Node temp = this.head;
        for (int i = 0; i < index; i++) {
            temp = temp.getNext();
        }
        return temp.getStr();
    } // get

    /** {@inheritDoc} */
    public String set(int index, String s) {
        // check exceptions
        this.otherCheckBounds(index);
        this.checkNull(s);
        this.checkEmpty(s);
        StringList.Node temp = this.head;
        String str = "";
        for (int i = 0; i < index; i++) {
            temp = temp.getNext();
        }
        str = temp.getStr();
        temp.setStr(s);
        return str;
    } // set

    /** {@inheritDoc} */
    public int size() {
        return this.size;
    } // size

    /** {@inheritDoc} */
    public String remove(int index) {
        // check exceptions
        this.otherCheckBounds(index);
        String s = "";
        StringList.Node temp = this.head;
        if (index == 0) {
            s = temp.getStr();
            // moves head to the next element in the list
            this.head = temp.getNext();
        } else {
            for (int i = 0; i < index - 1; i++) {
                temp = temp.getNext();
            }
            s = temp.getNext().getStr();
            // set the next value of temp to the element two elemtens over
            temp.setNext(temp.getNext().getNext());
        }
        size--;
        return s;
    } // remove

    /** {@inheritDoc} */
    public StringList splice(int fromIndex, int toIndex) {
        // check exceptions
        if (fromIndex < 0 || toIndex > this.size() || fromIndex > toIndex) {
            throw new IndexOutOfBoundsException("The endpoint index values are illegal.");
        }
        StringList splicedList = new LinkedStringList();
        for (int i = fromIndex; i < toIndex; i++) {
            splicedList.add(this.get(i));
        }
        return splicedList;
    } // splice

    /** {@inheritDoc} */
    public StringList distinct() {
        StringList distinctList = new LinkedStringList();
        for (int i = 0; i < this.size; i++) {
            // if the original list does not already contain this string
            if (!distinctList.contains(this.get(i))) {
                distinctList.add(this.get(i));
            }
        }
        return distinctList;
    } // distinct

    /** {@inheritDoc} */
    public StringList reverse() {
        StringList reversedList = new LinkedStringList();
        // add the elements of the original list to the new list, backwards
        for (int i = this.size() - 1; i >= 0; i--) {
            reversedList.add(this.get(i));
        }
        return reversedList;
    } // reverse

} // LinkedStringList
