package com.thrinod.client.upstox;

import com.thrinod.robo.config.AppCache;
import com.upstox.ApiClient;
import com.upstox.ApiException;
import com.upstox.Configuration;
import com.upstox.api.*;
import io.swagger.client.api.LoginApi;
import io.swagger.client.api.OrderApi;
import io.swagger.client.api.PortfolioApi;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpstoxClient {

    String clientId = "e1a1871d-b622-46a8-91c7-72b94c6a3679";
    String clientSecret = "hwmiwzb4vu";

    String API_VERSION = "v2";

    @Autowired
    AppCache appCache;

    public TokenResponse getAccessToken(String code) throws Exception {
        LoginApi loginApi = new LoginApi();
        return loginApi.token("v2", code, clientId, clientSecret, "http://localhost:8080/callback", "authorization_code");
    }

    public String cancelAllOrders() throws ApiException {
        OrderApi orderApi = new OrderApi(getApiClient());
        GetOrderBookResponse getOrderBookResponse = orderApi.getOrderBook(API_VERSION);
        for (OrderBookData orderBookData : getOrderBookResponse.getData()) {
            if ("open".equalsIgnoreCase(orderBookData.getStatus()) || "scheduled".equalsIgnoreCase(orderBookData.getStatus())) {
                try {
                    System.out.println(
                        orderBookData.getOrderId() + orderApi.cancelOrder(orderBookData.getOrderId(), API_VERSION).getStatus()
                    );
                } catch (Exception ex) {}
            }
        }
        return "Done";
    }

    public List<OrderBookData> getAllOrders() throws ApiException {
        OrderApi orderApi = new OrderApi(getApiClient());
        GetOrderBookResponse getOrderBookResponse = orderApi.getOrderBook(API_VERSION);
        return getOrderBookResponse.getData();
    }

    public String buyOrder() {
        OrderApi orderApi = new OrderApi(getApiClient());
        //        PlaceOrderRequest placeOrderRequest = new PlaceOrderRequest();
        //        placeOrderRequest.s
        //        orderApi.placeOrder(placeOrderRequest)
        return "NOT_IMPLEMENTED";
    }

    public List<PositionData> ExitAllOrderAC() throws ApiException {
        PortfolioApi portfolioApi = new PortfolioApi(getApiClient());
        GetPositionResponse getPositionResponse = portfolioApi.getPositions(API_VERSION);
        for (PositionData positionData : getPositionResponse.getData()) {
            System.out.println(positionData.getInstrumentToken());
        }
        return null;
    }

    ApiClient getApiClient() {
        ApiClient apiClient = Configuration.getDefaultApiClient();
        apiClient.setAccessToken(appCache.getAccToken());
        return apiClient;
    }
}
