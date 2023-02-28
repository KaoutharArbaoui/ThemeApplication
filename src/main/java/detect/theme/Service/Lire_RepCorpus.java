package detect.theme.Service;

import java.io.File;
import java.io.IOException;

public class Lire_RepCorpus {

    //Méthode utilisé dans lire_Corpus pour générer les fichier XML pour les lemmes
    /*public static void AlKhalil_XML_lemme(File file, String pth) {
        String str;
        String new_file;
        str= file.getName();
        System.out.println(str);
        System.out.println(file.getPath());
        new_file=str.replace(".txt","");
        System.out.println(new_file);
        net.oujda_nlp_team.ADATAnalyzer.getInstance().processLemmatization(file.getPath(),
                "utf-8",
                pth+"/"+new_file+"−OUT−Lemma.xml",
                "utf-8");
    }*/

    //Méthode utilisé dans lire_Corpus pour générer les fichier XML pour les stems
     public static void AlKhalil_XML_Stem(File file, String pth) {
        String str;
        String new_file;
        str= file.getName();
        new_file=str.replace(".txt","");
        net.oujda_nlp_team.ADATAnalyzer.getInstance().processLightStemmer(file.getPath(),
                "utf-8",
                pth+"/"+new_file+"−OUT−Stem.xml",
                "utf-8");
    }


    //Génération de tous les fichiers XML avec la même arboresence du dossier Corpus en utilisant la méthode AlKhalil_XML_lemme(Stem)
    public  static void lire_Corpus(File file){
        String str;
        String [] T;
        File f1;
        File f2;
        for (File f : file.listFiles()) {
            if (!f.isDirectory()) {
                str= String.valueOf(f.getParentFile()).replace("src\\main\\java\\detect\\theme\\LesCorpus\\Corpus\\Corpus_test\\","");
                T=str.split(",");
                f1=new File("src\\main\\java\\detect\\theme\\LesCorpus\\Corpus_Stem_XML\\Corpus_test_XML\\"+T[0]);
                if(!f1.exists()){
                    f1.mkdir();
                }else {
                    f2=new File("src\\main\\java\\detect\\theme\\LesCorpus\\Corpus_Stem_XML\\Corpus_test_XML\\"+T[0]);
                    if (!f2.exists()){
                        f2.mkdir();
                    }else {
                        AlKhalil_XML_Stem(f, String.valueOf(f2));
                    }
                }

            }else {
                lire_Corpus(f);
            }
        }
    }

    //generer les fichiers des themes, ou chaque fichier theme contient son vecteur mots
    public static void rep_Vecteur_theme(File file) throws IOException {
        String str;
        for (File f : file.listFiles()) {
            if (f.isDirectory()) {
                str = f.getPath().replace("src\\main\\java\\detect\\theme\\LesCorpus\\Corpus\\Corpus\\", "");
                File f1 = new File("src\\main\\java\\detect\\theme\\LesCorpus\\Corpus_Stem_XML\\Vecteur_theme\\" + str + ".txt");
                f1.createNewFile();

            }
        }
    }


}
