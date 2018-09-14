package com.crud.tasks.trello.facade;

import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TrelloMapper;
import com.crud.tasks.service.TrelloService;
import com.crud.tasks.trello.validator.TrelloValidator;
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
public class TrelloFacadeTest {

    @InjectMocks
    private TrelloFacade trelloFacade;

    @Mock
    private TrelloService trelloService;

    @Mock
    private TrelloValidator trelloValidator;

    @Mock
    private TrelloMapper trelloMapper;

    @Test
    public void shouldFetchEmptyList () {
        //Given
        List<TrelloListDto> trelloListDtoList =  new ArrayList<>();
        List<TrelloBoardDto> trelloBoardDto = new ArrayList<>();

        trelloListDtoList.add(new TrelloListDto("1","test list",false));
        trelloBoardDto.add(new TrelloBoardDto("test","2",trelloListDtoList));

        List<TrelloList> trelloList =  new ArrayList<>();
        List<TrelloBoard> trelloBoard = new ArrayList<>();

        trelloList.add(new TrelloList("1","test list",false));
        trelloBoard.add(new TrelloBoard("test ","2",trelloList));

        Mockito.when(trelloService.fetchTrelloBoards()).thenReturn(trelloBoardDto);
        Mockito.when(trelloMapper.mapToBoards(trelloBoardDto)).thenReturn(trelloBoard);
        Mockito.when(trelloMapper.mapToBoardsDto(Mockito.anyList())).thenReturn(new ArrayList());
        Mockito.when(trelloValidator.validateTrelloBoards(trelloBoard)).thenReturn(new ArrayList<>());

        //When

        List<TrelloBoardDto> trelloBoardDtos =  trelloFacade.fetchTrelloBoards();

        //Then
        Assert.assertNotNull(trelloBoardDtos);
        Assert.assertEquals(0, trelloBoardDtos.size());
    }

    @Test
    public void shouldFetchTrelloBoards () {
        //Given
        List<TrelloListDto> trelloListDtoList =  new ArrayList<>();
        List<TrelloBoardDto> trelloBoardDto = new ArrayList<>();

        trelloListDtoList.add(new TrelloListDto("1","my_list",false));
        trelloBoardDto.add(new TrelloBoardDto("my_task","1",trelloListDtoList));

        List<TrelloList> trelloList =  new ArrayList<>();
        List<TrelloBoard> trelloBoard = new ArrayList<>();

        trelloList.add(new TrelloList("1", "my_list",false));
        trelloBoard.add(new TrelloBoard("my_task","1",trelloList));

        Mockito.when(trelloService.fetchTrelloBoards()).thenReturn(trelloBoardDto);
        Mockito.when(trelloMapper.mapToBoards(trelloBoardDto)).thenReturn(trelloBoard);
        Mockito.when(trelloMapper.mapToBoardsDto(Mockito.anyList())).thenReturn(trelloBoardDto);
        Mockito.when(trelloValidator.validateTrelloBoards(trelloBoard)).thenReturn(trelloBoard);

        //When

        List<TrelloBoardDto> trelloBoardDtos =  trelloFacade.fetchTrelloBoards();

        //Then
        Assert.assertNotNull(trelloBoardDtos);
        Assert.assertEquals(1, trelloBoardDtos.size());
        trelloBoardDtos.forEach(trelloBoardDto1 -> {
            Assert.assertEquals("1",trelloBoardDto1.getId());
            Assert.assertEquals("my_task",trelloBoardDto1.getName());

            trelloBoardDto1.getLists().forEach(trelloListDto -> {
                Assert.assertEquals("1",trelloListDto.getId());
                Assert.assertEquals("my_list",trelloListDto.getName());
                Assert.assertEquals(false,trelloListDto.isIsclosed());
            });
        });
    }
}
