package biz.netcentric.cq.training.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import biz.netcentric.cq.training.RepositoryService;

import com.adobe.cq.sightly.WCMUse;



public class RepositoryComponent extends WCMUse {

    private RepositoryService repoService;
    
    private static final Logger LOG = LoggerFactory.getLogger(RepositoryComponent.class);
    
    @Override
    public void activate() throws Exception {
        this.repoService = this.getSlingScriptHelper().getService(RepositoryService.class);
        LOG.info("Repository Service = {}", repoService);
    }
    
    public String getRepositoryName() {
        return repoService.getRepositoryName();
    }

    public String getRepositoryVersion() {
        return repoService.getRepositoryVersion();
    }
}
