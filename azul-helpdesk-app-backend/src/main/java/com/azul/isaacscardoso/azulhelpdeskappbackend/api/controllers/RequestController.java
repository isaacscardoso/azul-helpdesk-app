package com.azul.isaacscardoso.azulhelpdeskappbackend.api.controllers;

import com.azul.isaacscardoso.azulhelpdeskappbackend.api.models.RequestDTO;
import com.azul.isaacscardoso.azulhelpdeskappbackend.domain.models.Request;
import com.azul.isaacscardoso.azulhelpdeskappbackend.domain.services.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/chamados")
public class RequestController {

    private RequestService requestService;

    public RequestController() { }

    @Autowired
    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping
    public ResponseEntity<List<RequestDTO>> findAll() {
        List<Request> requests = this.requestService.findAll();
        return ResponseEntity.ok(requests.stream().map(RequestDTO::new).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RequestDTO> findById(@PathVariable Long id) {
        Optional<Request> request = Optional.ofNullable(this.requestService.findById(id));
        return request.map(request_ -> ResponseEntity.ok(new RequestDTO(request_))).orElseGet(() -> ResponseEntity
                .notFound().build());
    }

    @PostMapping
    public ResponseEntity<Request> insert(@Valid @RequestBody Request request) {
        Request request_ = this.requestService.insert(request);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(request_.getId())
                .toUri();
        return ResponseEntity.created(uri).body(request_);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Request> update(@PathVariable Long id, @Valid @RequestBody Request request) {
        Request request_ = this.requestService.update(id, request);
        return request_ != null ? ResponseEntity.ok(request_) : ResponseEntity.notFound().build();
    }
}
