package org.reyabreu.rest.resources;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.reyabreu.domain.EmailTemplate;
import org.reyabreu.rest.FilesUploadController;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class EmailTemplateResourceAssembler extends
		ResourceAssemblerSupport<EmailTemplate, EmailTemplateResource> {

	@Override
	public EmailTemplateResource toResource(EmailTemplate entity) {
		final EmailTemplateResource resource = createResourceWithId(
				entity.getId(), entity);
		
		ControllerLinkBuilder linkBuilder = linkTo(FilesUploadController.class)
				.slash(entity.getId()).slash("contents.htm");

		resource.name = entity.getName();
		resource.add(linkBuilder.withRel("contents"));
		return resource;
	}

	public EmailTemplateResourceAssembler() {
		super(FilesUploadController.class, EmailTemplateResource.class);
	}

}
