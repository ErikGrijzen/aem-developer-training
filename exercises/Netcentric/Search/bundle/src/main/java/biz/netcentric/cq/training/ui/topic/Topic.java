package biz.netcentric.cq.training.ui.topic;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.jcr.query.Query;

import org.apache.sling.api.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUse;
import com.day.cq.wcm.api.Page;

public class Topic extends WCMUse {
    
    private String query;
    
    private static final Logger LOG = LoggerFactory.getLogger(Topic.class);
    
    @Override
    public void activate() throws Exception {
        if (getProperties().get("topic") != null) {
            query = "SELECT parent.* FROM [cq:Page] AS parent INNER JOIN [nt:base] AS child ON ISCHILDNODE(child,parent) WHERE CONTAINS(child.[jcr:description],  '" + getProperties().get("topic") + "')";
        }
        LOG.debug("Query = {}", query);
    }

    public List<Result> getResults() {
        List<Result> results = new LinkedList<Result>();
        if (query != null) {
            long start = System.currentTimeMillis();
            Iterator<Resource> resources = getResourceResolver().findResources(query, Query.JCR_SQL2);
            while (resources.hasNext()) {
                Resource resource = resources.next();
                Page page = resource.adaptTo(Page.class);
                if (page != null) {
                    results.add(new Result(page.getPath(), page.getTitle()));
                } else {
                    LOG.debug("Resource {} is not a Page", resource);
                }
            }
            long elapsed = System.currentTimeMillis() - start;
            LOG.debug("Query execution and processing took {}ms", elapsed);
        }
        return results;
    }
    
    public static class Result {

        private String path;
        private String title;
        
        public Result(String path, String title) {
            this.path = path;
            this.title = title;
        }

        public String getPath() {
            return path;
        }

        public String getTitle() {
            return title != null ? title : "No title";
        }
        
    }
    
}
