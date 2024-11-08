package com.battleship;

import java.util.ArrayList;
import java.util.List;

//GB-26-SA
public class Coordinates {
    private int row;
    private int col;

    //GB-26-SA
    public Coordinates(int row, int col) {
        this.row = row;
        this.col = col;
    }

    //GB-26-SA
    public static Coordinates getValueAtCoordinates(String message, char[][] gameBoard) {
        List<Character> letters = new ArrayList<Character>();
        //Kanske ska ha lower case?
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

        //Tar sen storleken på arrayen -1, för arrayen är storlek 3 men sista array elementet är på plats 2
        String coordinates = splitMessage[splitMessage.length - 1];

        int boardSize = 10;// Spelplanen är 10x10, börjar på 0 och A och går till 9 och J

        //Tar char på plats 0 och sätter det som row. I ex 4b är 4:ran row, gör om det från char till ett siffer tal
        int row  = Character.getNumericValue(coordinates.charAt(0));

        //Tar char på plats 1 som ska bli column. I ex 4b är b column. Gör om till LowerCase så det stämmer med listan
        char letter = coordinates.toLowerCase().charAt(1);

        //Tar listan och den bokstav som är den samma på listan dens index säts som column
        int col = letters.indexOf(letter);

        //Kollar att row och col är inom board storleken
        //Så om row eller col är mindre än 0 eller större eller lika som boardSize (10) ska den kasta "IllegalArgumentException"
        if(row < 0 || row >= boardSize || col < 0 || col >= boardSize){
            throw new IllegalArgumentException("Coordinates out of bounds");
        }

        //Skickar tillbaka det som finns på koordinaterna
        return new Coordinates(row, col);
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
