//Code to check if we are in the chorus or in the main

package ie.tudublin;
import processing.core.PApplet;
import processing.core.PImage;
import ddf.minim.AudioBuffer;
import ddf.minim.AudioInput;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;


import ddf.minim.*;
import ddf.minim.analysis.*;
public class frequencyvol extends PApplet{

Minim minim;
AudioPlayer song;
FFT fft;
float volumeThreshold = 0.27f; // 0.27 seems to be the sweet spot for detecting beats 



public void settings() {
  size(800, 600);
  minim = new Minim(this);
  song = minim.loadFile("java/data/Jb.mp3", 1024);
  fft = new FFT(song.bufferSize(), song.sampleRate());
  song.play();
 

}

public void draw() {
  background(0);

  fft.forward(song.mix);
  float volume = 0;

  //loop to calculates the volume /average amplitude of the audio signals in the current buffer
  for(int i = 0; i < song.bufferSize(); i++) {
    //abs is used  function,  used to convert the amplitude value to absolute value
    volume += abs(song.mix.get(i));
  }
  volume /= song.bufferSize();



  // condition to check for chorus vs verse
    if (volume > volumeThreshold) {
      String[] a = {"MAIN"};
      processing.core.PApplet.runSketch( a, new Sun());
      println("Chorus Detected!"); 
    } 
  
   if ( volume < 0.2f){
    println("We are not in the chorus");
    String[] a = {"MAIN"};
    processing.core.PApplet.runSketch( a, new Start());
    
  } 
  else if (volume > 0.2f|| volume < 0.2f) {
    println("Bridging to the chorus../pre chrous");
  }

 
}



    
}