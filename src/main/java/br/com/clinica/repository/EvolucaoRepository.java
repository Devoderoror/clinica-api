package br.com.clinica.repository;

import br.com.clinica.model.Evolucao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EvolucaoRepository extends JpaRepository<Evolucao, Long> {
    List<Evolucao> findBySessaoId(Long sessaoId);
}
