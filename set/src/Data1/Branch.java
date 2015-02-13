/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Data1;

/**
 *
 * @author 栗粒盐
 */
public class Branch implements BST {
    BST left;
    int key;
    BST right;    
    
    // return a new leaf to create an empty finite set
    public static BST empty() {
        return new Leaf();
    }    
    
    //constructor
    Branch(BST left, int key, BST right) {
        this.left = left;
        this.key = key;
        this.right = right;
    }
    
    //add the cardinality of left subtree and right 
    //subtree and 1 for the node itself
    
    public int cardinality() {
        return (1+left.cardinality() +right.cardinality());
    }
    
    //since it's not a Leaf, it's not empty, return false
    public boolean isEmptyHuh() {
        return false;
    }       

    //add an element to the BST
    public BST add( int x ) {
        if ( x == this.key ) {
            return this ;
        } else if ( x < this.key ) {
            return new Branch( this.left.add(x),
                               this.key,
                               this.right );
        } else { /* if x > this.key */
            return new Branch( this.left,
                               this.key,
                               this.right.add(x)  );
        }
    }
    
    //search if x is in the tree
    public boolean member(int x) {
        // return true if x equals its value
        if (x == this.key) {
            return true;
        }
        //otherwise see if x in the left subtree or 
        //in the right subree
        else
            if (x < this.key) {
                return this.left.member(x);
            }
            else return this.right.member(x);
    }
    
    public BST remove(int x) {
        if (x == this.key) {
            return this.left.union(this.right);
        }
        else 
            if (x < this.key) {
                return new Branch(this.left.remove(x), key, right);
            }
            else return new Branch(left,key,this.right.remove(x));
    }
    
    public BST union(BST u) {
        return u.union(left).union(right).add(key);
    }
    
    public BST inter(BST u) {
        if (u.member(key)) {
            return u.inter(this.remove(key)).add(key);
        }
        else {
            return u.inter(this.remove(key));
        }          
    }
    
    public boolean subset(BST u) {
        if (u.member(this.key)) {
            return left.subset(u) && right.subset(u);
        }
        else
            return false;
    }    
    
    public boolean equal(BST u) {
        return (u.subset(this) && this.subset(u));
    }    
    
    public BST diff(BST u) {
        return this.remove(key).diff(u.remove(key));
    }
    
    public String toString() {
        return left.toString() + " " + Integer.toString(key) + " " + right.toString();
    }
}