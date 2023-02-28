package detect.theme.Service;

import java.io.File;
import java.io.IOException;

public class Indi_Perf {
    static  int[][] m = new int[5][5];

    public static int [][] matriceconfusion(File corpus_Test, File vecteur_theme) throws IOException {
        File[] corp_test_tab = corpus_Test.listFiles();
        assert corp_test_tab != null;
        for (File file : corp_test_tab) {
            if (!file.isDirectory()) {
                String theme_obs = Calcul.affecter_theme(vecteur_theme, file);
                String theme_réel = file.getParentFile().getName();
                switch (theme_réel) {
                    case "culture":
                        switch (theme_obs) {
                            case "culture" -> m[0][0]++;
                            case "economie" -> m[0][1]++;
                            case "politique" -> m[0][2]++;
                            case "sciences_technologie" -> m[0][3]++;
                            default -> m[0][4]++;
                        }
                        break;
                    case "economie":
                        switch (theme_obs) {
                            case "culture" -> m[1][0]++;
                            case "economie" -> m[1][1]++;
                            case "politique" -> m[1][2]++;
                            case "sciences_technologie" -> m[1][3]++;
                            default -> m[1][4]++;
                        }
                        break;
                    case "politique":
                        switch (theme_obs) {
                            case "culture" -> m[2][0]++;
                            case "economie" -> m[2][1]++;
                            case "politique" -> m[2][2]++;
                            case "sciences_technologie" -> m[2][3]++;
                            default -> m[2][4]++;
                        }
                        break;
                    case "sciences_technologie":
                        switch (theme_obs) {
                            case "culture" -> m[3][0]++;
                            case "economie" -> m[3][1]++;
                            case "politique" -> m[3][2]++;
                            case "sciences_technologie" -> m[3][3]++;
                            default -> m[3][4]++;
                        }
                        break;
                    default:
                        switch (theme_obs) {
                            case "culture" -> m[4][0]++;
                            case "economie" -> m[4][1]++;
                            case "politique" -> m[4][2]++;
                            case "sciences_technologie" -> m[4][3]++;
                            default -> m[4][4]++;
                        }
                        break;
                }
            } else matriceconfusion(file, vecteur_theme);
        }

        return m;
    }

    public static float Exactitude(int [][] mc , int n) {
        float r = 0;
        for(int i=0;i<mc.length;i++) {
            r +=mc[i][i];
        }
        r=r/n;
        return r;
    }

    public static double PrecisionClasse(int [][] mc, int n) {
        double p=0;
        for (int[] ints : mc) {
            p += ints[n];
        }
        p = mc[n][n]/p;
        return p;
    }
    public static double PrecisionGlobal(int [][] mc) {
        double P=0;
        for(int i=0;i<mc.length;i++) {
            P +=PrecisionClasse(mc,i);
        }
        P = P/mc.length;
        return P;

    }

    public static double RappelClasse(int [][] mc, int n) {
        double r=0;
        for(int i=0;i<mc.length;i++) {
            r +=mc[n][i];
        }
        r = mc[n][n]/r;
        return r;
    }
    public static double RappelGlobal(int [][] mc) {
        double R=0;
        for(int i=0;i<mc.length;i++) {
            R +=RappelClasse(mc,i);
        }
        R = R/mc.length;
        return R;

    }

    public static double FmesureClasse(int [][] mc,int i) {
        double f;
        f=(2*PrecisionClasse(mc,i)*RappelClasse(mc,i))/(PrecisionClasse(mc,i)+RappelClasse(mc,i));
        return f;

    }
    public static double FmesureGlobal(int [][] mc) {
        double f;
        f=(2*PrecisionGlobal(mc)*RappelGlobal(mc))/(PrecisionGlobal(mc)+RappelGlobal(mc));
        return f;

    }

}
