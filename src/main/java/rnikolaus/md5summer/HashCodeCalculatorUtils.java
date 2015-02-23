package rnikolaus.md5summer;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rapnik
 */
public class HashCodeCalculatorUtils {

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("You may use the  commandline argument <directory> "
                    + "to run in non-interactive mode.");
            ChooseModeDialog c = new ChooseModeDialog(null, false);
            c.setVisible(true);
        } else {
            String directory = args[0];
            File dir = new File(directory);
            if (!dir.exists() || !dir.isDirectory()) {
                throw new IllegalArgumentException("Value for <directory> is invalid");
            }
            getHashCodes(directory);
        }
    }

    public static Map<String, String> getHashCodes(String p) throws IOException {
        return getHashCodes(p, System.out);

    }

    public static Map<String, String> getHashCodes(String p, OutputStream os) {
        final PrintStream printStream = new PrintStream(os);
        final HashCodeCalculatorVisitor hashCodeCalculator = new HashCodeCalculatorVisitor(Paths.get(p).normalize(), printStream);
        try {
            Files.walkFileTree(hashCodeCalculator.getStartPath(), hashCodeCalculator);
        } catch (IOException ex) {
            Logger.getLogger(HashCodeCalculatorUtils.class.getName()).log(Level.SEVERE, null, ex);
            printStream.println(ex);
        }
        return hashCodeCalculator.getResult();

    }

    public static void writeMap(Map<String, String> map, Path f) {

        List<String> x = new ArrayList<>();
        for (Map.Entry<String, String> e : map.entrySet()) {
                final String name = e.getValue() + " " + e.getKey();
                x.add(name);
               
            }
//        BufferedWriter bf = null;
//
//        try {
//            bf = new BufferedWriter(new FileWriter(f));
//            for (String s:x){
//                 bf.append(s);
//                bf.newLine();
//            }
//
//        } catch (IOException ex) {
//            Logger.getLogger(HashCodeCalculatorUtils.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            try {
//                if (bf != null) {
//                    bf.close();
//                }
//            } catch (IOException ex) {
//                Logger.getLogger(HashCodeCalculatorUtils.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }

        try {
            Files.write(f, x,Charset.defaultCharset(), StandardOpenOption.CREATE_NEW);
        } catch (IOException ex) {
            Logger.getLogger(HashCodeCalculatorUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Map<String, String> readMap(Path file) {
        Map<String, String> result = new TreeMap<>();
        try {
            List<String> lines = Files.readAllLines(file,Charset.defaultCharset());
            for (String line:lines){
                StringTokenizer st = new StringTokenizer(line, " ");
                if (st.countTokens() < 2) {
                    continue;
                }
                String hash = st.nextToken();
                String name = st.nextToken();
                while (st.hasMoreTokens()) {
                    name += " " + st.nextToken();
                }
                result.put(name, hash);
            }
        } catch (IOException ex) {
            Logger.getLogger(HashCodeCalculatorUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
//        BufferedReader br = null;
//        try {
//
//            br = new BufferedReader(new FileReader(file));
//            while (br.ready()) {
//                String line = br.readLine();
//                StringTokenizer st = new StringTokenizer(line, " ");
//                if (st.countTokens() < 2) {
//                    continue;
//                }
//                String hash = st.nextToken();
//                String name = st.nextToken();
//                while (st.hasMoreTokens()) {
//                    name += " " + st.nextToken();
//                }
//                result.put(name, hash);
//            }
//            return result;
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(HashCodeCalculatorUtils.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(HashCodeCalculatorUtils.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            try {
//                if (br != null) {
//                    br.close();
//                }
//            } catch (IOException ex) {
//                Logger.getLogger(HashCodeCalculatorUtils.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
        return result;
    }

    /**
     *
     * @param m1
     * @param m2
     * @return a Map that has all the values from m1 that m2 doesn't have
     */
    public static Map<String, String> getNonExistant(Map<String, String> m1, Map<String, String> m2) {
        Map<String, String> result = new TreeMap<>();
        Set<String> paths = new TreeSet<>(m1.keySet());
        paths.removeAll(m2.keySet());
        for (String path : paths) {
            result.put(path, m1.get(path));
        }
        return result;
    }

    public static Map<String, String> getCreated(Map<String, String> oldMap, Map<String, String> newMap) {
        return getNonExistant(newMap, oldMap);
    }

    public static Map<String, String> getDeleted(Map<String, String> oldMap, Map<String, String> newMap) {
        return getNonExistant(oldMap, newMap);

    }

    public static Map<String, String> getChanged(Map<String, String> oldMap, Map<String, String> newMap) {
        Map<String, String> result = new TreeMap<>();
        Set<String> paths = new TreeSet<>(oldMap.keySet());
        paths.retainAll(newMap.keySet());
        for (String path : paths) {
            String hashCodeOld = oldMap.get(path);
            String hashCodeNew = newMap.get(path);
            if (!hashCodeOld.equals(hashCodeNew)) {
                result.put(path, hashCodeNew);
            }
        }
        return result;
    }

}
