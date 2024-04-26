YouTube Video: https://www.youtube.com/watch?v=tqMkc-_airg

Students name: 
Omoteniola Ogunmuyiwa  C22419214
Saoirse Reposar        C22448686


Week1: (College Week 7)
We made the decision to utilize Processing along with either the Minim library 
or the Processing Sound library. 
Main goal: Analyze the audio tracks of songs, focusing on extracting key characteristics 
such as: 


1.frequency
2.volume 

This foundational step will enable us to create an engaging music visualization in Java.

 We wanted it to draw images based on if its in the chorus, or the pre chorus. We wanted to do this by simply measuring the volume/ frequency of the song and draw images based on how loud and energetic the frequencies are.

 We ran into some trouble when we realised that some parts of the song in the beginning would randomly be loud and the system would pick that up. So we spent more time developing an algorithm that can pick up the frequency and the volume so it can be more accurate.

End result: We created an algorithm in the frequencyvol.java file to extract the volume without the frequency. It passed it's testing stages and aided us in moving towards creating a refined version that is more complex and accurate and extracts both the frequency and volume.

Work split: Saoirse focused on extracting parts of the song based on the volume and Omoteniola focused on the frequency.


Week 2: (College Week8)
 Using our audio analysis Week 1, we are set to develop visual elements designed to interact with the audio in real-time. Our approach is to embrace abstraction through visuals.

 Main goal: Create visuals that dynamically respond to music, aiming to visually represent
 
 1.Mood 
 2.Tempo
 3.Narrative

 Should result in an experience that tells a story and conveys emotions.

 Week 3: (College Week 9)
 This week focuses on using exciting visual version of the song.

Main goal: Use visuals to show the feelings, themes, and layout of the song, with a focus on:

1.Colour:Bright colours for happy parts and darker colours for sad or intense parts.

2.Shape: Different shapes to represent parts of the song. Sharp shapes for exciting parts, and smooth shapes for calm parts.

3.Motion: Make the visuals move in a way that follows the beat and rhythm of the song. Fast movements for fast parts of the song, and slow movements for the slow parts.

4.Composition: Arrange the visual elements in a way that shows the structure of the song, e.g using the layout to differentiate between the chorus and verses.

By doing this, we hope to make a visual story that goes along with the song.

End result: We initially picked out 'Eenie meenie'  by Justin Bieber for our song so we created visuals based on the storyline of the song. Our first visual we came up with was a buildings visual where the buildings reacted to the beat based on the frequency and amplitude. We used the frquencyvol.java logic to get the buildings to be reactive however the output wasn't smooth enough so we decided to use lerp for a smoother visual. We also did some drafts of other visuals such as a girl on the clouds to represent the theme of the song (which we later changed as we changed our song choice).

We knew we wanted to do a night theme for the song so we had created a universe for the song. We ended up being really happy with the visual so we based our next song choice upon that visual.

So week 3 (college week 9) was mostly a trial and error session. We realised that the song didn't give us many options for complex visuals with immersive backgrounds. So we decided to switch to a song that offered us more ideas for creative visuals. 

Work Split: Omoteniola created the initial draft of the buildings and the logic of it reacting to the beat. He also did the logic of the universe and how each planet will rotate around the sun. Saoirse created the girl on the clouds and focused on the background for the universe constantly changing. She also designed the buildings.


week 4: (College week 10):
In the 4th week of doing our assignemnt we decided to use the song 'My Universe' by Coldplay and BTS. We had some designs prior that aligned with the theme of this song and they served as a template for most of our designs.

We knew we wanted the Universe.java visual to be in the main chorus so we started working on other parts of the song.

Main Goal: To create a visual for the intro and the first verse of the song.

So we decided on a lyric visual for the first few lines of the song. Omoteniola worked on the visual for the intro while Saoirse worked on the visual for the first verse. 

We used the buildings visual that Omoteniola made as a template for the first chorus and the immersive universe background template that Saoirse made as a template for the visual for the intro. 

Saoirse added to the buildings visual by turning it into an active city. This week she focused on the design of the glowing moon which was based on similar logic to the Sun.java she did in the weeks prior. And also focused on the appeal of the moon.

Omoteniola focused on the intro and getting the text to have a futuristic 3d feel. This week was also dedicated to figuring out how we were going to display the text (the timing etc).


week 5(College week 11):
In the 5th week we had 2 visuals completed. The universe and the intro.java Saoirse focused on populating the buildings.java visual to make it look more like an active city. And Omoteniola focused on getting the lyrics in the intro to be in sync and look more 3d. 

We also decided we wanted to scrap the person visual and instead put a visual of the Earth. We imported the sphere but couldn't figure out how to wrap the image around the sphere so initially we were going to have an interactive sphere rotating. 

We finished the lyrics, buildings and universe visual. Our priority now was to focus on the 3d earth and focus on getting the image to wrap around it and design the background more.

End result: 
We finished all visuals. We figured out how to add a moon to the 3d Earth in orbit. We just needed to decide how we were going to display our visuals and which method we were going to use to display it one by one.



Week6  (college week 12):


This week we put all the visuals together. Initially we had some issues getting the song to resume from where it was when each visual started but we managed to get it to all one after the other.
