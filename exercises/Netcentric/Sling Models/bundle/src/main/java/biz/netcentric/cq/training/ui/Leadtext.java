package biz.netcentric.cq.training.ui;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;

@Model(adaptables = Resource.class)
public class Leadtext {

    @Inject
    @Optional
    private String text;

    @Inject
    @Optional
    @Named("jcr:title")
    private String title;

    public boolean isEmptyContent() {
        return text == null || text.isEmpty();
    }

    public String getText() {
        return text;
    }

    public String getTitle() {
        return title;
    }

}
