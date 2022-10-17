package pl.edu.pg.eti.kask.lab.portrait.manager;

import lombok.Getter;
import pl.edu.pg.eti.kask.lab.config.Config;
import pl.edu.pg.eti.kask.lab.portrait.entity.Portrait;
import pl.edu.pg.eti.kask.lab.portrait.repository.PortraitRepository;

import javax.enterprise.context.ApplicationScoped;
import java.io.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Getter
@ApplicationScoped
public class PortraitManager {
   private final String portraitsPath;
   private Set<String> portraits = new HashSet<>();
   private final String portraitExtension = "png";

   public PortraitManager() {
        portraitsPath = Config.properties.getProperty("portraits_path");
        loadFiles();
        System.out.println("loaded");
   }

   private void loadFiles() {
       File[] files = new File(portraitsPath).listFiles();

       for (File file : files) {
           System.out.println(file);
           String extension = file.getName().split("\\.")[1];
           System.out.println(extension);
           if (file.isFile() && extension.equals(portraitExtension)) {
               portraits.add(file.getName());
           }
       }
       System.out.println(portraits);
   }

   public Optional<Portrait> getPortrait(Long id)  {
       boolean isPresent = portraits.contains(getFullPortraitName(id));
       if (isPresent) {
           System.out.println("isPresent");
           File initialFile = new File(getFullPortraitPath(id));
           try(FileInputStream fis = new FileInputStream(initialFile)) {
               Portrait portrait = new Portrait(id, fis.readAllBytes());
               return Optional.of(portrait);
           } catch (IOException e) {
               System.out.println(e.getCause());
               System.out.println(e.getMessage());
               return Optional.empty();
           }
       }
       return Optional.empty();
   }

   public void addPortrait(Portrait portrait) {
       try(FileOutputStream fos = new FileOutputStream(getFullPortraitPath(portrait.getId()))) {
           fos.write(portrait.getPortrait());
           portraits.add(getFullPortraitName(portrait.getId()));
       } catch (IOException e) {
           throw new RuntimeException("General io exception during saving a portrait with id:" + portrait.getId());
       }
   }

   public void deletePortrait(Portrait portrait) {
       File toDelete = new File(getFullPortraitPath(portrait.getId()));
       toDelete.delete();
       portraits.remove(getFullPortraitName(portrait.getId()));
   }

   public void updatePortrait(Portrait portrait) {
       deletePortrait(portrait);
       addPortrait(portrait);
   }
   private String getFullPortraitName(Long id) {
       return String.format("%s.%s", id, portraitExtension);
   }

   private String getFullPortraitPath(Long id) {
       return String.format("%s/%s.%s", portraitsPath, id, portraitExtension);
   }

}
