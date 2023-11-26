package br.com.brenoborges.gestao_vagas.modules.company.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.brenoborges.gestao_vagas.modules.company.repositories.CompanyRepository;
import br.com.brenoborges.gestao_vagas.exceptions.UserFoundException;
import br.com.brenoborges.gestao_vagas.modules.company.entities.CompanyEntity;

@Service
public class CreateCompanyUseCase {

    @Autowired
    private CompanyRepository CompanyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public CompanyEntity execute(CompanyEntity companyEntity) {
        this.CompanyRepository.findByUsernameOrEmail(companyEntity.getUsername(), companyEntity.getEmail())
                .ifPresent((user) -> {
                    throw new UserFoundException();
                });

        var password = passwordEncoder.encode(companyEntity.getPassword());
        companyEntity.setPassword(password);

        return this.CompanyRepository.save(companyEntity);
    }
}
