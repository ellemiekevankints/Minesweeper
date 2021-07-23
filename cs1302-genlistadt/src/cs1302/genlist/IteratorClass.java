package cs1302.genlist;

import cs1302.genlistadt.GenList;
import java.util.Iterator;

/**
 * This class implements {@link java.util.Interator}.
 */
public class IteratorClass<E> implements Iterator<E> {

    GenList<E> gen;
    int index;

    /**
     * This method constructs a new iterator object using the {@code gen} list and sets
     * the {@code index} to 0.
     * @param gen The list to be iterated over.
     */
    public IteratorClass(GenList<E> gen) {
        this.gen = gen;
        this.index = 0;
    }

    /** {@inheritDoc} */
    public boolean hasNext() {
        if (index < gen.size()) {
            return true;
        }
        return false;
    } // hasNext

    /** {@inheritDoc} */
    public E next() {
        // check exceptions
        IteratorClass<E> ic = new IteratorClass<>(gen);
        boolean b = ic.hasNext();
        Utility.noSuchElem(b);

        // get the element at the specified index
        E elem = gen.get(index);
        index++;
        return elem;
    } // next

} // IteratorClass
