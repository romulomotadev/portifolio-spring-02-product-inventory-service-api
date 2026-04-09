package com.rpdevelopment.product_inventory_service.dto.projection;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonPropertyOrder({ "Id", "username", "password", "Authority" })
public interface UserDetailsProjection {

    Long getRoleId();
    String getUsername();
    String getPassword();
    String getAuthority();

}
