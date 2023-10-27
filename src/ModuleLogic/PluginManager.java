package ModuleLogic;

import javax.swing.text.Utilities;
import java.util.List;
import Core.Util;

public final class PluginManager {
    private List<String> successfulDependencies;
    private List<String> FailedDependenciesPreComp;
    private List<String> FailedDependenciesPostComp;

    // setup for singleton class
    private static PluginManager firstInstance = null;
    private PluginManager() {}
    public static PluginManager getInstance() {
        if (firstInstance == null) firstInstance = new PluginManager();
        return  firstInstance;
    }

    // pluginManger's methods
    public void loadPlugins(String txtFile) {
        Util.getInstance().readTxtFile(txtFile);
    }


    // getters and setters
    public List<String> getSuccessfulDependencies() {
        return successfulDependencies;
    }

    public List<String> getFailedDependenciesPreComp() {
        return FailedDependenciesPreComp;
    }

    public List<String> getFailedDependenciesPostComp() {
        return FailedDependenciesPostComp;
    }

}
