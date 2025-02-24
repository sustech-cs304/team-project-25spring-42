package sustech.cs304.IDE;

import eu.mihosoft.monacofx.MonacoFX;


public class EditorController {

    private MonacoFX monacoFX;

    public EditorController() {
        monacoFX = new MonacoFX();
    }

    public MonacoFX getMonacoFX() {
        return monacoFX;
    }

    public void setMonacoFX(MonacoFX monacoFX) {
        this.monacoFX = monacoFX;
    }

}
