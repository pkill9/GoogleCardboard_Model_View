package com.example.bruno.opengl1.objects;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.bruno.opengl1.util.TextResourceReader.readTextFileFromResource;


public class jsonObject {

    public jsonObject(Context context, int id){
        String StringJSON = readTextFileFromResource(context, id);
        try {
            JSONObject reader = new JSONObject(StringJSON);
        }catch (JSONException e) {
            e.printStackTrace();
        }


    }

}
