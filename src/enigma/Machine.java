/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package enigma;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
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
    List<String> plugboard;
    
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
    //fix var names
    //clean up
    //check for doubles [X]
    // run three/four times depending on machine (var name max_rotors) [X]
    //set reflectors
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
    public void step(){
        
        if(config.get(2).getCurrentPostion() == config.get(2).getStepKey() || config.get(2).getCurrentPostion() == config.get(2).getStepKeySecondary()){
                config.get(1).step(); 
            }
        if(config.get(1).getCurrentPostion() == config.get(1).getStepKey() || config.get(1).getCurrentPostion() == config.get(1).getStepKeySecondary()){
                config.get(0).step();
                config.get(1).step();
            }
        
        config.get(2).step();
    }
    
    public void stepOffset(){
        
        if(config.get(2).getCurrentPostion() == config.get(2).getStepKey() || config.get(2).getCurrentPostion() == config.get(2).getStepKeySecondary()){
                config.get(1).stepOffset(); 
            }
        if(config.get(1).getCurrentPostion() == config.get(1).getStepKey() || config.get(1).getCurrentPostion() == config.get(1).getStepKeySecondary()){
                config.get(0).stepOffset();
                config.get(1).stepOffset();
            }
        
        config.get(2).stepOffset();
    }
    
    public void encrypt(String unencrypted){
        char[] tokens = unencrypted.toUpperCase().replaceAll("[^a-zA-Z]", "").toCharArray();
        for(int i = 0; i < tokens.length; i++){
            
        }
    }
    
    public char passThrough(char unencrypted){
        int index = config.get(2).getCurrentPostion();
        char encrypted;
        int uncharIndex = unencrypted - 65;
        
        
        if(uncharIndex + index > 25){
            uncharIndex = uncharIndex - 26;
            encrypted = config.get(2).getSequence()[uncharIndex];
        }else{
            encrypted = config.get(2).getSequence()[uncharIndex + index];
        }
        
        return encrypted;
    }
    //temp
    
    public char encrypt_temp(char character){
        //test seq AAAAA > BDZGO
        //basic logic I II III ETW  A>E>S>G>C>D>F [plugboard swap if F is linked]
        //           {0)(0)(0)      0>0>4>19>16>4 [plugboard swap]
        //A-(D-D-F)-S-(S-E-B)   
        //A-(F-K-N)-K-(B-J-D)
        //A-(H-S-S)-F-(D-C-Z)
        //A-(J-I-V)-W-(N-T-G)
        //A-(L-R-U)-C-(Y-V-O)
        char encrypted = character;
        int charIndex = character - 'A';
        //changing entry point?
        for(int i = 2; i > -1; i--){
            if((charIndex + config.get(i).getCurrentPostion()) > 25){
                charIndex = charIndex - 25;
                encrypted = config.get(i).getSequence()[charIndex + config.get(i).getCurrentPostion()];
            }else{
                encrypted = config.get(i).getSequence()[charIndex + config.get(i).getCurrentPostion()];
            }
            charIndex = encrypted - (int)'A' - config.get(i).getCurrentPostion();
            System.out.println("Debug: " + encrypted);
        }

        if(charIndex + reflector.getCurrentPostion() > 25){
            charIndex = charIndex - 25;
            encrypted = reflector.getSequence()[charIndex + reflector.getCurrentPostion()];
        }else{
            encrypted = reflector.getSequence()[charIndex + reflector.getCurrentPostion()];
        }
        charIndex = encrypted - (int)'A' - reflector.getCurrentPostion();
        System.out.println("Pass Reflector:" + encrypted);
        

        for(int i = 0; i < 3; i++){
            //System.out.println(charIndex + config.get(i).getCurrentPostion());
            if(charIndex + config.get(i).getCurrentPostion() > 25){
                charIndex = charIndex - 25;
                encrypted = config.get(i).getInvertedSequence()[charIndex + config.get(i).getCurrentPostion()];
            }else if(charIndex - config.get(i).getCurrentPostion() < 0){
                charIndex = charIndex + 25;
                encrypted = config.get(i).getInvertedSequence()[charIndex - config.get(i).getCurrentPostion()];
            }
            else{
                encrypted = config.get(i).getInvertedSequence()[charIndex - config.get(i).getCurrentPostion()];
            }
            charIndex = encrypted - (int)'A' - config.get(i).getCurrentPostion();
            System.out.println("Debug: " + encrypted);
        }
       
        
        
        return encrypted;
    }
    
    public char encrypt_test(char character){
        //test seq AAAAA > BDZGO
        //A-(D-D-F)-S-(S-E-B)   
        //A-(F-K-N)-K-(B-J-D)
        //A-(H-S-S)-F-(D-C-Z)
        //A-(J-I-V)-W-(N-T-G)
        //A-(L-R-U)-C-(Y-V-O)
        char encrypted = character;
        int charIndex = character - 'A';
        //changing entry point?
        for(int i = 2; i > -1; i--){
            encrypted = config.get(i).getSequence()[(charIndex + 26)% 26];
            charIndex = encrypted - (int)'A' - config.get(i).getCurrentPostion();
            System.out.println("Debug: " + encrypted);
        }
//        <editor-fold>
        if(charIndex + reflector.getCurrentPostion() > 25){
            charIndex = charIndex - 25;
            encrypted = reflector.getSequence()[charIndex + reflector.getCurrentPostion()];
        }else{
            encrypted = reflector.getSequence()[charIndex + reflector.getCurrentPostion()];
        }
        charIndex = encrypted - (int)'A' - reflector.getCurrentPostion();
        System.out.println("Pass Reflector:" + encrypted);
//       </editor-fold> 

        for(int i = 0; i < 3; i++){
            //System.out.println(charIndex + config.get(i).getCurrentPostion());
            int temp = 0;
                try{
                    //temp = (charIndex - config.get(i).getCurrentPostion() - config.get(i).getCurrentPostion() + 78) % 26;
                    temp = (charIndex + config.get(i).getCurrentPostion() + 26) % 26;
                    encrypted = config.get(i).getInvertedSequence()[temp];
                    charIndex = encrypted - (int)'A';
                    
                    System.out.println("Debug: " + encrypted + " Rotor: " + i + " Current position: " + config.get(i).getCurrentPostion());
                }catch(java.lang.ArrayIndexOutOfBoundsException e){
                    System.out.println();
                    displayRotorConfig();
                    System.out.println("This broke: " + temp);
                    System.out.println();
                }
            }
        
        //This works somehow, maybe the rotors are spinning the wrong way
        //Tried a fix needs more work reverded rotors
        char[] ref_ETW = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
        encrypted = ref_ETW[(charIndex + config.get(2).getCurrentPostion() + 78) % 26];
        
        return encrypted;
    }
    
    
}
