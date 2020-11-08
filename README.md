# Lab5

This program basically reads multiple CSV files that were downloaded from 
SpotifyCharts and then creates a Binary Search Tree for all the songs that appear
on the SpotifyCharts.csv file. The program then scans in the input files into three
separate created array lists: songList, artistList, streamList. Then, a method
called makeBST is called to insert appropriate objects into their respective places
within the Binary Search Tree. The class SongPlaylist basically is used to create
the specifics of the BST such the nodes. This class is also responsible for printing
out the required information. There is also a function called inorderTransversal
which basically orders the roots in a specific order. Furthermore, there is also
a subset function which basically helps in filtering the list of songs. The class
Song contains information about the songs and is primarily used as storage
and to basically return a certain value when called upon. The output of the completed program
is redirected to a text file which is called "Playlist.txt". The file contains
information such as how many times a song was streamed and how many average times the 
artist was streamed.

**You need to run the Lab5.java file which is located in the src folder. Make sure when you clone repository that all the "CSV" files are located in the Lab 4 folder so that the code can read it without throwing a Nullpointer error. No other files are needed except all the present "CSV" files because the program will create the other files listed.**
