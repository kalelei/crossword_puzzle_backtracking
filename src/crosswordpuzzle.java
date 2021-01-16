//Yiğit Kaleli 2152007
import java.awt.Point;
//Creating crossword_bloks class
class crossword_bloks{ //CLASS for BLOKS--Each block have their starting coordinate, size(length) and direction
    private Point starting_point;
    private int length;
    private Point direction;

    public crossword_bloks(Point starting_point, int length, Point direction){

        this.starting_point = starting_point; //Every blok have their own values initialized
        this.length = length;
        this.direction=direction;
    }

    public Point getStarting_point() { //Initializing getters to reach Private values

        return starting_point;
    }

    public int getLength() {

        return length;
    }

    public Point getDirection() {

        return direction;
    }
}
//Creating crossword_Words class
class crossword_Words{//CLASS for WORDS--I should keep track of the String of the word and if it is used or not

    private String word;
    private boolean isUsed;

    public crossword_Words(String word){
        this.word=word;
        this.isUsed=false; //Initially every word not used so they are false
    }

    public String getWord() {
        return word;
    }

    public void setUsed(boolean used) { // And initializing setter for isUsed variable so, we can change when needed
        isUsed = used;
    }

    public boolean isUsed() {
        return isUsed;
    }
}



//Creating crosswordpuzzle class
public class crosswordpuzzle {

    public static char BLANK = '-';  //Initializing BLANKS as '-'
    public static char WALL = '#';   //Initializing WALL as '#'

    private char[][] crossword_Puzzle_State; //initializing sates of puzzle

    private crossword_Words[] words; //All the words we trying to use

    private crossword_bloks[] bloks; //Puzzle have bloks to put words(1,2,3,4)

    private int[][] characters;

    private int number_of_backTrack; //to calculate algorithim repeat

    //Construct method to create Game and its values.
    public crosswordpuzzle(char[][] crossword_Puzzle_State, crossword_Words[] words, crossword_bloks[] bloks){
        this.crossword_Puzzle_State=crossword_Puzzle_State;
        this.bloks=bloks;
        this.words=words;

    }

    //Starts to solve puzzle
    public void solvePuzzle(){
        characters = new int[crossword_Puzzle_State.length][crossword_Puzzle_State[0].length]; //Initialize charaters 2D array with size of puzzle we have. [00000]
        number_of_backTrack=0;                                                                                                                                  //[00000]...
        if(backtrack_fill(0)){
            System.out.println("Solved");
            System.out.println("Backtracks applied "+ number_of_backTrack + " times");
        }
    }

    //Recursive method to check all possible variations.
    private boolean backtrack_fill(int block){

        if (block == bloks.length){ // Check if we filled all the blocks if then that means it should be equal to bloks.length
            showPuzzle();
            return true;
        }
        for (crossword_Words word : words){ //Getting every word from words list and  For example: For first block program tries to pick a word that can fit that "block"
            showPuzzle();
            if (wordFitToBlock(word,bloks[block])){

                putWordToBlock(word, bloks[block]);

                if(backtrack_fill(block+1)){ //Check if we have still bloks to fill and recall the backtrack_fill method again (RECURSIVE)
                    return true;
                }
                else {
                    removeWordFromBlock(word, bloks[block]);
                }
            }
        }
        number_of_backTrack++;
        return false;

    }


    private boolean wordFitToBlock(crossword_Words word, crossword_bloks block){
        //If the given word length not equal to block length OR (word is USED) we cannot use the word so returns false.
        if(word.getWord().length() != block.getLength() || word.isUsed()){
            return false;
        }

        Point position = new Point(block.getStarting_point()); //Initializing block starting position. For first black it is (0,0)

        for (int i = 0; i < block.getLength(); i++) {

            //Checking every position that block have
            if (crossword_Puzzle_State[position.x][position.y]!=BLANK && crossword_Puzzle_State[position.x][position.y] != word.getWord().charAt(i)){
                return false;
            }

            position.x += block.getDirection().x;  //By using direction I can move along the block with entered direction
            position.y += block.getDirection().y;
        }

        return true;

    }


    private void putWordToBlock(crossword_Words word, crossword_bloks block){

        Point position = new Point(block.getStarting_point()); //Initializing position as block starting point.
        for (int i = 0; i < block.getLength(); i++) {

            crossword_Puzzle_State[position.x][position.y] = word.getWord().charAt(i); //Place each character from word to puzzle position

            characters[position.x][position.y]++;

            position.x += block.getDirection().x; //By using direction I can move along the block with block specified direction
            position.y += block.getDirection().y; //By using direction I can move along the block with block specified direction
        }

        word.setUsed(true);

    }

    //OPPOSITE OF putWordToBlock method
    private void removeWordFromBlock(crossword_Words word, crossword_bloks block){
        Point position = new Point(block.getStarting_point()); //Initializing position to starting point of block

        for (int i = 0; i < block.getLength(); i++) {
            characters[position.x][position.y]--;

            if (characters[position.x][position.y] == 0){  //When characters deleted we can make them BLANK again
                crossword_Puzzle_State[position.x][position.y]=BLANK;
            }
            position.x += block.getDirection().x;  //By using direction I can move along the block with block specified direction
            position.y += block.getDirection().y;  //By using direction I can move along the block with block specified direction

        }
        word.setUsed(false);
    }

    /**Printing the puzzle*/
    public void showPuzzle(){
        for (int i = 0; i < crossword_Puzzle_State.length; i++) {

            for (int j = 0; j < crossword_Puzzle_State[i].length; j++) { //We need number that ROW has
                System.out.print(crossword_Puzzle_State[i][j]);
            }
            System.out.println();
        }

        System.out.println();
    }


    public static void main (String[] args){


        char[][] puzzle = {
                { BLANK, BLANK, BLANK, BLANK, BLANK},
                { WALL, WALL, BLANK, WALL, BLANK},
                { WALL, BLANK, BLANK, BLANK, BLANK},
                { BLANK, WALL, BLANK, BLANK, BLANK},
                { BLANK, BLANK, BLANK, BLANK, BLANK},
                { BLANK, WALL, WALL, BLANK, WALL}
        };

        crossword_bloks[] blocks = {
                new crossword_bloks(new Point(0, 0), 5, new Point(0, 1)),  //Starting_point, length, direction
                new crossword_bloks(new Point(0, 2), 5, new Point(1, 0)),
                new crossword_bloks(new Point(0, 4), 5, new Point(1, 0)),
                new crossword_bloks(new Point(2, 1), 4, new Point(0, 1)),
                new crossword_bloks(new Point(2, 3), 4, new Point(1, 0)),
                new crossword_bloks(new Point(3, 0), 3, new Point(1, 0)),
                new crossword_bloks(new Point(3, 2), 3, new Point(0, 1)),
                new crossword_bloks(new Point(4, 0), 5, new Point(0, 1))
        };

        crossword_Words[] words = {
                new crossword_Words("aft"),
                new crossword_Words("ale"),
                new crossword_Words("eel"),
                new crossword_Words("heel"),
                new crossword_Words("hike"),
                new crossword_Words("hoses"),
                new crossword_Words("keel"),
                new crossword_Words("knot"),
                new crossword_Words("laser"),
                new crossword_Words("lee"),
                new crossword_Words("line"),
                new crossword_Words("sails"),
                new crossword_Words("sheet"),
                new crossword_Words("steer"),
                new crossword_Words("tie")
        };

        //Puzzle_state, Words list, Blocks list
        crosswordpuzzle Newpuzzle = new crosswordpuzzle(puzzle, words, blocks );

        Newpuzzle.showPuzzle();

        System.out.println("\n\n:::::::After Solution::::::");

        Newpuzzle.solvePuzzle();

    }


}