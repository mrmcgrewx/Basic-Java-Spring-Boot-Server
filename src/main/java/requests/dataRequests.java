package requests;

import authentication.TokenManager;
import db.DataDBClient;
import models.MLData;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by kylemcgrew on 2/16/18.
 */

@RestController
@RequestMapping("/data")
public class dataRequests {

    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> createUser(@RequestHeader("Authorization") String token,@RequestBody MLData data) {
        if (!TokenManager.verifyUser(token)) {
            return new ResponseEntity<>("Validation failed, Token expired",HttpStatus.UNAUTHORIZED);
        }

        DataDBClient.storeToDataPool(data);
        JSONObject res = new JSONObject();
        res.put("status","success");
        return new ResponseEntity<>(res.toJSONString(), HttpStatus.OK);
    }
}
