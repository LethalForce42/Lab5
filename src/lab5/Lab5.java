/**
 * @author Adil Qumar
 * 
 * This program basically reads multiple CSV files that were downloaded from 
 * SpotifyCharts and then creates a Binary Search Tree for all the songs that appear
 * on the SpotifyCharts.csv file. The program then scans in the input files into three
 * separate created array lists: songList, artistList, streamList. Then, a method
 * called makeBST is called to insert appropriate objects into their respective places
 * within the Binary Search Tree. The class SongPlaylist basically is used to create
 * the specifics of the BST such the nodes. This class is also responsible for printing
 * out the required information. There is also a function called inorderTransversal
 * which basically orders the roots in a specific order. Furthermore, there is also
 * a subset function which basically helps in filtering the list of songs. The class
 * Song contains information about the songs and is primarily used as storage
 * and to basically return a certain value when called upon. The output of the completed program
 * is redirected to a text file which is called "Playlist.txt". The file contains
 * information such as how many times a song was streamed and how many average times the 
 * artist was streamed.
 */

package lab5;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.PrintStream;

public class Lab5 {
    static SongPlaylist lst = new SongPlaylist();
    // The next set of code:
    // creates an array list to store all the songs
    // creates an array list to store all arist names
    // creates an array list to store all stream counts
    // intializes scanner to read inputStream
    // split the line into parts for song variable declaration
    // adds track name into songlist, artist name to list, stream count to list
    public static void main(String[] args) throws IOException{
         PrintStream out = new PrintStream("Playlist.txt");
        System.setOut(out);
        String[] fileLocation = { "Week1.csv","Week2.csv","Week3.csv","Week4.csv" }; 
        ArrayList<String> songList = new ArrayList<>();
        songList.ensureCapacity(1000);
        ArrayList<String> artistList = new ArrayList<>();
        artistList.ensureCapacity(1000);
        ArrayList<Integer> streamList = new ArrayList<>();
        streamList.ensureCapacity(1000);
        for (String x : fileLocation) {
            FileInputStream inputStream = new FileInputStream(x); 
            Scanner sc = new Scanner(inputStream, StandardCharsets.UTF_8.name()); 
            while (sc.hasNextLine()) { 
                String line = sc.nextLine();
                String[] array = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                if (array.length > 1) {
                    for (int i = 0; i < array.length; i++) {
                        if (array[i].charAt(0) == '"') {
                            array[i] = array[i].substring(1, array[i].length() - 1);
                        }
                    }
                    songList.add(array[1]);
                    artistList.add(array[2]);
                    streamList.add(Integer.parseInt(array[3]));
                }
            }
            inputStream.close();
            sc.close();
        }
        makeBST(songList, artistList, streamList); // make BST
        lst.inorderTransversal();
    }
    // The next set of code:
    // makes a queue of Song objects from 3 lists
    // make song objct with elements from each list
    // inserts object into BST
    public static void makeBST(ArrayList<String> songLst, ArrayList<String> artistLst, ArrayList<Integer> streamLst) {
        for (int i = 0; i < songLst.size(); i++) {
            Song artist = new Song(songLst.get(i), artistLst.get(i), streamLst.get(i));
            lst.insert(artist);
        }
    }
}
// The next set of code: 
// creates root node of BST
// returns whether BST is empty
// adds objects into BST
// creates inorder transversal of BST
// creates subset function to print out songs within range of two values in argument
class SongPlaylist {
    public Song root;
    public SongPlaylist() {
        root = null;
    }
    public boolean isEmpty() {
        return root == null;
    }
    public void insert(Song obj) {
        if (root == null) {
            root = obj;
        }
        else {
            Song current = root;
            Song parent;
            while (true) {
                parent = current;
                // if the object's songtitle eqauals the current nodes song title, reset average stream for the song
                if (obj.songTitle.compareToIgnoreCase(current.songTitle) == 0) {
                    current.setArtistAverage(obj);
                    return;
                }
                else if (obj.songTitle.compareToIgnoreCase(current.songTitle) < 0) {
                    current = current.left;
                    if (current == null) {
                        parent.left = obj;
                        return;
                    }
                }
                else {
                    current = current.right;
                    if (current == null) {
                        parent.right = obj;
                        return;
                    }
                }
            }
        }
    }
    public void inorderTransversal() {
        inorder(root);
    }
    // helper function or can be used to in order transversal for any subtree of BST
    public void inorder(Song root) {
        if (root == null) {
            return;
        }
        inorder(root.left);
        System.out.println(root);
        inorder(root.right);
    }
    public void subset(String song1, String song2) {
        subset(root, song1, song2);
    }
    public Song subset(Song root, String song1, String song2) { // takes in three arguments
        if (root == null) {
            return root;
        }
        Song current = root;
        if (song1.compareTo(current.songTitle) < 0) {
            subset(current.left, song1, song2);
        }
        if ((song1.compareTo(current.songTitle) <= 0) && (song2.compareTo(current.songTitle) >= 0)) {
            System.out.println(current);
        }
        if (song2.compareTo(current.songTitle) > 0) {
            subset(current.right, song1, song2);
        }
        return root;
    }
}
class Song implements Comparable<Song>{
    public String songTitle;
    public int streamCount;
    public String artistName;
    public int artistAverage;
    public Song left;
    public Song right;
    // empty constructor
    public Song() {
        this.songTitle = null;
        this.streamCount = 0;
        this.artistName = null;
        this.artistAverage = 0;
        left = null;
        right = null;
    }
    // constructor with arguments
    public Song(String songTitle, String artistName, int streamCount) {
        this.songTitle = songTitle;
        this.streamCount = streamCount;
        this.artistName = artistName;
        this.artistAverage = streamCount;
        left = null;
        right = null;
    }
    // set songTitle method
    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }
    // get songTitle
    public String getSongTitle() {
        return this.songTitle;
    }
    // set streamCount method
    public void setStreamCount(int streams) {
        this.streamCount = streams;
    }
    // get streamCount
    public int getStreamCount() {
        return this.streamCount;
    }
    // set artistName
    public void setArtistName(String name) {
        this.artistName = name;
    }
    // get artistName
    public String getArtistName() {
        return this.artistName;
    }
    // set number of plays during comparison + 1
    public void setArtistAverage(Song obj) {
        this.artistAverage = (this.streamCount + obj.streamCount) / 2;
    }
    //compare string values of each song object
    public int compareTo(Song obj) {
        return this.songTitle.compareToIgnoreCase(obj.songTitle);
    }
    public String toString() {
        return this.songTitle + " by " + this.artistName + " = " + this.streamCount + " streams  ||  " + this.artistName +  " = " + this.artistAverage + " average streams";
    }
}