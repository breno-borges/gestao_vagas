package br.com.brenoborges.gestao_vagas.modules.candidate.dto;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileCandidateResponseDTO {

    @Schema(example = "Dev Java")
    private String description;
    @Schema(example = "breno")
    private String username;
    @Schema(example = "breno@email.com")
    private String email;
    @Schema(example = "Breno")
    private String name;
    private UUID id;
}
