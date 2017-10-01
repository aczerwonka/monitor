package pck;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class LoggingController {


    private JsonFileLogger jsonFileLogger;


    private synchronized JsonFileLogger getJsonFileLogger() {
        if (jsonFileLogger == null) {
            jsonFileLogger = new JsonFileLogger();
        }
        return jsonFileLogger;
    }

    @RequestMapping("/addEntry")
    public String addEntry(@RequestParam(value = "name", defaultValue = "noName") String name) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String timestamp = dateFormat.format(date);
        System.out.println("addingEntry: " + name + " when: " + timestamp);

        getJsonFileLogger().addEntry(name, timestamp);

        return "bangla";
    }

    @RequestMapping("/")
    public String get() {
        return "hello";
    }
    @RequestMapping("/getEntries")
    public String getEntries() {
        System.out.println("getting entries");
        return getJsonFileLogger().getLogFile().toJSONString();
    }
}
