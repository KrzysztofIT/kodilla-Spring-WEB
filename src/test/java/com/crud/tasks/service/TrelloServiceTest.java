package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class TrelloServiceTest {

    @InjectMocks
    private TrelloService trelloService;

    @Mock
    private TrelloClient trelloClient;

    @Mock
    private SimpleEmailService emailService;

    @Mock
    private AdminConfig adminConfig;


    @Test
    public void shouldFetchTrelloBoards(){
        //Given
        TrelloBoardDto trelloBoardDto = new TrelloBoardDto("test name","1",new ArrayList<>());
        List<TrelloBoardDto> trelloBoardDtoList = new ArrayList<>();
        trelloBoardDtoList.add(trelloBoardDto);
        //When
        Mockito.when(trelloClient.getTrelloBoards()).thenReturn(trelloBoardDtoList);
        //Then
        Assert.assertEquals("1",trelloService.fetchTrelloBoards().get(0).getId());
        Assert.assertEquals("test name",trelloService.fetchTrelloBoards().get(0).getName());
        Assert.assertEquals(0,trelloService.fetchTrelloBoards().get(0).getLists().size());
    }
    @Test
    public void shouldCreateTrelloCard(){
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto(
                "Test",
                "Test description",
                "top",
                "1");
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto(
                "323",
                "Test",
                "http://test.com"
        );
        //When
        Mockito.when(trelloClient.createNewCard(trelloCardDto)).thenReturn(createdTrelloCardDto);
        //Then
        Assert.assertEquals( createdTrelloCardDto, trelloService.createTrelloCard(trelloCardDto));
    }
}
