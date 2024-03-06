package ie.tudublin;
import ddf.minim.AudioBuffer;
import ddf.minim.AudioInput;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import ddf.minim.analysis.FFT;
import processing.core.PApplet;
import ddf.minim.*;

public class Audio extends PApplet{

   

Minim minim;
AudioPlayer player;
FFT fft;

public void settings() {
  size(800, 600);
  minim = new Minim(this);
  player = minim.loadFile("java/data/Jb.mp3");
  fft = new FFT(player.bufferSize(), player.sampleRate());
  player.play();
}

public void draw() {
  background(255);
  
  // analysing the audio
  fft.forward(player.mix);
  
  // extract frequency information, for i less than the frequency
  for (int i = 0; i < fft.specSize(); i++) {
    float frequency = fft.indexToFreq(i);
    float amplitude = fft.getBand(i);
    // drawing  barssomething based on frequency and amplitude
    rect(i * (width / fft.specSize()), height, width / fft.specSize(), -amplitude * 10);
  }
  
  // calculating the volume
  float volume = player.mix.level();
  // drawing based on the volume so if in the pre chorus or chorus
  ellipse(width / 2, height / 2, volume * 100, volume * 100);
}

}