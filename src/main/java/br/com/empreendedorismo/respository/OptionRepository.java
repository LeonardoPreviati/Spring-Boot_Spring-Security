package br.com.empreendedorismo.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.empreendedorismo.entity.Option;

@Repository
public interface OptionRepository extends JpaRepository<Option, Integer>{

}
