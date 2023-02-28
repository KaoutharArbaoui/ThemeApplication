package detect.theme.Service;

public class Sim_theme {
    String theme;
    Float Sim;

    public Sim_theme(String theme, Float sim) {
        this.theme = theme;
        Sim = sim;
    }

    public String getTheme() {
        return theme;
    }
    public Float getSim() {
        return Sim;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }
    public void setSim(Float sim) {
        Sim = sim;
    }
}
