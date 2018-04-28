package edu.tacoma.uw.css.alvin3.rateuwtprofessors.rating;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Rating {
    /**
     * An array of sample (dummy) items.
     */
    public static final List<RatingItem> ITEMS = new ArrayList<RatingItem>();

    /**
     * A map of sample (Rating) items, by ID.
     */
    public static final Map<String, RatingItem> ITEM_MAP = new HashMap<String, RatingItem>();
    private static final int COUNT = 25;
    static{
        //Add some sample items.
        for(int i = 1; i<= COUNT;i++){
            addRating(createRatingItem(i));
        }
    }

    private static RatingItem createRatingItem (int postion){
        return new RatingItem(String.valueOf(postion),"Recent Rating " + postion);
    }
//
//
//    private static String makeDetails(int position) {
//        StringBuilder builder = new StringBuilder();
//        builder.append("Details about Item: ").append(position);
//        for (int i = 0; i < position; i++) {
//            builder.append("\nMore details information here.");
//        }
//        return builder.toString();
//    }

    private static void addRating(RatingItem item){
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public static class RatingItem implements Serializable {
        public final String id;
        public final String content;
        public RatingItem(String id, String content) {
            this.id = id;
            this.content = content;
            //this.details = details;
        }

        @Override
        public String toString() {
            return content;
        }
    }


}
