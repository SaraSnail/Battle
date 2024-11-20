package com.battleship;

import java.util.ArrayList;
import java.util.List;

//GB-26-SA
public class Coordinates {
    private int row;
    private int col;

    //GB-49-SA, ändra från public till default
    //GB-26-SA
    Coordinates(int col, int row) {
        this.col = col;
        this.row = row;
    }

    //GB-49-SA, ändra från public till default
    //GB-26-SA
    static Coordinates getValueAtCoordinates(String message) {
        List<Character> letters = new ArrayList<Character>();
        //Lista på bokstäver a-j
        letters.add('a');//0
        letters.add('b');//1
        letters.add('c');//2
        letters.add('d');//3
        letters.add('e');//4
        letters.add('f');//5
        letters.add('g');//6
        letters.add('h');//7
        letters.add('i');//8
        letters.add('j');//9

        //Får in typ "i shot 4b" och delar upp den med mellanslag, så vi får 3 arrayer
        String[] splitMessage = message.split(" ");

        //Tar sista arrayen och sätter i coordinates
        String coordinates = splitMessage[splitMessage.length - 1];

        int boardSize = 10;// Spelplanen är 10x10, A-J, 0-9

        //Tar char på plats 0 och sätter det som row
        int col  = Character.getNumericValue(coordinates.charAt(0));

        //Tar char på plats 1 som ska bli column. Gör om till LowerCase så det stämmer med listan
        char letter = coordinates.toLowerCase().charAt(1);

        //Tar listan och den bokstav som är den samma på listan dens index siffra säts som column
        int row = letters.indexOf(letter);

        //Kollar att row och col är inom board storleken kasta annars "IllegalArgumentException"
        if(row < 0 || row >= boardSize || col < 0 || col >= boardSize){
            throw new IllegalArgumentException("Coordinates out of bounds");
        }

        //Skapar Coordinates objekt med dessa col och row
        return new Coordinates(col, row);
    }

    //GB-26-SA
    public int getRow() {
        return row;
    }

    //GB-26-SA
    public void setRow(int row) {
        this.row = row;
    }

    //GB-26-SA
    public int getCol() {
        return col;
    }

    //GB-26-SA
    public void setCol(int col) {
        this.col = col;
    }

}
