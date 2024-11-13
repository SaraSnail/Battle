package com.battleship;// BG-9 CF

import java.util.ArrayList;
import java.util.List;

public class Ship {

        private String kind;
        private int size;
        private boolean sunk;
        private int numberOfHits;
        private List<int[]> coordinates;        //GB-36-AWS

        public Ship(String kind, int size, boolean sunk, int numberOfHits) {
            this.kind = kind;
            this.size = size;
            this.sunk = sunk;
            this.numberOfHits = numberOfHits;
            this.coordinates = new ArrayList<>();       //GB-36-AWS
        }

        public String getKind() {
            return kind;
        }

        public void setKind(String kind) {
            this.kind = kind;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public boolean isSunk() {
            return sunk;
        }

        public void setSunk(boolean sunk) {
            this.sunk = sunk;
        }

        public int getNumberOfHits() {
            return numberOfHits;
        }

        public void setNumberOfHits(int numberOfHits) {
            this.numberOfHits = numberOfHits;
        }
        //GB-36-AWS
        public List<int[]> getCoordinates() {
            return coordinates;
        }
        //GB-36-AWS
        public void addCoordinates(int row, int col) {
            coordinates.add(new int[]{row, col});
        }
}
