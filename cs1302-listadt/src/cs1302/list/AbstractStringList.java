package cs1302.list;

import cs1302.listadt.StringList;

/**
 * This class implements {@link cs1302.listadt.StringList}.
 */
abstract class AbstractStringList implements StringList {

    protected int size;

    /**
     * This method constructs an new list object with size 0.
     */
    public AbstractStringList() {
        this.size = 0;
    }

    /**
     * This method checks if the index is in range.
     * @param index The specified index to be checked.
     */
    protected void addCheckBounds(int index) {
        if (index < 0 || index > this.size) {
            throw new IndexOutOfBoundsException("The index is out of range.");
        }
    } // addCheckBounds

    /**
     * This method checks if the index is in range.
     * @param index The specified index to be checked.
     */
    protected void otherCheckBounds(int index) {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException("The index is out of range.");
        }
    } // otherCheckBounds

    /**
     * This method checks if the specified String is null.
     * @param str The specified String to be checked.
     */
    protected void checkNull(String str) {
        if (str == null) {
            throw new NullPointerException("The specified String is null.");
        }
    } // checkNull

    /**
     * This method checks if the specified String is empty.
     * @param str The specified String to be checked.
     */
    protected void checkEmpty(String str) {
        if (str.equals("")) {
            throw new IllegalArgumentException("The specified String is empty.");
        }
    } // checkEmpty

    /**
     * This method checks if the specified list is null.
     * @param sl The specified list to be checked.
     */
    protected void checkListExceptions(StringList sl) {
        if (sl == null) {
            throw new NullPointerException("The specified StringList is null.");
        }
    } // checkListExceptions

    /** {@inheritDoc} */
    public String makeString(String sep) {
        String str = "";
        // add the elements at this index, plus the separator, to the string
        for (int i = 0; i < this.size; i++) {
            str += this.get(i) + sep;
        }
        // store the string, without the last seperator, into the variable "str"
        str = str.substring(0, (str.length() - sep.length()));
        return str;
    } // makeString

    /** {@inheritDoc} */
    public boolean contains(String o) {
        // check exceptions
        this.checkNull(o);
        this.checkEmpty(o);
        for (int i = 0; i < this.size(); i++) {
            if (o.equals(this.get(i))) {
                return true;
            }
        }
        return false;
    } // contains

    /** {@inheritDoc} */
    public boolean containsIgnoreCase(String o) {
        // check exceptions
        this.checkNull(o);
        this.checkEmpty(o);
        for (int i = 0; i < this.size(); i++) {
            if (o.equalsIgnoreCase(this.get(i))) {
                return true;
            }
        }
        return false;
    } // containsIgnoreCase

    /** {@inheritDoc} */
    public boolean containsSubstring(String o) {
        // check exceptions
        this.checkNull(o);
        this.checkEmpty(o);
        for (int i = 0; i < this.size(); i++) {
            // if the element at this index contains the substring
            if (this.get(i).contains(o)) {
                return true;
            }
        }
        return false;
    } // containsSubstring

    /** {@inheritDoc} */
    public int indexOf(String s) {
        // check exceptions
        this.checkNull(s);
        this.checkEmpty(s);
        for (int i = 0; i < this.size(); i++) {
            // if the element at this index equals the specified string
            if (this.get(i).equals(s)) {
                return i;
            }
        }
        return -1;
    } // indexOf

    /** {@inheritDoc} */
    public int indexOfIgnoreCase(String s) {
        // check exceptions
        this.checkNull(s);
        this.checkEmpty(s);
        // if the element at this index equals the specified string, ignoring case
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).equalsIgnoreCase(s)) {
                return i;
            }
        }
        return -1;
    } // indexOfIgnoreCase

    /** {@inheritDoc} */
    public boolean isEmpty() {
        if (this.size() == 0) {
            return true;
        } else {
            return false;
        }
    } // isEmpty

    /** {@inheritDoc} */
    public String[] toArray() {
        String[] arr = new String[this.size()];
        for (int i = 0; i < this.size(); i++) {
            // add the element at the specified index to the array
            arr[i] = this.get(i);
        }
        return arr;
    } // toArray

} // ParentStringList
