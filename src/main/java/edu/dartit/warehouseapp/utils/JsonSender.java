package edu.dartit.warehouseapp.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import edu.dartit.warehouseapp.entities.Item;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by vysokov-mg on 21.06.2018.
 */
public class JsonSender {

    public static void sendJson(Object data, HttpServletResponse response) throws IOException {
        response.setContentType("text/json");
        response.setCharacterEncoding("UTF-8");
        Writer out = response.getWriter();
        //Type type = new TypeToken<Map<Item, Integer>>(){}.getType();
        String json = new Gson().toJson(data);// type);
        out.write(json);
        out.flush();
    }
}
