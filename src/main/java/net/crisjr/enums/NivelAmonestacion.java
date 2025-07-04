package net.crisjr.enums;

public enum NivelAmonestacion {
    PRIMER_TIEMPO("Primer tiempo"),
    SEGUNDO_TIEMPO("Segundo tiempo"),
    TERCER_TIEMPO("Tercer tiempo"),
    CUARTO_TIEMPO("Cuarto tiempo");

    private final String label;

    NivelAmonestacion(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static NivelAmonestacion fromLabel(String label) {
        for (NivelAmonestacion nivel : values()) {
            if (nivel.label.equalsIgnoreCase(label)) {
                return nivel;
            }
        }
        throw new IllegalArgumentException("Nivel inválido: " + label);
    }
}
