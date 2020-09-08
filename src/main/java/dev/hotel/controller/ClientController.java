package dev.hotel.controller;
import dev.hotel.entite.Client;

import org.springframework.web.bind.annotation.RestController;

import dev.hotel.repository.ClientRepository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

//Contrôleur
//=> Récupère la requête HTTP
//=> Génère la réponse HTTP
//@Controller
//@ResponseBody
//@RestController = @Controller + @ResponseBody

@RestController
public class ClientController {

	private ClientRepository clientRepository;

	public ClientController(ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}

	// GET /clients?start=X&size=Y
	@RequestMapping(method = RequestMethod.GET, path = "clients")
	public List<Client> listerClients(@RequestParam Integer start, @RequestParam Integer size) {
		return clientRepository.findAll(PageRequest.of(start, size)).getContent();
	}
	
	// GET /clients/UUID
	@RequestMapping(method = RequestMethod.GET, path = "clients/{uuid}")
	public Client getClientByUUID(@PathVariable UUID uuid) {
		return clientRepository.findOneClientByUUID(uuid);
	}
}
