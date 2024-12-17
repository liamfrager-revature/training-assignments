package test.java.com.liamfrager.connect;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins="http://localhost:3000")
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/cors")
    public ResponseEntity<String> testCors() {
        return ResponseEntity.ok("CORS test passed");
    }
}