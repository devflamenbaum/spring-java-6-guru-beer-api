package br.com.byteartworks.controllers;

import br.com.byteartworks.dto.BeerDTO;
import br.com.byteartworks.services.BeerService;
import br.com.byteartworks.services.BeerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/*
 *   @author: gabflbm. created on 15/06/2023 !
 */
@WebMvcTest(BeerController.class)
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    BeerService beerService;

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Captor
    ArgumentCaptor<BeerDTO> beerArgumentCaptor;

    BeerServiceImpl beerServiceImpl;

    @BeforeEach
    void setUp() throws Exception {
        beerServiceImpl = new BeerServiceImpl();
    }

    @Test
    void testPatchBeer() throws Exception {
        BeerDTO testBeer = beerServiceImpl.getAllBeers().get(0);

        Map<String, Object> beerMap = new HashMap<>();
        beerMap.put("name", "new name");

        mockMvc.perform(patch(BeerController.BEER_PATH_ID, testBeer.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerMap)))
                .andExpect(status().isNoContent());

        verify(beerService).patchBeerById(uuidArgumentCaptor.capture(), beerArgumentCaptor.capture());

        assertEquals(testBeer.getId(), uuidArgumentCaptor.getValue());
        assertEquals(beerMap.get("name"), beerArgumentCaptor.getValue().getName());
    }

    @Test
    void testDeleteBeer() throws Exception {
        BeerDTO testBeer = beerServiceImpl.getAllBeers().get(0);

        when(beerService.deleteById(testBeer.getId())).thenReturn(true);

        mockMvc.perform(delete(BeerController.BEER_PATH_ID, testBeer.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(beerService).deleteById(uuidArgumentCaptor.capture());

        assertEquals(testBeer.getId(), uuidArgumentCaptor.getValue());
    }

    @Test
    void testUpdateBeer() throws Exception {
        BeerDTO testBeer = beerServiceImpl.getAllBeers().get(0);

        given(beerService.updateBeerById(any(), any())).willReturn(Optional.of(testBeer));

        mockMvc.perform(put(BeerController.BEER_PATH_ID, testBeer.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testBeer)));

        verify(beerService).updateBeerById(any(UUID.class), any(BeerDTO.class));
    }

    @Test
    void testCreateNewBeer() throws Exception {
        BeerDTO testBeer = beerServiceImpl.getAllBeers().get(0);
        testBeer.setVersion(null);
        testBeer.setId(null);

        given(beerService.saveNewBeer(any(BeerDTO.class)))
                .willReturn(beerServiceImpl.getAllBeers().get(1));

        mockMvc.perform(post(BeerController.BEER_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testBeer)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));


    }

    @Test
    void testUpdateBeerBlankName() throws Exception {
        BeerDTO beer = beerServiceImpl.getAllBeers().get(0);
        beer.setName("");
        given(beerService.updateBeerById(any(), any())).willReturn(Optional.of(beer));

        mockMvc.perform(put(BeerController.BEER_PATH_ID, beer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beer)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length()", is(1)));

    }

    @Test
    void testCreateBeerNullBeerName() throws Exception {
        BeerDTO beerDTO = BeerDTO.builder().build();


        given(beerService.saveNewBeer(any(BeerDTO.class)))
                .willReturn(beerServiceImpl.getAllBeers().get(1));

        MvcResult mvcResult = mockMvc.perform(post(BeerController.BEER_PATH)
                                                        .accept(MediaType.APPLICATION_JSON)
                                                        .contentType(MediaType.APPLICATION_JSON)
                                                        .content(objectMapper.writeValueAsString(beerDTO)))
                                                        .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length()", is(6)))
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void testListBeers() throws Exception {

        given(beerService.getAllBeers()).willReturn(new ArrayList<>(beerServiceImpl.getAllBeers()));

        mockMvc.perform(get(BeerController.BEER_PATH)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(3)));
    }

    @Test
    void getBeerByIdNotFound() throws Exception {

        given(beerService.getBeerById(any(UUID.class))).willReturn(Optional.empty());

        mockMvc.perform(get(BeerController.BEER_PATH_ID, UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    void getBeerById() throws Exception {

        BeerDTO testBeer = beerServiceImpl.getAllBeers().get(0);

        given(beerService.getBeerById(testBeer.getId())).willReturn(Optional.of(testBeer));

        mockMvc.perform(
                get(BeerController.BEER_PATH_ID, testBeer.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(testBeer.getId().toString())))
                .andExpect(jsonPath("$.name", is(testBeer.getName())));

    }
}