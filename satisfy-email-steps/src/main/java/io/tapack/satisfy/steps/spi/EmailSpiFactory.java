package io.tapack.satisfy.steps.spi;

import io.tapack.satisfy.steps.email.EmailSenderSMTPS;
import io.tapack.satisfy.steps.email.FakeEmailServerRunnerImpl;
import io.tapack.satisfy.steps.email.InboxMessagesInspectorPOP3S;
import org.apache.commons.lang3.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.ServiceLoader;

import static io.tapack.satisfy.email.props.PropertyAccessor.*;

public class EmailSpiFactory {
    private static Logger LOG = LoggerFactory.getLogger(EmailSpiFactory.class);

    private EmailSpiFactory() {
    }

    public static FakeEmailServerRunner getFakeEmailServerRunner() {
        return loadProvider(getUseFakeProperty(), FakeEmailServerRunner.class, FakeEmailServerRunnerImpl.class);
    }

    public static EmailSender getEmailSender() {
        return loadProvider(getSendingProtocol(), EmailSender.class, EmailSenderSMTPS.class);
    }

    public static InboxMessagesInspector getInboxMessagesInspector() {
        return loadProvider(getReceiveProtocol(), InboxMessagesInspector.class, InboxMessagesInspectorPOP3S.class);
    }

    private static <LoadType extends LoadAcceptor> LoadType loadProvider(String param, Class<LoadType> typeClass, Class<? extends LoadType> basicImplClass) {
        ServiceLoader<LoadType> loadTypes = ServiceLoader.load(typeClass);
        for (LoadType loadType : loadTypes) {
            if (loadType.accept(param)) {
                LOG.debug("LOADED IMPLEMENTATION FOR " + typeClass.getSimpleName() + " IS " + loadType.getClass().getName());
                return loadType;
            }
        }
        return createBasicImplInstance(basicImplClass);
    }

    private static <LoadType extends LoadAcceptor> LoadType createBasicImplInstance(Class<? extends LoadType> basicImplClass) {
        LoadType basicImpl = null;
        try {
            basicImpl = basicImplClass.newInstance();
        } catch (InstantiationException e) {
            logError(basicImplClass, e);
        } catch (IllegalAccessException e) {
            logError(basicImplClass, e);
        }
        logWarning(basicImplClass, basicImpl);
        return basicImpl;
    }

    private static <LoadType extends LoadAcceptor> void logWarning(Class<? extends LoadType> basicImplClass, LoadType basicImpl) {
        StringBuilder warn = new StringBuilder();
        warn.append("ServiceLoader can not find acceptable implementation, the instance of ")
                .append("\n")
                .append(basicImpl == null ? "null" : basicImpl.getClass().getName())
                .append(" will be returned for ")
                .append(getSimpleInterfaceName(basicImplClass));
        LOG.warn(warn.toString());
    }

    private static String getSimpleInterfaceName(Class<?> basicImplClass) {
        List<Class<?>> list = ClassUtils.getAllInterfaces(basicImplClass);
        for (Class interfaceImplemented : list) {
            if (!interfaceImplemented.getSimpleName().equals("LoadAcceptor"))
                return interfaceImplemented.getSimpleName();
        }
        return "LoadAcceptor";
    }

    private static <LoadType extends LoadAcceptor> void logError(Class<? extends LoadType> basicImplClass, Exception e) {
        LOG.error(" Can't create basicImpl for class" + basicImplClass.getName() + "  " + e.getCause().toString());
    }
}