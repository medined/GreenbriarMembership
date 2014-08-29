package org.egreenbriar.reports;

import java.util.Set;
import java.util.TreeSet;
import org.egreenbriar.service.ZeroBlocksService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ZeroBlocks {

    private ZeroBlocksService zeroBlocksService = null;

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        ZeroBlocks driver = new ZeroBlocks();
        driver.process(args, context);
    }

    private void process(final String[] args, final ApplicationContext context) {
        Set<String> lines = new TreeSet<>();
        setZeroBlocksService(context.getBean("zeroBlocksService", ZeroBlocksService.class));
        getZeroBlocksService().getZeroBlocks();
    }

    public ZeroBlocksService getZeroBlocksService() {
        return zeroBlocksService;
    }

    public void setZeroBlocksService(ZeroBlocksService zeroBlocksService) {
        this.zeroBlocksService = zeroBlocksService;
    }

}
