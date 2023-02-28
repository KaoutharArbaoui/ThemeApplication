package detect.theme;

import detect.theme.Service.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static detect.theme.Service.Calcul.IM;

//@SpringBootApplication
public class ThemeApplication {

    //public static void main(String[] args) throws IOException {
        //SpringApplication.run(ThemeApplication.class, args);


//++++++++++++++++++++++++++++++++++++ Partition ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        File Corpus=new File("src\\main\\java\\detect\\theme\\LesCorpus\\Corpus\\Corpus");
        //Partition.Pourcentage(Corpus);


//++++++++++++++++++++++++++++++++++++ Prétraitement +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        File Corpus_Apprentissage=new File("src\\main\\java\\detect\\theme\\LesCorpus\\Corpus\\Corpus_Apprentissage");
        File f_app_xml=new File("src\\main\\java\\detect\\theme\\LesCorpus\\Corpus_Mots_XML\\Corpus_Apprentissage_XML");

        //prétr pour le mot (por le lemme et stem il faut changer les rép
             //Lire_RepCorpus.lire_Corpus(Corpus_Apprentissage);
            //Prétraitement prétraitement=new Prétraitement();
            //prétraitement.Frenquent_Mot(f_app_xml);


//+++++++++++++++++++++++++++++++ Générer les vecteurs théme +++++++++++++++++++++++++++++++++++++++++++++++++++
        File f_test_mot=new File("src\\main\\java\\detect\\theme\\LesCorpus\\Corpus_Mots_XML\\Corpus_test_XML");
        File f_test_lemme=new File("src\\main\\java\\detect\\theme\\LesCorpus\\Corpus_Lemme_XML\\Corpus_test_XML");
        File f_test_stem=new File("src\\main\\java\\detect\\theme\\LesCorpus\\Corpus_Stem_XML\\Corpus_test_XML");

        File vec_theme_mot=new File("src\\main\\java\\detect\\theme\\LesCorpus\\Corpus_Mots_XML\\Vecteur_theme");
        File vec_theme_lemme=new File("src\\main\\java\\detect\\theme\\LesCorpus\\Corpus_Lemme_XML\\Vecteur_theme");
        File vec_theme_stem=new File("src\\main\\java\\detect\\theme\\LesCorpus\\Corpus_Stem_XML\\Vecteur_theme");

        File fréq_Mots = new File("src\\main\\java\\detect\\theme\\LesCorpus\\Corpus_Mots_XML\\FreqMots\\Mots_Fréq.txt");

        //Calcul.generate_vectors(f_app_xml,fréq_Mots,vec_theme_mot,4);


//++++++++++++++++++++++++++++++++ Détection du thème ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        File doc_mot=new File("src\\main\\java\\detect\\theme\\LesCorpus\\Corpus_Mots_XML\\Corpus_test_XML\\politique\\document_90−OUT−Lemma.xml");
        File doc_lemme=new File("src\\main\\java\\detect\\theme\\LesCorpus\\Corpus_Lemme_XML\\Corpus_test_XML\\politique\\document_90−OUT−Lemma.xml");
        File doc_stem=new File("src\\main\\java\\detect\\theme\\LesCorpus\\Corpus_stem_XML\\Corpus_test_XML\\politique\\document_90−OUT−Lemma.xml");

        //pour mot Calcul.affecter_theme(vec_theme_mot,doc_mot);
        // pour lemme Calcul.affecter_theme(vec_theme_lemme,doc_lemme);
        // pour stem Calcul.affecter_theme(vec_theme_stem,doc_stem);




//++++++++++++++++++++++++++++++++ Indicateurs de performances +++++++++++++++++++++++++++++++++++++++++++++++++

        // indi de perf pour lemme Indi_Perf.matriceconfusion(f_test_lemme,vec_theme_lemme)/Exactitude(matrice,(int) Calcul.number_of_files(f_test_lemme))
        // indi de perf pour stem Indi_Perf.matriceconfusion(f_test_stem,vec_theme_stem)/Exactitude(matrice,(int) Calcul.number_of_files(f_test_stem))
        // indi de perf pour mot
        /*int [][] matrice = Indi_Perf.matriceconfusion(f_test_mot,vec_theme_mot);
        for(int i=0;i<matrice.length;i++){
            for(int j=0;j<matrice.length;j++){
                System.out.print(matrice[i][j]+"\t");
            }
            System.out.print("\n");
        }
        float exa = Indi_Perf.Exactitude(matrice, (int) Calcul.number_of_files(f_test_mot));
        System.out.println("l'exactitude est : " + exa);

        double pré = Indi_Perf.PrecisionGlobal(matrice);
        System.out.println("Precision : " + pré);

        double rap = Indi_Perf.RappelGlobal(matrice);
        System.out.println("Rappel est : " + rap);

        double fms = Indi_Perf.FmesureGlobal(matrice);
        System.out.println("F_mesure est : " + fms);*/

   // }

}
