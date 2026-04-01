package controller;

import java.io.IOException;
import java.io.InputStream;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;


public class RequestReader<T> {
    public T readAsObject(HttpServletRequest request, Class<T> valueType) throws IOException {
        return this.readJsonAsObject(request, valueType);
    }

    private T readJsonAsObject(HttpServletRequest req, Class<T> valueType) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream inputStream = req.getInputStream();
        String jsonstring = new String(inputStream.readAllBytes());
        return objectMapper.readValue(jsonstring, valueType);
    }
}