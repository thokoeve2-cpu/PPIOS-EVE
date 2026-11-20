package models;

public class BookFormat {
    private String formatName;
    private double weight;
    private double height;
    private double width;
    private boolean digital;

    public BookFormat(String formatName, double weight,
                      double height, double width,
                      boolean digital) {
        this.formatName = formatName;
        this.weight = weight;
        this.height = height;
        this.width = width;
        this.digital = digital;
    }

    public double calculateVolume() {
        return height * width * 2.0;
    }

    public boolean isDigital() {
        return digital;
    }

    public double calculateShippingWeight() {
        return digital ? 0 : weight;
    }

    public void updateDimensions(double height, double width) {
        if (height > 0 && width > 0) {
            this.height = height;
            this.width = width;
        }
    }

    public void displayFormat() {
        System.out.println("Format: " + formatName);
        System.out.println("Weight: " + weight);
        System.out.println("Digital: " + digital);
    }
    public String getFormatName() {
    return formatName;
}
}

