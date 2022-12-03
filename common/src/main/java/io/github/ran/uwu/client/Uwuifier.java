package io.github.ran.uwu.client;

import java.util.Random;

/**
 * This class either contains the best code or the worst code ever written
 * @author Ran
 */
public class Uwuifier {
    public static String signedUwu(String stringToUwuify) {
        if (stringToUwuify.equals(UwUMod.prevMessage)) return UwUMod.prevUwuifiedMessage;
        if (stringToUwuify.equals(UwUMod.prevUwuifiedMessage)) return UwUMod.prevUwuifiedMessage; // Making sure that same uwuification occurs for the same message (used when signing the message as it'll invalidate the signature if the message is different)
        return UwUMod.prevUwuifiedMessage = (UwUMod.prevMessage = stringToUwuify).toLowerCase().replaceAll("r|l","w").replaceAll("n([aeiou])", "ny$1").replaceAll("ove", "uve").replaceAll("uck", "uwq").replaceFirst("i", "i-i").replaceFirst("(?s)(.*)" + "i-i-i", "$1" + "i-i") + ((new Random().nextInt(10)) <= 2 ? " >_<" : ""); // Wanted to put >~< but it doesn't look good in minecraft font rendering
    }

    public static String uwu(String stringToUwuify) {
        if (stringToUwuify.equals(UwUMod.prevUwuifiedMessage)) stringToUwuify = UwUMod.prevMessage;
        if (stringToUwuify.endsWith(" >_<")) stringToUwuify = stringToUwuify.substring(0, stringToUwuify.length() - 4); // Make sure messages aren't spammed with >_<
        return UwUMod.prevUwuifiedMessage = (UwUMod.prevMessage = stringToUwuify).toLowerCase().replaceAll("r|l","w").replaceAll("n([aeiou])", "ny$1").replaceAll("ove", "uve").replaceAll("uck", "uwq").replaceFirst("i", "i-i").replaceFirst("(?s)(.*)" + "i-i-i", "$1" + "i-i") + ((new Random().nextInt(10)) <= 2 ? " >_<" : ""); // Wanted to put >~< but it doesn't look good in minecraft font rendering
    }

    public static String uwuWithoutCuteFace(String stringToUwuify) {
        if (stringToUwuify.equals(UwUMod.prevUwuifiedMessage)) return stringToUwuify;
        return UwUMod.prevUwuifiedMessage = (UwUMod.prevMessage = stringToUwuify).toLowerCase().replaceAll("r|l","w").replaceAll("n([aeiou])", "ny$1").replaceAll("ove", "uve").replaceAll("uck", "uwq").replaceFirst("i", "i-i").replaceFirst("(?s)(.*)" + "i-i-i", "$1" + "i-i");
    }

    @SuppressWarnings("unused")
    public static String uwuify(String stringToUwuify) {
        return stringToUwuify.toLowerCase().replaceAll("r|l","w").replaceAll("n([aeiou])", "ny$1").replaceAll("ove", "uve").replaceAll("uck", "uwq").replaceFirst("i", "i-i").replaceFirst("(?s)(.*)" + "i-i-i", "$1" + "i-i") + ((new java.util.Random().nextInt(10)) <= 2 ? " >~<" : "");
    }
}
