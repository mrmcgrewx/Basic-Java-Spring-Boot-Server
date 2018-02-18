package requests;

import authentication.TokenManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import db.UserDBClient;
import io.jsonwebtoken.Claims;
import jdk.nashorn.internal.ir.ObjectNode;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import models.Client;
import models.Credentials;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by kylemcgrew on 11/27/17.
 */

@RestController
@RequestMapping("/client")
public class UserRequests {

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> createUser(@RequestBody Client client) {
        Integer newUserCreated = UserDBClient.createNewClient(client);
        switch (newUserCreated) {
            case 0:
                return new ResponseEntity<>("OK", HttpStatus.OK);
            case 1:
                return new ResponseEntity<>("User Exists",HttpStatus.BAD_REQUEST);
            case 2:
                return new ResponseEntity<>("Incomplete data",HttpStatus.BAD_REQUEST);
            default:
                return new ResponseEntity<>("Internal Error",HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> loginUser(@RequestBody Credentials credentials) {

        String username = credentials.getUsername();

        if (UserDBClient.checkCredentials(credentials)) {
            String token = TokenManager.createJWT(username);

            JSONObject data = new JSONObject();
            data.put("username",username);
            data.put("token",token);
            return new ResponseEntity<>(data.toJSONString(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Bad Login Attempt",HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "/verify", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> verifyToken(@RequestHeader("Authorization") String token) {
        if (!TokenManager.verifyUser(token)) {
            return new ResponseEntity<>("Validation failed, Token expired",HttpStatus.UNAUTHORIZED);
        }
        Claims claim = TokenManager.parseJWT(token);
        return new ResponseEntity<>(claim.toString(), HttpStatus.OK);
    }

}
