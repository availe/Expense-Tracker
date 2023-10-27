package Core;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public final class Util {
    // setup for singleton class
    private static Util firstInstance = null;
    private Util() {
        this.infoMap = new HashMap<>();
    }
    public static Util getInstance() {
        if (firstInstance == null) firstInstance = new Util();
        return  firstInstance;
    }

    // utilities' variables
    private Map<String, String> infoMap;
    private String txtPath;

    // utilities' methods
    public void readTxtFile(String txtFile) {
        File file = new File(txtFile);
        try {
            Scanner scanner = new Scanner(file);
            String line;
            String[] tempStoreValues;
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                tempStoreValues = line.split(":");
                if (infoMap.containsKey(tempStoreValues[0])) {
                    System.out.println("File already contained " + tempStoreValues[0] + " at least once before" + "\nOriginal value to be overwritten by duplicate value");
                }
                infoMap.put(tempStoreValues[0], tempStoreValues[1]);
                tempStoreValues[0] = tempStoreValues[1] = null;
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

    public List<String> getFileNames(String fileDirectory) {
        List<String> filesNameList = new ArrayList<>();
        File directory = new File(fileDirectory);
        File[] files = directory.listFiles();

        if (files == null) {
            System.out.println("Error: filesInDirectory returned null\nMethod getFileNames in Util.Java");
            return filesNameList;
        }

        for (File file : files) {
            filesNameList.add(file.getName());
        }
        return filesNameList;
    }

    // getter and setters
    public Map<String, String> getInfoMap() {
        return infoMap;
    }

    public void setInfoMap(Map<String, String> infoMap) {
        this.infoMap = infoMap;
    }

    public String getTxtPath() {
        return txtPath;
    }

    public void setTxtPath(String txtPath) {
        this.txtPath = txtPath;
    }
}
