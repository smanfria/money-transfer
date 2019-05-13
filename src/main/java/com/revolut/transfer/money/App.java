package com.revolut.transfer.money;

import com.google.gson.Gson;
import com.revolut.transfer.money.dto.ErrorDTO;
import com.revolut.transfer.money.dto.MoneyDTO;
import com.revolut.transfer.money.exception.AccountNotFoundException;
import com.revolut.transfer.money.exception.IncompatibleCurrencyCode;
import com.revolut.transfer.money.exception.InsufficientMoneyInAccount;
import com.revolut.transfer.money.service.AccountService;
import com.revolut.transfer.money.service.IAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static spark.Spark.*;

public class App {
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    private static final String JSON_CONTENT_TYPE = "application/json";
    private static Gson gson = new Gson();

    private static IAccountService accountService = new AccountService();

    public static void main(String[] args) {
        after((req, res) -> {
            res.type(JSON_CONTENT_TYPE);
        });

        post("/api/account", JSON_CONTENT_TYPE, (request, response) ->
                        accountService.create(gson.fromJson(request.body(), MoneyDTO.class))
                , new JsonTransformer());

        get("/api/account/:id", JSON_CONTENT_TYPE, (request, response) ->
                        accountService.get(request.params(":id"))
                , new JsonTransformer());

        delete("/api/account/:id", JSON_CONTENT_TYPE, (request, response) ->
                        accountService.close(request.params(":id"))
                , new JsonTransformer());

        post("/api/account/:fromId/transfer/:toId", JSON_CONTENT_TYPE, (request, response) ->
                {
                    String accountFrom = request.params(":fromId");
                    String accountTo = request.params(":toId");
                    MoneyDTO moneyPayload = gson.fromJson(request.body(), MoneyDTO.class);
                    return accountService.transfer(accountFrom, accountTo, moneyPayload);
                }
                , new JsonTransformer());


        exception(AccountNotFoundException.class, (e, request, response) -> {
            int statusCode = 404;
            response.status(statusCode);
            response.body(JsonUtil.toJson(new ErrorDTO(statusCode, e.getMessage())));
            response.type(JSON_CONTENT_TYPE);
            LOGGER.error(response.body());
        });

        exception(IncompatibleCurrencyCode.class, (e, request, response) -> {
            int statusCode = 409;
            response.status(statusCode);
            response.body(JsonUtil.toJson(new ErrorDTO(statusCode, e.getMessage())));
            response.type(JSON_CONTENT_TYPE);
            LOGGER.error(response.body());
        });

        exception(InsufficientMoneyInAccount.class, (e, request, response) -> {
            int statusCode = 409;
            response.status(statusCode);
            response.body(JsonUtil.toJson(new ErrorDTO(statusCode, e.getMessage())));
            response.type(JSON_CONTENT_TYPE);
            LOGGER.error(response.body());
        });
    }

}
