package locale;

/*
The Learn Programming Academy
Java SE 11 Developer 1Z0-819 OCP Course - Part 2
Section 16:  Localization
Topic:  Resource Bundles
*/

import java.util.Locale;
import java.util.ResourceBundle;

public class LocaleResources {
    public static void main(String[] args) {

        Locale.setDefault(new Locale("en", "AU"));

        testProperties(Locale.getDefault());
        
        testProperties(Locale.CANADA_FRENCH);
        
        testProperties(Locale.US);
        
        testProperties(Locale.GERMANY);
        testProperties(new Locale("en", "US", "slang")); 
        
        testProperties(Locale.CANADA);
    }

    public static void testProperties(Locale locale) {

        System.out.println("---- Locale Passed: [" + locale
                + "] ---------");
        ResourceBundle localeData = ResourceBundle.getBundle(
                "firstBundle", locale);
        System.out.println("\tResourceBundle class = " +
                localeData.getClass().getName());
        // Get the 'selected' locale
        System.out.println("\tLocale Actually Used:  [" +
                localeData.getLocale() + "]");
        // Get a single valued based on a key
        System.out.println("\tInternationalizing the word yes  = " +
                localeData.getString("yes") + " [" + locale + "]");
        System.out.println("\t---Key/Values in properties file---");
        // Iterate through keys and do something
        localeData.keySet().forEach((s) ->
                System.out.println("\t" + s + " = " +
                        localeData.getObject(s)));

    }
}
