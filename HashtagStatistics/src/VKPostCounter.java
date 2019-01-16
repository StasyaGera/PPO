import com.google.gson.JsonParser;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

class VKPostCounter implements PostCounter {
    private final String FEED_SEARCH = "https://api.vk.com/method/newsfeed.search?";
    private final String ACCESS_TOKEN_FILE = "/home/penguinni/ITMO/PPO/HashtagStatistics/resources/VKAuthToken";
    private final String API_VERSION = "5.58";

    private String accessToken = null;
    private String baseRequest = null;
    private String hashtag = null;

    VKPostCounter() {
        try (BufferedReader br = new BufferedReader(new FileReader(new File(ACCESS_TOKEN_FILE)))) {
            accessToken = br.readLine();
        } catch (IOException e) {
            System.out.println("Error while reading access token: " + e.getMessage());
        }
    }

    private void setHashtag(String newHashtag) {
        baseRequest = FEED_SEARCH + "q=%23" + newHashtag + "&access_token=" + accessToken + "&v=" + API_VERSION;
    }

    private int parseResult(String response) {
        return new JsonParser().parse(response).getAsJsonObject()
                .getAsJsonObject("response")
                .get("total_count")
                .getAsInt();
    }

    @Override
    public int count(String hashtag, long start_time, long end_time) {
        if (this.hashtag == null || !this.hashtag.equals(hashtag)) {
            setHashtag(hashtag);
        }

        String currentRequest = baseRequest + "&start_time=" + start_time + "&end_time=" + end_time;
        try (BufferedReader in = new BufferedReader(new InputStreamReader(new URL(currentRequest).openStream()))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = in.readLine()) != null) {
                response.append(responseLine);
                response.append("\n");
            }
            return parseResult(response.toString());
        } catch (MalformedURLException e) {
            System.out.println("MalformedURLException received: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Wrong data from server: " + e.getMessage());
        }
        return -1;
    }
}
