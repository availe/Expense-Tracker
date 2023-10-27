package Core;

public class Main {
    public static void main(String[] args) {
        PluginManager.getInstance().loadPlugins("src/main/java/plugins/");
        PluginManager.getInstance().executePlugins();
    }
}