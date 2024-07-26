package br.com.brenoborges.gestao_vagas.modules.candidate.useCases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.brenoborges.gestao_vagas.exceptions.JobNotFoundException;
import br.com.brenoborges.gestao_vagas.exceptions.UserNotFoundException;
import br.com.brenoborges.gestao_vagas.modules.candidate.entities.ApplyJobEntity;
import br.com.brenoborges.gestao_vagas.modules.candidate.entities.CandidateEntity;
import br.com.brenoborges.gestao_vagas.modules.candidate.repositories.ApplyJobRepository;
import br.com.brenoborges.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import br.com.brenoborges.gestao_vagas.modules.company.entities.JobEntity;
import br.com.brenoborges.gestao_vagas.modules.company.repositories.JobRepository;

@ExtendWith(MockitoExtension.class) // Extensão do JUnit para falar o que ele quer que utilize.
public class ApplyJobUseCaseTest {

    @InjectMocks // Faz a injeção mock da classe
    private ApplyJobCandidateUseCase applyJobCandidateUseCase;

    @Mock // Injeção mock que é dependencia do InjectMocks
    private CandidateRepository candidateRepository;

    @Mock
    private JobRepository jobRepository;

    @Mock
    private ApplyJobRepository applyJobRepository;

    @Test
    @DisplayName("Should not be able to apply job with candidate not found")
    public void shouldNotBeAbleToApplyJobWithCandidateNotFound() {
        try {
            applyJobCandidateUseCase.execute(null, null);
        } catch (Exception e) {
            // Verifica se a exceção acima é a experada.
            assertThat(e).isInstanceOf(UserNotFoundException.class);
        }
    }

    @Test
    @DisplayName("Should not be able to apply job with job not found")
    public void shouldNotBeAbleToApplyJobWithJobNotFound() {
        UUID idCandidate = UUID.randomUUID();
        CandidateEntity candidate = new CandidateEntity();
        candidate.setId(idCandidate);

        // Quando fizer a busca pelo Id, retorna o candidato.
        when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(candidate));

        try {
            applyJobCandidateUseCase.execute(idCandidate, null);
        } catch (Exception e) {
            // Verifica se a exceção acima é a experada.
            assertThat(e).isInstanceOf(JobNotFoundException.class);
        }
    }

    @Test
    @DisplayName("Should be able to create a new apply job")
    public void shouldBeAbleToCreateANewApplyJob() {
        UUID idCandidate = UUID.randomUUID();
        UUID idJob = UUID.randomUUID();

        ApplyJobEntity applyJob = ApplyJobEntity.builder()
                .candidateId(idCandidate)
                .jobId(idJob)
                .build();

        ApplyJobEntity applyJobCreated = ApplyJobEntity.builder()
                .id(UUID.randomUUID()).build();

        // Quando achar o Id, retorna o objeto
        when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(new CandidateEntity()));

        when(jobRepository.findById(idJob)).thenReturn(Optional.of(new JobEntity()));

        when(applyJobRepository.save(applyJob)).thenReturn(applyJobCreated);

        ApplyJobEntity result = applyJobCandidateUseCase.execute(idCandidate, idJob);

        assertThat(result).hasFieldOrProperty("id"); // Valida se tem a propriedade Id.
        assertNotNull(result.getId()); // Valida se o Id não é nulo.
    }
}
