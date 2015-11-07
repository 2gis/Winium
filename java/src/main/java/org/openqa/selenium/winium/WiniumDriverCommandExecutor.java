package org.openqa.selenium.winium;

import com.google.common.base.Throwables;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.*;
import org.openqa.selenium.remote.http.HttpMethod;
import org.openqa.selenium.remote.service.DriverCommandExecutor;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


/**
 * {@link DriverCommandExecutor} that understands WiniumDriver specific commands.
 */
public class WiniumDriverCommandExecutor extends HttpCommandExecutor {
    private static final Map<String, CommandInfo> WINIUM_COMMAND_NAME_TO_URL;

    private final WiniumDriverService service;

    static {
        WINIUM_COMMAND_NAME_TO_URL = new HashMap<String, CommandInfo>();

        WINIUM_COMMAND_NAME_TO_URL.put("findDataGridCell",
                new CommandInfo("/session/:sessionId/element/:id/datagrid/cell/:row/:column", HttpMethod.POST));
        WINIUM_COMMAND_NAME_TO_URL.put("getDataGridColumnCount",
                new CommandInfo("/session/:sessionId/element/:id/datagrid/column/count", HttpMethod.POST));
        WINIUM_COMMAND_NAME_TO_URL.put("getDataGridRowCount",
                new CommandInfo("/session/:sessionId/element/:id/datagrid/row/count", HttpMethod.POST));
        WINIUM_COMMAND_NAME_TO_URL.put("scrollToDataGridCell",
                new CommandInfo("/session/:sessionId/element/:id/datagrid/scroll/:row/:column", HttpMethod.POST));
        WINIUM_COMMAND_NAME_TO_URL.put("selectDataGridCell",
                new CommandInfo("/session/:sessionId/element/:id/datagrid/select/:row/:column", HttpMethod.POST));

        WINIUM_COMMAND_NAME_TO_URL.put("scrollToListBoxItem",
                new CommandInfo("/session/:sessionId/element/:id/listbox/scroll", HttpMethod.POST));

        WINIUM_COMMAND_NAME_TO_URL.put("findMenuItem",
                new CommandInfo("/session/:sessionId/element/:id/menu/item/:path", HttpMethod.POST));
        WINIUM_COMMAND_NAME_TO_URL.put("selectMenuItem",
                new CommandInfo("/session/:sessionId/element/:id/menu/select/:path", HttpMethod.POST));

        WINIUM_COMMAND_NAME_TO_URL.put("isComboBoxExpanded",
                new CommandInfo("/session/:sessionId/element/:id/combobox/expanded", HttpMethod.POST));
        WINIUM_COMMAND_NAME_TO_URL.put("expandComboBox",
                new CommandInfo("/session/:sessionId/element/:id/combobox/expand", HttpMethod.POST));
        WINIUM_COMMAND_NAME_TO_URL.put("collapseComboBox",
                new CommandInfo("/session/:sessionId/element/:id/combobox/collapse", HttpMethod.POST));
        WINIUM_COMMAND_NAME_TO_URL.put("findComboBoxSelectedItem",
                new CommandInfo("/session/:sessionId/element/:id/combobox/items/selected", HttpMethod.POST));
        WINIUM_COMMAND_NAME_TO_URL.put("scrollToComboBoxItem",
                new CommandInfo("/session/:sessionId/element/:id/combobox/scroll", HttpMethod.POST));
    }

    public WiniumDriverCommandExecutor(WiniumDriverService driverService) {
        super(WINIUM_COMMAND_NAME_TO_URL, driverService.getUrl());
        service = driverService;
    }

    public WiniumDriverCommandExecutor(URL remoteUrl) {
        super(WINIUM_COMMAND_NAME_TO_URL, remoteUrl);
        service = null;
    }

    @Override
    public Response execute(Command command) throws IOException {
        if (service != null) {
            if (DriverCommand.NEW_SESSION.equals(command.getName())) {
                service.start();
            }
        }


        try {
            return super.execute(command);
        } catch (Throwable t) {
            Throwable rootCause = Throwables.getRootCause(t);
            if (rootCause instanceof ConnectException && "Connection refused".equals(rootCause.getMessage()) &&
                    ((service == null) || (!service.isRunning()))) {
                throw new WebDriverException("The driver server has unexpectedly died!", t);
            }
            Throwables.propagateIfPossible(t);
            throw new WebDriverException(t);
        } finally {
            if ((service != null) && (DriverCommand.QUIT.equals(command.getName()))) {
                service.stop();
            }
        }
    }
}
