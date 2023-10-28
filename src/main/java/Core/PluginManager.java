package Core;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ModuleLogic.Module;

public final class PluginManager {
    private List<String> allDependencies = new ArrayList<>();
    private List<String> successfulDependencies = new ArrayList<>();
    private List<String> failedDependencies = new ArrayList<>();

    // setup for singleton class
    private static PluginManager firstInstance = null;
    private PluginManager() {}
    public static PluginManager getInstance() {
        if (firstInstance == null) firstInstance = new PluginManager();
        return  firstInstance;
    }

    // pluginManger's methods
    public void loadPlugins(String filePath) {
        // we're utilizing streams, note that .map isn't a hashmap but rather a higher ordered function used to generate a collection
        this.allDependencies = Util.getInstance().getFileNames(filePath).stream().map(temp -> "plugins." + temp.replace(".java", "")).collect(Collectors.toList());
    }

    public void executePlugins() {
        for (String fileName : allDependencies) {
            try {
                // load the class of unknown type
                Class<?> tempClass = Class.forName(fileName);
                // check if class implements Module
                if (Module.class.isAssignableFrom(tempClass)) {
                    Module module = (Module)tempClass.getDeclaredConstructor().newInstance();
                    successfulDependencies.add(fileName);
                    module.execute();
                }
            } catch (Exception e) {
                failedDependencies.add(String.valueOf(fileName));
                System.out.println("Error while executing " + fileName + "\nMethod in executePlugins() in PluginManager.java");
            }
        }
    }

    // getters and setters
    public List<String> getAllDependencies() {
        return allDependencies;
    }

    public void setAllDependencies(List<String> allDependencies) {
        this.allDependencies = allDependencies;
    }

}
