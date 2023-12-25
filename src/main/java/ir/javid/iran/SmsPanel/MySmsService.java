package ir.javid.iran.SmsPanel;

import com.mashape.unirest.http.JsonNode;
import com.nimbusds.jose.shaded.gson.Gson;
import ir.javid.iran.SmsPanel.SmsDto;
import org.springframework.stereotype.Service;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * author: Mr.javidmolaei
 */

@Service
public class MySmsService {

    public void sendSms(SmsDto smsDto) throws UnirestException {
        HttpResponse<JsonNode> response = Unirest.post("https://api2.ippanel.com/api/v1/sms/send/webservice/single")
                .header("Content-Type","application/json")
                .header("apikey","WFeaK4kfH59GJJkI5q984s6S5oi5-R3Rjc0GZjftaZY=")
                .body(new Gson().toJson(smsDto))
                .asJson();
        int statusCode = response.getStatus();
        System.out.println("send sms status code : "+ statusCode);
        JsonNode responseBody= response.getBody();
        System.out.println(responseBody.toString());
    }



}
