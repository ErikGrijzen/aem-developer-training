package biz.netcentric.cq.training.ui;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.adobe.cq.sightly.WCMUse;
import com.day.cq.commons.Filter;
import com.day.cq.wcm.api.Page;

public class ChildPageLister extends WCMUse {

    List<Page> children = new LinkedList<Page>();
    
    @Override
    public void activate() throws Exception {
        final String initial = getRequest().getParameter("initial");
        Iterator<Page> it = getCurrentPage().listChildren(new Filter<Page>() {

            @Override
            public boolean includes(Page page) {
                return initial == null || page.getTitle() != null && page.getTitle().startsWith(initial);
            }
            
        });
        while (it.hasNext()) {
            children.add(it.next());
        }
    }

    public List<Page> getChildren() {
        return children;
    }
}
