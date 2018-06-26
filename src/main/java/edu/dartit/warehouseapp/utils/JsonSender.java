package edu.dartit.warehouseapp.utils;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import edu.dartit.warehouseapp.entities.Action;
import edu.dartit.warehouseapp.entities.Item;
import edu.dartit.warehouseapp.entities.Record;
import edu.dartit.warehouseapp.entities.enums.ActionType;
import jdk.nashorn.internal.ir.debug.JSONWriter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by vysokov-mg on 21.06.2018.
 */
public class JsonSender {

    private static Gson gson;

    static {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Action.class, new ActionAdapter());
        gson = builder.create();
    }

    public static class ActionAdapter extends TypeAdapter<Action> {

        @Override
        public void write(JsonWriter writer, Action action) throws IOException {
            if (action == null) {
                writer.nullValue();
                return;
            }
            writer.beginObject();
            writer.name("date");
            writeDate(writer, action.getDate());
            writer.name("type");
            writer.value(action.getType().toString());
            writer.name("supplier");
            writer.value(
                    (action.getSupplier() != null) ? action.getSupplier().getName() : ""
            );
            writer.name("consumer");
            writer.value(
                    (action.getConsumer() != null) ? action.getConsumer().getName() : ""
            );
            writer.name("item");
            writer.value(action.getItem().getName());
            writer.name("amount");
            writer.value(action.getAmount());
            writer.name("user");
            writer.value(action.getUser().getUsername());
            writer.endObject();
        }

        @Override
        public Action read(JsonReader reader) throws IOException {
            return null;
        }

        public void writeDate(JsonWriter writer, LocalDate date) throws IOException {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM YYYY");
            String dateFormatted = date.format(formatter);
            writer.value(dateFormatted);
        }
    }

    public static void sendJson(Object data, HttpServletResponse response) throws IOException {
        response.setContentType("text/json");
        response.setCharacterEncoding("UTF-8");
        Writer out = response.getWriter();
        String json = gson.toJson(data);
        out.write(json);
        out.flush();
    }

    public static void sendJson(List<Record> data1, List<Record> data2, HttpServletResponse response) throws IOException {
        List<List<Record>> result = new ArrayList<>();
        result.add(data1);
        result.add(data2);
        sendJson(result, response);
    }
}