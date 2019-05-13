package com.revolut.transfer.money;

import com.google.gson.Gson;

class JsonUtil {
    private static final Gson gson = new Gson();

    static String toJson(Object model) {
        return gson.toJson(model);
    }
}
