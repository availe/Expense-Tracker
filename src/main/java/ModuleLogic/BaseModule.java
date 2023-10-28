package ModuleLogic;

import java.util.Collections;
import java.util.List;

public abstract class BaseModule implements Module {
    private final List<String> dependencies;
    protected BaseModule() {
        this.dependencies = Collections.emptyList();
    }
    protected BaseModule(List<String> dependencies) {
        this.dependencies = dependencies;
    }

    public List<String> getDependencies() {
        return dependencies;
    }

    public boolean isSelfContained() {
        return dependencies.isEmpty();
    }
    public abstract void execute();
}
