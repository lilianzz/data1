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
public class Leaf implements BST {
    Leaf(){}
    public static Leaf empty() {
        return new Leaf();
    }
    public boolean isEmptyHuh() {
        return true;
    }
    public int cardinality() {
        return 0;
    }    
    public boolean member(int x) {
	return false;
    }
    public BST add(int x) {
	return new Branch(this, x, this);
    }
    public BST remove(int x) {
	return this;
    }  
    public BST union(BST u) {
	return u;
    }
    public BST inter(BST u) {
	return this;
    }
    public BST diff(BST u) {
	return u;
    }
    public boolean equal(BST u) {
	return u.isEmptyHuh();
    }
    public boolean subset(BST u) {
	return true;
    }
    public String toString() {
	return "";
    }
}