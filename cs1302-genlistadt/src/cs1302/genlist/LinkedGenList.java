package cs1302.genlist;

import cs1302.genlistadt.GenList;
import java.util.function.Predicate;
import java.util.function.IntFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.Iterator;
import java.util.Comparator;

/**
 * This class extends {@code BaseGenList<T>}.
 */
public class LinkedGenList<T> extends BaseGenList<T> {

    NodeClass<T> head;

    /**
     * This methods constructs a new object.
     */
    public LinkedGenList() {
        this.head = new NodeClass<T>();
    }

    /**
     * This method constructs a deep copy of the other list.
     * @param other The list to be deep-copied.
     * @param <U> The type argument to be parameterized.
     */
    public <U extends T> LinkedGenList(GenList<U> other) {
        this.head = new NodeClass<T>(other.get(0));
        for (int i = 0; i < other.size(); i++) {
            // add the specified element at the specified index to the new list
            this.add(i, other.get(i));
        }
    }

    /** {@inheritDoc} */
    public T get(int index) {
        // check exceptions
        Utility.otherCheckBounds(index, this.size);

        NodeClass<T> temp = this.head;
        for (int i = 0; i < index; i++) {
            temp = temp.getNext();
        }
        return temp.getContents();
    } // get

    /** {@inheritDoc} */
    public T set(int index, T obj) {
        // check exceptions
        Utility.otherCheckBounds(index, this.size);
        Utility.checkNull(obj);

        NodeClass<T> temp = this.head;
        for (int i = 0; i < index; i++) {
            temp = temp.getNext();
        }
        T contents = temp.getContents();
        temp.setContents(obj);
        return contents;
    } // set

    /** {@inheritDoc} */
    public int size() {
        return this.size;
    } // size

    /** {@inheritDoc} */
    public boolean add(int index, T obj) {
        // check exceptions
        Utility.addCheckBounds(index, this.size);
        Utility.checkNull(obj);

        NodeClass<T> newNode = new NodeClass<>(obj, null); // create a new node
        NodeClass<T> temp = this.head;
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
    public <U extends T> boolean add(int index, GenList<U> list) {
        // check exceptions
        Utility.addCheckBounds(index, this.size);
        Utility.checkNull(list);

        if (list.isEmpty()) {
            return false;
        }
        for (int i = 0; i < list.size(); i++) {
            this.add(i + index, list.get(i));
        }
        return true;
    } // add

    /** {@inheritDoc} */
    public boolean add(T obj) {
        // check exceptions
        Utility.checkNull(obj);

        this.add(this.size, obj); // add the object to the end of the list
        return true;
    } // add

    /** {@inheritDoc} */
    public <U extends T> boolean add(GenList<U> list) {
        // check exceptions
        Utility.checkNull(list);

        if (list.isEmpty()) {
            return false;
        }
        for (int i = 0; i < list.size(); i++) {
            // add the element at the specified index to the end of the list
            this.add(this.size, list.get(i));
        }
        return true;
    } // add

    /** {@inheritDoc} */
    public void clear() {
        NodeClass<T> temp = this.head;
        for (int i = 0; i < this.size; i++) {
            // set all the list contents to null
            temp.setContents(null);
            temp = temp.getNext();
        }
        size = 0;
    } // clear

    /** {@inheritDoc} */
    public T remove(int index) {
        // check exceptions
        Utility.otherCheckBounds(index, this.size);

        T t = null;
        NodeClass<T> temp = this.head;
        if (index == 0) {
            t = temp.getContents();
            // moves head to the next element in the list
            this.head = temp.getNext();
        } else {
            for (int i = 0; i < index - 1; i++) {
                temp = temp.getNext();
            }
            t = temp.getNext().getContents();
            // set the next value of temp to the element two elemtens over
            temp.setNext(temp.getNext().getNext());
        }
        size--;
        return t;
    } // remove

    /** {@inheritDoc} */
    public GenList<T> splice(int fromIndex, int toIndex) {
        // check exceptions
        if (fromIndex < 0 || toIndex > this.size || fromIndex > toIndex) {
            throw new IndexOutOfBoundsException("The endpoint index values are illegal.");
        }

        GenList<T> splicedList = new LinkedGenList<T>();
        // the all the elements from the "fromIndex" to the "toIndex" to the new list
        for (int i = fromIndex; i < toIndex; i++) {
            splicedList.add(this.get(i));
        }
        return splicedList;
    } // splice

    /** {@inheritDoc} */
    public GenList<T> distinct() {
        GenList<T> distinctList = new LinkedGenList<T>();
        for (int i = 0; i < this.size; i++) {
            T t = this.get(i);
            // if the original list does not already contain this string
            if (!distinctList.contains(t)) {
                // add it to the new list
                distinctList.add(t);
            }
        }
        return distinctList;
    } // distinct

    /** {@inheritDoc} */
    public GenList<T> reverse() {
        GenList<T> reversedList = new LinkedGenList<T>();
        // add the elements of the original list to the new list, backwards
        for (int i = this.size() - 1; i >= 0; i--) {
            reversedList.add(this.get(i));
        }
        return reversedList;
    } // reverse

    /** {@inheritDoc} */
    public boolean allMatch(Predicate<T> p) {
        // check exceptions
        Utility.checkNull(p);

        int count = 0;
        for (int i = 0; i < this.size; i++) {
            // if the specified element matches the specified predicate test
            if (p.test(this.get(i))) {
                count++;
            }
        }

        // if all the elements matched
        if (count == this.size) {
            return true;
        }
        return false;
    } // allMatch

    /** {@inheritDoc} */
    public boolean anyMatch(Predicate<T> p) {
        // check exceptions
        Utility.checkNull(p);

        for (int i = 0; i < this.size; i++) {
            // if the element at this index passes the specified predicate test
            if (p.test(this.get(i))) {
                return true;
            }
        }
        return false;
    } // anyMatch

    /** {@inheritDoc} */
    public Iterator<T> iterator() {
        LinkedGenList<T> list = new LinkedGenList<T>();
        for (int i = 0; i < this.size; i++) {
            list.add(this.get(i));
        }
        Iterator<T> i = new IteratorClass<T>(list);
        return i;
    } // iterator

    /** {@inheritDoc} */
    public GenList<T> filter(Predicate<T> p) {
        // check exceptions
        Utility.checkNull(p);

        GenList<T> gen = new LinkedGenList<T>();
        for (int i = 0; i < this.size; i++) {
            // if the element at this index passes the specified predicate test
            if (p.test(this.get(i))) {
                // add it to the new list
                gen.add(this.get(i));
            }
        }
        return gen;
    } // filter

    /** {@inheritDoc} */
    public <R> GenList<R> map(Function<T, R> f) {
        // check exceptions
        Utility.checkNull(f);

        GenList<R> gen = new LinkedGenList<>();
        for (int i = 0; i < this.size; i++) {
            // store the result of applying the specified function to the element
            R result = f.apply(this.get(i));
            if (result == null) {
                throw new NullPointerException(
                    "The result of calling apply using the function is null.");
            } else {
                // add that result to the new list
                gen.add(result);
            }
        }
        return gen;
    } // map

    /** {@inheritDoc} */
    public T reduce(T start, BinaryOperator<T> f) {
        // check exceptions
        Utility.checkNull(f);

        T result = start;
        for (int i = 0; i < this.size; i++) {
            // update the new result of applying the function to the element
            result = f.apply(result, this.get(i));
            if (result == null) {
                throw new NullPointerException(
                    "The result of calling apply using the function is null.");
            }
        }
        return result;
    } // reduce

    /** {@inheritDoc} */
    public T min(Comparator<T> c) {
        // check exceptions
        Utility.checkNull(c);

        if (this.isEmpty()) {
            return null;
        }

        T m = this.get(0); // sets "m" to the first element in the list
        for (T e : this) {
            // if the comparison returns 0 or less
            if (c.compare(e, m) <= 0) {
                // reassign "m" to the the specified element
                m = e;
            }
        }
        return m;
    } // min

    /** {@inheritDoc} */
    public T max(Comparator<T> c) {
        // check exceptions
        Utility.checkNull(c);

        if (this.isEmpty()) {
            return null;
        }

        T m = this.get(0); // sets "m" to the first element in the list
        for (T e : this) {
            // if the comparison returns 0 or more
            if (c.compare(e, m) >= 0) {
                // reassign "m" to the specified element
                m = e;
            }
        }
        return m;
    } // max

} //LinkedGenList
