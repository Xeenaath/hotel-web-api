package dev.hotel.controller;

import dev.hotel.entite.Client;
import dev.hotel.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.UUID;

@WebMvcTest(ClientController.class)
public class ClientControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	ClientRepository clientRepository;

	@Test
	void listerClientsShouldReturnJsonWithTwoClients() throws Exception {
		Client c1 = new Client();
		c1.setNom("Debbouze");
		c1.setPrenoms("Jamel");

		Client c2 = new Client();
		c2.setNom("Elmaleh");
		c2.setPrenoms("Gad");

		Mockito.when(clientRepository.findAll(PageRequest.of(15, 30)))
				.thenReturn(new PageImpl<>(Arrays.asList(c1, c2)));

		mockMvc.perform(MockMvcRequestBuilders.get("/clients?start=15&size=30"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("[0].nom").value("Debbouze"))
				.andExpect(MockMvcResultMatchers.jsonPath("[0].prenoms").value("Jamel"))
				.andExpect(MockMvcResultMatchers.jsonPath("[1].nom").value("Elmaleh"))
				.andExpect(MockMvcResultMatchers.jsonPath("[1].prenoms").value("Gad"));
	}
	
	@Test
	void getClientFromUuidShouldReturnJsonWithOneClient() throws Exception {
		Client client = new Client("Reno", "Jean");
		UUID uuid = UUID.fromString("91defde1-9ad3-4e4f-886b-f5f06f601a0d");
		client.setUuid(uuid);
		Mockito.when(clientRepository.findOneClientByUUID(uuid)).thenReturn(client);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/clients/91defde1-9ad3-4e4f-886b-f5f06f601a0d"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("nom").value("Reno"))
		.andExpect(MockMvcResultMatchers.jsonPath("prenoms").value("Jean"))
		.andExpect(MockMvcResultMatchers.jsonPath("uuid").value("91defde1-9ad3-4e4f-886b-f5f06f601a0d"));
	}
}
