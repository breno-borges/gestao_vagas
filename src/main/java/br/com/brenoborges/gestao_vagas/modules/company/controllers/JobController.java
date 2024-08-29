package br.com.brenoborges.gestao_vagas.modules.company.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.brenoborges.gestao_vagas.modules.company.dto.CreateJobDTO;
import br.com.brenoborges.gestao_vagas.modules.company.entities.JobEntity;
import br.com.brenoborges.gestao_vagas.modules.company.useCases.CreateJobUseCase;
import br.com.brenoborges.gestao_vagas.modules.company.useCases.ListAllJobsByCompanyIdUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/company/job")
public class JobController {

        @Autowired
        private CreateJobUseCase createJobUseCase;

        @Autowired
        private ListAllJobsByCompanyIdUseCase listAllJobsByCompanyIdUseCase;

        @PostMapping("/")
        @PreAuthorize("hasRole('COMPANY')")
        @Tag(name = "Vagas", description = "Informacoes das vagas")
        @Operation(summary = "Cadastro de vagas", description = "Essa funcao e responsavel por cadastrar as vagas dentro da empresa")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", content = {
                                        @Content(schema = @Schema(implementation = JobEntity.class))
                        })
        })

        @SecurityRequirement(name = "jwt_auth")
        public ResponseEntity<Object> create(@Valid @RequestBody CreateJobDTO createJobDTO,
                        HttpServletRequest request) {
                var companyId = request.getAttribute("company_id");

                try {
                        JobEntity jobEntity = JobEntity.builder()
                                        .benefits(createJobDTO.getBenefits())
                                        .companyId(UUID.fromString(companyId.toString()))
                                        .description(createJobDTO.getDescription())
                                        .level(createJobDTO.getLevel())
                                        .build();
                        JobEntity result = this.createJobUseCase.execute(jobEntity);

                        return ResponseEntity.ok().body(result);
                } catch (Exception e) {
                        return ResponseEntity.badRequest().body(e.getMessage());
                }
        }

        @GetMapping("/")
        @PreAuthorize("hasRole('COMPANY')")
        @Tag(name = "Vagas", description = "Informacoes das vagas")
        @Operation(summary = "Listagem de vagas cadastradas pela empresa", description = "Essa funcao e responsavel por listar todas as vagas cadastradas pela empresa")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", content = {
                                        @Content(array = @ArraySchema(schema = @Schema(implementation = JobEntity.class)))
                        })
        })

        @SecurityRequirement(name = "jwt_auth")
        public ResponseEntity<Object> listByCompany(HttpServletRequest request) {
                var companyId = request.getAttribute("company_id");

                try {
                        List<JobEntity> result = this.listAllJobsByCompanyIdUseCase
                                        .execute(UUID.fromString(companyId.toString()));
                        return ResponseEntity.ok().body(result);
                } catch (Exception e) {
                        return ResponseEntity.badRequest().body(e.getMessage());
                }
        }
}
