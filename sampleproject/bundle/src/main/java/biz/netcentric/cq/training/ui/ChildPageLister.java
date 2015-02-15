package biz.netcentric.cq.training.ui;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.adobe.cq.sightly.WCMUse;
import com.day.cq.commons.Filter;
import com.day.cq.wcm.api.Page;

public class ChildPageLister extends WCMUse {

    List<Page> children = new LinkedList<Page>();

    private String filter;
    
    @Override
    public void activate() throws Exception {
        filter = get("initial", String.class);

        Iterator<Page> it = getCurrentPage().listChildren(new Filter<Page>() {

            @Override
            public boolean includes(Page page) {
                return filter == null || page.getTitle() != null && page.getTitle().startsWith(filter);
            }
            
        });
        while (it.hasNext()) {
            children.add(it.next());
        }
    }

    public List<Page> getChildren() {
        return children;
    }

    public String getFilter() {
        return filter;
    }
}
