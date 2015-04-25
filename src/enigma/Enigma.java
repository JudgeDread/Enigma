package enigma;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Judge Dread
 */
public class Enigma {

    //TODO
    //add turnover postions
    //add plugboard
    //basic logic I II III ETW  A>E>S>G>C>D>F [plugboard swap if F is linked]
    //           {0)(0)(0)      0>0>4>19>16>4 [plugboard swap]
    
    public static void main(String[] args) {
        

        //Rotors Enigma 1
        char[] rotor_I =   {'E','K','M','F','L','G','D','Q','V','Z','N','T','O','W','Y','H','X','U','S','P','A','I','B','R','C','J'};
        char[] rotor_II =  {'A','J','D','K','S','I','R','U','X','B','L','H','W','T','M','C','Q','G','Z','N','P','Y','F','V','O','E'};
        char[] rotor_III = {'B','D','F','H','J','L','C','P','R','T','X','V','Z','N','Y','E','I','W','G','A','K','M','U','S','Q','O'};
        //M3 Army
        char[] rotor_IV = {'E','S','O','V','P','Z','J','A','Y','Q','U','I','R','H','X','L','N','F','T','G','K','D','C','M','W','B'};
        char[] rotor_V = {'V','Z','B','R','G','I','T','Y','U','P','S','D','N','H','L','X','A','W','M','J','Q','O','F','E','C','K'};
        //M3 & M4 Naval
        char[] rotor_VI = {'J','P','G','V','O','U','M','F','Y','Q','B','E','N','H','Z','R','D','K','A','S','X','L','I','C','T','W'};
        char[] rotor_VII = {'N','Z','J','H','G','R','C','X','M','Y','S','W','B','O','U','F','A','I','V','L','P','E','K','Q','D','T'};
        char[] rotor_VIII = {'F','K','Q','H','T','L','X','O','C','B','J','S','P','D','Z','R','A','M','E','W','N','I','U','Y','G','V'};
        
        
        //char[] ref_ETW = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
        //Reflector Enigma 1 and M3(-A)
        char[] ref_A = {'E','J','M','Z','A','L','Y','X','V','B','W','F','C','R','Q','U','O','N','T','S','P','I','K','H','G','D'};
        char[] ref_B = {'Y','R','U','H','Q','S','L','D','P','X','N','G','O','K','M','I','E','B','F','Z','C','W','V','J','A','T'};
        char[] ref_C = {'F','V','P','J','I','A','O','Y','E','D','R','Z','X','W','G','C','T','K','U','Q','S','B','N','M','H','L'};
        //M4 R1 (M3 + Thin)
        char[] ref_Bthin = {'E','N','K','Q','A','U','Y','W','J','I','C','O','P','B','L','M','D','X','Z','V','F','T','H','R','G','S'};
        char[] ref_Cthin = {'R','D','O','B','J','N','T','K','V','E','H','M','L','F','C','W','Z','A','X','G','Y','I','P','S','U','Q'};
        //M4 R2
        char[] ref_Beta = {'L','E','Y','J','V','C','N','I','X','W','P','B','Q','M','D','R','T','A','K','Z','G','F','U','H','O','S'};
        char[] ref_Gamma = {'F','S','O','K','A','N','U','E','R','H','M','B','T','I','Y','C','W','L','Q','P','Z','X','V','G','J','D'};
        
        //Declare rotors
        Rotor r_one = new Rotor("I", rotor_I, 'Q', 0);
        Rotor r_two = new Rotor("II", rotor_II, 'E', 0);
        Rotor r_three = new Rotor("III", rotor_III, 'V', 0);
        Rotor r_four = new Rotor("IV", rotor_IV, 'J', 0);
        Rotor r_five = new Rotor("V", rotor_V, 'Z', 0);
        Rotor r_six = new Rotor("VI", rotor_VI, 'Z', 'M', 0);
        Rotor r_seven = new Rotor("VII", rotor_VII, 'Z', 'M', 0);
        Rotor r_eight = new Rotor("VIII", rotor_VIII, 'Z', 'M', 0);
        
        //Declare reflectors
        Rotor reflector_A = new Rotor("A", ref_A, 0);
        Rotor reflector_B = new Rotor("B", ref_B, 0);
        Rotor reflector_C = new Rotor("C", ref_C, 0);
        Rotor reflector_Bthin = new Rotor("Bthin", ref_Bthin, 0);
        Rotor reflector_Cthin = new Rotor("Cthin", ref_Cthin, 0);
        Rotor reflector_Beta = new Rotor("Beta", ref_Beta, 0);
        Rotor reflector_Gamma = new Rotor("Gamma", ref_Gamma, 0);
        
        
        //needs plugboard
        //needs to encrypt
        //
        List<Rotor> enigmaOneRotors = Arrays.asList(r_one, r_two, r_three, r_four, r_five);
        List<Rotor> enigmaOneReflectors = Arrays.asList(reflector_A, reflector_B, reflector_C);
        Machine enigmaOne = new Machine("Enigma 1", enigmaOneRotors, enigmaOneReflectors , 3);
        //select rotors
//        enigmaOne.selectRotors();
        enigmaOne.defaultRotors();
        //select reflector
//        enigmaOne.selectReflectors();
        enigmaOne.defaultReflector();
        //set ring settings (AAA) AAA
//        enigmaOne.configRotorRing();
        enigmaOne.defaultRotorRing();
                //set ground setting AAA (AAA)
//        enigmaOne.configRotorGround();
        enigmaOne.defaultRotorGround();
        //breaks at 3478 double step        
        for(int i = 0; i < 1; i++){
            enigmaOne.stepOffset();
            System.out.println(enigmaOne.encrypt_test('A'));
        }
//Decrypting test
////            enigmaOne.stepOffset();
////            System.out.println(enigmaOne.encrypt_test('B')); //1
////            enigmaOne.stepOffset();
////            System.out.println(enigmaOne.encrypt_test('D'));
////            enigmaOne.stepOffset();
////            System.out.println(enigmaOne.encrypt_test('Z'));
////            enigmaOne.stepOffset();
////            System.out.println(enigmaOne.encrypt_test('G'));
////            enigmaOne.stepOffset();
////            System.out.println(enigmaOne.encrypt_test('O'));
////            enigmaOne.stepOffset();
////            System.out.println(enigmaOne.encrypt_test('W'));
////            enigmaOne.stepOffset();
////            System.out.println(enigmaOne.encrypt_test('C'));
////            enigmaOne.stepOffset();
////            System.out.println(enigmaOne.encrypt_test('X'));
////            enigmaOne.stepOffset();
////            System.out.println(enigmaOne.encrypt_test('L'));
////            enigmaOne.stepOffset();
////            System.out.println(enigmaOne.encrypt_test('T')); //10
////            enigmaOne.stepOffset();
////            System.out.println(enigmaOne.encrypt_test('K'));
////            enigmaOne.stepOffset();
////            System.out.println(enigmaOne.encrypt_test('S'));
////            enigmaOne.stepOffset();
////            System.out.println(enigmaOne.encrypt_test('B'));
////            enigmaOne.stepOffset();
////            System.out.println(enigmaOne.encrypt_test('T'));
////            enigmaOne.stepOffset();
////            System.out.println(enigmaOne.encrypt_test('M')); //15
////            enigmaOne.stepOffset();
////            System.out.println(enigmaOne.encrypt_test('C'));
////            enigmaOne.stepOffset();
////            System.out.println(enigmaOne.encrypt_test('D'));
////            enigmaOne.stepOffset();
////            System.out.println(enigmaOne.encrypt_test('L'));
////            enigmaOne.stepOffset();
////            System.out.println(enigmaOne.encrypt_test('P'));
////            enigmaOne.stepOffset();
////            System.out.println(enigmaOne.encrypt_test('B')); //20
////            enigmaOne.stepOffset();
////            System.out.println(enigmaOne.encrypt_test('M')); //fails
////            enigmaOne.stepOffset();
////            System.out.println(enigmaOne.encrypt_test('Y'));
////            enigmaOne.stepOffset();
////            System.out.println(enigmaOne.encrypt_test('K'));
////            enigmaOne.stepOffset();
////            System.out.println(enigmaOne.encrypt_test('C'));
////            enigmaOne.stepOffset();
////            System.out.println(enigmaOne.encrypt_test('G')); //25
        
        
        }
}
