package com.lilsf.client.security;

import com.lilsf.client.models.Client;
import com.lilsf.client.repositories.ClientRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@Component
public class CurrentClient {
    private final ClientRepository clients;

    public CurrentClient(ClientRepository clients) {
        this.clients = clients;
    }

    public int idFor(String username) {
        return clients.findByUser_Username(username)
                .map(Client::getId)
                .orElseThrow(() -> new AccessDeniedException("Client profile required"));
    }
}
