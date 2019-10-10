package me.mralecroyt.gcaptcha;

import java.util.*;
import java.io.*;

public class GetCaptcha
{
    public static String getSaltString() {
        final String ss = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        final StringBuilder s = new StringBuilder();
        final Random rnd = new Random();
        while (s.length() < 7) {
            final int index = (int)(rnd.nextFloat() * ss.length());
            s.append(ss.charAt(index));
        }
        final String string = s.toString();
        return string;
    }
}
