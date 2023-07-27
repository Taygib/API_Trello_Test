package config;

import org.aeonbits.owner.Config;

@Config.Sources({"classpath:trello.properties"})
public interface TrelloConfig extends Config {

    @Key("token")
    String token();

    @Key("boardID")
    String idBoard();
}