package com.nokia.webui.jpa;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = As.PROPERTY, property = "identifer")
@JsonSubTypes({ @JsonSubTypes.Type(value = Player.class, name = "Player")

})
public abstract class CommonRepository {

}
