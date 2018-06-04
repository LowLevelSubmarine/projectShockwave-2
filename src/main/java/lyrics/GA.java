package lyrics;

import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import core.VersionInfo;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import tools.Toolkit;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

public class GA {
    OAuth20Service service;
    String accessToken;

    private static final String GENIUSAPIURL = "https://api.genius.com";
    private static final String GENIUSURL = "https://genius.com";

    private static final String[] SEARCH_BLACKLIST = {"Audio", "Music Videos", "Music Video", "Original", "Official Video",
            "Official Music Video", "Lyric Video", "Lyrics"};

    GA (OAuth20Service service, String accessToken) {
        this.service = service;
        this.accessToken = accessToken;
    }

    private static final String SEARCHSONGSURL = GENIUSAPIURL + "/search?q=";

    public ArrayList<String> searchSongPath(String parameter, boolean enhanceSearchParam) {
        try {
            if (enhanceSearchParam) parameter = enhanceSearchParam(parameter);

            ArrayList<String> songIds = new ArrayList<>();
            parameter = parameter.replace(" ", "%20");
            parameter = parameter.replace("/", "");
            parameter = parameter.replace(".", "");
            OAuthRequest request = new OAuthRequest(Verb.GET, SEARCHSONGSURL + parameter);
            this.service.signRequest(this.accessToken, request);
            Response response = this.service.execute(request);

            JSONObject json = new JSONObject(response.getBody());
            JSONObject jResponse = json.getJSONObject("response");
            JSONArray jHits = jResponse.getJSONArray("hits");
            for (int i = jHits.length(); i > 0; i--) {
                JSONObject jHit = jHits.getJSONObject(i - 1);
                JSONObject jResult = jHit.getJSONObject("result");
                if (jResult.getString("lyrics_state").equals("complete") && jHit.getString("type").equals("song")) {
                    songIds.add(jResult.getString("path"));
                }
            }
            Collections.reverse(songIds);
            return songIds;
        } catch (IOException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private static String enhanceSearchParam(String param) {
        param = tools.Toolkit.deleteAll(param, SEARCH_BLACKLIST);
        param = tools.Toolkit.removeInOut(param, "(", ")");
        param = tools.Toolkit.removeInOut(param, "[", "]");
        param = tools.Toolkit.deleteAll(param, "- ", "-", "\\(", "\\)", "\\.", "|", "\\,");
        param = Toolkit.removeLastIfIs(param, " ");
        return param;
    }

    public String getLyrics(String path) {
        try {
            Document doc = Jsoup.connect(GENIUSURL + path).userAgent(VersionInfo.PROJECTTITLE).get();
            Element element = doc.getElementsByClass("lyrics").first();
            element.getElementsByTag("a").unwrap();
            element.getElementsByTag("p").unwrap();
            Elements elements = element.getAllElements();
            for (Element element1 : elements) {
                if (!element1.tag().getName().equals("a") && !element1.tag().getName().equals("br") && !element1.tag().getName().equals("p")) element1.remove();
            }
            removeComments(element);
            String out = element.html();
            out = out.replaceAll("\n", "");
            out = out.replaceAll("<br> ", "\n");
            out = out.replaceAll("<p>", "").replaceAll("</p>", "");
            return out;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void removeComments(Node node) {
        for (int i = 0; i < node.childNodes().size();) {
            Node child = node.childNode(i);
            if (child.nodeName().equals("#comment"))
                child.remove();
            else {
                removeComments(child);
                i++;
            }
        }
    }
}
