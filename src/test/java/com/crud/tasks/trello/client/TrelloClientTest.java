package com.crud.tasks.trello.client;

import com.crud.tasks.domain.CreatedTrelloCard;
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
    }


    @Test
    public void shouldFetchTrelloBoards() throws URISyntaxException {
        //Given
        TrelloBoardDto[] trelloBoards = new TrelloBoardDto[1];
        trelloBoards[0] = new TrelloBoardDto("test_id", "test_board", new ArrayList<>());

        URI uri = new URI("http://test.com/members/TWOJ_USERNAME_TRELLO/boards?key=test&token=test&fields=name,id&lists=all");

        Mockito.when(restTemplate.getForObject(uri , TrelloBoardDto[].class)).thenReturn(trelloBoards);


        //When
        List<TrelloBoardDto> fetchedTrelloBoards = trelloClient.getTrelloBoards();

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
        URI uri = new URI("http://test.com/cards?key=test&token=test&name=Test%20task&desc=Test%20Description&pos=top&idList=test_id");

        CreatedTrelloCard createdTrelloCard = new CreatedTrelloCard(
                "1",
                "Test task",
                "http://test.com"
        );
        Mockito.when(restTemplate.postForObject(uri , null,CreatedTrelloCard.class)).thenReturn(createdTrelloCard);

        //When
        CreatedTrelloCard newCard = trelloClient.createNewCard(trelloCardDto);

        //Then
        Assert.assertEquals("1" , newCard.getId());
        Assert.assertEquals("Test task" , newCard.getName());
        Assert.assertEquals("http://test.com" , newCard.getShortUrl());
    }
}