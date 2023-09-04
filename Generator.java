import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Generator {
    final static int LOWERCASE_MIN = 97; // ASCII value for 'a'
    final static int LOWERCASE_MAX = 122; // ASCII value for 'z'
    final static int UPPERCASE_MIN = 65; // ASCII value for 'A'
    final static int UPPERCASE_MAX = 90; // ASCII value for 'Z'
    final static int DIGIT_MIN = 48; // ASCII value for '0'
    final static int DIGIT_MAX = 57; // ASCII value for '9'
    final static String SYMBOLS = "!@#$%^&*()-_=+[]{}|;:'\",.<>?";
    

    public static String generatePassword(){
        char lowercase = (char) (LOWERCASE_MIN + Math.random() * (LOWERCASE_MAX - LOWERCASE_MIN + 1));
        char uppercase = (char) (UPPERCASE_MIN + Math.random() * (UPPERCASE_MAX - UPPERCASE_MIN + 1));
        char digit = (char) (DIGIT_MIN + Math.random() * (DIGIT_MAX - DIGIT_MIN + 1));
        char symbol = SYMBOLS.charAt((int) (Math.random() * SYMBOLS.length()));

        String tempPassword = String.valueOf(lowercase) + String.valueOf(uppercase) + String.valueOf(digit) + String.valueOf(symbol);
    

        int desiredLength = 12; // Change this to your desired password length
        int remainingLength = desiredLength - tempPassword.length();

        StringBuilder passwordBuilder = new StringBuilder(tempPassword);

        for (int i = 0; i < remainingLength; i++) {
            int randomCategory = (int) (Math.random() * 4); // 0 for lowercase, 1 for uppercase, 2 for digit, 3 for symbol

        switch (randomCategory) {
            case 0:
                passwordBuilder.append((char) (LOWERCASE_MIN + Math.random() * (LOWERCASE_MAX - LOWERCASE_MIN + 1)));
                break;
            case 1:
                passwordBuilder.append((char) (UPPERCASE_MIN + Math.random() * (UPPERCASE_MAX - UPPERCASE_MIN + 1)));
                break;
            case 2:
                passwordBuilder.append((char) (DIGIT_MIN + Math.random() * (DIGIT_MAX - DIGIT_MIN + 1)));
                break;
            case 3:
                passwordBuilder.append(SYMBOLS.charAt((int) (Math.random() * SYMBOLS.length())));
                break;
        }
    }

    String randomPassword = passwordBuilder.toString();

  	List<String> characters = Arrays.asList(randomPassword.split(""));
 	Collections.shuffle(characters);
  	String afterShuffle = "";
  	for (String character : characters){
    	afterShuffle += character;
  	}
  	return afterShuffle;
    }
}