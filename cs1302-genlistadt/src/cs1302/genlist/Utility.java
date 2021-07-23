package cs1302.genlist;

import java.util.NoSuchElementException;

/**
 * This class contains utility methods for checking the validity of
 * input parameters.
 */
public class Utility {

    /**
     * This method checks if the specified object is null.
     * @param obj The specified object to be checked.
     * @throws NullPointerException if parameter {@code obj} is {@code null}.
     */
    public static void checkNull(Object obj) {
        if (obj == null) {
            throw new NullPointerException("The specified object is null.");
        }
    } // checkNull

    /**
     * This method checks if the index is in range.
     * @param index The specified index to be checked.
     * @param size The size of the list.
     */
    public static void addCheckBounds(int index, int size) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("The index is out of range.");
        }
    } // addCheckBounds

    /**
     * This method checks if the index is in range.
     * @param index The specified index to be checked.
     * @param size The size of the list.
     */
    public static void otherCheckBounds(int index, int size) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("The index is out of range.");
        }
    } // otherCheckBounds

    /**
     * This method checks if the iteration has more elements.
     * @param b The boolean to be checked.
     */
    public static void noSuchElem(boolean b) {
        if (b == false) {
            throw new NoSuchElementException("The iteration has no more elements.");
        }
    } // noSuchElem

} // Utility
