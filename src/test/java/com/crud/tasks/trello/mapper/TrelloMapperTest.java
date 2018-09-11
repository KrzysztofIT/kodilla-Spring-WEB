package com.crud.tasks.trello.mapper;

import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TrelloMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class TrelloMapperTest {

    @InjectMocks
    private TrelloMapper trelloMapper;

    @Test
    public void checkMapperFromDto ()  {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("test trelloCardDto","test ","1" ,"1");

        List<TrelloListDto> trelloListDtoList =  new ArrayList<>();
        List<TrelloBoardDto> trelloBoardDto = new ArrayList<>();

        trelloListDtoList.add(new TrelloListDto("1","test TrelloListDto",false));
        trelloBoardDto.add(new TrelloBoardDto("test TrelloBoardDto","2",trelloListDtoList));

        //When
        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto);
        List<TrelloBoard> trelloBoards  = trelloMapper.mapToBoards(trelloBoardDto);
        List<TrelloList> trelloLists  = trelloMapper.mapToList(trelloListDtoList);

        //Then
        Assert.assertEquals("test trelloCardDto" , trelloCard.getName());
        Assert.assertEquals("test TrelloListDto" , trelloLists.get(0).getName());
        Assert.assertEquals("test TrelloListDto" , trelloBoards.get(0).getLists().get(0).getName());
        Assert.assertEquals("test TrelloBoardDto" , trelloBoards.get(0).getName());
    }

    @Test
    public void checkMapperToDto ()  {
        //Given
        TrelloCard trelloCard = new TrelloCard("test trelloCard","test ","1" ,"1");

        List<TrelloList> trelloList =  new ArrayList<>();
        List<TrelloBoard> trelloBoard = new ArrayList<>();

        trelloList.add(new TrelloList("1","test TrelloList",false));
        trelloBoard.add(new TrelloBoard("test TrelloBoard","2",trelloList));

        //When
        TrelloCardDto trelloCardDto = trelloMapper.mapToCardDto(trelloCard);
        List<TrelloBoardDto> trelloBoardsDto  = trelloMapper.mapToBoardsDto(trelloBoard);
        List<TrelloListDto> trelloListDtoList  = trelloMapper.mapToListDto(trelloList);

        //Then
        Assert.assertEquals("test trelloCard" , trelloCardDto.getName());
        Assert.assertEquals("test TrelloList" , trelloListDtoList.get(0).getName());
        Assert.assertEquals("test TrelloList" , trelloBoardsDto.get(0).getLists().get(0).getName());
        Assert.assertEquals("test TrelloBoard" , trelloBoardsDto.get(0).getName());
    }
}
