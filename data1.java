package Data1;

/**
 *
 * @author Lilianzz
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

public class Branch implements BST {
    BST left;
    int key;
    BST right;    
    
    //Should the empty() be a method or it is better to be an independent class?
    //return a new leaf to create an empty finite set
    public static BST empty() {
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
                return new Branch(this.left.remove(x), key, right);
            }
            else return new Branch(left,key,this.right.remove(x));
    }
    
     //BST -> BST
    //return a new BST combined two original ones
    public BST union(BST u) {
        return u.union(left).union(right).add(key);
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
            return left.subset(u) && right.subset(u);
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
        return this.remove(key).diff(u.remove(key));
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
    
    
    // -> BST
    //generate random finite set of certain size
    public static BST randBST(int size) {
	BST temp = new Leaf();
	for(; size > 0; size--) {
	    temp = temp.add((int) ((Math.random()-.5) * 100));
	}
	return temp;
    }
    // test if cardinality of a random generated set is right
    public static boolean tCardi() {
        BST temp = new Leaf();
        int size = ((int)(Math.random() * 50));
        int s = 0;
        int c;
        for(; size > 0; size--) {
            c = (int) ((Math.random()-.5) * 100);
            if (!temp.member(c)) s++;
	    temp = temp.add(c);
	}
        if (temp.cardinality() == s) 
            return true;        
        else
            return false;               
        
    }
    
    //member (add t x) y = true <-> x = y \/ member t y = true
    public static boolean memberAdd (BST t) {
        int x = ((int) ((Math.random()-.5) * 100));
        int y = ((int) ((Math.random()-.5) * 100));
        boolean o1 = t.add(x).member(y);
        boolean o2 = ((x == y) || (t.member(y)));
        return o1 == o2;      
        
    }
    
    public static boolean removeAddEqual (BST t) {
        int x = ((int) ((Math.random()-.5) * 100));
        BST temp = t.remove(x).add(x);
        boolean o1 = temp.equal(t);
        boolean o2 = t.member(x);
        return o1 == o2;      
        
    }
    
    public static boolean memberRemove (BST t) {
        int x = ((int) ((Math.random()-.5) * 100));
        int y = ((int) ((Math.random()-.5) * 100));
        boolean o1 = t.remove(x).member(y);
        boolean o2 = (!(x == y) && (t.member(y)));
        return o1 == o2;      
        
    }
    
    //member (union s s') x = true <-> member s x = true \/ member s' x = true
    public static boolean memberUnion (BST s1, BST s2) {
        int x = ((int) ((Math.random()-.5) * 100));
        boolean o1 = s1.union(s2).member(x);
        boolean o2 = (s1.member(x) || s2.member(x));
        return o1 == o2;
    }
    
    public static boolean CardiUnion(BST t, BST u) {
	return t.union(u).cardinality() <= t.cardinality() + u.cardinality();
    }
    
    public static boolean memberInter (BST s1,BST s2) {
        int x = ((int) ((Math.random()-.5) * 100));
        boolean o1 = s1.inter(s2).member(x);
        boolean o2 = (s1.member(x) && s2.member(x));
        return o1 == o2;
        
    }
    
    public static boolean memberDiff (BST s1,BST s2) {
        int x = ((int) ((Math.random()-.5) * 100));
        boolean o1 = s1.diff(s2).member(x);
        boolean o2 = (!s1.member(x) && s2.member(x));
        return o1 == o2;
        
    }
    
    public static boolean memberEqual(BST t) {
        BST t2;
        int a = ((int) ((Math.random()-.5) * 100));
        t2 = t.remove(a);
        boolean o1 = t2.equal(t);
        boolean o2 = !t.member(a);
        return o1==o2;             
        
    }
    
    
    
    public static boolean memberSubset(BST t) {        
        BST u = new Leaf();
        BST v = new Leaf();
        int a;
        for (int i = 0; i<30; i++) {
            a = ((int) ((Math.random()-.5) * 100));
            if (t.member(a)) u.add(a); else v.add(a);     
            
        }
        return (u.subset(t) && (v.isEmptyHuh() || !(v.subset(t))));
    }
    
    public static void main(String[] args) {
        // test on a given finite set
        
        BST bot = new Leaf();
        BST t1 = new Branch( bot, 1, bot );
        BST t3 = new Branch( bot, 3, bot );
        BST t2 = new Branch( t1, 2, t3 );
        BST t6 = new Branch( bot, 6, bot );
        BST t8 = new Branch( bot, 8, bot );
        BST t7 = new Branch( t6, 7, t8 );
        BST t5 = new Branch( t2, 5, t7 );
        System.out.println("is bot empty? should be true: "+ bot.isEmptyHuh());
        System.out.println("t5 should be 1 2 3 5 6 7 8. It actually is: "+t5);
        System.out.println("t5.add(4) should add 4 before 5 and after 3: "+t5.add(4));
        System.out.println("cardinality of t5 = "+ t5.cardinality()+ "  should be 7");
        System.out.println("cardinality of bot = " + bot.cardinality()+"   should be 0");
        System.out.println("t5 remove 1 2 3 should be 5 6 7 8: "+t5.remove(1).remove(2).remove(3));
        System.out.println("t5 remove 9 should be 1 2 3 5 6 7 8: "+t5.remove(9));        
        System.out.println("t5 remove 5 and 6 should be 1 2 3 7 8: "+t5.remove(5).remove(6));
        System.out.println("Is 9 a member of t5? should be false: "+t5.member(9));        
        System.out.println("Is 3 a member of t5? should be true: "+t5.member(3));
        System.out.println("unoin of t3 and t6 should be 3 6: ." +t3.union(t6));
        System.out.println("unoin of t3 and bot should be 1 2 3: ." +t3.union(bot));        
        System.out.println("union of t6 and t2 should be 1 2 3 6: "+ t6.union(t2));
        System.out.println("intersection of t5 after removing 6 and t7 should be 7 8: "+t5.remove(6).inter(t7));
        System.out.println("intersection of t5 and bot should be nothing: "+t5.inter(bot));
        System.out.println("is t3 a subset of t5? should be true: "+t3.subset(t5));
        System.out.println("is t7 a subset of t3? should be false: "+t5.subset(t3));
        System.out.println("is bot a subset of t3? should be true: "+bot.subset(t3));
        System.out.println("does t5 equal t3? should be false: "+t5.equal(t3));        
        System.out.println("does t3 equal t3? should be true: "+t3.equal(t3));
        System.out.println("elements in t3 but not t6 should be 3:"+ t6.diff(t3));       
        System.out.println("elements in t3 but not t5 should be nothing:"+ t5.diff(t3));     
        
        
        // tests on randomly generated finite sets
        boolean t = true;
        for(int i=0; i < 300; i++) {
            if (!tCardi()) {
                t = false;
            }            
        }
        System.out.println("Whether passed cardinality test: "+t+"   (supposed to be true)");
        t=true;
        for(int i = 0; i< 300; i++) {
            t1 = randBST((int)(Math.random() * 30));
            t2 = randBST((int)(Math.random() * 30));
            if (!CardiUnion(t1,t2)) {
                t = false;                
                System.out.println(""+t1+" and "+t2+" failed on cardinality of union test");
            }
        }        
        System.out.println("Whether passed Cardinality of Union test: "+t+"   (supposed to be true)");
        t = true;
        for(int i = 0; i< 300; i++) {
            t1 = randBST((int)(Math.random() * 30));
            if (!memberAdd(t1)) {
                t = false;
                System.out.println(""+t1+"  failed on memberAdd test");
            }
        }                
        System.out.println("Whether passed memberAdd test: "+t+"   (supposed to be true)");
        t = true;
        for(int i = 0; i< 300; i++) {
            t1 = randBST((int)(Math.random() * 30));
            if (!memberRemove(t1)) {
                t = false;
                System.out.println(""+t1+"  failed on memberRemove test");
            }
        }                
        System.out.println("Whether passed memberRemove test: "+t+"   (supposed to be true)");
        
        t = true;
        for(int i = 0; i< 300; i++) {
            t1 = randBST((int)(Math.random() * 30)); 
            t2 = randBST((int)(Math.random() * 30));
            if (!memberUnion(t1,t2)) {
                t = false;
                System.out.println(""+t1+" and "+t2+" failed on memberUnion test");
            }
        }                
        System.out.println("Whether passed memberUnion test: "+t+"   (supposed to be true)");
        t = true;
        for(int i = 0; i< 300; i++) {
            t1 = randBST((int)(Math.random() * 30)); 
            t2 = randBST((int)(Math.random() * 30));
            if (!memberInter(t1,t2)) {                
                System.out.println(""+t1+" and "+t2+" failed on memberInter test");
                t = false;
            }
        }                
        System.out.println("Whether passed memberInter test: "+t+"   (supposed to be true)");
        t = true;
        for(int i = 0; i< 300; i++) {
            t1 = randBST((int)(Math.random() * 30)); 
            t2 = randBST((int)(Math.random() * 30));
            if (!memberDiff(t1,t2)) {
                t = false;
                System.out.println(""+t1+" and "+t2+" failed on memberDiff test");
            }
        }                
        System.out.println("Whether passed memberDiff test: "+t+"   (supposed to be true)");
        
        t = true;
        for(int i = 0; i< 300; i++) {
            t1 = randBST((int)(Math.random() * 30)); 
            if (!memberSubset(t1)) {
                t = false;                
                System.out.println(""+t1+"  failed on memberSubset test");
            }
        }                
        System.out.println("Whether passed memberSubset test: "+t+"   (supposed to be true)");
        
        t = true;
        for(int i = 0; i< 300; i++) {
            t1 = randBST((int)(Math.random() * 30)); 
            if (!memberEqual(t1)) {
                t = false;
                System.out.println(""+t1+"  failed on memberEqual test");
            }
        }                
        System.out.println("Whether passed memberEqual test: "+t+"   (supposed to be true)");
        
        t = true;
        for(int i = 0; i< 300; i++) {
            t1 = randBST((int)(Math.random() * 30)); 
            if (!removeAddEqual(t1)) {
                t = false;
                System.out.println(""+t1+"  failed on removeAddEqual test");
            }
        }                
        System.out.println("Whether passed removeAddEqual test: "+t+"   (supposed to be true)");
        
       
    }
    
}
       
