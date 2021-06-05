import java.util.ArrayList;

class Node {
    int val;
    Node left, right;
    
    public Node() {
        this(0);
    }
    
    public Node(int val) {
        this.val = val;
        this.left = null;
        this.right = null;
    }
}


class BST {
    Node root;

    BST() {
        root = null;
    }
    
    public Node createNode(int val) {
        return new Node(val);
    }
    
    public Node insertNode(Node node, int val) {        
        //if first node
        if(node == null)
        {
            Node temp = createNode(val);
            if(this.root == null)
                this.root = temp;
            return temp;
        }
        //otherwise add new node to its correct position
        if(node.val > val)
            node.left = insertNode(node.left, val);
        else if(node.val < val)
            node.right = insertNode(node.right, val);
        //node has same value which needs to be inserted
        return node;
    }
    
    public Node deleteNode(Node node, int val) {
        if(node == null)
            return node;
        if(node.val > val)
            node.left = deleteNode(node.left, val);
        else if(node.val < val)
            node.right = deleteNode(node.right, val);
        //current node needs to be deleted
        else {
            //either no or one child cases
            if(node.left == null)
                return node.right;
            if(node.right == null)
                return node.left;
            //both child are there
            node.val = getMinVal(node.right);
            node.right = deleteNode(node.right, node.val);
        }
        return node;
    }
    
    public int getMinVal(Node node) {
        if(node == null)
            return -1;
        while(node.left != null)
            node = node.left;
        return node.val;
    }
    
    public int findInorderSuccessor(int val) {
        Node node = this.root;
        if(node == null)
            return -1;
        Node parent = null;
        //finding the node first
        while(node != null) {
            if(node.val == val)
                break;
            parent = node;
            if(node.val > val)
                node = node.left;
            else
                node = node.right;
        }
        //if right child exists then find min in right subtree
        if(node.right != null) {
            node = node.right;
            while(node.left != null)
                node = node.left;
            return node.val;
        }
        //if right child doesn't exists then parent will be the sucessor
        return parent.data;
    }
    
    public int getInorderPredecessor(int val) {
        Node node = this.root;
        if(node == null)
            return -1;
        Node parent = null;
        while(node != null) {
            if(node.data > val) {
                parent = node;
                node = node.left;
            }
            else if(node.data < val) {
                parent = node;
                node = node.right;
            }
            else
                break;
        }
        //if left child exists then find max value in left child subtree
        if(node.left != null) {
            node = node.left;
            while(node.right != null)
                node = node.right;
            return node.data;
        }
        //no left child means predecessor will be parent
        return parent.data;
    }
    
    
    //Different traversals
    public void preorderTraverse(Node root) {
        if(root == null)
            return;
        System.out.println(root.val);
        preorderTraverse(root.left);
        preorderTraverse(root.right);
    }
    
    public void inorderTraverse(Node root) {
        if(root == null)
            return;
        inorderTraverse(root.left);
        System.out.println(root.val);
        inorderTraverse(root.right);
    }
    
    public void postorderTraverse(Node root) {
        if(root == null)
            return;
        postorderTraverse(root.left);
        postorderTraverse(root.right);
        System.out.println(root.val);
    }
    
    
    public boolean nodeExists(int val) {
        if(this.root == null)
            return false;
        
        Node node = this.root;
        while(node != null) {
            if(node.val == val)
                return true;
            else if(node.val > val)
                node = node.left;
            else if(node.val < val)
                node = node.right;
        }
        return false;
    }
    
    public Node getParentNode(int val) {
        Node node = this.root;
        while(node != null) {
            if((node.left != null && node.left.val == val) ||(node.right != null && node.right.val == val))
                return node;
            if(node.val > val)
                node = node.left;
            else if(node.val < val)
                node = node.right;
            else
                return null;
        }
        return node;
    }
}


class BSTTest {
    public static void main(String[] args) {
        BST tree = new BST();
        Node root = null;
        root = tree.insertNode(root, 8);
        root = tree.insertNode(root, 3);
        root = tree.insertNode(root, 6);
        root = tree.insertNode(root, 10);
        root = tree.insertNode(root, 4);
        root = tree.insertNode(root, 7);
        root = tree.insertNode(root, 1);
        root = tree.insertNode(root, 14);
        root = tree.insertNode(root, 13);
        tree.inorderTraverse(tree.root);
        /*System.out.println(tree.nodeExists(1));
        System.out.println(tree.nodeExists(2));
        System.out.println(tree.nodeExists(3));
        System.out.println(tree.getParentNode(1).val);
        System.out.println(tree.getParentNode(8)==null?"null":"wrong");
        System.out.println(tree.getParentNode(13).val);*/
        System.out.println(tree.getInorderSuccessor(13));
        System.out.println(tree.getInorderSuccessor(7));
        System.out.println(tree.getInorderSuccessor(8));
    }
}
