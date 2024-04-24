package ie.tudublin;

import processing.core.PApplet;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;

public class Main extends PApplet {
    Minim minim;
    AudioPlayer song;

    public void settings() {
        fullScreen(P3D);
    }

    @Override
    public void setup() {
        minim = new Minim(this);
        song = minim.loadFile("java/data/myuniverse.mp3", 1024);
        song.play();
        runIntro();
    }

    private void runIntro() {
        String[] args = {"Intro"};
        intro introSketch = new intro(this, song); 
        PApplet.runSketch(args, introSketch);
    }

    public void switchToBuildings() {
        String[] args = {"Buildings"};
        Buildings buildings = new Buildings(this,song);
        PApplet.runSketch(args, buildings);
    }
    public void switchToPerson() {
        String[] args = {"person"};
        person person = new person(this,song);
        PApplet.runSketch(args, person);
    }
    public void switchToUniverse(){

        String[] args = {"universe"};
        Universe universe = new Universe(this,song);
        PApplet.runSketch(args, universe);
    
    }
    public void switchToBuildings2(){

        String[] args = {"buildings2"};
        Buildings2 buildings2 = new Buildings2(this,song);
        PApplet.runSketch(args, buildings2);
    
    }
    public void switchToUniverse2(){

        String[] args = {"universe2"};
        Universe2 universe2 = new Universe2(this,song);
        PApplet.runSketch(args, universe2);
    
    }
    public void switchToPerson2(){
        String[] args = {"person2"};
        person2 person2 = new person2(this, song);
        PApplet.runSketch(args, person2);
    }
    public void switchToUniverse3(){
        String[] args = {"universe3"};
        Universe3 universe3 = new Universe3(this,song);
        PApplet.runSketch(args, universe3);
    }
    public static void main(String[] args) {
        PApplet.main("ie.tudublin.Main");
    }
}