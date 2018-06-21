package edu.dartit.warehouseapp.utils;

import com.google.gson.Gson;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

/**
 * Created by vysokov-mg on 21.06.2018.
 */
public class JsonSender {

    public static void sendJson(Object data, HttpServletResponse response) throws IOException {
        response.setContentType("text/json");
        response.setCharacterEncoding("UTF-8");
        Writer out = response.getWriter();
        String json = new Gson().toJson(data);
        out.write(json);
        out.flush();
    }
}
