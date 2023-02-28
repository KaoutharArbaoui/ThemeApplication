package detect.theme.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Calcul {
    public Calcul() {
    }


    //une fonction qui retourne le nombre des fichiers contenant dans un rep
    public static float number_of_files(File dir){
        File[] f = dir.listFiles();
        float  x = 0;
        assert f != null;
        for (File file : f) {
            if (file.isFile()) {
                x = x + 1;
            } else if (file.isDirectory()) {
                x = x + number_of_files(file);
            }
        }
        return x;
    }

    //la fonction qui determine la probablite d’apparition du mot w dans le corpus C
    public static float P_w(String w, File freq_mots) throws IOException {
        float p;
        Prétraitement prétraitement = new Prétraitement();
        List<Nombre_mots> mots_freq =prétraitement.geneListFich(freq_mots);
        float card_C =0;
        float freq_w = 0;
        //calculer le cardinal du corpus en terme de mots
        for (Nombre_mots nombre_mots : mots_freq) {
            card_C = card_C + nombre_mots.nombre;
        }
        //retourner la freq de chaque mot dans le corpus
        for (Nombre_mots nombre_mots : mots_freq) {
            if (nombre_mots.mots.equals(w)) {
                freq_w = nombre_mots.nombre;
                break;
            }
        }
        p= freq_w/card_C;
        return p;
    }

    //la fonction qui retourne le nombre des docs dans C appartenant au theme qui le path "path" et contient le mot w
    public  static float n_wt(String w, String path) throws IOException {
        float result =0;
        File theme = new File(path);
        File[] themes_files = theme.listFiles();
        Prétraitement prétraitement = new Prétraitement();
        List <String> words_themes;
        assert themes_files != null;
        for (File themes_file : themes_files) {
            if (themes_file.isFile()) {
                words_themes = prétraitement.Words_Doc(themes_file);
                if (words_themes.contains(w)) result++;
            } else if (themes_file.isDirectory()) {
                System.out.println(themes_file.getPath());
                result = result + n_wt(w, themes_file.getPath());
            }

        }
        return result;
    }

    //la fonction qui calcule l'info mutuelle, ses arguments sont le mot et le path du corpus des themes
    public static List<IM_themes> IM(String w, File corpus_xml, File freq_mots) throws IOException {
        List <IM_themes> result = new ArrayList<>(); //la liste des info mutuelle de chaque theme avec un mot donnée
        float p_w = P_w(w,freq_mots); // la proba d’apparition du mot w dans le corpus C, le path est incorrect
        float P_t;
        float m = number_of_files(corpus_xml); //le nombre total des documents
        //lire les corpus
        File[] themes_files_xml = corpus_xml.listFiles();
        for(int i = 0; i< Objects.requireNonNull(themes_files_xml).length; i++){
            float nwt = n_wt(w,themes_files_xml[i].getPath());
            float n_t = number_of_files(themes_files_xml[i]); //le nombre des documents pour chaque theme i
            P_t = n_t/m; //la proba d’apparition du thème tj dans le corpus C.
            float P_w_t= nwt/n_t;
            float im = (float) Math.log(P_w_t/(p_w*P_t));
            result.add(new IM_themes(im,themes_files_xml[i].getName()));
        }
        return result;
    }

    //calcule l'information mutuelle maximale a partir d'une liste
    public static IM_themes max_im(List<IM_themes> im_themes){
        IM_themes max = im_themes.get(0);
        for (IM_themes im_theme : im_themes) {
            if (im_theme.getIm() > max.getIm()) max = im_theme;
        }
        return max;
    }

    //generer les fichiers contenant les vecteurs de chaque theme
    public static void generate_vectors(File Corpus_xml, File freq_mots, File vecteur_theme, int seuil) throws IOException {

        File [] vec_theme_tab = vecteur_theme.listFiles();
        List<Nombre_mots> mots_freq =Prétraitement.geneListFich(freq_mots);
        for (Nombre_mots nombre_mots : mots_freq) {
            if(nombre_mots.nombre >=seuil){
            List<IM_themes> im_mot = IM(nombre_mots.mots, Corpus_xml, freq_mots);
            IM_themes max_im = max_im(im_mot);
            //System.out.println("mot " + nombre_mots.mots + " max im " + max_im.getIm() + " le theme " + max_im.getTheme());
            assert vec_theme_tab != null;
            for (File file : vec_theme_tab) {
                FileWriter f = new FileWriter(file, true);
                BufferedWriter bw = new BufferedWriter(f);
                String name_f = file.getName().replace(".txt", "");
                if (max_im.getTheme().equals(name_f)) {
                    bw.write(nombre_mots.mots + "\n");
                    bw.close();
                    break;
                }

            }
        }else break;
    }
    }

    // la fonction qui calcule la simlarité
    public static List <Sim_theme> Sim(File theme, File doc_test_xml) throws IOException {
        List <Sim_theme> sim = new ArrayList<>();
        Prétraitement pr = new Prétraitement();
        File [] th = theme.listFiles();
        List <String> liste_doc;
        liste_doc = pr.List_MotAvecRép(doc_test_xml);
        assert th != null;
        for (File file : th) {
            List<String> liste_th;
            liste_th = pr.filetolist(file);
            float inter = 0;
            float union = liste_doc.size() + liste_th.size();
            for (String s : liste_th) {
                if (liste_doc.contains(s)) {
                    inter++;
                }
            }
            //System.out.println("inter"+inter);
            sim.add(new Sim_theme(file.getName().replace(".txt",""),inter/union));
        }
        return sim;
    }

    //la fonction qui calcule le max des sim
    public static Sim_theme max_sim(List<Sim_theme> sim_themes){
        Sim_theme max = sim_themes.get(0);
        for (Sim_theme sim_theme : sim_themes) {
            if (sim_theme.getSim() > max.getSim()) max = sim_theme;
        }
        return max;
    }

    //la fonction qui affecte le theme
    public  static String affecter_theme(File theme, File doc) throws IOException {
        String resultat;
        List <Sim_theme> liste = Sim(theme,doc);
        Sim_theme sim_max = max_sim(liste);
        resultat = sim_max.getTheme();
        return resultat;
    }
}

