package com.fiap.forkup.clean.arch.infra.persistence.jpa.repository;

import com.fiap.forkup.clean.arch.infra.persistence.jpa.entity.EnderecoJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EnderecoRepository extends JpaRepository<EnderecoJpaEntity, UUID> {



}
