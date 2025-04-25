package weblab.webproject4.utils;

import javax.management.*;
import javax.servlet.ServletContextListener;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;

public class MBeanRegistry implements ServletContextListener {
    private final Map<Class<?>, ObjectName> beans = new HashMap<>();

    public void registerBean(Object bean, String name) {
        try {
            var domain = bean.getClass().getPackageName();
            var type = bean.getClass().getSimpleName();
            var objName = new ObjectName(String.format("%s:type=%s, name=%s", domain, type, name));

            ManagementFactory.getPlatformMBeanServer().registerMBean(bean, objName);
            beans.put(bean.getClass(), objName);
        } catch (MalformedObjectNameException | InstanceAlreadyExistsException | MBeanRegistrationException |
                 NotCompliantMBeanException e) {
            e.getStackTrace();
        }
    }

    public void unregisterBean(Object bean) {
        if (!beans.containsKey(bean.getClass())) {
            throw new IllegalArgumentException("This bean has not been registered!");
        }

        try {
            ManagementFactory.getPlatformMBeanServer().unregisterMBean(beans.get(bean.getClass()));
        } catch (InstanceNotFoundException | MBeanRegistrationException e) {
            e.printStackTrace();
        }
    }
}
