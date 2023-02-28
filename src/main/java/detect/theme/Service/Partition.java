package detect.theme.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class Partition {

    public Partition(){

    }

    //partitionner avec 80% apprentissage et 20% test
    public static void Pourcentage(File corpus) throws IOException {
        String str;
        double partie_app=0.8;
        double partie_test=0.2;
        for (File f : Objects.requireNonNull(corpus.listFiles())) {
            if (f.isDirectory()) {
                str = f.getPath().replace("src\\main\\java\\detect\\theme\\Corpus\\", "");
                File f1 = new File("src\\main\\java\\detect\\theme\\Corpus_test\\" + str );
                //f1.mkdir();
                int app_size = (int) (Calcul.number_of_files(f)*partie_app);
                int test_size = (int) (Calcul.number_of_files(f)*partie_test);
                for(int i=app_size+1;i<=app_size+test_size;i++){
                    File f3 = new File(f+"\\document_"+i+".txt");
                    File f2 = new File(f1 +"\\document_"+i+".txt");
                    f2.createNewFile();
                    Files.copy(f3.toPath(), f2.toPath(),REPLACE_EXISTING);
                }
            }
        }
    }

    //partition avec cross validation
    public static void cross_validation(File corpus) throws IOException {
        File[] fichiers = corpus.listFiles();
        int nbPlis = 5;
        for (int i = 0; i < nbPlis; i++) {
            // Sélectionne l'ensemble de test pour ce pli
            List<File> ensembleTest = Arrays.asList(fichiers).subList(i * fichiers.length / nbPlis, (i + 1) * fichiers.length / nbPlis);

            // Sélectionne l'ensemble d'entraînement pour ce pli
            List<File> ensembleEntrainement = new ArrayList<File>(Arrays.asList(fichiers));
            ensembleEntrainement.removeAll(ensembleTest);

            // Entraîne et évalue le modèle sur cet ensemble
            //double performance = entrainerEtEvaluer(ensembleEntrainement, ensembleTest);

            // Ajoute la performance à la liste des performances
            //performances.add(performance);
        }

    }
}
