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
interface BST { 
    int cardinality();
    boolean isEmptyHuh();
    boolean member(int elt);
    BST add(int elt);
    BST remove(int elt);
    BST union(BST u);
    BST inter(BST u);
    BST diff(BST u);
    boolean equal(BST u);
    boolean subset(BST u);
    String toString();
}

