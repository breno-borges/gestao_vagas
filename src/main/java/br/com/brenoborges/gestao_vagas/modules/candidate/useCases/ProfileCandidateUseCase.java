package br.com.brenoborges.gestao_vagas.modules.candidate.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.brenoborges.gestao_vagas.exceptions.UserNotFoundException;
import br.com.brenoborges.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;
import br.com.brenoborges.gestao_vagas.modules.candidate.repositories.CandidateRepository;

@Service
public class ProfileCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    public ProfileCandidateResponseDTO execute(UUID idCandidate) {
        var candidate = this.candidateRepository.findById(idCandidate)
                .orElseThrow(() -> {
                    throw new UserNotFoundException();
                });

        var candidateDTO = ProfileCandidateResponseDTO.builder()
                .description(candidate.getDescription())
                .email(candidate.getEmail())
                .name(candidate.getName())
                .username(candidate.getUsername())
                .id(candidate.getId())
                .build();
        return candidateDTO;
    }
}
