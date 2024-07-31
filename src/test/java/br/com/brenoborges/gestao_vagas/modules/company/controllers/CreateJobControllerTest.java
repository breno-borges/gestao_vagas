package br.com.brenoborges.gestao_vagas.modules.company.controllers;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.com.brenoborges.gestao_vagas.modules.company.dto.CreateJobDTO;
import br.com.brenoborges.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.brenoborges.gestao_vagas.modules.company.repositories.CompanyRepository;
import br.com.brenoborges.gestao_vagas.utils.TestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CreateJobControllerTest {

        private MockMvc mvc; // Instância MockMvc que vai simular as requisições Http

        @Autowired
        private WebApplicationContext context; // Contexto da aplicação web

        @Autowired
        private CompanyRepository companyRepository;

        @Before // Antes de cada método de teste, irá executar esse método.
        // Método para preparar o ambiente de teste para o MockMvc simular as
        // requisições.
        public void setup() {
                mvc = MockMvcBuilders
                                .webAppContextSetup(context)
                                .apply(SecurityMockMvcConfigurers.springSecurity()) // Aplica mock do spring security
                                .build();
        }

        @Test
        @DisplayName("Should be able to create a new job")
        public void shouldBeAbleToCreateANewJob() throws Exception {

                CompanyEntity company = CompanyEntity.builder()
                                .name("COMPANY_NAME")
                                .description("COMPANY_DESCRIPTION")
                                .email("enail@company.com")
                                .username("COMPANY_USERNAME")
                                .password("1234567890")
                                .build();

                this.companyRepository.saveAndFlush(company);

                CreateJobDTO createJobDTO = CreateJobDTO.builder()
                                .benefits("BENEFITS_TEST")
                                .description("DESCRIPTION_TESTE")
                                .level("LEVEL_TESTE")
                                .build();

                mvc.perform(MockMvcRequestBuilders.post("/company/job/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtils.objectToJSON(createJobDTO))
                                .header("Authorization", TestUtils.generateToekn(company.getId(), "JAVACOMPANY_@123#")))
                                .andExpect(MockMvcResultMatchers.status().isOk());

        }

        @Test
        @DisplayName("Should not be able to create a new job if company not found")
        public void ShouldNotBeAbleToCreateANewJobIfCompanyNotFound() throws Exception {
                CreateJobDTO createJobDTO = CreateJobDTO.builder()
                                .benefits("BENEFITS_TEST")
                                .description("DESCRIPTION_TESTE")
                                .level("LEVEL_TESTE")
                                .build();

                mvc.perform(MockMvcRequestBuilders.post("/company/job/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtils.objectToJSON(createJobDTO))
                                .header("Authorization",
                                                TestUtils.generateToekn(UUID.randomUUID(), "JAVACOMPANY_@123#")))
                                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        }
}