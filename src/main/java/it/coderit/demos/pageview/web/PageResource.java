package it.coderit.demos.pageview.web;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import it.coderit.demos.pageview.data.repository.PageViewRepository;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/")
public class PageResource {

    private final Template page1;
    private final Template page2;
    private final PageViewRepository repository;

    public PageResource(Template page1, Template page2, PageViewRepository repository) {
        this.page1 = page1;
        this.page2 = page2;
        this.repository = repository;
    }


    // /page1
    @GET
    @Path("/page1")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance page1() {
        long pageViews = repository.incrementPageView("page1");
        return page1.data("visite", pageViews);
    }

    // /page2
    @GET
    @Path("/page2")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance page2() {
        long pageViews = repository.incrementPageView("page2");
        return page2.data("visite", pageViews);
    }
}
