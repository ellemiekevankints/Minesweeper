package cs1302.genlist;

import cs1302.genlistadt.GenList;
import java.util.function.IntFunction;

/**
 * This class implements {@link cs1302.listadt.GenList}.
 */
abstract class BaseGenList<T> implements GenList<T> {

    protected int size;

    /**
     * This method constructs a new list object with size 0.
     */
    public BaseGenList() {
        this.size = 0;
    }

    /** {@inheritDoc} */
    public String makeString(String sep) {
        String str = "";
        for (int i = 0; i < this.size; i++) {
            str += this.get(i) + sep;
        }
        // store the string, without the last seperator, into the variable "str"
        str = str.substring(0, (str.length() - sep.length()));
        return str;
    } // makeString

    /** {@inheritDoc} */
    public boolean contains(T o) {
        // check exceptions
        Utility.checkNull(o);

        for (int i = 0; i < this.size; i++) {
            if (o.equals(this.get(i))) {
                return true;
            }
        }
        return false;
    } // contains

    /** {@inheritDoc} */
    public int indexOf(T obj) {
        // check exceptions
        Utility.checkNull(obj);

        for (int i = 0; i < this.size; i++) {
            if (obj.equals(this.get(i))) {
                return i;
            }
        }
        return -1;
    } // indexOf

    /** {@inheritDoc} */
    public boolean isEmpty() {
        if (this.size == 0) {
            return true;
        } else {
            return false;
        }
    } // isEmpty

    /** {@inheritDoc} */
    public T[] toArray(IntFunction<T[]> gen) {
        T[] arr = gen.apply(this.size);
        for (int i = 0; i < arr.length; i++) {
            // add the element at the specified index to the array
            arr[i] = this.get(i);
        }
        return arr;
    } // toArray

} // BaseGenList
