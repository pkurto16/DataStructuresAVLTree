import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
//

class AVLTest {
	
	final static int stressTestNum = 100006;
	final static int multiplier = 5;
	final static int smallTestNum = 20;
	final static int smallTestDepth = 4;
	
	/*	
	The following tests are individual put cases where a rotation occurs
	and each combination of subtrees possible for that rotation. These are all
	about the root, as that part is what the stress test can't isolate well.
	The null case for add is in the section with other null cases for delete
	for organization sake.
	
	Key for what the rotation method names mean:
	
	First 2-3 Letters: Identify Type of Rotation
	LR = Left Rotation
	RR = Right Rotation
	RLR = Right Left Rotation
	LRR = Left Right Rotation
	
	Second Phrase: Type of subtree orientation
	NoS: No subtrees
	OneLSLTwo: One left subtree on location two (location goes from first added to last added in the NoS case
	for each type of rotation)
	TwoRSLOneLThree: Two right subtrees, one in location one and the other in location three
	TwoLSLOneLTwoOneRSLThree : Two left subtrees at locations one and two, and one right subtree at location 3
	
	etc.
	
	AllS: All nodes have subtrees wherever possible
	
	Additional Notes:
	
	1.	For simplicity sake subtrees all contain one datum as if the tree works properly all subtrees should be 
	balanced and unchanged. I test larger subtree cases to ensure that this is the case (subtrees are always
	balanced), so these tests work off this knowledge, and don't need to test multiple rotations in a row.
	
	2.	Additionally, to get to the start of these tests without triggering previous rotations, the delete method
	is used, so these serve as some alternate tests of the delete method
	
	3. This goes for my delete method names as well, but the reason why I chose to have long method names
	is because I believe it makes it easier to understand exactly what needs to occur, and exactly what the
	tree created visually looks like before the rotation, as the many delete/put methods required seem to jumble into
	a mess that would be hard for a reader to otherwise decipher in a timely fashion (regardless of decomposition
	you still have to follow each step of each put, so decomposition wouldn't really help here).
	*/
	
	@Test
	void LRNoS() {
		AVLTree<Integer> testTree = new AVLTree<Integer>();
		testTree.put(10);
		testTree.put(20);
		testTree.put(30);
		
		assertEquals("20 10 30", testTree.preOrder());
	}
	
	@Test
	void LROneLSLTwo() {
		AVLTree<Integer> testTree = new AVLTree<Integer>();
		testTree.put(10);
		testTree.put(5);
		testTree.put(20);
		testTree.put(15);
		testTree.put(30);
		
		assertEquals("10 5 20 15 30", testTree.preOrder());
		testTree.delete(5);
		assertEquals("20 10 15 30", testTree.preOrder());
	}
	
	@Test
	void LRTwoLSLOneLTwoOneRSLThree() {
		AVLTree<Integer> testTree = new AVLTree<Integer>();
		testTree.put(10);
		testTree.put(5);
		testTree.put(20);
		testTree.put(15);
		testTree.put(30);
		testTree.put(35);
		
		assertEquals("20 10 5 15 30 35", testTree.preOrder());	
	}
	
	@Test
	void LRAllS() {
		AVLTree<Integer> testTree = new AVLTree<Integer>();
		testTree.put(10);
		testTree.put(5);
		testTree.put(20);
		testTree.put(15);
		testTree.put(30);
		testTree.put(25);
		testTree.put(35);
		
		assertEquals("20 10 5 15 30 25 35", testTree.preOrder());	
	}
	
	@Test
	void RRNoS() {
		AVLTree<Integer> testTree = new AVLTree<Integer>();
		testTree.put(30);
		testTree.put(20);
		testTree.put(10);
		
		assertEquals("20 10 30", testTree.preOrder());
	}
	
	@Test
	void RROneRSLTwo() {
		AVLTree<Integer> testTree = new AVLTree<Integer>();
		testTree.put(30);
		testTree.put(35);
		testTree.put(20);
		testTree.put(25);
		testTree.put(10);
		
		assertEquals("30 20 10 25 35", testTree.preOrder());
		testTree.delete(35);
		assertEquals("20 10 30 25", testTree.preOrder());
	}
	
	@Test
	void RRTwoRSLOneLTwoOneRSLThree() {
		AVLTree<Integer> testTree = new AVLTree<Integer>();
		testTree.put(30);
		testTree.put(35);
		testTree.put(20);
		testTree.put(25);
		testTree.put(10);
		testTree.put(15);
		testTree.put(5);
		
		assertEquals("20 10 5 15 30 25 35", testTree.preOrder());
	}
	
	@Test
	void RRAllS() {
		AVLTree<Integer> testTree = new AVLTree<Integer>();
		testTree.put(30);
		testTree.put(35);
		testTree.put(20);
		testTree.put(25);
		testTree.put(10);
		testTree.put(5);
		
		assertEquals("20 10 5 30 25 35", testTree.preOrder());
	}
	
	@Test
	void LRRNoSub() {
		AVLTree<Integer> testTree = new AVLTree<Integer>();
		testTree.put(30);
		testTree.put(10);
		testTree.put(20);
		
		assertEquals("20 10 30", testTree.preOrder());
	}
	
	@Test
	void LRRTwoLSLTwoThreeOneRSLOne() {
		AVLTree<Integer> testTree = new AVLTree<Integer>();
		testTree.put(30);
		testTree.put(35);
		testTree.put(10);
		testTree.put(20);
		testTree.put(5);
		testTree.put(15);
		
		assertEquals("20 10 5 15 30 35", testTree.preOrder());
	}
	
	@Test
	void LRROneLSLTwoTwoRSLOneThree() {
		AVLTree<Integer> testTree = new AVLTree<Integer>();
		testTree.put(30);
		testTree.put(35);
		testTree.put(10);
		testTree.put(20);
		testTree.put(5);
		testTree.put(25);
		
		assertEquals("20 10 5 30 25 35", testTree.preOrder());
	}
	
	@Test
	void RLRNoSub() {
		AVLTree<Integer> testTree = new AVLTree<Integer>();
		testTree.put(10);
		testTree.put(30);
		testTree.put(20);
		
		assertEquals("20 10 30", testTree.preOrder());
	}
	
	@Test
	void RLRTwoLSLOneThreeOneRSLTwo() {
		AVLTree<Integer> testTree = new AVLTree<Integer>();
		testTree.put(10);
		testTree.put(5);
		testTree.put(30);
		testTree.put(20);
		testTree.put(35);
		testTree.put(15);
		
		assertEquals("20 10 5 15 30 35", testTree.preOrder());
	}
	
	@Test
	void RLROneLSLOneTwoRSLTwoThree() {
		AVLTree<Integer> testTree = new AVLTree<Integer>();
		testTree.put(10);
		testTree.put(5);
		testTree.put(30);
		testTree.put(20);
		testTree.put(35);
		testTree.put(25);
		
		assertEquals("20 10 5 30 25 35", testTree.preOrder());
	}
	
	/*
	 * Below are delete cases/edge cases involving leaf deletion and
	 * root and non root deletion. The replacement for this root/non root deletion
	 * comes from the left with balance of -1, right with balance of 1, and left with
	 * balance of 0 where the replacements both have or don't have
	 * a subtree in the same direction of where the replacement came from (relative to the deleted node).
	 * There are also additional null cases below that that I'll explain.
	 * 
	 * Key: 
	 * (ZeroBal,OneBal,NOneBal)=(0,1,-1): this refers to the balance of the node being deleted 
	 * NoS -> No subtree attached to value replacing the deleted node
	 * WithS -> Subtree attached to value replacing the deleted node
	 * Root -> Deletion of the root (if there is no "Root" in the method name,
	 * the deletion is not of the root)
	 */ 
	
	@Test
	void deleteAllLeaves() {
		AVLTree<Integer> testTree = new AVLTree<Integer>();
		testTree.setDebugMode(true);
		testTree.put(20);
		testTree.put(10);
		testTree.put(30);
		testTree.put(5);
		testTree.put(15);
		testTree.put(25);
		testTree.put(35);
		
		assertEquals("20 10 5 15 30 25 35", testTree.preOrder());
		System.out.println(testTree.multiD());
		testTree.delete(5);
		System.out.println(testTree.multiD());
		assertEquals("20 10 15 30 25 35", testTree.preOrder());
		testTree.delete(15);
		System.out.println(testTree.multiD());
		assertEquals("20 10 30 25 35", testTree.preOrder());
		testTree.delete(25);
		System.out.println(testTree.multiD());
		assertEquals("20 10 30 35", testTree.preOrder());
		testTree.delete(35);
		System.out.println(testTree.multiD());
		assertEquals("20 10 30", testTree.preOrder());
		testTree.delete(10);
		System.out.println(testTree.multiD());
		assertEquals("20 30", testTree.preOrder());
		testTree.delete(30);
		System.out.println(testTree.multiD());
		assertEquals("20", testTree.preOrder());
		testTree.delete(20);
		System.out.println(testTree.multiD());
		assertEquals("", testTree.preOrder());
		testTree.setDebugMode(false);
	}
	
	@Test
	void deleteZeroBalNoSRoot() {
		AVLTree<Integer> testTree = new AVLTree<Integer>();
		testTree.put(20);
		testTree.put(10);
		testTree.put(30);
		testTree.put(5);
		testTree.put(15);
		testTree.put(25);
		testTree.put(35);
		
		assertEquals("20 10 5 15 30 25 35", testTree.preOrder());
		testTree.delete(20);
		assertEquals("15 10 5 30 25 35", testTree.preOrder());
	}
	
	@Test
	void deleteOneBalNoSRoot() {
		AVLTree<Integer> testTree = new AVLTree<Integer>();
		testTree.put(20);
		testTree.put(10);
		testTree.put(30);
		testTree.put(25);
		testTree.put(35);
		
		assertEquals("20 10 30 25 35", testTree.preOrder());
		testTree.delete(20);
		assertEquals("25 10 30 35", testTree.preOrder());
	}
	
	@Test
	void deleteNOneBalNoSRoot() {
		AVLTree<Integer> testTree = new AVLTree<Integer>();
		testTree.put(20);
		testTree.put(10);
		testTree.put(30);
		testTree.put(5);
		testTree.put(15);
		
		assertEquals("20 10 5 15 30", testTree.preOrder());
		testTree.delete(20);
		assertEquals("15 10 5 30", testTree.preOrder());
	}
	
	@Test
	void deleteZeroBalWithSRoot() {
		AVLTree<Integer> testTree = new AVLTree<Integer>();
		testTree.put(20);
		testTree.put(10);
		testTree.put(30);
		testTree.put(5);
		testTree.put(25);
		testTree.put(35);
		
		assertEquals("20 10 5 30 25 35", testTree.preOrder());
		testTree.delete(20);
		assertEquals("10 5 30 25 35", testTree.preOrder());
	}
	
	@Test
	void deleteOneBalWithSRoot() {
		AVLTree<Integer> testTree = new AVLTree<Integer>();
		testTree.put(20);
		testTree.put(10);
		testTree.put(30);
		testTree.put(5);
		testTree.put(15);
		testTree.put(25);
		testTree.put(35);
		testTree.put(12);
		testTree.put(27);
		
		assertEquals("20 10 5 15 12 30 25 27 35", testTree.preOrder());
		testTree.delete(20);
		assertEquals("15 10 5 12 30 25 27 35", testTree.preOrder());
	}
	
	@Test
	void deleteNOneBalWithSRoot() {
		AVLTree<Integer> testTree = new AVLTree<Integer>();
		testTree.put(20);
		testTree.put(10);
		testTree.put(30);
		testTree.put(5);
		testTree.put(15);
		testTree.put(25);
		testTree.put(35);
		testTree.put(12);
		
		assertEquals("20 10 5 15 12 30 25 35", testTree.preOrder());
		testTree.delete(20);
		assertEquals("15 10 5 12 30 25 35", testTree.preOrder());
	}
	
	@Test
	void deleteZeroBalNoS() {
		AVLTree<Integer> testTree = new AVLTree<Integer>();
		testTree.put(0);
		testTree.put(20);
		testTree.put(-20);
		testTree.put(10);
		testTree.put(-10);
		testTree.put(30);
		testTree.put(-30);
		testTree.put(5);
		testTree.put(-5);
		testTree.put(15);
		testTree.put(-15);
		testTree.put(25);
		testTree.put(-25);
		testTree.put(35);
		testTree.put(-35);
		
		assertEquals("0 -20 -30 -35 -25 -10 -15 -5 20 10 5 15 30 25 35", testTree.preOrder());
		testTree.delete(20);
		assertEquals("0 -20 -30 -35 -25 -10 -15 -5 15 10 5 30 25 35", testTree.preOrder());
	}
	
	@Test
	void deleteOneBalNoS() {
		AVLTree<Integer> testTree = new AVLTree<Integer>();
		testTree.put(0);
		testTree.put(20);
		testTree.put(-20);
		testTree.put(10);
		testTree.put(-10);
		testTree.put(30);
		testTree.put(-30);
		testTree.put(25);
		testTree.put(-25);
		testTree.put(35);
		testTree.put(-35);
		
		assertEquals("0 -20 -30 -35 -25 -10 20 10 30 25 35", testTree.preOrder());
		testTree.delete(20);
		assertEquals("0 -20 -30 -35 -25 -10 25 10 30 35", testTree.preOrder());
	}
	
	@Test
	void deleteNOneBalNoS() {
		AVLTree<Integer> testTree = new AVLTree<Integer>();
		testTree.put(0);
		testTree.put(20);
		testTree.put(-20);
		testTree.put(10);
		testTree.put(-10);
		testTree.put(30);
		testTree.put(-30);
		testTree.put(5);
		testTree.put(-5);
		testTree.put(15);
		testTree.put(-15);
		
		assertEquals("0 -20 -30 -10 -15 -5 20 10 5 15 30", testTree.preOrder());
		testTree.delete(20);
		assertEquals("0 -20 -30 -10 -15 -5 15 10 5 30", testTree.preOrder());
	}
	
	@Test
	void deleteZeroBalWithS() {
		AVLTree<Integer> testTree = new AVLTree<Integer>();
		testTree.put(0);
		testTree.put(20);
		testTree.put(-20);
		testTree.put(10);
		testTree.put(-10);
		testTree.put(30);
		testTree.put(-30);
		testTree.put(25);
		testTree.put(-25);
		testTree.put(5);
		testTree.put(-5);
		testTree.put(35);
		testTree.put(-35);
		
		assertEquals("0 -20 -30 -35 -25 -10 -5 20 10 5 30 25 35", testTree.preOrder());
		testTree.delete(20);
		assertEquals("0 -20 -30 -35 -25 -10 -5 10 5 30 25 35", testTree.preOrder());
	}
	
	@Test
	void deleteOneBalWithS() {
		AVLTree<Integer> testTree = new AVLTree<Integer>();
		testTree.put(0);
		testTree.put(20);
		testTree.put(-20);
		testTree.put(10);
		testTree.put(-10);
		testTree.put(30);
		testTree.put(-30);
		testTree.put(5);
		testTree.put(-5);
		testTree.put(15);
		testTree.put(-15);
		testTree.put(25);
		testTree.put(-25);
		testTree.put(35);
		testTree.put(-35);
		testTree.put(27);
		testTree.put(-27);
		
		assertEquals("0 -20 -30 -35 -25 -27 -10 -15 -5 20 10 5 15 30 25 27 35", testTree.preOrder());
		testTree.delete(20);
		assertEquals("0 -20 -30 -35 -25 -27 -10 -15 -5 25 10 5 15 30 27 35", testTree.preOrder());
	}
	
	@Test
	void deleteNOneBalWithS() {
		AVLTree<Integer> testTree = new AVLTree<Integer>();
		testTree.put(0);
		testTree.put(20);
		testTree.put(-20);
		testTree.put(10);
		testTree.put(-10);
		testTree.put(30);
		testTree.put(-30);
		testTree.put(5);
		testTree.put(-5);
		testTree.put(15);
		testTree.put(-15);
		testTree.put(25);
		testTree.put(-25);
		testTree.put(35);
		testTree.put(-35);
		testTree.put(12);
		testTree.put(-12);
		
		assertEquals("0 -20 -30 -35 -25 -10 -15 -12 -5 20 10 5 15 12 30 25 35", testTree.preOrder());
		testTree.delete(20);
		assertEquals("0 -20 -30 -35 -25 -10 -15 -12 -5 15 10 5 12 30 25 35", testTree.preOrder());
	}
	
	//these 4 methods check cases where the AVLTree deals with nulls whether it be in the parameter or
	//because a tree starts out null (if no errors are thrown and the preorder doesn't become unordinary
	//these cases are successful)
	@Test
	void deleteRoot() {
		AVLTree<Integer> testTree = new AVLTree<Integer>();
		testTree.put(10);
		testTree.delete(10);
		assertEquals(null, testTree.getData());
	}
	
	@Test
	void deleteFirst() {
		AVLTree<Integer> testTree = new AVLTree<Integer>();
		testTree.delete(1);
		assertEquals("", testTree.preOrder());
	}
	
	@Test
	void deleteNull() {
		AVLTree<Integer> testTree = new AVLTree<Integer>();
		testTree.delete(null);
		assertEquals("", testTree.preOrder());
		
		testTree.put(10);
		testTree.delete(null);
		assertEquals("10", testTree.preOrder());
	}
	
	@Test
	void putNull() {
		AVLTree<Integer> testTree = new AVLTree<Integer>();
		testTree.put(null);
		assertEquals("", testTree.preOrder());
		
		testTree.put(10);
		testTree.put(null);
		assertEquals("10", testTree.preOrder());
	}
	
	//The following are tests of other public methods in the AVLTree class:
	
	//makes a balanced tree, then should return the strings as
		//laid out in the asserts
		@Test
		void stringTest() {
			AVLTree<Integer> testTree = new AVLTree<Integer>();
			testTree.put(20);
			testTree.put(10);
			testTree.put(30);
			testTree.put(5);
			testTree.put(15);
			testTree.put(25);
			testTree.put(35);
			assertEquals("20 10 5 15 30 25 35", testTree.preOrder());
			assertEquals("5 15 10 25 35 30 20", testTree.postOrder());
			assertEquals("5 10 15 20 25 30 35", testTree.inOrder());
			assertEquals("20 10 30 5 15 25 35", testTree.levelOrder());
		}
		
		//tests contains as i+1 shouldn't be in the AVLTree yet/ever in each call, also tests
		//min, max, and depth (height) as these are known for this test
		@Test
		void containsTest() {
			AVLTree<Integer> testTree = new AVLTree<Integer>();
			for(int i = 0; i<smallTestNum; i++) {
				testTree.put(i);
				assertEquals(true,testTree.contains(i));
				assertEquals(false,testTree.contains(i+1));
			}
			assertEquals(0,testTree.min());
			assertEquals(smallTestNum-1,testTree.max());
			assertEquals(smallTestDepth,testTree.getDepth());
		}
		
		//both an empty and nonempty tree are tested for this
		@Test
		void emptyTest() {
			AVLTree<Integer> testTree = new AVLTree<Integer>();
			assertEquals(true,testTree.isEmpty());
			testTree.put(1);
			assertEquals(false,testTree.isEmpty());
		}
		
		//returns correct size (number added is stressTestNum, so size should be stressTestNum)
		@Test
		void sizeTest() {
			AVLTree<Integer> testTree = new AVLTree<Integer>();
			for(int i = 0; i<stressTestNum; i++) {
				testTree.put(i);
			}
			assertEquals(stressTestNum,testTree.size());
	}
	
	//The following tests are stress tests of some kind:
		
	//This test just shows that there are no errors when adding a large amount of items
	//and that the balances and values are correct for an AVL tree
	//this test statistically (with large stressTestNum) covers every case possible that involves filled subtrees without
	//rotating about the root (this is also true with rotations about the root, but not with unfilled subtrees
	//hence the casework)
	
		
	@Test
	void twoDTestInt() {
			AVLTree<Integer> testTree = new AVLTree<Integer>();
			testTree.put(8);
			testTree.put(4);
			testTree.put(12);
			testTree.put(2);
			testTree.put(6);
			testTree.put(10);
			testTree.put(14);
			testTree.put(1);
			testTree.put(3);
			testTree.put(5);
			testTree.put(7);
			testTree.put(9);
			testTree.put(11);
			testTree.put(13);
			testTree.put(15);
			System.out.println(testTree.multiD());
	}
	@Test
	void stressTestInt() {
		AVLTree<Integer> testTree = new AVLTree<Integer>();
		for (int i = 0; i < stressTestNum; i++) {
			for(int j = 0; j<(int)(Math.random()*multiplier); j++){
				testTree.put((int) (Math.random() * stressTestNum*multiplier));
			}
			testTree.delete((int) (Math.random() * stressTestNum*multiplier));
		}
		//System.out.println(testTree.multiD());
		rigorousCheck(testTree.getRoot(), testTree);
	}
	
	//checks more generic scenario other than just Integer to test comparable type
	@Test
	void stressTestLong() {
		AVLTree<Float> testTree = new AVLTree<Float>();
		for (int i = 0; i < stressTestNum; i++) {
			for(int j = 0; j<(int)(Math.random()*multiplier); j++){
				testTree.put((float)Math.random() * smallTestNum*multiplier);
			}
			//since these are floats its likely that only a few deletes will happen here,
			//over the full test, so the Integer stress test is better for stress testing deletes
			testTree.delete((float)Math.random() * smallTestNum*multiplier);
		}
		rigorousCheck(testTree.getRoot(), testTree);
	}
	
	//checks each node on the tree with checkTreePoint using preOrder traversal
	private void rigorousCheck(AVLTree.Node n, AVLTree t) {
		if(n == null) {
			return;
		}
		checkTreePoint(n,t);
		rigorousCheck(t.getLeft(n),t);
		rigorousCheck(t.getRight(n),t);
	}
	
	//checks both the left and right data and the balance to assure that the node being checked is valid
	private void checkTreePoint(AVLTree.Node n, AVLTree t) {
		if (t.getLeft(n) != null) {
			assertEquals(-1,t.getData(t.getLeft(n)).compareTo(t.getData(n)));
		}
		if (t.getRight(n) != null) {
			assertEquals(1,t.getData(t.getRight(n)).compareTo(t.getData(n)));
		}
		assertEquals(true,t.getBalance(n) < 2 && t.getBalance(n) > -2);
	}
	
	//this tests some delete cases as well as showing the adds we did in class
	//(also tests repeat put)
	@Test
	void inClassTest() {
		AVLTree<Integer> testTree = new AVLTree<Integer>();
		testTree.put(0);
		testTree.put(3);
		testTree.put(1);
		testTree.put(16);
		testTree.put(14);
		testTree.put(12);
		testTree.put(13);
		testTree.put(2);
		testTree.put(5);
		testTree.put(9);
		testTree.put(6);
		testTree.put(15);
		testTree.put(8);
		testTree.put(7);
		testTree.put(11);
		testTree.put(4);
		testTree.put(10);
		testTree.put(18);
		testTree.put(17);
		testTree.put(19);
		
		//repeat put test for both boolean and preserving correct tree
		assertEquals("12 6 3 1 0 2 5 4 8 7 10 9 11 16 14 13 15 18 17 19", testTree.preOrder());
		assertEquals(false, testTree.put(19));
		testTree.put(19);
		assertEquals("12 6 3 1 0 2 5 4 8 7 10 9 11 16 14 13 15 18 17 19", testTree.preOrder());
		//delete case not in tree
		testTree.delete(30);
		assertEquals("12 6 3 1 0 2 5 4 8 7 10 9 11 16 14 13 15 18 17 19", testTree.preOrder());
		//delete root case
		testTree.delete(12);
		assertEquals("11 6 3 1 0 2 5 4 8 7 10 9 16 14 13 15 18 17 19", testTree.preOrder());
		
		//mini stress test delete the rest randomly until all elements are gone
		while(testTree.size()>1) {
			testTree.delete((int)(Math.random()*20-1));
			System.out.println(testTree.multiD());
			rigorousCheck(testTree.getRoot(), testTree);
		}
	}
}