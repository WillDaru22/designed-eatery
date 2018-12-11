package application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

/**
 * Implementation of a B+ tree to allow efficient access to many different
 * indexes of a large data set. BPTree objects are created for each type of
 * index needed by the program. BPTrees provide an efficient range search as
 * compared to other types of data structures due to the ability to perform
 * log_m N lookups and linear in-order traversals of the data items.
 * 
 * @author sapan (sapan@cs.wisc.edu)
 *
 * @param <K> key - expect a string that is the type of id for each item
 * @param <V> value - expect a user-defined type that stores all data for a food
 *        item
 */
public class BPTree<K extends Comparable<K>, V> implements BPTreeADT<K, V> {

	// Root of the tree
	private Node root;

	// Branching factor is the number of children nodes
	// for internal nodes of the tree
	private int branchingFactor;
	private static final int DEFAULT = 100;

	public BPTree() {
		this(DEFAULT);
	}

	/**
	 * Public constructor for BPTree class
	 * 
	 * @param branchingFactor number of successors
	 */
	public BPTree(int branchingFactor) {
		if (branchingFactor <= 2) { // branching factor should be greater or equal to 2
			throw new IllegalArgumentException("ERROR: Illegal branching factor: " + branchingFactor);
		}
		this.branchingFactor = branchingFactor;
		root = new LeafNode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see BPTreeADT#insert(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void insert(K key, V value) {
		root.insert(key, value); // call insert method in Node Class
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see BPTreeADT#rangeSearch(java.lang.Object, java.lang.String)
	 */
	@Override
	public List<V> rangeSearch(K key, String comparator) {
		if (!comparator.contentEquals(">=") && !comparator.contentEquals("==") && !comparator.contentEquals("<=")) {
			return new ArrayList<V>();
		} else {
			return root.rangeSearch(key, comparator);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		Queue<List<Node>> queue = new LinkedList<List<Node>>();
		queue.add(Arrays.asList(root));
		StringBuilder sb = new StringBuilder();
		while (!queue.isEmpty()) {
			Queue<List<Node>> nextQueue = new LinkedList<List<Node>>();
			while (!queue.isEmpty()) {
				List<Node> nodes = queue.remove();
				sb.append('{');
				Iterator<Node> it = nodes.iterator();
				while (it.hasNext()) {
					Node node = it.next();
					sb.append(node.toString());
					if (it.hasNext())
						sb.append(", ");
					if (node instanceof BPTree.InternalNode)
						nextQueue.add(((InternalNode) node).children);
				}
				sb.append('}');
				if (!queue.isEmpty())
					sb.append(", ");
				else {
					sb.append('\n');
				}
			}
			queue = nextQueue;
		}
		return sb.toString();
	}

	/**
	 * This abstract class represents any type of node in the tree This class is a
	 * super class of the LeafNode and InternalNode types.
	 * 
	 * @author sapan
	 */
	private abstract class Node {

		// List of keys
		List<K> keys;

		/**
		 * Package constructor
		 */
		Node() {
			super();
		}

		/**
		 * Inserts key and value in the appropriate leaf node and balances the tree if
		 * required by splitting
		 * 
		 * @param key
		 * @param value
		 */
		abstract void insert(K key, V value);

		/**
		 * Gets the first leaf key of the tree
		 * 
		 * @return key
		 */
		abstract K getFirstLeafKey();

		/**
		 * Gets the new sibling created after splitting the node
		 * 
		 * @return Node
		 */
		abstract Node split();

		/*
		 * (non-Javadoc)
		 * 
		 * @see BPTree#rangeSearch(java.lang.Object, java.lang.String)
		 */
		abstract List<V> rangeSearch(K key, String comparator);

		/**
		 * 
		 * @return boolean
		 */
		abstract boolean isOverflow();

		public String toString() {
			return keys.toString();
		}

		abstract V getVal(K key);

		abstract void removeVal(K key);

		abstract void merge(Node sibling);;

		abstract boolean isUnderflow();

		int keySize() {
			return keys.size();
		}

	} // End of abstract class Node

	/**
	 * This class represents an internal node of the tree. This class is a concrete
	 * sub class of the abstract Node class and provides implementation of the
	 * operations required for internal (non-leaf) nodes.
	 * 
	 * @author sapan
	 */
	private class InternalNode extends Node {

		// List of children nodes
		List<Node> children;

		/**
		 * Package constructor
		 */
		InternalNode() {
			super();
			this.keys = new ArrayList<K>();
			this.children = new ArrayList<Node>();
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see BPTree.Node#getFirstLeafKey()
		 */
		K getFirstLeafKey() {
			return children.get(0).getFirstLeafKey(); // recursively get first leaf key
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see BPTree.Node#isOverflow()
		 */
		boolean isOverflow() {
			if (children.size() > branchingFactor) {
				return true;
			} else {
				return false;
			}
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see BPTree.Node#insert(java.lang.Comparable, java.lang.Object)
		 */
		void insert(K key, V value) {

			Node child = getChild(key);
			child.insert(key, value);

			if (child.isOverflow()) { // overflow in child node
				Node sibling = child.split();
				insertChild(sibling.getFirstLeafKey(), sibling);
			}

			if (root.isOverflow()) { // overflow in root node
				Node sibling = split();
				InternalNode temp = new InternalNode();
				temp.keys.add(sibling.getFirstLeafKey());
				temp.children.add(this);
				temp.children.add(sibling);
				root = temp;
			}
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see BPTree.Node#split()
		 */
		Node split() {
			InternalNode sibling = new InternalNode();

			int fromIndex = keySize() / 2 + 1;
			int toIndex = keySize();
			sibling.keys.addAll(keys.subList(fromIndex, toIndex));
			sibling.children.addAll(children.subList(fromIndex, toIndex + 1));

			keys.subList(fromIndex - 1, toIndex).clear();
			children.subList(fromIndex, toIndex + 1).clear();
			return sibling;
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see BPTree.Node#rangeSearch(java.lang.Comparable, java.lang.String)
		 */
		List<V> rangeSearch(K key, String comparator) {
			return getChild(key).rangeSearch(key, comparator);
		}

		@Override
		V getVal(K key) {
			return getChild(key).getVal(key);
		}

		@Override
		void removeVal(K key) {
			Node child = getChild(key);
			child.removeVal(key);

			if (child.isUnderflow()) { // child underflow
				Node leftChild = getLeftChild(key);
				Node rightChild = getRightChild(key);

				Node left = leftChild != null ? leftChild : child;
				Node right = leftChild != null ? child : rightChild;
				left.merge(right); // merge both nodes
				deleteChild(right.getFirstLeafKey());

				if (left.isOverflow()) {
					Node sibling = left.split();
					insertChild(sibling.getFirstLeafKey(), sibling);
				}
				if (root.keySize() == 0) {
					root = left;
				}
			}
		}

		@Override
		void merge(BPTree<K, V>.Node sibling) {
			@SuppressWarnings("unchecked")
			InternalNode node = (InternalNode) sibling;

			keys.add(node.getFirstLeafKey()); // add first leaf key
			keys.addAll(node.keys); // add all keys
			children.addAll(node.children); // add all children
		}

		Node getChild(K key) {
			int location = Collections.binarySearch(keys, key);
			int childIndex = 0;
			if (location >= 0) {
				childIndex = location + 1;
			} else {
				childIndex = -location - 1;
			}
			return children.get(childIndex);

		}

		@Override
		boolean isUnderflow() {
			if (children.size() < (branchingFactor + 1) / 2) {
				return true;
			}
			return false;
		}

		void deleteChild(K key) {
			int location = Collections.binarySearch(keys, key);
			if (location >= 0) {
				keys.remove(location);
				children.remove(location + 1);
			}

		}

		void insertChild(K key, Node child) {
			int location = Collections.binarySearch(keys, key);
			int childIndex = 0;
			if (location >= 0) {
				childIndex = location + 1;
			} else {
				childIndex = -location - 1;
			}

			if (location >= 0) {
				children.set(childIndex, child);
			} else {
				keys.add(childIndex, key);
				children.add(childIndex + 1, child);
			}
		}

		Node getLeftChild(K key) {
			int location = Collections.binarySearch(keys, key);
			int childIndex = 0;

			if (location >= 0) {
				childIndex = location + 1;
			} else {
				childIndex = -location - 1;
			}
			if (childIndex > 0) {
				return children.get(childIndex - 1);

			}
			return null;
		}

		Node getRightChild(K key) {
			int location = Collections.binarySearch(keys, key);
			int childIndex = 0;
			if (location >= 0) {
				childIndex = location + 1;
			} else {
				childIndex = -location - 1;
			}
			if (childIndex < keySize()) {
				return children.get(childIndex + 1);

			}
			return null;
		}
	} // End of class InternalNode

	/**
	 * This class represents a leaf node of the tree. This class is a concrete sub
	 * class of the abstract Node class and provides implementation of the
	 * operations that required for leaf nodes.
	 * 
	 * @author sapan
	 */
	private class LeafNode extends Node {

		// List of values
		List<V> values;

		// Reference to the next leaf node
		LeafNode next;

		// Reference to the previous leaf node
		LeafNode previous;

		/**
		 * Package constructor
		 */
		LeafNode() {
			super();
			values = new ArrayList<V>();
			keys = new ArrayList<K>();
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see BPTree.Node#getFirstLeafKey()
		 */
		K getFirstLeafKey() {
			return keys.get(0);
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see BPTree.Node#isOverflow()
		 */
		boolean isOverflow() {
			if (values.size() > branchingFactor - 1) {
				return true;
			}
			return false;
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see BPTree.Node#insert(Comparable, Object)
		 */
		void insert(K key, V value) {
			int location = Collections.binarySearch(keys, key);
			int index = 0;
			if (location >= 0) {
				index = location + 1;
			} else {
				index = -location - 1;
			}
			if (location >= 0) {
				values.set(index, value);

			} else {
				keys.add(index, key);
				values.add(index, value);

			}

			if (root.isOverflow()) {
				Node sibling = split();
				InternalNode newRoot = new InternalNode();
				newRoot.keys.add(sibling.getFirstLeafKey());
				newRoot.children.add(this);
				newRoot.children.add(sibling);
				root = newRoot;
			}
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see BPTree.Node#split()
		 */
		Node split() {
			LeafNode sibling = new LeafNode();
			int fromIndex = (keySize() + 1) / 2;
			int toIndex = keySize();
			sibling.keys.addAll(keys.subList(fromIndex, toIndex));
			sibling.values.addAll(values.subList(fromIndex, toIndex));

			keys.subList(fromIndex, toIndex).clear();
			values.subList(fromIndex, toIndex).clear();

			sibling.next = next;
			next = sibling;
			return sibling;

		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see BPTree.Node#rangeSearch(Comparable, String)
		 */
		List<V> rangeSearch(K key, String comparator) {
			List<V> result = new LinkedList<V>();
			LeafNode node = this;
			while (node != null) {
				Iterator<K> KIterator = node.keys.iterator();
				Iterator<V> VIterator = node.values.iterator();

				// TODO
				while (KIterator.hasNext()) {
					K keyIterator = KIterator.next();
					V value = VIterator.next();
					int cmp = keyIterator.compareTo(key);

				}
			}
			return result;
		}

		@Override
		V getVal(K key) {
			int index = Collections.binarySearch(keys, key);
			if (index >= 0) {
				return values.get(index);
			}
			return null;
		}

		@Override
		void removeVal(K key) {
			int index = Collections.binarySearch(keys, key);
			if (index >= 0) {
				keys.remove(index);
				values.remove(index);
			}
		}

		@Override
		void merge(BPTree<K, V>.Node sibling) {
			@SuppressWarnings("unchecked")
			LeafNode node = (LeafNode) sibling;
			keys.addAll(node.keys);
			values.addAll(node.values);
			next = node.next;
		}

		@Override
		boolean isUnderflow() {
			if (values.size() < branchingFactor / 2) {
				return true;
			}
			return false;
		}

	} // End of class LeafNode

	/**
	 * Contains a basic test scenario for a BPTree instance. It shows a simple
	 * example of the use of this class and its related types.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// create empty BPTree with branching factor of 3
		BPTree<Double, Double> bpTree = new BPTree<>(3);

		// create a pseudo random number generator
		Random rnd1 = new Random();

		// some value to add to the BPTree
		Double[] dd = { 0.0d, 0.5d, 0.2d, 0.8d, 1.1d, 1.3d, 1.5d };
		Double[] array = { 5d, 1d, 4d, 3d, 6d, 7d, 8d, 2d, 9d };
		List<Double> list = new ArrayList<>();
		for (int i = 0; i < 400; i++) {
			Double j = array[rnd1.nextInt(9)];
			list.add(j);
			bpTree.insert(j, j);
			System.out.println("\n\nTree structure:\n" + bpTree.toString());
		}
		// build an ArrayList of those value and add to BPTree also
		// allows for comparing the contents of the ArrayList
		// against the contents and functionality of the BPTree
		// does not ensure BPTree is implemented correctly
		// just that it functions as a data structure with
		// insert, rangeSearch, and toString() working.
//        List<Double> list = new ArrayList<>();
//        for (int i = 0; i < 400; i++) {
//            Double j = dd[rnd1.nextInt(7)];
//            list.add(j);
//            bpTree.insert(j, j);
//            System.out.println("\n\nTree structure:\n" + bpTree.toString());
//        }
		List<Double> filteredValues = bpTree.rangeSearch(0.2d, ">=");
		System.out.println("Filtered values: " + filteredValues.toString());
	}

} // End of class BPTree
