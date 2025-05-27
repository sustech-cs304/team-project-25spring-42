package sustech.cs304.utils;

/**
 * Utility class for string-related helper methods.
 */
public class StringUtils {

    /**
     * Checks if the provided email string is a valid email address format.
     *
     * @param email the email address string to validate
     * @return true if the email matches the standard email format, false otherwise
     */
    public static boolean checkIfValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }
}
