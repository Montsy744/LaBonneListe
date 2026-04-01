package controller;

import java.io.IOException;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import jakarta.servlet.http.HttpServletResponse;

public class ResponseWriter<T> {
    public void writeResponse(HttpServletResponse resp, String contentType, T object) throws IOException {
        // content type can be null
        Optional<String> cT = Optional.ofNullable(contentType);
        if (cT.isEmpty()) {
            this.writeAsJson(resp, object);
        } else if (cT.get().contains("application/xml") || cT.get().contains("text/xml")) {
            this.writeAsXml(resp, object);
        } else {
            this.writeAsJson(resp, object);
        }
    }

    private void writeAsXml(HttpServletResponse res, T object) throws IOException {
        XmlMapper mapper = new XmlMapper();
        res.getWriter().println(mapper.writeValueAsString(object));
    }

    private void writeAsJson(HttpServletResponse res, T object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String resp = mapper.writeValueAsString(object);
        res.getWriter().println(resp);
    }
}
