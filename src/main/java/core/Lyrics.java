package core;

import lyrics.GA;
import lyrics.GABuilder;

import java.util.ArrayList;

public class Lyrics {
    private static final String CLIENT_ID = "e_AArVm8rW1BA03QxfvQCd1bfgNBg7CcP6pcU-dyUI1dUoyt2Nz83t8P0P6iuQ_v";
    private static final String ACCESS_TOKEN = "-mNl-lXJ6kNkCF-oN80OWjSmyf1_faZURavDbieSgKMA-esQ5RYGYKFIAj3D6z9U";
    private static final String RAW_PARAM = "4 Chords | Music Videos | The Axis Of Awesome";

    public static void main(String... args) {
        GA ga = new GABuilder(CLIENT_ID, ACCESS_TOKEN, "GA").build();

        System.out.println("Searching with param: \"" + RAW_PARAM + "\"...");
        ArrayList<String> results = ga.searchSongPath(RAW_PARAM, true);
        if (results.isEmpty()) {
            System.out.println("NO-SEARCH-RESULTS");
        } else {
            System.out.println(ga.getLyrics(results.get(0)));
        }
    }
}
