package locale;

/*
The Learn Programming Academy
Java SE 11 Developer 1Z0-819 OCP Course - Part 2
Section 16:  Localization
Topic:  Creating instance of a Locale class
*/

import java.util.Locale;

public class LocaleObjects {
    public static void main(String[] args) {

        // Using Locale constructors
        System.out.println("Locale(\"fr\") = " + new Locale("fr"));
        System.out.println("Locale(\"FR\") = " + new Locale("FR"));
        System.out.println("Locale(\"fr\",\"CA\") = " + new Locale("fr", "CA"));
        System.out.println("Locale(\"\", \"CA\") = " + new Locale("", "CA"));

        // Using Locale Constants
        Locale l = Locale.CANADA_FRENCH;
        System.out.println("Locale.CANADA_FRENCH = " + l);
        l = Locale.US;
        System.out.println("Locale.US = " + l);

        // Create Locale using Locale.Builder(), you can create a Locale
        // with an empty string as language.
        l = new Locale.Builder().setRegion("CA").build();
        System.out.println("Builder().setRegion(\"CA\") = " + l);

        // Order does not matter when using builder.
        l = new Locale.Builder()
                .setRegion("CA")
                .setLanguage("fr")
                .build();
        System.out.println("Builder().setRegion(\"CA\")." +
                "setLanguage(\"fr\") = " + l);

        // You can do this, but why would you?
        l = new Locale.Builder().setRegion("CA")
                .setLanguage("fr")
                .setRegion("FR")
                .build();
        System.out.println("Builder().setRegion(\"CA\")." +
                "setLanguage(\"fr\").setRegion(\"FR\") = " + l);

        // Returns a locale for the specified IETF BCP 47 language
        // tag string.  Introduced with JDK 7
        System.out.println("Locale.forLanguageTag(\"fr-CA\") =" +
                Locale.forLanguageTag("fr-CA"));

        System.out.println("Locale.forLanguageTag(\"es-SP\") =" +
                Locale.forLanguageTag("es-SP"));
        
//        l = new Locale();
        
        // There are no set methods on Locale
//        l.setRegion("CA");
//        l.setLanguage("en");
        
//        l = new Locale("fr",null);
        
        // IETF BCP 47 language are delimited with a dash, not underscore
        System.out.println("Locale.forLanguageTag(\"en_US\") =" +
                Locale.forLanguageTag("en_US"));
        
        l = new Locale("en", "U&S");
        System.out.println(l);

        l = Locale.forLanguageTag("en-U&S");
        System.out.println(l);

        l = new Locale.Builder().setRegion("U&S")
                .setLanguage("en")
                .build();
        System.out.println(l);

    }
}
