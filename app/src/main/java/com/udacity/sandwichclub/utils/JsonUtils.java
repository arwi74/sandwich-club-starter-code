package com.udacity.sandwichclub.utils;


import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//{"name":
//           {
//            "mainName" :"Ham and cheese sandwich",
//            "alsoKnownAs":[]
//            },
// "placeOfOrigin":"",
// "description":"A ham and cheese sandwich",
// "image":"https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/Grilled_ham_and_cheese_014.JPG/800px-Grilled_ham_and_cheese_014.JPG",
// "ingredients":["Sliced bread","Cheese","Ham"]}


public class JsonUtils {
    private static final String NAME = "name";
    private static final String MAIN_NAME = "mainName";
    private static final String ALSO_KNOWN_AS = "alsoKnownAs";
    private static final String PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String DESCRIPTION = "description";
    private static final String IMAGE = "image";
    private static final String INGREDIENTS = "ingredients";


    public static Sandwich parseSandwichJson(String json) {
        try {
            JSONObject fullJsonSandwich = new JSONObject(json);

            JSONObject name = fullJsonSandwich.getJSONObject(NAME);
            String mainName = name.optString(MAIN_NAME);
            JSONArray alsoKnownAsJson = name.getJSONArray(ALSO_KNOWN_AS);

            List<String> alsoKnownAs = new ArrayList<>();

            for ( int i=0;i<alsoKnownAsJson.length();i++){
                alsoKnownAs.add( alsoKnownAsJson.optString(i) );
            }

            String placeOfOrigin = fullJsonSandwich.optString(PLACE_OF_ORIGIN);
            String description = fullJsonSandwich.optString(DESCRIPTION);
            String image = fullJsonSandwich.optString(IMAGE);
            JSONArray ingredientsJson = fullJsonSandwich.getJSONArray(INGREDIENTS);

            List<String> ingredients = new ArrayList<>();

            for ( int i=0;i<ingredientsJson.length();i++){
                ingredients.add( ingredientsJson.optString(i) );
            }

            return new Sandwich(mainName,alsoKnownAs,placeOfOrigin,description,image,ingredients);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

}
