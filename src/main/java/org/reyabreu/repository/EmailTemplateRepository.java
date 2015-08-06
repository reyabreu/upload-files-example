package org.reyabreu.repository;

import java.util.UUID;

import org.reyabreu.domain.EmailTemplate;
import org.springframework.data.repository.CrudRepository;

public interface EmailTemplateRepository extends
		CrudRepository<EmailTemplate, UUID> {

}
