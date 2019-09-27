import models.DnsQuery;
import models.QtypeEnum;

import java.io.*;
import java.net.*;

public class Main {

    public static void main(String[] args) throws Exception {
//
//        System.out.println("Hello World!");
//
//        // Create two string variables to hold the info we'll send and receive from the server
//        String sentence,
//                nameserver,
//                modifiedSentence;
//
//        // Open a reader to input from the command line
//        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
//
//        // Open a TCP socket to the server, running on port 6789 "localhost" (i.e., this machine)
//        Socket clientSocket = new Socket("8.8.8.8", 53);
//        if(clientSocket.isConnected()) System.out.println("connected");
//
//        // Open readers to send/receive from server
//        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
//        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//
//        // Read input from the user:
//        System.out.println("Type a message and hit enter.");
//        nameserver = inFromUser.readLine();
//        sentence = new DnsQuery(nameserver, QtypeEnum.A).toString();
//        byte[] v = sentence.getBytes();
//        for(byte b: sentence.getBytes()){
//            System.out.print(String.format("%02x",b) + " ");
//        }
//        System.out.println(sentence);
//        // Send the message to the server
//        outToServer.writeBytes(sentence + '\n');
//
//        // Read the response from the server
//        modifiedSentence = inFromServer.readLine();
//        System.out.println("From Server: " + modifiedSentence);
//
//        // Close the socket
//        clientSocket.close();

        // Open a reader to input from the command line
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

        // Create a UDP socket
        // (Note, when no port number is specified, the OS will assign an arbitrary one)
        DatagramSocket clientSocket = new DatagramSocket();

        // Resolve a domain name to an IP address object
        // In this case, "localhost" maps to the so-called loop-back address, 127.0.0.1
        InetAddress ipAddress = InetAddress.getByName("8.8.8.8");

        // Allocate buffers for the data to be sent and received
        byte[] sendData = new byte[1024];
        byte[] receiveData = new byte[1024];

        // Read a sentence from the user
        System.out.println("Type a message and hit enter.");
        String nameserver = inFromUser.readLine();
        String sentence = new DnsQuery(nameserver, QtypeEnum.A).toString();
        // Convert the sentence from a String to an array of bytes
        sendData = sentence.getBytes();

        // Create a UDP packet to be sent to the server
        // This involves specifying the sender's address and port number
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress, 53);

        // Send the packet
        clientSocket.send(sendPacket);

        // Create a packet structure to store data sent back by the server
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

        // Receive data from the server
        clientSocket.receive(receivePacket);

        // Extract the sentence (as a String object) from the received byte stream
        String modifiedSentence = new String(receivePacket.getData());
        System.out.println("From Server: " + modifiedSentence);

        // Close the socket
        clientSocket.close();
    }
}
