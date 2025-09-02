package oi.github.pedroMartinsMJ.librayapi2.service;

import lombok.RequiredArgsConstructor;
import oi.github.pedroMartinsMJ.librayapi2.model.Client;
import oi.github.pedroMartinsMJ.librayapi2.repository.ClientRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    public Client salvar(Client client){
        client.setClientSecret(passwordEncoder.encode(client.getClientSecret()));
        return clientRepository.save(client);
    }

    public Client obterPorClientID(String clientId){
        return clientRepository.findByClientId(clientId);
    }
}
