package org.reyabreu.rest;

import java.io.IOException;
import java.util.UUID;

import javax.validation.Valid;

import org.reyabreu.domain.EmailTemplate;
import org.reyabreu.repository.EmailTemplateRepository;
import org.reyabreu.rest.resources.EmailTemplateResource;
import org.reyabreu.rest.resources.EmailTemplateResourceAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("v1/emailtemplates")
public class FilesUploadController {

	@Autowired
	EmailTemplateRepository repository;

	@Autowired
	EmailTemplateResourceAssembler emailTemplateResourceAssembler;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<String> getInfo() {
		return new ResponseEntity<String>("You can post a template here.",
				HttpStatus.OK);
	}

	@RequestMapping(value = "", method = RequestMethod.POST, consumes = "multipart/form-data", produces = "application/json")
	public ResponseEntity<EmailTemplateResource> create(
			@RequestParam("name") final String name,
			@RequestParam("contents") final MultipartFile file) {

		final EmailTemplate requestEntity = new EmailTemplate();
		requestEntity.setName(name);
		requestEntity.setContents(getContents(file));

		final EmailTemplate responseEntity = repository.save(requestEntity);
		final EmailTemplateResource responseResource = emailTemplateResourceAssembler
				.toResource(responseEntity);

		return new ResponseEntity<EmailTemplateResource>(responseResource,
				HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<EmailTemplateResource> get(
			@PathVariable("id") final String id) {
		final EmailTemplate requestEntity = repository.findOne(UUID.fromString(id));
		if (requestEntity == null) {
			throw new RuntimeException("id not found in the database.");
		}
		final EmailTemplateResource responseResource = emailTemplateResourceAssembler
				.toResource(requestEntity);
		return new ResponseEntity<EmailTemplateResource>(responseResource,
				HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}/contents.htm", method = RequestMethod.GET, produces = {
			"text/html", "application/json" })
	public ResponseEntity<byte[]> getContents(@PathVariable("id") final String id) {
		final EmailTemplate requestEntity = repository.findOne(UUID.fromString(id));
		if (requestEntity == null) {
			throw new RuntimeException("id not found in the database.");
		}
		final byte[] contents = requestEntity.getContents().getBytes();
		return new ResponseEntity<byte[]>(contents, HttpStatus.OK);
	}

	private String getContents(final MultipartFile file) {
		String contents;
		try {
			contents = new String(file.getBytes());
		} catch (IOException cause) {
			throw new RuntimeException("cannot retrieve file contents", cause);
		}
		return contents;
	}
}
