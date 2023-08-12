package com.davidandw190.emailnotifier.repository;

import com.davidandw190.emailnotifier.domain.Confirmation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfirmationRepository extends CrudRepository<Confirmation, Long> {

    Optional<Confirmation> findByToken(String token);

}
