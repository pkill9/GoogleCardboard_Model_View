package com.example.bruno.opengl1.objects;


import android.content.Context;

import com.example.bruno.opengl1.util.TextureHelper;

import java.lang.reflect.Field;

public class Material {
    public String diffuseTexture;
    public int textId;

    public Material(String dT, Context context){
        diffuseTexture = dT;
        textId = TextureHelper.loadTexture(context, getId(dT, android.R.drawable.class));
    }

    public static int getId(String resourceName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(resourceName);
            return idField.getInt(idField);
        } catch (Exception e) {
            throw new RuntimeException("No resource ID found for: "
                    + resourceName + " / " + c, e);
        }
    }

}
