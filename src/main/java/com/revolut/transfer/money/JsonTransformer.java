package com.revolut.transfer.money;

import spark.ResponseTransformer;

public class JsonTransformer implements ResponseTransformer {

    @Override
    public String render(Object model) {
        return JsonUtil.toJson(model);
    }

}
