package br.com.brenoborges.gestao_vagas.modules.candidate.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.brenoborges.gestao_vagas.exceptions.JobNotFoundException;
import br.com.brenoborges.gestao_vagas.exceptions.UserNotFoundException;
import br.com.brenoborges.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import br.com.brenoborges.gestao_vagas.modules.company.repositories.JobRepository;

@Service
public class ApplyJobCandidateUseCase {

    @Autowired
    CandidateRepository candidateRepository;

    @Autowired
    JobRepository jobRepository;

    public void execute(UUID idCandidate, UUID idJob) {
        // Verifica se o candidato foi encontrato
        this.candidateRepository.findById(idCandidate)
                .orElseThrow(() -> new UserNotFoundException());

        // Verifica se o job foi encontrato
        this.jobRepository.findById(idJob)
                .orElseThrow(() -> new JobNotFoundException());
    }
}
