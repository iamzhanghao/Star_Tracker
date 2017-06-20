package com.tinymos.demo.star_tracker.simbad;
/* Interface to the online Staralt program.
 *
 * Copyright (c) 2011 Victor Terron. All rights reserved.
 * Institute of Astrophysics of Andalusia, IAA-CSIC
 *
 * This is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 **********************************************************************/

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Staralt {

    public double longitude;
    public double latitude;
    public int altitude;

    /* Two constructors, the second uses the coordinates and height of
     * the Calar Alto Observatory, where PANIC will be commissioned */
    public Staralt(double longitude, double latitude, int altitude){
        this.longitude = longitude;
        this.latitude = latitude;
        this.altitude = altitude;
    }

    public Staralt(){
        this.longitude = 357.4537;
        this.latitude = 37.23;
        this.altitude = 2168;
    }

    /* Return the URL of the plot generated by Staralt (available online at
     * http://catserver.ing.iac.es/staralt/) which plots the altitude against
     * time for this object and date, seen from an observatory at the given
     * coordinates and altitude. Although it does not have any extension,
     * the plot is a GIF image, */

    public static String
    build_query_url(TargetInformation info, int day, int month, int year,
                    double longitude, double latitude, int altitude) {

        String query = "http://catserver.ing.iac.es/staralt/index.php?" +
                "action=showImage&" +
                "form[mode]=1&" +
                "form[day]=%02d&" + /* Day of the observation (1) */
                "form[month]=%02d&" + /* month (2) */
                "form[year]=%04d&" + /* and year (3) */
                "form[sitecoord]=%.4f+" + /* Longitude (dec, 4), */
                "%.4f+" + /* latitude (dec, 5) */
                "%+d&" + /* and altitude (m, 6) */
                "form[coordlist]=%s+" + /* object name (7) */
                "%.4f+" + /* Right ascension (dec, 8) */
                "%.4f&" + /* Declination (dec, 9) */
                "form[paramdist]=2&" +
                "form[minangle]=10&" +
                "form[format]=gif&" +
                "submit=+Retrieve+";

        /* The object name must be a single word */
        String name = info.name.replaceAll("\\s", "");
        return String.format(query, day, month, year, longitude, latitude,
                             altitude, name, info.ra_deg, info.dec_deg);
      }


    /* Receives the path to a Staralt plot and downloads it to the default
     * temporary-file directory. Returns a File object which encapsulates that
     * to which the plot was saved. Bear in mind that you are responsible for
     * the deletion of the file when it is no longer needed (for example, you
     * could choose to use the deleteOnExit() method to arrange for it to be
     * removed automatically). The plot is saved to a file with the .gif
     * extension, as that is the format being currently used by Staralt */

    public static
    File download(String url) throws IOException{

        /* As seen at: http://stackoverflow.com/a/921400 */
        URL staralt = new URL(url);
        ReadableByteChannel rbc = Channels.newChannel(staralt.openStream());
        File dst = File.createTempFile("staralt_", ".gif");
        FileOutputStream fos = new FileOutputStream(dst);
        fos.getChannel().transferFrom(rbc, 0, 1 << 24);
        fos.close();
        return dst;
    }

    /* Ask Staralt to plot the altitude against time for this object and date,
     * downloading the graph to a temporary GIF file and returning it as a File
     * instance. Note that you are responsible for the deletion of the file. */

    public File plot(TargetInformation info, int day, int month, int year) throws IOException {
        String url = Staralt.build_query_url(info, day, month, year,
                                             this.longitude, this.latitude,
                                             this.altitude);
        return Staralt.download(url);

    }

    /* If the date is not given, default to today */
    public File plot(TargetInformation info) throws IOException {

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        String currDate = dateFormat.format(cal.getTime());
        String[] tokens = currDate.split("/");
        int day = Integer.parseInt(tokens[0]);
        int month = Integer.parseInt(tokens[1]);
        int year = Integer.parseInt(tokens[2]);
        return this.plot(info, day, month, year);
    }

    /* An illustration of how Staralt might be used in real code */
    public static void main(String[] args) throws IOException {

        try {
            TargetResolver resolver = new TargetResolver();
            TargetInformation info = resolver.submit("M101");
            System.out.println(info);

            Staralt staralt = new Staralt();
            File plot = staralt.plot(info);
            System.out.println();
            System.out.println("Staralt plot saved to: " + plot.getAbsolutePath());
            /* Do stuff with the plot */
            plot.deleteOnExit(); /* don't clutter the temporary directory */


        } catch (TargetNotFoundException e) {
            System.out.println("not found!");
        } catch (SIMBADQueryException e) {
            System.out.println("connection failed");
        }

        System.exit(0);

    }
}