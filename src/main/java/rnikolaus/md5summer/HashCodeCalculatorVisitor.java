package rnikolaus.md5summer;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigInteger;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rapnik
 */
public class HashCodeCalculatorVisitor extends SimpleFileVisitor<Path> {

    private final TreeMap<String, String> result = new TreeMap<>();
    private final Path startPath;
    private final byte[] byteBuffer = new byte[1024 * 1024];
     private final MessageDigest md5Digest;
    private final PrintStream os;
    private boolean run = true;

    public HashCodeCalculatorVisitor(Path startPath, PrintStream os) {
        this.startPath = startPath;
        this.os=os;
        try {
            md5Digest= MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }
    public Path getStartPath() {
        return startPath;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        if (!run)return FileVisitResult.TERMINATE;
        final String absolutePath = startPath.relativize(file).toString();
        try {
            String hash = calculateHash(file);
            
        if (os!=null)os.println(hash+" "+absolutePath);
            result.put(absolutePath, hash);
        } catch (Exception ex) {
             if (os!=null)os.println(ex);
        }
        return FileVisitResult.CONTINUE;
    }

    private  String calculateHash(final Path path) {
        try {
            md5Digest.reset();
            try (BufferedInputStream is = 
                    new BufferedInputStream(
                            Files.newInputStream(path, StandardOpenOption.READ))) {
                while (is.available() > 0) {
                    md5Digest.update(byteBuffer, 0, is.read(byteBuffer));
                }
            }
            BigInteger bigInt = new BigInteger(1, md5Digest.digest());
            return String.format("%032x", bigInt);
        } catch ( IOException ex) {
            Logger.getLogger(HashCodeCalculatorUtils.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        }
    }

    public TreeMap<String, String> getResult() {
        return result;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
         if (os!=null)os.println("Failed to access file: " + file);
        return FileVisitResult.CONTINUE;
    }
    
    public void stop(){
        run=false;
    }

}
