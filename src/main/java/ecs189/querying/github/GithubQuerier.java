package ecs189.querying.github;

import ecs189.querying.Util;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GithubQuerier {

    private static final String BASE_URL = "https://api.github.com/users/";

    public static String eventsAsHTML(String user) throws IOException, ParseException {
        List<JSONObject> response = getEvents(user);
        StringBuilder sb = new StringBuilder();
        sb.append("<div>");
        int i = 0;
        for (JSONObject event : response) {
            // Get pushed repo name
            JSONObject repo = event.getJSONObject("repo");
            String name = repo.getString("name");
            // Get created_at date, and format it in a more pleasant style
            String creationDate = event.getString("created_at");
            SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'");
            SimpleDateFormat outFormat = new SimpleDateFormat("dd MMM, yyyy");
            Date date = inFormat.parse(creationDate);
            String formatted = outFormat.format(date);

            // Add type of event as header
            sb.append("<h3 class=\"type\">");
            sb.append((i+1) + ". Pushed to " + name);
            sb.append("</h3>");
            // Add formatted date
            sb.append(" on ");
            sb.append("<i>" + formatted + "</i>");
            sb.append("<br />");

            // Get SHA-1 and Commit message
            JSONObject payload = event.getJSONObject("payload");
            JSONArray commits = payload.getJSONArray("commits");

            // Construct table
            sb.append("<table class=\"table table-hover\">");
            sb.append("<thead class=\"thead-inverse\">");
            sb.append("<tr> <th class=\"text-left\">SHA-1</th> <th class=\"text-left\">Commit Message</th> </tr>");
            sb.append("</thead><tbody>");

            // Loop for each commit
            for (int j = 0; j < commits.length(); j++) {
                JSONObject fields = commits.getJSONObject(j);
                String hashVal = fields.getString("sha");
                String messageStr = fields.getString("message");

                sb.append("<tr>");
                sb.append("<td>" + hashVal + "</td>");
                sb.append("<td>" + messageStr + "</td>");
                sb.append("</tr>");
            }
            sb.append("</tbody></table>");

            // Add collapsible JSON textbox (don't worry about this for the homework; it's just a nice CSS thing I like)
            sb.append("<a data-toggle=\"collapse\" href=\"#event-" + i + "\">JSON</a>");
            sb.append("<div id=event-" + i + " class=\"collapse\" style=\"height: auto;\"> <pre>");
            sb.append(event.toString());
            sb.append("</pre> </div>");
            i++;

            sb.append("<br />");
            sb.append("<br />");

        }
        sb.append("</div>");
        return sb.toString();
    }

    private static List<JSONObject> getEvents(String user) throws IOException {
        // j limits 10 latest push events only
        int j = 0;
        int page = 2;

        List<JSONObject> eventList = new ArrayList<JSONObject>();

        // https://api.github.com/users/khgkim/events?page=1&per_page=100
        String url = BASE_URL + user + "/events?page=1&per_page=100";
        System.out.println(url);
        JSONObject json = Util.queryAPI(new URL(url));
        System.out.println(json);
        JSONArray events = json.getJSONArray("root");

        // Pagination Loop
        while (events.length() != 0 && j < 10) {
            for (int i = 0; i < events.length() && j < 10; i++) {
                JSONObject pushEvents = events.getJSONObject(i);
                String type = pushEvents.getString("type");

                if (type.equals("PushEvent")) {
                    eventList.add(events.getJSONObject(i));
                    j++;
                }
            }

            String nextUrl = BASE_URL + user + "/events?page=" + page + "&per_page=100";
            json = Util.queryAPI(new URL(nextUrl));
            events = json.getJSONArray("root");
            page++;
        }

        return eventList;
    }
}
