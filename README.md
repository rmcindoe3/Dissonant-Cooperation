Dissonant Cooperation
================

### Overview
This is a game for the Android OS inspired by the recently popular "Twitch Plays..." streams.  All of the code and image resources were developed in a sixteen hour timespan for the Spring 2014 GITMAD App-a-thon.  Due to the time constraints of the competition, most of the code went largely uncommented.  To preserve this repo as representative of what was completed for the competition it will remain that way.  Any questions about the project design or implementation can be sent to rmcindoe3@gatech.edu.

The project won second place overall in the competition.

### The Game
The game concept is pretty simple.  The goal is to collect all the coins located on the game map before anyone else.  The twist is that when a player presses one of the controls (up, down, etc...) those commands also control every other user playing the game.  Since the coins are randomly generated on the map everyone will be trying to go in different directions at the same time, making the game a battle for control over your own player.

In order to create the real time multi-user interaction in such a short development time the Firebase (www.firebase.com) API was used.  More info about that can be found at their website.

[Screenshots of Application](http://imgur.com/a/etuSO)

### How to Setup the Android Project
1. Clone the repo to your machine.
2. Using your IDE of choice, import the DissonantCooperation folder as an existing Android project.
3. Add appcompat-v7 as a library to the project.
4. Download the latest Firebase .jar from their website and add this library to the project as well.
5. Clean/Build the project.
