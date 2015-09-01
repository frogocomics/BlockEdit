package org.blockedit.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Contains utilities to test the user's internet status.
 *
 * @deprecated Method {@link InternetUtils#testInternet()} has been moved to {@link UserInformation#hasInternet()}
 * @author Jeff Chen
 */
@Deprecated
public class InternetUtils {

    /**
     * Test if the user has internet. Returns <code>true</code> if there is internet.
     *
     * @deprecated Replaced by newer method: {@link UserInformation#hasInternet()}
     * @return If the user has internet or not
     */
    @Deprecated
    public static boolean testInternet() {
        try {
            URL url = new URL("http://www.google.ca");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            return connection.getResponseCode() == 200;
        } catch (IOException e) {
            return false;
        }
    }
}
