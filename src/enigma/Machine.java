/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package enigma;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;

/**
 *
 * @author Judge Dread
 */

//todo


public class Machine {
    String name;
    int max_rotors;
    List<Rotor> rotors;
    List<Rotor> reflectors;
    Rotor reflector;
    List<Rotor> config = new ArrayList<>();
    char[] plugboard = new char[] {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
    
    public Machine(String m_name, List<Rotor> all_rotors, List<Rotor> all_reflectors, int max_rotor_config){
        name = m_name;
        rotors = all_rotors;
        reflectors = all_reflectors;
        max_rotors = max_rotor_config;
    }
    
    public List<Rotor> getRotors(){
        return rotors;
    }
    
    public List<Rotor> getConfig(){
        return config;
    } 
    
    //plugboard configuration
    public void configPlugboard(){
        String rawInput;
        String cleanInput;
//        String[] pairs;
        List<String> pairs;
        String[] temp;
        Scanner in = new Scanner(System.in);
        
        System.out.println("Please enter 10 or less letters to be paired (Example A:C):");
        rawInput = in.nextLine();
        //rawInput = "B:O B:D D:G AA:B @:r E:QQ" ;
        
        //remove all unwanted characters
        cleanInput = rawInput.toUpperCase().replaceAll("[^a-zA-Z: ]", "");
        
        //filter into char:char pairings
        pairs = new ArrayList(Arrays.asList(cleanInput.split(" ")));
        
        //move to a collection and back to list if duplicates are found
        Set<String> duplicates = new HashSet<>(pairs);
        if(duplicates.size() < pairs.size()){
            pairs.clear();
            pairs.addAll(duplicates);
        }
        
        //remove incorrectly formatted pairs
        for (Iterator<String> it = pairs.iterator(); it.hasNext();) {
            String pair = it.next();
            if(!pair.toUpperCase().matches("[A-Z]{1}:[A-Z]{1}")){
                System.out.println("Entry " + pair + " was invalid");
                it.remove();
            }
        }
        
        //insert into plugboard and checks to make sure a letter isn't reused
        for (String pair : pairs) {
            temp = pair.split(":");
            //find value of char in temp, find set value of char temp[1] to
            //65 == 'A'
            //check to see if values have been changed
            if(plugboard[temp[0].charAt(0) - 65] == temp[0].charAt(0) && plugboard[temp[1].charAt(0) - 65] == temp[1].charAt(0)){
                plugboard[temp[0].charAt(0) - 65] = temp[1].charAt(0);
                plugboard[temp[1].charAt(0) - 65] = temp[0].charAt(0);
            }else if(plugboard[temp[0].charAt(0) - 65] != temp[0].charAt(0)){
                System.out.println("Value " + temp[0] + " was already modified");
            }else if(plugboard[temp[1].charAt(0) - 65] != temp[1].charAt(0)){
                System.out.println("Value " + temp[1] + " was already modified");
            }
            
        }
        //take value of pair and place them in plugboard array
        
        
    }
    //fix var names
    //clean up
    public void selectRotors(){
        displayRotors();
        String choice;
        Scanner in = new Scanner(System.in);
        
        for(int i = 0;i < max_rotors; i++){
            choice = in.nextLine();
            int found = search(rotors, choice);
            //nullpointer if config is empty
            if(found > -1 && (config.contains(rotors.get(found)) == false)){
                config.add(rotors.get(found));
            }
            else {
                System.out.println("Rotor not found");
                i--;
            }
        }
        displayConfig();
    }
    //debug code
    public void defaultRotors(){

        config.add(rotors.get(0));
        config.add(rotors.get(1));
        config.add(rotors.get(2));
       
        displayConfig();
    }
    
    //Seperate reflector from config
    public void selectReflectors(){
        displayReflectors();
        String choice;
        Scanner in = new Scanner(System.in);
        
        for(int i = 0;i < 1; i++){
            choice = in.nextLine();
            int found = search(reflectors, choice);
            //nullpointer if config is empty
            if(found > -1 && (config.contains(reflectors.get(found)) == false)){
                reflector = reflectors.get(found);
            }
            else {
                System.out.println("Reflector not found");
                i--;
            }
        }
        
        System.out.println("Reflector " + reflector.getName() + " was chosen");
    }
    
    public void defaultReflector(){

        reflector = reflectors.get(1);
        System.out.println("Reflector " + reflector.getName() + " was chosen");
        
    }
    
    //Set the starting point for each rotor in use
    public void configRotorGround(){
        
        Scanner in = new Scanner(System.in);
        char choice;
        String temp;
        
        for(int i = 0;i < config.size(); i++){
            System.out.println("Select ground setting for rotor " + config.get(i).getName());
            temp = in.next();
            //choice = in.next(".").charAt(0);
            choice = temp.charAt(0);
            if(Pattern.matches("[a-zA-Z]", Character.toString(choice))){
                choice = Character.toUpperCase(choice);
                config.get(i).setCurrentPostion(choice - 'A');
            }else{
                System.out.println("Not a valid selection");
                i--;
            }
        }       
        displayRotorConfig();
    }
    
    public void defaultRotorGround(){

        for(int i = 0;i < config.size(); i++){
            config.get(i).setCurrentPostion(0);
        }
        //override
        config.get(2).setCurrentPostion(0);
        config.get(1).setCurrentPostion(0);
        //displayRotorConfig();
    }
    
    //reverse offset AAA AAZ == AAB AAA
    public void configRotorRing(){
        
        Scanner in = new Scanner(System.in);
        char choice;
        String temp;
        
        for(int i = 0;i < config.size(); i++){
            System.out.println("Select ring setting for rotor " + config.get(i).getName());
            temp = in.next();
            //choice = in.next(".").charAt(0);
            choice = temp.charAt(0);
            if(Pattern.matches("[a-zA-Z]", Character.toString(choice))){
                choice = Character.toUpperCase(choice);
                config.get(i).invert();
                config.get(i).setRingSetting(choice - 'A');
                config.get(i).offsetRing();
                config.get(i).offsetRingInverted();
                //test
                System.out.println(config.get(i).getSequence());
                System.out.println(config.get(i).getInvertedSequence());
            }else{
                System.out.println("Not a valid selection");
                i--;
            }
        }       
        displayRotorConfig();
    }
    
    public void defaultRotorRing(){
        
        for(int i = 0;i < config.size(); i++){
            config.get(i).invert();
            config.get(i).setRingSetting(0);
            config.get(i).offsetRing();
            config.get(i).offsetRingInverted();
        }       
        displayRotorConfig();
    }
    
    //displays all available rotors for the current machine
    public void displayRotors(){
        for(Rotor choices : rotors){
            System.out.println(choices.name);
        }
    }
    
    //displays all available rotors for the current machine
    public void displayReflectors(){
        for(Rotor choices : reflectors){
            System.out.println(choices.name);
        }
    }
    
    //displays current configuration of the machine
    public void displayRotorConfig(){
        for(Rotor choices : config){
            System.out.println(choices.name + " Ground Position: " + choices.getCurrentPostion() + " Ring setting: " + choices.getRingSetting());
        }
    }
    
    //displays the current configuration of the machine
    public void displayConfig(){
        for(Rotor choices : config){
            System.out.println(choices.name);
        }
    }
    //looks for rotor name in list of rotors
    private int search(List<Rotor> selections, String something){        
        for(int i = selections.size() - 1; i >= 0 ; i--){
            if(selections.get(i).getName().equalsIgnoreCase(something)){
               return i;
            }
        }  
        return -1;
    }
    //steps the rotor to the next position
   
    public void stepOffset(){
        
        if(config.get(2).getCurrentPostion() == config.get(2).getStepKey() || config.get(2).getCurrentPostion() == config.get(2).getStepKeySecondary()){
                config.get(1).stepOffset(); 
            }
        else if(config.get(1).getCurrentPostion() == config.get(1).getStepKey() || config.get(1).getCurrentPostion() == config.get(1).getStepKeySecondary()){
                config.get(0).stepOffset();
                config.get(1).stepOffset();
            }
        
        config.get(2).stepOffset();
    }
    
    public String encrypt(String unencrypted){
        char[] tokens = unencrypted.toUpperCase().replaceAll("[^a-zA-Z]", "").toCharArray();
        char[] encrypting = new char[tokens.length];
        String encrypted;
        for(int i = 0; i < tokens.length; i++){
            encrypting[i] = encryptChar(tokens[i]);
        }
        encrypted = new String(encrypting);
        return encrypted;
    }
    
    //seems to work
    public char encryptChar(char character){
        //test seq AAAAA > BDZGO
        //A-(D-D-F)-S-(S-E-B)   
        //A-(F-K-N)-K-(B-J-D)
        //A-(H-S-S)-F-(D-C-Z)
        //A-(J-I-V)-W-(N-T-G)
        //A-(L-R-U)-C-(Y-V-O)
        char encrypted;
        int charIndex = character - 'A';
        //changing entry point?
        
        stepOffset();
        
        //plugboard entry
        encrypted = plugboard[(charIndex + 26)% 26];
        charIndex = encrypted - (int)'A';
        
        for(int i = 2; i > -1; i--){
            encrypted = config.get(i).getSequence()[(charIndex + 26)% 26];
            charIndex = encrypted - (int)'A' - config.get(i).getCurrentPostion();
            //System.out.println("Debug: " + encrypted);
//            System.out.println("Debug: " + encrypted + " Rotor: " + i + " Current position: " + config.get(i).getCurrentPostion());
        }
//        <editor-fold>
        if(charIndex + reflector.getCurrentPostion() > 25){
            charIndex = charIndex - 25;
            encrypted = reflector.getSequence()[charIndex + reflector.getCurrentPostion()];
        }else{
            encrypted = reflector.getSequence()[(charIndex + reflector.getCurrentPostion() + 26) % 26];
        }
        charIndex = (encrypted - (int)'A' - reflector.getCurrentPostion() +26) %26;
//        System.out.println("Pass Reflector:" + encrypted);
//       </editor-fold> 

        for(int i = 0; i < 3; i++){
            //System.out.println(charIndex + config.get(i).getCurrentPostion());
            int temp = 0;
                    temp = (charIndex + 26) % 26;
                    encrypted = config.get(i).getInvertedSequence()[temp];
                    charIndex = encrypted - (int)'A' - config.get(i).getCurrentPostion();                    
                    //System.out.println("Debug: " + encrypted + " Rotor: " + i + " Current position: " + config.get(i).getCurrentPostion());
//                    System.out.println("Debug: " + encrypted);
            }
        

        char[] ref_ETW = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
        
        encrypted = ref_ETW[(charIndex + 78) % 26];
        charIndex = encrypted - (int)'A';
        
        //plugboard exit
        encrypted = plugboard[(charIndex + 26)% 26];

        return encrypted;
    }
    
    
}
