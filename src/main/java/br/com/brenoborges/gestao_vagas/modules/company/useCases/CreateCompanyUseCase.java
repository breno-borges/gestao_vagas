package br.com.brenoborges.gestao_vagas.modules.company.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.brenoborges.gestao_vagas.modules.company.repositories.CompanyRepository;
import br.com.brenoborges.gestao_vagas.exceptions.UserFoundException;
import br.com.brenoborges.gestao_vagas.modules.company.entities.CompanyEntity;

@Service
public class CreateCompanyUseCase {

    @Autowired
    private CompanyRepository CompanyRepository;

    public CompanyEntity execute(CompanyEntity companyEntity) {
        this.CompanyRepository.findByUsernameOrEmail(companyEntity.getUsername(), companyEntity.getEmail())
                .ifPresent((user) -> {
                    throw new UserFoundException();
                });

        return this.CompanyRepository.save(companyEntity);
    }
}
