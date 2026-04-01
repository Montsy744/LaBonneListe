package MiddleWare;

import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;


public class IdMiddleWare {
    public static Optional<String> getPath(HttpServletRequest req, int length) {
        String info = req.getPathInfo();
        if (info == null) {
            return Optional.empty();
        }
        String[] splits = info.split("/");
        if(splits.length != length) {
            return Optional.empty();
        }
        return Optional.of(splits[1]);
    }
}
