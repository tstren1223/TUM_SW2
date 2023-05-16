package de.tum.in.ase.pse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.concurrent.CompletableFuture;

@RequestMapping(value = "/messages/", consumes = "application/json")
public class InboxResource {
    private final InboxClient in= new InboxClient();
    @PostMapping("r2")
    public ResponseEntity<String> droidReadyR2(@RequestBody String droid) {
        in.droidReadyR2(droid);
        return new ResponseEntity<String>(in.getMessages().get(in.getMessages().size() - 1), HttpStatus.OK);
    }

    @PostMapping("3po")
    public ResponseEntity<String> droidReady3PO(@RequestBody String droid) {
        in.droidReady3PO(droid);
        return new ResponseEntity<String>(in.getMessages().get(in.getMessages().size() - 1), HttpStatus.OK);
    }
}
