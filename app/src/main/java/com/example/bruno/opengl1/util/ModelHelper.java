package com.example.bruno.opengl1.util;

import android.content.Context;
import android.util.Log;

import com.example.bruno.opengl1.R;
import com.example.bruno.opengl1.objects.Material;
import com.example.bruno.opengl1.objects.Mesh;
import com.example.bruno.opengl1.objects.Node;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

import static android.opengl.Matrix.setIdentityM;
import static com.example.bruno.opengl1.util.TextResourceReader.readTextFileFromResource;


public class ModelHelper {

    public static void longInfo(String str) {
        if(str.length() > 4000) {
            Log.i("JSON String!!!", str.substring(0, 4000));
            longInfo(str.substring(4000));
        } else
            Log.i("JSON String!!!", str);
    }

    public static Node CreateModel(Context context){
        List<Material> mat = new LinkedList<>();

        float[] mMatrix = new float[16];
        setIdentityM(mMatrix, 0);
        Node head = new Node(mMatrix);

        String StringJSON = readTextFileFromResource(context, R.raw.model_json);

      Log.i("String Size: ",Integer.toString(StringJSON.length()));
      //longInfo(StringJSON);


        try {
            JSONObject reader = new JSONObject(StringJSON);

            JSONArray materials = reader.getJSONArray("materials");
            JSONArray nodes = reader.getJSONArray("nodes").getJSONObject(0).getJSONArray("meshIndices");
            JSONArray meshes = reader.getJSONArray("meshes");

            for(int i = 0; i < materials.length(); i++){
                JSONObject o = materials.getJSONObject(i);
                mat.add(new Material(o.getJSONArray("diffuseTexture").getString(0), context));
            }
            JSONArray nodeMMatrix = reader.getJSONArray("nodes").getJSONObject(0).getJSONArray("modelMatrix");

            for(int j =0;j<16;j++){
                mMatrix[j]= (float)nodeMMatrix.getDouble(j);
            }

            for(int i = 0; i < nodes.length(); i++){

                JSONObject m = meshes.getJSONObject(i);
                JSONArray c = m.getJSONArray("vertexPositions");

                float [] vertexPos = new float[c.length()];
                for (int j = 0; j <c.length();j++){
                    vertexPos[j] = (float) c.getDouble(j);
                }

                JSONArray d = m.getJSONArray("vertexTexCoordinates");
                float [] vertexTex = new float[d.length()];
                for (int j = 0; j < d.length(); j++){
                    vertexTex[j] = (float) d.getDouble(j);
                }

                JSONArray e =m.getJSONArray("indices");
                short [] indices = new short[e.length()];
                for (int j = 0; j <e.length(); j++){
                    indices[j] = (short) e.getInt(j);
                }

                int matIndx = m.getInt("materialIndex");
                Mesh newM = new Mesh(vertexPos,vertexTex,indices,mat.get(matIndx));
                Node newN = new Node(mMatrix,newM);
                head.addChild(newN);
            }

        }catch (JSONException e) {
            e.printStackTrace();
        }

        return head;
    }

}
