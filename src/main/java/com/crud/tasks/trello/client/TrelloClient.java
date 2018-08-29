package com.crud.tasks.trello.client;

import com.crud.tasks.domain.CreatedTrelloCard;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class TrelloClient {

    @Value("${trello.api.endpoint.prod}")
    private String trelloApiEndpoint;

    @Value("${trello.app.key}")
    private String trelloAppKey;

    @Value("${trello.app.token}")
    private String trelloToken;

    @Value("${trello.app.username}")
    private String trelloUsername;

    @Autowired
    private RestTemplate restTemplate;

    private URI getUriToTrello(){
        return  UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint + "/members/" + trelloUsername + "/boards")
                .queryParam("key", trelloAppKey)
                .queryParam("token", trelloToken)
                .queryParam("lists", "all")
                .build().encode().toUri();


    }

    public List<TrelloBoardDto> getTrelloBoards(){

        //TrelloBoardDto[] boardsResponse = restTemplate.getForObject("htpps://api.trello.com/1/mmebers/krzysztofwasilewski10/boards"
        //      ,TrelloBoardDto[].class);
        //TrelloBoardDto[] boardsResponse = restTemplate.getForObject(trelloEndpoint + "/members/krzysztofwasilewski10/boards"
        //        + "?key=" trelloAppKey + "?token=" trelloToken,
        //        TrelloBoardDto[].class);
        /*URI url = UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint + "/members/" + trelloUsername + "/boards")
                .queryParam("key", trelloAppKey)
                .queryParam("token", trelloToken).build().encode().toUri();*/

        TrelloBoardDto[] boardsResponse = restTemplate.getForObject(getUriToTrello(), TrelloBoardDto[].class);

        /*
        if (boardsResponse != null){
            return Arrays.asList(boardsResponse);
        }
        return new ArrayList<>();
        */
        //return Optional.ofNullable(Arrays.asList(boardsResponse)).orElse(new ArrayList<>());

        TrelloBoardDto[] tmp = Optional.ofNullable(boardsResponse).orElse(new TrelloBoardDto[0]);
        return Arrays.asList(tmp);
    }

    public CreatedTrelloCard createNewCard(TrelloCardDto trelloCardDto){
        URI url = UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint + "/cards")
                .queryParam("key" , trelloAppKey)
                .queryParam("token" , trelloToken)
                .queryParam("name" , trelloCardDto.getName())
                .queryParam("desc" , trelloCardDto.getDescription())
                .queryParam("pos" , trelloCardDto.getPos())
                .queryParam("idList" , trelloCardDto.getListId())
                .build()
                .encode()
                .toUri();

        return restTemplate.postForObject(url , null,CreatedTrelloCard.class);
    }

}
