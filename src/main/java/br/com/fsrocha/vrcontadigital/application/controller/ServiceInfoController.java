package br.com.fsrocha.vrcontadigital.application.controller;

import br.com.fsrocha.vrcontadigital.application.dto.response.ServiceInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ServiceInfoController {

    @GetMapping
    public ResponseEntity<ServiceInfo> getServiceInfo() {
        return ResponseEntity.ok(new ServiceInfo("0.0.1-SNAPSHOT", "VR Conta Digital"));
    }

}
