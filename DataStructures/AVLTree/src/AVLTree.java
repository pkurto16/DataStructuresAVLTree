import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class AVLTree<E extends Comparable<E>> {
	protected class Node {
		private int depth;
		private Node left;
		private Node right;
		private Node parent;
		private E data;

		private Node() {
		}

		private Node(Node left, Node right, Node parent, E data) {
			this.left = left;
			this.right = right;
			this.parent = parent;
			this.data = data;
		}
	}

	private Node root;
	private int size;
	private int balanceThreshold;
	private boolean debugMode = false;

	public AVLTree() {
		balanceThreshold = 2;
		root = null;
		size = 0;
	}

	public AVLTree(int threshold) {
		balanceThreshold = threshold;
		root = null;
		size = 0;
	}

	public boolean isEmpty() {
		return root == null;
	}

	protected Node getRoot() {
		return root;
	}

	protected Node getLeft() {
		if (root == null) {
			throw new IllegalArgumentException();
		}
		return root.left;
	}

	public E min() {
		if (root == null) {
			return null;
		}
		return minHelper(root);
	}

	private E minHelper(Node node) {
		if (node.left != null) {
			return minHelper(node.left);
		}
		return node.data;
	}

	public E max() {
		if (root == null) {
			return null;
		}
		return maxHelper(root);
	}

	private E maxHelper(Node node) {
		if (node.right != null) {
			return maxHelper(node.right);
		}
		return node.data;
	}

	protected Node getRight() {
		if (root == null) {
			throw new IllegalArgumentException();
		}
		return root.right;
	}

	public int size() {
		return size;
	}

	protected Node getLeft(Node node) {
		if (node == null) {
			throw new IllegalArgumentException();
		}
		return node.left;
	}

	protected Node getRight(Node node) {
		if (node == null) {
			throw new IllegalArgumentException();
		}
		return node.right;
	}

	public E getData() {
		if (root == null) {
			return null;
		}
		return root.data;
	}

	protected E getData(Node node) {
		if (node == null) {
			throw new IllegalArgumentException();
		}
		return node.data;
	}

	public int getDepth() {
		if (root == null) {
			return -1;
		}
		return root.depth;
	}

	protected Node getParent(Node node) {
		if (node == null) {
			throw new IllegalArgumentException();
		}
		return node.parent;
	}

	protected int getDepth(Node node) {
		return node.depth;
	}

	public void setDebugMode(boolean b) {
		debugMode = b;
	}

	public boolean getDebugMode() {
		return debugMode;
	}

	public boolean contains(E data) {
		return searchFor(data, root) != null;
	}

	public boolean delete(E data) {
		Node deletion = searchFor(data, root);
		if (deletion != null) {
			deletion.data = findReplacement(deletion);
			size--;
			if (deletion.data == null) {
				deletion = null;
			}
			return true;
		}
		return false;
	}

	private Node searchFor(E data, Node node) {
		if (node == null || node.data == null || data == null) {
			return null;
		}
		if (data.compareTo(node.data) == 0) {
			return node;
		}
		if (data.compareTo(node.data) == 1) {
			return searchFor(data, node.right);
		}
		return searchFor(data, node.left);
	}

	private E findReplacement(Node node) {
		if (debugMode) System.out.println("Replacing: " + node.data);
		if (getBalance(node) == 1) {
			return replaceWithRight(node.right);
		}
		// if at a leaf, this will be true
		if (node.left == null) {
			if (node.parent == null) {
				return null;
			}
			return deleteLeaf(node);
		}
		// not a leaf or right replacement side, so must
		return replaceWithLeft(node.left);
	}

	private E deleteLeaf(Node node) {
		if (node.parent.left == node) {
			node.parent.depth = chooseDepth(0, correctDepth(node.parent.right) + 1);
			if (debugMode) System.out.println("With: null" + " depth:" + (int) (correctDepth(node.parent.right) + 1));
			node.parent.left = null;
		} else {
			node.parent.depth = chooseDepth(0, correctDepth(node.parent.left) + 1);
			if (debugMode) System.out.println("With: null" + " depth:" + (int) (correctDepth(node.parent.left) + 1));
			node.parent.right = null;
		}

		surveyParentPath(node.parent);
		return null;
	}

	private E replaceWithLeft(Node node) {
		if (node.right == null) {
			E replaceData = node.data;
			replaceOnLeft(node);
			surveyParentPath(node.parent);
			return replaceData;
		}
		return replaceWithLeft(node.right);
	}

	private void replaceOnLeft(Node node) {
		if (node.parent.left == node) {
			node.parent.left = node.left;
		} else {
			node.parent.right = node.left;
		}
		if (node.left != null) {
			node.left.parent = node.parent;
		}
	}

	private E replaceWithRight(Node node) {
		if (node.left == null) {
			if (debugMode) System.out.println("With: " + node.data);
			E replaceData = node.data;
			replaceOnRight(node);

			surveyParentPath(node.parent);
			return replaceData;
		}
		return replaceWithRight(node.left);
	}

	private void replaceOnRight(Node node) {
		if (node.parent.right == node) {
			node.parent.right = node.left;
		} else {
			node.parent.left = node.right;
		}
		if (node.right != null) {
			node.right.parent = node.parent;
		}
	}

	/*
	 * cases: 0: this.data==null 1: right != null && data>this.data 2: left != null
	 * && data<this.data 3: (1||2) != true && data>this.data 4: (1||2) != true &&
	 * data<this.data default: data == this.data so just return
	 */
	public boolean put(E data) {
		if (data == null) {
			return false;
		}
		if (put(data, root)) {
			size++;
			return true;
		}
		return false;
	}

	private boolean put(E data, Node node) {

		if (node == null)
			return startBlankTree(data);
		if (checkRightPut(data, node))
			return true;
		if (checkLeftPut(data, node))
			return true;

		return false;
	}

	private boolean checkRightPut(E data, Node node) {
		if (data.compareTo(node.data) == 1) {
			if (node.right != null) {
				return putRightSide(data, node);
			}
			return instantialSetRight(data, node);
		}
		return false;
	}

	private boolean checkLeftPut(E data, Node node) {
		if (data.compareTo(node.data) == -1) {
			if (node.left != null) {
				return putLeftSide(data, node);
			}
			return instantialSetLeft(data, node);
		}
		return false;
	}

	private boolean startBlankTree(E data) {
		if (debugMode) System.out.println("Insert Data to Instantialized Tree:[New Data:" + data.toString() + "]");
		root = new Node();
		root.data = data;
		surveyParentPath(root);
		return true;
	}

	private boolean putLeftSide(E data, Node node) {
		if (debugMode) System.out.println("Left Put: [Current = " + node.data.toString() + ", New = " + data.toString() + "]");

		return put(data, node.left);
	}

	private boolean putRightSide(E data, Node node) {
		if (debugMode) System.out.println("Right Put: [Current = " + node.data.toString() + ", New = " + data.toString() + "]");

		return put(data, node.right);
	}

	private boolean instantialSetLeft(E data, Node node) {
		if (debugMode) System.out.println("Left Instantial Set: [Current = " + node.data.toString() + ", New = " + data.toString() + "]");
		node.left = new Node(null, null, node, data);
		surveyParentPath(node.left);
		return true;
	}

	private boolean instantialSetRight(E data, Node node) {
		if (debugMode) System.out.println("Right Instantial Set: [Current = " + node.data.toString() + ", New = " + data.toString() + "]");
		node.right = new Node(null, null, node, data);
		surveyParentPath(node.right);
		return true;
	}

	public String toString() {
		return levelOrder();
	}

	public String preOrder() {
		String returned = "";
		if (root != null && root.data != null) {
			returned += preOrderHelper(root);
			return returned.substring(0, returned.length() - 1);
		}
		return returned;

	}

	private String preOrderHelper(Node node) {
		String returned = "";
		if (node == null) {
			return returned;
		}
		returned += node.data.toString() + " ";
		returned += preOrderHelper(node.left);
		returned += preOrderHelper(node.right);
		return returned;
	}

	public String inOrder() {
		String returned = "";
		if (root != null && root.data != null) {
			returned += inOrderHelper(root);
			return returned.substring(0, returned.length() - 1);
		}
		return returned;
	}

	private String inOrderHelper(Node node) {
		String returned = "";
		if (node == null) {
			return returned;
		}
		returned += inOrderHelper(node.left);
		returned += node.data.toString() + " ";
		returned += inOrderHelper(node.right);
		return returned;
	}

	public String postOrder() {
		String returned = "";
		if (root != null && root.data != null) {
			returned += postOrderHelper(root);
			return returned.substring(0, returned.length() - 1);
		}
		return returned;
	}

	private String postOrderHelper(Node node) {
		String returned = "";
		if (node == null) {
			return returned;
		}
		returned += postOrderHelper(node.left);
		returned += postOrderHelper(node.right);
		returned += node.data.toString() + " ";
		return returned;
	}

	public String levelOrder() {
		String returned = "";
		if (root != null && root.data != null) {
			Queue<Node> q = new LinkedList<Node>();
			returned += levelOrderHelper(q,returned,root);
			return returned.substring(0, returned.length() - 1);
		}
		return returned;

	}

	private String levelOrderHelper(Queue<Node> q, String s ,Node node) {
		if (node.left != null) {
			q.add(node.left);
		}
		if (node.right != null) {
			q.add(node.right);
		}
		s += node.data.toString() + " ";
		if(q.peek() != null) {
			s = levelOrderHelper(q,s,q.poll());
		}
		return s;
	}

	public String multiD() {
		if (root != null && root.data != null) {
			return multiDHelper(root);
		}
		return "";
	}

	private String multiDHelper(Node node) {
		String returned = "";
		if (node == null) {
			return returned;
		}
		returned += multiDHelper(node.right);
		returned += multDData(node);
		returned += multiDHelper(node.left);
		return returned;
	}

	private String multDData(Node node) {
		String returned = "";
		if (node.parent != null) {
			returned += parentPathSpacesString(node);
			if (node.parent.data.compareTo(node.data) == 1) {
				returned += "╚═══════════";
			} else {
				returned += "╔═══════════";
			}
			for (int i = 0; i < node.parent.depth - node.depth - 2; i++) {
				returned += "════════════";
			}
		}
		return returned + node.data.toString() + "\n";
	}

	private String parentPathSpacesString(Node node) {
		ArrayList<String> parentPathSpaces = parentPathSpaces(node.parent, node.parent.data.compareTo(node.data) == 1,
				new ArrayList<String>());
		String returned = "";
		for (int i = parentPathSpaces.size() - 1; i >= 0; i--) {
			returned += parentPathSpaces.get(i);
		}
		return returned;
	}

	// true is right, false is left
	private ArrayList<String> parentPathSpaces(Node node, boolean direction, ArrayList<String> stringArray) {
		if (node.parent == null) {
			return stringArray;
		}
		if (node.parent.data.compareTo(node.data) == -1 && direction
				|| node.parent.data.compareTo(node.data) == 1 && !direction) {
			stringArray.add("║           ");
			stringArray = parentPathSpaces(node.parent, !direction, stringArray);
		} else {
			stringArray.add("            ");
			stringArray = parentPathSpaces(node.parent, direction, stringArray);
		}
		return stringArray;
	}

	private void rotateLeft(Node node) {
		Node rightLeft = node.right.left;
		node.right.left = node;
		aboveLeftRotate(node);
		setNewRightPointers(node, rightLeft);
		fixDepthsLR(node);
		if (debugMode) System.out.print(" (Rotated Left)");
	}

	private void aboveLeftRotate(Node node) {
		if (node.parent == null) {
			root = node.right;
		} else {
			if (node.parent.right == node) {
				node.parent.right = node.right;
			} else {
				node.parent.left = node.right;
			}
		}
	}

	private void setNewRightPointers(Node node, Node rightLeft) {
		node.right.parent = node.parent;
		node.parent = node.right;
		node.right = rightLeft;

		if (node.right != null) {
			node.right.parent = node;
		}
	}

	private void fixDepthsLR(Node node) {
		node.depth = chooseDepth(correctDepth(node.left) + 1, correctDepth(node.right) + 1);
		node.parent.depth = chooseDepth(node.depth + 1, correctDepth(node.parent.right) + 1);
	}

	private void rotateRight(Node node) {
		Node leftRight = node.left.right;
		node.left.right = node;
		aboveRightRotate(node);
		setNewLeftPointers(node, leftRight);
		fixDepthsRR(node);

		if (debugMode) System.out.print(" (Rotated Right)");
	}

	private void aboveRightRotate(Node node) {
		if (node.parent == null) {
			root = node.left;
		} else {
			if (node.parent.right == node) {
				node.parent.right = node.left;
			} else {
				node.parent.left = node.left;
			}
		}
	}

	private void setNewLeftPointers(Node node, Node leftRight) {
		node.left.parent = node.parent;
		node.parent = node.left;
		node.left = leftRight;
		if (node.left != null) {
			node.left.parent = node;
		}
	}

	private void fixDepthsRR(Node node) {
		node.depth = chooseDepth(correctDepth(node.left) + 1, correctDepth(node.right) + 1);
		node.parent.depth = chooseDepth(node.depth + 1, correctDepth(node.parent.left) + 1);
	}

	private void checkBalance(Node node) {
		int rightDepth = correctDepth(node.right);
		int leftDepth = correctDepth(node.left);

		if (rightDepth - leftDepth == balanceThreshold || rightDepth - leftDepth == -balanceThreshold) {
			balance(rightDepth - leftDepth, node);
		}

	}

	public int getBalance() {
		return correctDepth(root.right) - correctDepth(root.left);
	}

	protected int getBalance(Node node) {
		return correctDepth(node.right) - correctDepth(node.left);
	}

	private int correctDepth(Node node) {
		if (node != null && node.data != null) {
			return node.depth;
		}
		return -1;
	}

	private void surveyParentPath(Node node) {
		if (debugMode) System.out.print("│\n└─>Parent Path:[");
		parentPathHelper(node);
		if (debugMode) System.out.print("]\n\n");
	}

	private void parentPathHelper(Node node) {
		node.depth = chooseDepth(correctDepth(node.left)+1, correctDepth(node.right)+1);
		if (debugMode) {
			System.out.print("(b = " + getBalance(node) + ", d = " + node.depth + ", v = "
					+ node.data.toString() + ")");
		}
		checkBalance(node);
		if (node.parent != null) {
			if (debugMode) System.out.print(" -> ");
			parentPathHelper(node.parent);
		}
	}
	//picks the deepest
	private int chooseDepth(int depth1, int depth2) {
		if (depth1 > depth2) {
			return depth1;
		}
		return depth2;
	}
	private void balance(int balVal, Node node) {
		if (balVal == balanceThreshold) {
			if (getBalance(node.right) == -balanceThreshold + 1) {
				rotateRight(node.right);
			}
			rotateLeft(node);
		} else {
			if (getBalance(node.left) == balanceThreshold - 1) {
				rotateLeft(node.left);
			}
			rotateRight(node);
		}
	}
}