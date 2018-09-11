package com.crud.tasks.trello.client;

import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.config.TrelloConfig;
import java.net.URI;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class TrelloClientTest {

    @InjectMocks
    private TrelloClient trelloClient;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private TrelloConfig trelloConfig;



    @Before
    public void init(){
        Mockito.when(trelloConfig.getTrelloApiEndpoint()).thenReturn("http://test.com");
        Mockito.when(trelloConfig.getTrelloAppKey()).thenReturn("test");
        Mockito.when(trelloConfig.getTrelloToken()).thenReturn("test");
        Mockito.when(trelloConfig.getTrelloUsername()).thenReturn("krzysztofwasilewski10");

    }


    @Test
    public void shouldFetchTrelloBoards() throws URISyntaxException {
        //Given
        TrelloBoardDto[] trelloBoards = new TrelloBoardDto[1];
        trelloBoards[0] = new TrelloBoardDto("test_board", "test_id", new ArrayList<>());

        URI uri = new URI("http://test.com/members/krzysztofwasilewski10/boards?key=test&token=test&lists=all");


        System.out.println(uri);
        Mockito.when(restTemplate.getForObject(uri , TrelloBoardDto[].class)).thenReturn(trelloBoards);
        System.out.println(uri);


        //When
        List<TrelloBoardDto> fetchedTrelloBoards = trelloClient.getTrelloBoards();
        System.out.println(uri);


        //Then
        Assert.assertEquals(1 , fetchedTrelloBoards.size());
        Assert.assertEquals("test_id" , fetchedTrelloBoards.get(0).getId());
        Assert.assertEquals("test_board" , fetchedTrelloBoards.get(0).getName());
        Assert.assertEquals(new ArrayList<>() , fetchedTrelloBoards.get(0).getLists());
    }

    @Test
    public void shouldCreateCard () throws URISyntaxException {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto(
                "Test_task",
                "Test_description",
                "top",
                "test_id"
        );
        URI uri = new URI("http://test.com/cards?key=test&token=test&name=Test_task&desc=Test_description&pos=top&idList=test_id");

        System.out.println(uri);

        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto(
                "1",
                "Test task",
                "http://test.com"
        );
        Mockito.when(restTemplate.postForObject(uri , null,CreatedTrelloCardDto.class)).thenReturn(createdTrelloCardDto);

        //When
        CreatedTrelloCardDto newCard = trelloClient.createNewCard(trelloCardDto);

        //Then
        Assert.assertEquals("1" , newCard.getId());
        Assert.assertEquals("Test task" , newCard.getName());
        Assert.assertEquals("http://test.com" , newCard.getShortUrl());
    }

    @Test
    public void shouldReturnEmptyList () throws URISyntaxException {
        //Given

        URI uri = new URI("http://test.com/members/krzysztofwasilewski10/boards?key=test&token=test&lists=all");

        //When
        Mockito.when(restTemplate.getForObject(uri , TrelloBoardDto[].class)).thenReturn(null);


        //Then
        Assert.assertEquals(0 , trelloClient.getTrelloBoards().size());
    }
}