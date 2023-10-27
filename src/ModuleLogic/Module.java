package ModuleLogic;

import java.util.List;

public interface Module {
    void execute();
    List<String> getDependencies();
    boolean isSelfContained();
}
