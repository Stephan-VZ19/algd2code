import ch.fhnw.algd2.treeeditor.base.Tree;

/**
 * Implements an unbalanced binary search tree. Note that all "matching" is
 * based on the compareTo method.
 * 
 * @author Dominik Gruntz
 */
class BinarySearchTree<K extends Comparable<? super K>, E> implements
		Tree<K, E> {
	private Node<K, E> root = null;
	private int nodeCount = 0;

	/*
	 * (non-Javadoc)
	 *
	 * @see Tree#getRoot()
	 */
	@Override
	public Node<K, E> getRoot() {
		return root;
	}

	/**
	 * number of nodes in the tree
	 * 
	 * @return size of the tree.
	 */
	@Override
	public int size() {
		return nodeCount;
	}

	/**
	 * Test if the tree is logically empty.
	 * 
	 * @return true if empty, false otherwise.
	 */
	@Override
	public boolean isEmpty() {
		return root == null;
	}

	/**
	 * Insert a value into the tree; if an element is already stored under the
	 * given key the element is replaced by the new one.
	 * 
	 * @param key
	 *          key with which the specified element is to be associated.
	 * @param element
	 *          element to be inserted into the tree.
	 */
	@Override
	public void insert(K key, E element) {
		root = insert(root, key, element);
	}

	/**
	 * Internal method to insert into a subtree.
	 * 
	 * @param p
	 *          the node that roots the tree.
	 * @param key
	 *          the key of the element to insert.
	 * @param element
	 *          the element to insert.
	 * @return the new root.
	 */
	private Node<K, E> insert(Node<K, E> p, K key, E element) {
		if (p == null) {
			nodeCount++;
			return new Node<K, E>(key, element);
		} else {
			int c = key.compareTo(p.key);
			if (c < 0) p.left = insert(p.left, key, element);
			//else if (c > 0) p.right = insert(p.right, key, element);
			//else p.element = element;
			else p.right = insert(p.right, key, element);
			return p;
		}
	}

	/**
	 * Searches an item in the tree.
	 *
	 * @param key
	 *          the key to search for.
	 * @return the matching item or null if not found.
	 */
	@Override
	public E search(K key) {
		return search(root, key);
	}

	private E search(Node<K, E> p, K key) {
		if (p == null) return null;
		else {
			int c = key.compareTo(p.key);
			if (c == 0) return p.element;
			else if (c < 0) return search(p.left, key);
			else return search(p.right, key);
		}
	}

	/**
	 * Remove Node with key <code>key</code> from the tree. Nothing is done if x
	 * is not found.
	 * 
	 * @param key
	 *          the key of the item to remove.
	 */
	@Override
	public void remove(K key) {
		root = remove(root, key);
	}

	private Node<K, E> remove(Node<K,E> node, K key){
		if (node == null) {
			return null;
		}
		else {
			int c = key.compareTo(node.key);
			if (c < 0) {
				node.left = remove(node.left, key);
			}
			else if (c > 0) {
				node.right = remove(node.right, key);
			}
			else {
				if (node.left == null) {
					node = node.right;
					nodeCount--;
				}
				else if (node.right == null) {
					node = node.left;
					nodeCount--;
				}
				else {
					Node<K, E> succ = symSucc(node.right);
					succ.right = remove(node.right, succ.key);
					succ.left = node.left;
					node = succ;
				}
			}
			return node;
		}
	}

	private Node<K, E> symSucc(Node<K,E> node){
		Node<K, E> succ = node;
		while (succ.left != null) {
			succ = succ.left;
		}
		return succ;
	}

	@Override
	public String toString() {
		// inorder traverse
		StringBuilder sb = new StringBuilder();
		createExpression(root, sb);
		return sb.toString();
	}

	private void createExpression(Node <K, E> node, StringBuilder sb) {
		if (node != null) {
			sb.append("[");
			createExpression(node.left, sb);
			sb.append(node.key);
			createExpression(node.right, sb);
			sb.append("]");
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see Tree#height()
	 */
	@Override
	public int height() {
		return height(root);
	}

	/**
	 * Return the height of node t, or 0, if null.
	 */
	private int height(Node<K, E> t) {
		if (t != null) {
			int hl = height(t.left), hr = height(t.right);
			return hl >= hr ? hl + 1 : hr + 1;
		} else return 0;
	}

	private static class Node<K extends Comparable<? super K>, E> implements
			Tree.Node<K, E> {
		final K key;
		E element;
		Node<K, E> left, right;

		@SuppressWarnings("unused")
		Node(K key) {
			this(key, null);
		}

		Node(K key, E element) {
			this.key = key;
			this.element = element;
		}

		@SuppressWarnings("unused")
		Node(K key, E element, Node<K, E> left, Node<K, E> right) {
			this(key, element);
			this.left = left;
			this.right = right;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see Tree.Node#getKey()
		 */
		@Override
		public K getKey() {
			return key;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see Tree.Node#getLeft()
		 */
		@Override
		public Node<K, E> getLeft() {
			return left;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see Tree.Node#getRight()
		 */
		@Override
		public Node<K, E> getRight() {
			return right;
		}
	}
}

