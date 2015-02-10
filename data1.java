package Data1;

/**
 *
 * @author Lilianzz
 */
interface BST { 
    BST empty();
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

public class Branch implements BST {
    BST left;
    int key;
    BST right;    
    
    //Should the empty() be a method or it is better to be an independent class?
    //return a new leaf to create an empty finite set
    public static Leaf empty() {
        return new Leaf();
    }    
    
    //constructor
    Branch(BST left, int key, BST right) {
        this.left = left;
        this.key = key;
        this.right = right;
    }
    
    //-> int
    //add the cardinality of left subtree and right 
    //subtree and 1 for the node itself
    public int cardinality() {
        return (1+left.cardinality() +right.cardinality());
    }
    
    //-> boolean
    //since it's not a Leaf, it's not empty, return false
    public boolean isEmptyHuh() {
        return false;
    }       
    
    //int -> BST
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
    
    
    //int -> boolean
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
    
    //int -> BST
    public BST remove(int x) {
        if (x == this.key) {
            return this.left.union(this.right);
        }
        else 
            if (x < this.key) {
                return this.left.remove(x);
            }
            else return this.right.remove(x);
    }
    
    //BST -> BST
    //return a new BST combined two original ones
    public BST union(BST u) {
        return left.union(u).union(right).add(key);
    }
    
    //BST -> BST
    //return a new BST composed by elements shared by both BSTs
    public BST inter(BST u) {
        if (u.member(key)) {
            return u.inter(this.remove(key)).add(key);
        }
        else {
            return u.inter(this.remove(key));
        }          
    }
    
    //BST -> boolean
    public boolean subset(BST u) {
        if (u.member(this.key)) {
            return this.remove(this.key).subset(u);
        }
        else
            return false;
    }    
    
    // BST -> boolean
    public boolean equal(BST u) {
        return (u.subset(this) && this.subset(u));
    }    
    
    // BST -> BST
    public BST diff(BST u) {
        
        if (u.member(key)) {
            return left.union(right).diff(u.remove(key));}
        else
            return  left.diff(u).union(right.diff(u)).add(key);
        
    }
    
    public String toString() {
        return left.toString() + " " + Integer.toString(key) + " " + right.toString();
    }
}

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

public class Finite {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        // tests            
        BST bot = new Leaf();
        BST t1 = new Branch( bot, 1, bot );
        BST t3 = new Branch( bot, 3, bot );
        BST t2 = new Branch( t1, 2, t3 );
        BST t6 = new Branch( bot, 6, bot );
        BST t8 = new Branch( bot, 8, bot );
        BST t7 = new Branch( t6, 7, t8 );
        BST t5 = new Branch( t2, 5, t7 );
        System.out.println("cardinality of t5 = "+ t5.cardinality()+ "  should be 7");
        System.out.println("cardinality of bot = " + bot.cardinality()+"   should be 0");
        System.out.println("t5: "+t5);
        System.out.println("Is 9 a member of t5?"+t5.member(9));
        System.out.println("intersection of t5 removing 5 and t7:"+t5.remove(5).inter(t7));
        System.out.println("union of t6 and t2"+ t6.union(t2));
        System.out.println("is t3 a subset of t5?"+t3.subset(t5));
        System.out.println("is t5 a subset of t3?"+t5.subset(t3));
        System.out.println("does t5 equal t3?"+t5.equal(t3));
        }

        
    }
    
}
