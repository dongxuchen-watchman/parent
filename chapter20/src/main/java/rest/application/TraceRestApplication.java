package rest.application;

import org.activiti.rest.common.application.ActivitiRestApplication;
import org.activiti.rest.common.filter.JsonpFilter;
import org.activiti.rest.diagram.application.DiagramServicesInit;
import org.restlet.Restlet;
import org.restlet.routing.Router;

/**
 * Created by Administrator on 2019/6/3.
 */
public class TraceRestApplication extends ActivitiRestApplication{
    public TraceRestApplication(){
        super();
    }

    @Override
    public synchronized Restlet createInboundRoot() {
        Router router = new Router(getContext());
        DiagramServicesInit.attachResources(router);
        JsonpFilter jsonpFilter = new JsonpFilter(getContext());
        jsonpFilter.setNext(router);
        return  jsonpFilter;

    }
}
