import java.util.*;

import javax.swing.tree.TreeNode;

class BinaryTrees {

    // First we will create a Node class which will represt a single node of a tree
    static class Node {
        int data;
        Node left;
        Node right;

        Node(int data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    // This class will be used to create a binary tree
    static class BinaryTree {
        // This function will be used to create a Tree and will return a Root node
        // So, for inserting a Node we need to travel at every index so will be mainting
        // a varible
        static int idx = -1;

        public static Node buildTree(int[] nodes) {
            idx++; // At first pass this will be 0 means our Root Node is created
            if (nodes[idx] == -1) {
                // This represnt the last node at the data is NULL
                return null;
            }

            // For rest other data we will create a new node
            // Creating a parent node
            // Recursively
            // Then will go for left Node
            // Then for right Node
            Node newNode = new Node(nodes[idx]);
            newNode.left = buildTree(nodes);
            newNode.right = buildTree(nodes);

            // Returning the Node
            return newNode;
        }
    }

    public static void preorder(Node root) {
        if (root == null) {
            return;
        }

        System.out.print(root.data + " ");
        preorder(root.left);
        preorder(root.right);
    }

    public static void inorder(Node root) {
        if (root == null) {
            return;
        }
        inorder(root.left);
        System.out.print(root.data + " ");
        inorder(root.right);
    }

    public static void postorder(Node root) {
        if (root == null) {
            return;
        }
        postorder(root.left);
        postorder(root.right);
        System.out.print(root.data + " ");
    }

    public static void leveOrder(Node root) {
        if (root == null) {
            // The Tree is Empty
            return;
        }
        Queue<Node> q = new LinkedList<>();
        q.add(root);
        q.add(null);

        while (!q.isEmpty()) {
            // fetching the single node
            Node currNode = q.remove(); // Remove will give us First ele in queue
            if (currNode == null) {
                // If the current node is null print \n
                System.out.println();
                // check if it is the last ele in queue
                if (q.isEmpty()) {
                    break;
                } else {
                    // If q is not empty add null for the next level
                    q.add(null);
                }
            } else {
                // Print the data
                System.out.print(currNode.data + " ");
                if (currNode.left != null) {
                    q.add(currNode.left);
                }
                if (currNode.right != null) {
                    q.add(currNode.right);
                }
            }
        }
    }

    public static int countNodes(Node root) {

        if (root == null) {
            return 0;
        }

        int leftNodes = countNodes(root.left); // Getting for the left node
        int rightNodes = countNodes(root.right); // Count fot the right node

        return leftNodes + rightNodes + 1;
    }

    public static int sumNodes(Node root) {

        if (root == null) {
            return 0;
        }

        int leftSum = sumNodes(root.left); // Getting for the left node
        int rightSum = sumNodes(root.right); // Count fot the right node

        return leftSum + rightSum + root.data;
    }

    public static int heightTree(Node root) {
        if (root == null) {
            return 0;
        }

        int leftHeight = heightTree(root.left);
        int rightHeight = heightTree(root.right);

        // Max height between the to childs
        // +1 for the root node
        int maxheigth = Math.max(leftHeight, rightHeight) + 1;

        return maxheigth;
    }

    static public int diameter(Node root) {
        if (root == null) {
            return 0;
        }

        int leftDimm = diameter(root.left);
        int rigthDimm = diameter(root.right);
        int rootDimm = heightTree(root.left) + heightTree(root.right) + 1;

        return Math.max(rootDimm, Math.max(leftDimm, rigthDimm));
    }

    static class TreeInfo {
        // This is used to return both heigth and diameter of a tree as we cannot return
        // 2 things at the same time. This is used for recursion.
        //
        int ht;
        int diam;

        TreeInfo(int height, int diameter) {
            this.ht = height;
            this.diam = diameter;
        }
    }

    static public TreeInfo diameter2(Node root) {
        if (root == null) {
            return new TreeInfo(0, 0);
        }

        // This will return TreeInfo as every node has to return its height nd diameter
        // at the same time.
        // 1st fetch height and diamter of left subtree THEN for he right subtree

        TreeInfo left = diameter2(root.left);
        TreeInfo right = diameter2(root.right);

        // Height of the Tree
        // Height will be left + right ka heigth + 1
        // +1 is for root node as explained above
        int myHeight = Math.max(left.ht, right.ht) + 1;

        // Diameter of the subtree
        int diam1 = left.diam;
        int diam2 = right.diam;
        int diam3 = left.ht + right.ht + 1;

        int maxDiam = Math.max(diam3, Math.max(diam2, diam1));

        // For the current node we will create a TreeInfo
        TreeInfo myInfo = new TreeInfo(myHeight, maxDiam);

        return myInfo;
    }

    static class Pairwbt {
        TreeNode node;
        int index;

        Pairwbt(TreeNode node, int index) {
            this.node = node;
            this.index = index;
        }
    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public int widthOfBinaryTree(TreeNode root) {
		if(root == null){
			return 0;
		}   
		int ans = 0;
		Queue<Pairwbt> q = new LinkedList<>();
		q.offer(new Pairwbt(root,0));

		while(!q.isEmpty()){
			int size = q.size();
			int mmin = q.peek().index;// here min index will be the 1st one in that level
			int first = 0;
			int last = 0;

			for(int i = 0; i< size; i++){
				int curr_index = q.peek().index - mmin;
				TreeNode node = q.peek().node;
				q.poll();
				if(i==0){
					// This means that the current node is the 1st node in the q of that level so this will be the first pointer
					first = curr_index;	
				}
				if(i == size -1){
					// this will be the last one
					last = curr_index;
				}
				if(node.left != null){
					q.offer(new Pairwbt(node.left, curr_index * 2 +1));
				}
				if(node.right != null){
					q.offer(new Pairwbt(node.right, curr_index*2+2));
				}
				ans = Math.max(ans,last - first+1);
			}
		}
		return ans;
    }

    // 124. Binary Tree Maximum Path Sum
    public int maxPathSum(TreeNode root) {
        int maxVal[] = new int[1];
        maxVal[0] = Integer.MIN_VALUE;
        moveDown(root,maxVal);
        return maxVal[0];
    }
    private int moveDown(TreeNode root, int[] maxVal){
        if(root == null){
            return 0;
        }
        // here 0 is used to comapre if we got some negative path 
        int left = Math.max(0,moveDown(root.left,maxVal));
        int right = Math.max(0,moveDown(root.right,maxVal));

        maxVal[0] = Math.max(maxVal[0], left + right + root.val);
        return Math.max(left,right) + root.val;
    }

    public static void main(String[] args) {
        // System.out.println("Hello Node");
        int[] nodes = { 1, 2, 4, -1, -1, 5, -1, -1, 3, -1, 6, -1, -1 };
        BinaryTree tree = new BinaryTree();
        Node root = tree.buildTree(nodes); // As the function will return a ROOT Node
        // System.out.println(root.data);
        System.out.println();
        preorder(root);
        System.out.println();
        inorder(root);
        System.out.println();
        postorder(root);
        System.out.println();
        leveOrder(root);
        System.out.println("Count of Nodes: " + countNodes(root));
        System.out.println("Sum of data of Nodes: " + sumNodes(root));
        System.out.println("Height of Tree: " + heightTree(root));
        System.out.println("Diameter of Tree: " + diameter(root));
        System.out.println("Diameter2 of Tree: " + diameter2(root).diam);
    }
}
