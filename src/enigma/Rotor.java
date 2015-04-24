/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package enigma;

/**
 *
 * @author Judge Dread
 */
public class Rotor {
    
    String name;
    char[] sequence;
    char[] invertedSequence;
    int step_key;
    int step_key_secondary;
    int ring_setting;
    int current_position;
    
    public Rotor(String ex_name, char[] ex_sequence, char ex_step_key, int config){
        name = ex_name;
        sequence = ex_sequence;
        step_key = ex_step_key - 65;
        step_key_secondary = -1;
        current_position = config;
    }
    //VI VII VIII
    public Rotor(String ex_name, char[] ex_sequence, char ex_step_key, char ex_step_key_secondary, int config){
        name = ex_name;
        sequence = ex_sequence;
        step_key = ex_step_key - 65;
        step_key_secondary = ex_step_key_secondary - 65;
        current_position = config;
    }
    //Reflectors
    public Rotor(String ex_name, char[] ex_sequence, int config){
        name = ex_name;
        sequence = ex_sequence;
        current_position = config;
    }
    
    public String getName(){
        return name;
    }
    
    public char[] getSequence(){
        return sequence;
    }
    
    public char[] getInvertedSequence(){
        return invertedSequence;
    }
    
    public int getStepKey(){
        return step_key;
    }
    
    public int getStepKeySecondary(){
        return step_key_secondary;
    }
    
    public int getRingSetting(){
        return ring_setting;
    }
    
    public void setRingSetting(int index){
        ring_setting = index;
    }
    
    public int getCurrentPostion(){
        return current_position;
    }
    
    public void setCurrentPostion(int index){
        //current_position = index;
        for(int i = 0; i < index; i++){
            stepOffset();
        }
    }
    
    public void step(){
        if(current_position > 24){
            current_position = 0;
        }else{
            current_position++;
        }
    }
    
    public void stepOffset(){
        if(current_position > 24){
            current_position = 0;
        }else{
            current_position++;
        }
        manOffset();
    }
    
    //out of bounds need to work out
    public void offsetRing(){
        char[] new_sequence = new char[sequence.length];
        for(int i = 0; i < sequence.length; i++){
            if( i +ring_setting < sequence.length){
                new_sequence[i] = sequence[i+ring_setting];
            }else{
                new_sequence[i] = sequence[i+(ring_setting - sequence.length)];
            }
        }
        sequence = new_sequence;
    }
    
    public void offsetRingInverted(){
        char[] new_sequence = new char[invertedSequence.length];
        for(int i = 0; i < invertedSequence.length; i++){
            if( i +ring_setting < invertedSequence.length){
                new_sequence[i] = invertedSequence[i+ring_setting];
            }else{
                new_sequence[i] = invertedSequence[i+(ring_setting - invertedSequence.length)];
            }
        }
        invertedSequence = new_sequence;
    }
    
    public void invert(){
        char[] inverted = new char[sequence.length];
        
        for(int i = 0; i < sequence.length; i++){
            inverted[sequence[i] - 65] = (char) (i + 65);
        }
        invertedSequence = inverted;
    }
    
    public void manOffset(){
        char[] new_sequence = new char[sequence.length];
        
        for(int i = 0; i < sequence.length; i++){
            if( i + 1 < sequence.length){
                new_sequence[i] = sequence[i+1];
            }else{
                new_sequence[i] = sequence[i+(1 - sequence.length)];
            }
        }
        sequence = new_sequence;
        
        char[] new_sequence_inv = new char[invertedSequence.length];
        for(int i = 0; i < invertedSequence.length; i++){
            if( i + 1 < invertedSequence.length){
                new_sequence_inv[i] = invertedSequence[i+1];
            }else{
                new_sequence_inv[i] = invertedSequence[i+(1 - invertedSequence.length)];
            }
        }
        
//something different 
////        System.arraycopy(invertedSequence, 0, new_sequence_inv, 1, invertedSequence.length - 1);
////        new_sequence_inv[0] = invertedSequence[invertedSequence.length - 1];
        
        invertedSequence = new_sequence_inv;
    }
    
}
