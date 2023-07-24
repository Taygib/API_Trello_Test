package config;

import org.aeonbits.owner.Config;

@Config.Sources({"classpath:trello.properties"})
public interface DriverConfig extends Config {

    @Key("token")
    @DefaultValue("645c12a850d5fdbf2f1fbd5b%2FATTShNfDZQufzmsN6o5oSN2HhWzj27ywLiZF6k3FYpUs7KcEYEYEGQPtHEfaVRVaF9vi36DA6283")
    String token();

    @Key("boardID")
    @DefaultValue("645c13607c2dc8c246aa821d")
    String idBoard();
}