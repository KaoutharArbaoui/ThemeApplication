package detect.theme.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class Prétraitement {

    public Prétraitement() throws IOException {
    }

    // fonction qui retourne une liste des mots vide à partir des fichiers
    public List<String> mot_vide() throws IOException {
        File Vide = new File("src\\main\\java\\detect\\theme\\MotsVide\\stopwords-ar.txt");
        BufferedReader br =new BufferedReader(new FileReader(Vide));
        List<String> list_vide=new ArrayList<>();
        String mot_vide;
        while ((mot_vide=br.readLine())!=null){
            mot_vide=mot_vide.replace(" ","");
            list_vide.add(mot_vide);
        }
        return list_vide;
    }

    // list mots vide
    public List<String> listMotsVide=new ArrayList<>(this.mot_vide());

    // fonction qui remplace les carac spéc ...
    public String  remp(String str){
        str=str.replace("...","");
        str=str.replace("","");
        str=str.replace("","");
        str=str.replace("|","");
        str=str.replace("(","");
        str=str.replace(")","");
        str=str.replace("[","");
        str=str.replace("]","");
        str=str.replace("{","");
        str=str.replace("}","");
        str=str.replace(".","");
        str=str.replace(";","");
        str=str.replace(",","");
        str=str.replace("?","");
        str=str.replace("!","");
        str=str.replace("=","");
        str=str.replace("-","");
        str=str.replace("+","");
        str=str.replace(":","");
        str=str.replace("\"","");
        str=str.replace("\'","");
        str=str.replace("، ","");
        str=str.replace("؛","");
        str=str.replace("‘","");
        str=str.replace("ـ","");
        str=str.replace("\t"," ");
        str=str.replace("<<","");
        str=str.replace(">>","");
        str=str.replace("&","");
        str=str.replace("#","");
        str=str.replace("*","");
        str=str.replace("؟","");
        str=str.replace("~","");
        str=str.replace("$","");
        str=str.replace("^","");
        str=str.replace("%","");
        str=str.replace("،","");
        str=str.replace("’","");
        str=str.replace("‘","");
        str=str.replace("؛","");
        str=str.replace("-","");
        str=str.replace("(","");
        str=str.replace("ǃ","");
        str=str.replace("«","");
        str=str.replace(" \""," ");
        str=str.replace("\\","");
        str=str.replace("\n"," ");
        str=str.replace("\r"," ");
        str=str.replace("\t"," ");

        if(str.matches("^.*[0-9].*$") || str.matches("^.*[A-Za-z].*$")){
            str="";
        }
        return str;
    }

    // vérifier qu'un mot ne contient pas les caractére mentionné dans remp
    public boolean  Si_word(String mot) throws IOException {
        String str = remp(mot);
        if(!str.equals("") && !listMotsVide.contains(str)){
            return true;
        }
        return false;
    }

    // pour recupére seulement le mot, le lemme ou le stem du balise
    public String Filtre_Mot_lemme(String ligne){
        String [] str;
        ligne=ligne.replace(">",">,");
        ligne=ligne.replace("<",",<");
        str=ligne.split(",");
        return str[2];
    }

    // les mots d'un fichier avec répétition, il faut changer la balise word avec <lemma> si vous travailler avec les lemmes, même chose pour les stems
    public  List<String> List_MotAvecRép(File fichier_xml) throws IOException {
        BufferedReader br =new BufferedReader(new FileReader(fichier_xml));
        List<String> word =new ArrayList<>();
        String ligne;
        String str;
        while ((ligne= br.readLine())!=null){
            if(ligne.matches("^.*<word>.*$")){
                str=Filtre_Mot_lemme(ligne);
                if(Si_word(str)) {
                    word.add(Filtre_Mot_lemme(ligne));
                }
            }
        }
        return  word;
    }

    // pour avoir une liste de tous les mots qui existent dans les fichiers (XML) d'un document théme du répertoire Corpus_XML
    public List<String> Words_theme(File doc_theme) throws IOException {
        List<String> list=new ArrayList<>();
        for (File f : doc_theme.listFiles()){
            if(!f.isDirectory()){
                list.addAll(List_MotAvecRép(f));
            }
        }
        return  list;
    }

    public List<String> Words_Doc(File doc_theme) throws IOException {
        List<String> list=new ArrayList<>();
        BufferedReader obj = new BufferedReader(new FileReader(doc_theme));
        String strng;
        String str;
        while ((strng = obj.readLine()) != null) {
            if(strng.matches("^.*<word>.*$")){
                str=Filtre_Mot_lemme(strng);
                if(Si_word(str)) {
                    list.add(Filtre_Mot_lemme(strng));
                }
            }
        }
        return  list;
    }

    // pour avoir une list de tous le mots du répertoire Corpus_XML
    public List<String> Words_Corpus(File Corpus_XML) throws IOException {
        List<String> list =new ArrayList<>();
        for (File f : Corpus_XML.listFiles()){
            if(f.isDirectory()){
                list.addAll(Words_theme(f));
            }
        }
        return  list;
    }

    // retourner une liste qui contient les mots et leur frequence dans tout le repertoire Corpus_XML
    public  List<Nombre_mots> mot_Frequ(File Corpus_xml) throws IOException {
        List<String> tous_les_mots=new ArrayList<>(Words_Corpus(Corpus_xml));
        List<String> mots_sans_repetition =new ArrayList<>(new HashSet<>(tous_les_mots));
        List<Nombre_mots> list_mot_frequent=new ArrayList<>();

        for (int i=0;i<mots_sans_repetition.size();i++){
            list_mot_frequent.add(new Nombre_mots(mots_sans_repetition.get(i), Collections.frequency(tous_les_mots,mots_sans_repetition.get(i))));
        }
        Nombre_mots mf =new Nombre_mots();
        mf.Sort_nombre_mots(list_mot_frequent);
        // System.out.println(list_mot_frequent);
        return list_mot_frequent;
    }

    //ajouter la liste des mots et leur frequence dans un fichier text créé en avance
    public void Frenquent_Mot(File Corpus_XML, File index_Frequence) throws IOException {
        List<Nombre_mots> list =new ArrayList<>(mot_Frequ(Corpus_XML));
        if(index_Frequence.exists()){
            FileWriter f = new FileWriter(index_Frequence);
            for (int i=0;i<list.size();i++){
                System.out.println(list.get(i).mots);
                f.write(list.get(i).mots+","+list.get(i).nombre+"\n");
            }
            f.close();
        }
    }

    //Générer une liste à partir d'un fichier text (mots,fréq)
    public  static List<Nombre_mots> geneListFich(File Fréq_mots) throws IOException {
        List<Nombre_mots> l= new ArrayList<>();
        BufferedReader obj = new BufferedReader(new FileReader(Fréq_mots));
        String strng;
        while ((strng = obj.readLine()) != null) {
            String[] s;
            s = strng.split(",");
            int t=Integer.parseInt(s[1]);
            l.add(new Nombre_mots(s[0],t));
        }
        return l;
    }

    //générer une liste a partir d'un fichier texte
    public List <String> filetolist(File f) throws IOException {
        List<String> listeMots = new ArrayList<String>();
        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);
        String ligne;
        while ((ligne = br.readLine()) != null) {
            String[] mots = ligne.split(" ");

            for (String mot : mots) {
                listeMots.add(mot);
            }
        }
        return listeMots;
    }
}
