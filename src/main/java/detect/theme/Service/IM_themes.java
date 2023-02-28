package detect.theme.Service;

import java.util.Collections;
import java.util.List;

public class IM_themes {
    float im;
    String theme;

    public IM_themes(float im, String theme) {
        this.im = im;
        this.theme = theme;
    }
    public IM_themes(){

    }


    public float getIm() {
        return im;
    }

    public String getTheme() {
        return theme;
    }



    public void setIm(float im) {
        this.im = im;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }
}
