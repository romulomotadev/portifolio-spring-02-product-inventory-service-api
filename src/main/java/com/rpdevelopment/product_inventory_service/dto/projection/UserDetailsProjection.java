package com.rpdevelopment.product_inventory_service.dto.projection;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;


@Schema(description = "Projeção de dados do usuário para autenticação e autorização")
@JsonPropertyOrder({ "roleId", "username", "authority" })
public interface UserDetailsProjection {

    @Schema(description = "ID da role do usuário", example = "1")
    Long getRoleId();

    @Schema(description = "Nome de usuário", example = "admin")
    String getUsername();

    String getPassword(); // não documentar para Swagger (uso interno)

    @Schema(description = "Perfil de acesso do usuário", example = "ROLE_ADMIN")
    String getAuthority();
}
