package br.edu.fei.macrow.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.fei.macrow.entities.ClienteEntity;

public interface ClienteRepository extends JpaRepository<ClienteEntity, Integer>{

}
