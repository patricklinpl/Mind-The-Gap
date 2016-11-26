package ca.ubc.cs.cpsc210.mindthegap.TfL;

/*
 * Copyright 2015-2016 Department of Computer Science UBC
 */

import ca.ubc.cs.cpsc210.mindthegap.model.Line;
import ca.ubc.cs.cpsc210.mindthegap.model.Station;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

/**
 * Wrapper for TfL Arrival Data Provider
 */
public class TfLHttpArrivalDataProvider extends AbstractHttpDataProvider {
    private static final String ARRIVALS_API_BASE = "https://api.tfl.gov.uk";              // for TfL data
    //private static final String ARRIVALS_API_BASE = "http://kunghit.ugrad.cs.ubc.ca:6060";   // for simulated data (3pm to midnight)
    private Station stn;

    public TfLHttpArrivalDataProvider(Station stn) {
        super();
        this.stn = stn;
    }

    // This method produces the URL used to query the TfL web service for arrivals at a given station.
    // structure of this URL, use the Transport for London Unified API website
    // Experiment with different stations and lines and note the form of the URL that is generated for you

    // The getURL method must build a string to represent the URL that requests arrivals at the station passed to the constructor of the TfLHttpArrivalDataProvider.
    // It must then return a URL object constructed from that string.
    @Override
    protected URL getURL() throws MalformedURLException {
        Iterator itrLn = stn.getLines().iterator();

        String nearest = null;
        while (itrLn.hasNext()) {
            Line currentStation = (Line) itrLn.next();

            if (nearest == null) {
                nearest = currentStation.getName().toLowerCase();

                if (itrLn.hasNext()) {
                    nearest = nearest + ",";
                }
            }

            else {
                nearest = nearest + currentStation.getName().toLowerCase();

                if (itrLn.hasNext()) {
                    nearest = nearest + ",";
                }
            }
        }

        String url = "/Line/" + nearest + "/Arrivals?stopPointId=" + stn.getID() + "&app_id=&app_key=";
        return new URL(ARRIVALS_API_BASE + url);
    }
}
