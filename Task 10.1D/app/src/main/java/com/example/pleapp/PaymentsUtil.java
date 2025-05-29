package com.example.pleapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PaymentsUtil {

    public static JSONObject getBaseRequest() throws JSONException {
        JSONObject baseRequest = new JSONObject();
        baseRequest.put("apiVersion", 2);
        baseRequest.put("apiVersionMinor", 0);
        return baseRequest;
    }

    public static JSONObject getGatewayTokenizationSpecification() throws JSONException {
        JSONObject tokenizationSpec = new JSONObject();
        tokenizationSpec.put("type", "PAYMENT_GATEWAY");

        JSONObject parameters = new JSONObject();
        parameters.put("gateway", "example");
        parameters.put("gatewayMerchantId", "exampleGatewayMerchantId");

        tokenizationSpec.put("parameters", parameters);
        return tokenizationSpec;
    }

    public static JSONObject getCardPaymentMethod() throws JSONException {
        JSONObject cardPaymentMethod = new JSONObject();
        cardPaymentMethod.put("type", "CARD");

        JSONObject parameters = new JSONObject();
        parameters.put("allowedAuthMethods", new JSONArray()
                .put("PAN_ONLY")
                .put("CRYPTOGRAM_3DS"));
        parameters.put("allowedCardNetworks", new JSONArray()
                .put("AMEX")
                .put("DISCOVER")
                .put("JCB")
                .put("MASTERCARD")
                .put("VISA"));

        parameters.put("billingAddressRequired", true);
        JSONObject billingAddressParameters = new JSONObject();
        billingAddressParameters.put("format", "FULL");
        parameters.put("billingAddressParameters", billingAddressParameters);

        cardPaymentMethod.put("parameters", parameters);
        cardPaymentMethod.put("tokenizationSpecification", getGatewayTokenizationSpecification());

        return cardPaymentMethod;
    }

    public static JSONObject createPaymentDataRequest(String price) throws JSONException {
        JSONObject paymentDataRequest = getBaseRequest();
        paymentDataRequest.put("allowedPaymentMethods",
                new JSONArray().put(getCardPaymentMethod()));

        JSONObject transactionInfo = new JSONObject();
        transactionInfo.put("totalPrice", price);
        transactionInfo.put("totalPriceStatus", "FINAL");
        transactionInfo.put("currencyCode", "USD");

        JSONObject merchantInfo = new JSONObject();
        merchantInfo.put("merchantName", "Example Merchant");

        paymentDataRequest.put("transactionInfo", transactionInfo);
        paymentDataRequest.put("merchantInfo", merchantInfo);

        return paymentDataRequest;
    }
}
