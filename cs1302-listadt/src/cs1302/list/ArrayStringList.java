package cs1302.list;

import cs1302.listadt.StringList;

/**
 * This class extends {@code AbstractStringList}.
 */
public class ArrayStringList extends AbstractStringList {

    private final int arraySize = 10;
    String[] array;

    /**
     * This method constructs a new list object.
     */
    public ArrayStringList() {
        this.array = new String[arraySize];
    }

    /**
     * This method constructs a deep copy of the other list.
     * @param other The list to be deep-copied.
     */
    public ArrayStringList(StringList other) {
        array = new String[arraySize];
        for (int i = 0; i < other.size(); i++) {
            this.add(i, other.get(i));
        }
        this.size = other.size();
    }

    /**
     * This method extends the array.
     * @param length The length you want to extend the array by.
     */
    private void extend(int length) {
        int newSize = this.array.length + length;
        String[] newArr = new String[newSize];
        for (int i = 0; i < this.array.length; i++) {
            newArr[i] = this.array[i];
        }
        array = newArr;
    } // extend

    /** {@inheritDoc} */
    public boolean add(int index, String s) {
        // check exceptions
        this.addCheckBounds(index);
        this.checkNull(s);
        this.checkEmpty(s);
        // extends the array if the array is full
        if (this.size == this.array.length) {
            this.extend(arraySize);
        }
        // adds 1 to the indicies of the elements from the specified index and on
        for (int i = this.size - 1; i >= index; i--) {
            array[i + 1] = array[i];
        }
        // adds the string at the specified index
        this.array[index] = s;
        size++;
        return true;
    } // add

    /** {@inheritDoc} */
    public boolean add(int index, StringList sl) {
        // checks exceptions
        this.addCheckBounds(index);
        this.checkListExceptions(sl);
        if (sl.isEmpty()) {
            return false;
        }
        String[] slArray = sl.toArray();
        // if the adding the list will extend the array past its max value
        if (this.size + slArray.length >= array.length) {
            this.extend(slArray.length + arraySize);
        }
        // adds the list length to the indicies of the elements from the specified index and on
        for (int i = this.size - 1; i >= index; i--) {
            array[i + slArray.length] = array[i];
        }
        // adds list to the array
        for (int j = 0; j < slArray.length; j++) {
            array[index + j] = slArray[j];
        }
        size += sl.size();
        return true;
    } // add

    /** {@inheritDoc} */
    public boolean add(String s) {
        // checks exceptions
        this.checkNull(s);
        this.checkEmpty(s);
        // if the array has reached its max length, extend it
        if (this.size == this.array.length) {
            this.extend(arraySize);
        }
        // add the string to the end of the list
        array[this.size] = s;
        size++;
        return true;
    } // add

    /** {@inheritDoc} */
    public boolean add(StringList sl) {
        // checks exceptions
        this.checkListExceptions(sl);
        if (sl.isEmpty()) {
            return false;
        }
        String[] slArray = sl.toArray();
        // if the adding the list will extend the array past its max value
        if (this.size + slArray.length >= this.array.length) {
            this.extend(slArray.length + arraySize);
        }
        // add the list to the end of the array
        for (int i = 0; i < slArray.length; i++) {
            array[this.size + i] = slArray[i];
        }
        size += sl.size();
        return true;
    } // add

    /** {@inheritDoc} */
    public String get(int index) {
        // checks exceptions
        this.otherCheckBounds(index);
        return array[index];
    }

    /** {@inheritDoc} */
    public int size() {
        int count = 0;
        for (int i = 0; i < array.length; i++) {
            // counts the non-null elements in the array
            if (array[i] != null) {
                count++;
            }
        }
        return count;
    } // size

    /** {@inheritDoc} */
    public String set(int index, String s) {
        // checks exceptions
        this.otherCheckBounds(index);
        this.checkNull(s);
        this.checkEmpty(s);
        String previousString = array[index];
        // sets element at the specified index to the specified string
        array[index] = s;
        return previousString;
    } // set

    /** {@inheritDoc} */
    public String remove(int index) {
        // check exceptions
        this.otherCheckBounds(index);
        String removed = array[index];
        // move the elements in the array to the left 1
        for (int i = index; i < array.length - 1; i++) {
            array[i] = array[i + 1];
        }
        size--;
        return removed;
    } // remove

    /** {@inheritDoc} */
    public StringList splice(int fromIndex, int toIndex) {
        // checks exceptions
        if (fromIndex < 0 || toIndex > this.size() || fromIndex > toIndex) {
            throw new IndexOutOfBoundsException("The endpoint index values are illegal.");
        }
        StringList splicedList = new ArrayStringList();
        // add the elements to the new list
        for (int i = fromIndex; i < toIndex; i++) {
            splicedList.add(this.get(i));
        }
        return splicedList;
    } // splice

    /** {@inheritDoc} */
    public void clear() {
        // set all elements in the array to null
        for (int i = 0; i < this.array.length; i++) {
            array[i] = null;
        }
        this.size = 0;
    } // clear

    /** {@inheritDoc} */
    public StringList distinct() {
        StringList distinctList = new ArrayStringList();
        for (int i = 0; i < this.size(); i++) {
            // if the new list does not already contain this element, add it
            if (!distinctList.contains(this.get(i))) {
                distinctList.add(this.get(i));
            }
        }
        return distinctList;
    } // distinct

    /** {@inheritDoc} */
    public StringList reverse() {
        StringList reversedList = new ArrayStringList();
        // add the elements from the array to the new list, backwards
        for (int i = this.size() - 1; i >= 0; i--) {
            reversedList.add(this.get(i));
        }
        return reversedList;
    } // reverse

} // ArrayStringList
