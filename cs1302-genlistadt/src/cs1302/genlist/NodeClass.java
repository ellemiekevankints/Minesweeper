package cs1302.genlist;

/**
 * Represents a node with the associated properties {@code contents} and {@code next}.
 */
public class NodeClass<T> {

    private T contents;
    private NodeClass<T> next;

    /**
     * Constructs a new node with all properties unset.
     */
    public NodeClass() {
        this.contents = null;
        this.next = null;
    }

    /**
     * Constructs a new node with the {@code contents} property set and the {@code next} property
     * unset.
     * @param contents The contents of the node.
     */
    public NodeClass(T contents) {
        this.contents = contents;
        this.next = null;
    }

    /**
     * Constructs a new node with the {@code contents} and {@code next} properties set.
     * @param contents The contents of the node.
     * @param next The next node to point to.
     */
    public NodeClass(T contents, NodeClass<T> next) {
        this.contents = contents;
        this.next = next;
    }

    /**
     * Returns the value of the {@code contents} property for this node.
     * @return a referene to the associated object
     */
    public T getContents() {
        return this.contents;
    } // getContents

    /**
     * Returns the value of the {@code next} property for this node.
     *
     * @return a reference to the next node
     */
    public NodeClass<T> getNext() {
        return this.next;
    } // getNext

    /**
     * Sets the value for the {@code contents} property for this node.
     * @param t a reference to the associated object
     */
    public void setContents(T t) {
        this.contents = t;
    } // setContents

    /**
     * Sets the value for the {@code next} property for this node.
     * @param n a reference to the next node
     */
    public void setNext(NodeClass<T> n) {
        this.next = n;
    } //setNext

} // NodeClass
