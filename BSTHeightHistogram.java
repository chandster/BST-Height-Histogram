package task1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BSTHeightHistogram {
    /*
    Accept input integer n from user
    For each permutation of {1,2,...,n}, construct BST and get height
    Count frequencies of different heights that occur in an array of size n
    Print histogram with frequencies of different heights
    Compute and print the average height of BSTs with n nodes
    */
    public Map<Integer,Integer> histogram;

    public BSTHeightHistogram(int size){
        createHistogram(size);
    }

    public static String intArrayToString(int[] nums){
        String outputString = "";
        for (int i = 0; i < nums.length; i++) {
            if (i==nums.length-1) {
                outputString += String.valueOf(nums[i]);
            } else {
                outputString += String.valueOf(nums[i]) + " ";
            }
        }
        return outputString;
    }

    public double getAverageHeight(Map<Integer,Integer> histogram){
        double total = 0;
        int sum = 0;

        for (int height : histogram.keySet()) {
            int frequency = histogram.get(height);
            total += height * frequency;
            sum += frequency;
        }

        return total / sum;
    }

    public void createHistogram(int size){
        int[] values = new int[size];

        for (int i = 0; i < values.length; i++) {
            values[i] = i+1;
        }

        BinarySearchTree tree = new BinarySearchTree(values);
        histogram = new HashMap<>(values.length);

        //All heights have initial frequency 0
        for (int i = 0; i < values.length; i++) {
            histogram.put(i, 0);
        }

        int[] nums = tree.values;
        ArrayList<int[]> permutations = tree.permute(nums);

        //Adding frequencies to the histogram HashMap
        BinarySearchTree treePermutation;
        for (int[] ordering : permutations) {
            treePermutation = new BinarySearchTree(ordering); //Create a binary tree for every permutation of the sequence {1,2,..n}
            int height = treePermutation.height();
            histogram.put(height, histogram.get(height)+1);
        }
    }

    public void outputHistogram(){
        System.out.println("Height\tFrequency");
        System.out.println("-".repeat(16));
        for (int treeHeight : histogram.keySet()) {
            System.out.println(String.valueOf(treeHeight)+"\t"+histogram.get(treeHeight));
        }
        double avg = getAverageHeight(histogram);
        System.out.println("\nAverage height of BSTs:\n" + String.valueOf(avg));
    }

    /*
     * Get all permutations of {1,2,...,n}
     * Construct a tree for each permutation
     * Work out the tree that is formed
     * Can call inOrder() to see traversal
     * Print the height of the tree formed using height()
     */
        
     public static void main(String[] args) {
        System.out.println("Enter a positive integer n.");
        Scanner scannerIn = new Scanner(System.in);
        int size = scannerIn.nextInt();
        scannerIn.close();
        BSTHeightHistogram histogram = new BSTHeightHistogram(size);
        histogram.outputHistogram();
    }
}    

class BinarySearchTree {
    public Node[] tree;
    public Node root;
    public int[] values;
    public int next;
    public ArrayList<int[]> permutations;

    BinarySearchTree(int rootValue, int numNodes) {
        root = new Node(rootValue);
        values = new int[numNodes];
        values[0] = rootValue;
        next = 1;
    }

    BinarySearchTree(int[] numbers){
        values = new int[numbers.length];
        next = 0;
        for (int num : numbers) {
            add(num);
        }
    }

    public void add(int item) {
        root = add(item, root);
        values[next] = item;
        next++;
    }

    public Node add(int item, Node root) {
        if (root == null) {
            root = new Node(item);
            return root;
        }

        if (item < root.key) {
            root.left = add(item, root.left);
        } else if (item > root.key) {
            root.right = add(item, root.right);
        }
        return root;
    }

    public ArrayList<Integer> inOrder() {
        ArrayList<Integer> list = new ArrayList<Integer>();
        return inOrder(root, list);
    }

    public ArrayList<Integer> inOrder(Node root, ArrayList<Integer> treeAsList) {
        if (root == null) {
            return null;
        }
        inOrder(root.left, treeAsList);
        treeAsList.add(root.key);
        inOrder(root.right, treeAsList);
        
        return treeAsList;
    }

    //get the height from the root
    public int height(){
        return height(root);
    }
    
    //get the height from a particular node
    public int height(Node node) {
        if (node == null) {
            return -1;
        } else {
            int lHeight = height(node.left);
            int rHeight = height(node.right);

            if (lHeight > rHeight) {
                return lHeight + 1;
            } else {
                return rHeight + 1;
            }
        }
    }

    //swap the indices of two integers in array
    static void swap(int nums[], int i, int j) { 
        int temp = nums[i]; 
        nums[i] = nums[j]; 
        nums[j] = temp; 
    } 
  
    //get all the possible permutations of an int[] array
    public void permutations(ArrayList<int[]> permutations, int[] nums, int low, int high) { 
        if (permutations==null) {
            permutations = new ArrayList<int[]>();
        }
        // Base case
        if (low == high) { 
            permutations.add(Arrays.copyOf(nums, nums.length));
            return; 
        } 
  
        // Permutations made here
        for (int i = low; i <= high; i++) { 
            // Swapping
            swap(nums, low, i); 
            // Calling permutations for next low value
            permutations(permutations, nums, low + 1, high); 
            // Backtracking
            swap(nums, low, i); 
        } 
    }

    // Function to get the permutations 
    public ArrayList<int[]> permute(int[] nums) {
        ArrayList<int[]> permutations = new ArrayList<int[]>(); 
        int max = nums.length - 1; 
  
        // Calling permutations for the first time, passing 0 as low and max (last index) as high
        permutations(permutations, nums, 0, max); 
        return permutations; 
    }

    public class Node {
        int key;
        Node left, right;

        Node(int item) {
            key = item;
            left = right = null;
        }
    }

}