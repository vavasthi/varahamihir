package com.avasthi.varahamihir.identityserver.services;

import com.avasthi.varahamihir.identityserver.entities.Equation;
import com.avasthi.varahamihir.identityserver.entities.Recipe;
import com.avasthi.varahamihir.identityserver.repositories.EquationRepository;
import com.avasthi.varahamihir.identityserver.repositories.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EquationService {
    private final EquationRepository equationRepository;

    public Optional<Equation> findOne(UUID id) {
        return equationRepository.findById(id);
    }

    public Page<Equation> findAll(int page, int size) {
        return equationRepository.findAll(PageRequest.of(page, size));
    }

    public Optional<Equation> save(Equation equation) {
        equation = equationRepository.save(equation);
        return Optional.of(equation);
    }
    public List<Equation> save(List<Equation> equations) {
        return equationRepository.saveAll(equations);
    }
}
